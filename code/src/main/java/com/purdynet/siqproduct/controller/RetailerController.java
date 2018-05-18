package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.biqquery.BigqueryUtils;
import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.ProductProgress;
import com.purdynet.siqproduct.retailer.Retailer;
import com.purdynet.siqproduct.service.RetailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.purdynet.siqproduct.biqquery.BigqueryUtils.convertTableRowToModel;
import static com.purdynet.siqproduct.biqquery.BigqueryUtils.runQuerySync;
import static com.purdynet.siqproduct.util.HTMLUtils.*;

@RestController
public class RetailerController {

    private final List<Retailer> retailers;
    private final RetailerService retailerService;

    private final Predicate<ProductProgress> completePred = ProductProgress::isComplete;
    private final Predicate<ProductProgress> incompletePred = ProductProgress::isNotComplete;

    private final Predicate<ProductProgress> bothPred = (ProductProgress pp) -> true;
    private final Predicate<ProductProgress> upcPred = (ProductProgress pp) -> pp.getIsUpc().equals("UPC");
    private final Predicate<ProductProgress> pluPred = (ProductProgress pp) -> pp.getIsUpc().equals("PLU");

    @Autowired
    public RetailerController(List<Retailer> retailers, RetailerService retailerService) {
        this.retailers = retailers;
        this.retailerService = retailerService;
    }

    @RequestMapping(value = "/retailer/{id}/missing", produces = MediaType.TEXT_HTML_VALUE)
    public String missing(@PathVariable("id") String requestId) {
        return wrapHtmlBody(toHTMLTableFromProgress(missingJson(requestId)));
    }

    @RequestMapping(value = "/retailer/{id}/missing", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductProgress> missingJson(@PathVariable("id") String requestId) {
        return runProductProgress(requestId, " WHERE c.description IS NULL ", 1000).orElse(new ArrayList<>());
    }

    @RequestMapping(value = "/retailer/{id}/detail", produces = MediaType.TEXT_HTML_VALUE)
    public String detail(@PathVariable("id") String requestId) {
        return wrapHtmlBody(toHTMLTableFromProgress(detailJson(requestId)));
    }

    @RequestMapping(value = "/retailer/{id}/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductProgress> detailJson(@PathVariable("id") String requestId) {
        return runProductProgress(requestId, "", 1000).orElse(new ArrayList<>());
    }

    @RequestMapping(value = "/retailer/{id}/summary", produces = MediaType.TEXT_HTML_VALUE)
    public String summary(@PathVariable("id") String requestId) {
        return runProductProgress(requestId, "", null).map(productProgress -> wrapHtmlBody("<h1>Overall</h1><table>" +
                "<tr><td>COMPLETE</td><td>"+percentFmt(getTotalRevenue(productProgress, completePred, bothPred))+"</td></tr>" +
                "<tr><td>TODO</td><td> "+percentFmt(getTotalRevenue(productProgress, incompletePred, bothPred))+"</td></tr>"+
                "</table>" +
                "<h1>Breakout</h1><table>" +
                "<tr><td></td><td>UPC</td><td>PLU</td></tr>" +
                "<tr><td>COMPLETE</td><td>"+percentFmt(getTotalRevenue(productProgress, completePred, upcPred))+"</td><td>"+percentFmt(getTotalRevenue(productProgress, completePred, pluPred))+"</td></tr>" +
                "<tr><td>TODO</td><td>"+percentFmt(getTotalRevenue(productProgress, incompletePred, upcPred))+"</td><td>"+percentFmt(getTotalRevenue(productProgress, incompletePred, pluPred))+"</td></tr>" +
                "</table>" +
                "<h1>Department</h1>" + deptTable(productProgress)+
                "<h1>Top 25 Products</h1>" + top25Products(productProgress)
        )).orElse("");
    }

    private Optional<List<ProductProgress>> runProductProgress(final String requestId, final String where, final Integer limit) {
        return matchRetailer(requestId).map(projectId -> {
            BigqueryUtils bigqueryUtils = runQuerySync(retailerService.progressSql(projectId, limit, where));
            return makeProgressList(bigqueryUtils.getBqTableData());
        });
    }

    private String top25Products(List<ProductProgress> productProgress) {
        StringBuilder mTable = new StringBuilder("<table><tr><td>UPC/PLU</td><td>ITEMID</td><td>DESC</td><td>MANUF</td></tr>");
        productProgress.stream().filter(incompletePred).sorted(Comparator.comparing(ProductProgress::getRevPortion).reversed()).limit(25).forEach(pp ->
                mTable.append("<tr>")
                        .append(td(pp.getIsUpc()))
                        .append(td(pp.getItemId()))
                        .append(td(pp.getDescription()))
                        .append(td(pp.getManufacturer()))
                        .append("</tr>")
        );
        mTable.append("</table>");
        return mTable.toString();
    }

    private String deptTable(List<ProductProgress> productProgress) {
        StringBuilder mTable = new StringBuilder("<table><tr><td>Dept</td><td>COMPLETE</td><td>INCOMPLETE</td></tr>");
        productProgress.stream()
                .map(ProductProgress::getRetailerDept)
                .collect(Collectors.toSet())
                .forEach(dept -> {
                    List<ProductProgress> deptProgress = productProgress.stream().filter(pp -> pp.getRetailerDept().equals(dept)).collect(Collectors.toList());
                    mTable.append("<tr>")
                            .append(td(dept))
                            .append(td(percentFmt(getTotalRevenue(deptProgress, completePred, bothPred))))
                            .append(td(percentFmt(getTotalRevenue(deptProgress, incompletePred, bothPred))))
                            .append("</tr>");
                });
        mTable.append("</table>");
        return mTable.toString();
    }

    private BigDecimal getTotalRevenue(List<ProductProgress> productProgress, Predicate<ProductProgress> firstPred, Predicate<ProductProgress> secondPred) {
        return productProgress.stream()
                .filter(firstPred)
                .filter(secondPred)
                .map(ProductProgress::getRevPortion)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<ProductProgress> makeProgressList(BqTableData bqTableData) {
        return convertTableRowToModel(bqTableData, ProductProgress::of);
    }

    private Optional<Retailer> matchRetailer(String requestId) {
        return retailers.stream().filter(r -> r.projectId().equals(requestId)).findFirst();
    }
}
