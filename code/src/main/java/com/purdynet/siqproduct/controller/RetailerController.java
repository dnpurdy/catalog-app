package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.biqquery.BigqueryUtils;
import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.ProductProgress;
import com.purdynet.siqproduct.retailer.Retailer;
import com.purdynet.siqproduct.service.RetailerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.purdynet.siqproduct.util.HTMLUtils.percentFmt;
import static com.purdynet.siqproduct.util.HTMLUtils.toHTMLTableFromProgress;

@RestController
public class RetailerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final List<Retailer> retailers;
    private final RetailerService retailerService;

    @Autowired
    public RetailerController(List<Retailer> retailers, RetailerService retailerService) {
        this.retailers = retailers;
        this.retailerService = retailerService;
    }

    @RequestMapping("/retailer/{id}/missing")
    public String missing(@PathVariable("id") String requestId) throws IOException {
        BigqueryUtils bigqueryUtils = new BigqueryUtils();

        Optional<Retailer> projectId = matchRetailer(requestId);
        if (projectId.isPresent()) {
            bigqueryUtils.beginQuery(retailerService.progressSql(projectId.get(), 1000, " WHERE c.description IS NULL "));

            bigqueryUtils.pollForCompletion();

            List<ProductProgress> productProgress = makeProgressList(bigqueryUtils.getBqTableData());

            return toHTMLTableFromProgress(productProgress);
        } else {
            return requestId + "not found!";
        }
    }

    @RequestMapping("/retailer/{id}/detail")
    public String detail(@PathVariable("id") String requestId) throws IOException {
        BigqueryUtils bigqueryUtils = new BigqueryUtils();

        Optional<Retailer> projectId = matchRetailer(requestId);
        if (projectId.isPresent()) {
            bigqueryUtils.beginQuery(retailerService.progressSql(projectId.get(), 1000));

            bigqueryUtils.pollForCompletion();

            List<ProductProgress> productProgress = makeProgressList(bigqueryUtils.getBqTableData());

            return toHTMLTableFromProgress(productProgress);
        } else {
            return requestId + "not found!";
        }
    }

    @RequestMapping("/retailer/{id}/summary")
    public String summary(@PathVariable("id") String requestId) throws IOException {
        BigqueryUtils bigqueryUtils = new BigqueryUtils();

        Optional<Retailer> projectId = matchRetailer(requestId);
        if (projectId.isPresent()) {
            bigqueryUtils.beginQuery(retailerService.progressSql(projectId.get()));

            bigqueryUtils.pollForCompletion();

            List<ProductProgress> productProgress = makeProgressList(bigqueryUtils.getBqTableData());

            return "<h1>Overall</h1><table>" +
                    "<tr><td>COMPLETE</td><td>"+percentFmt(getTotalRevenue(productProgress, completePred, bothPred))+"</td></tr>" +
                    "<tr><td>TODO</td><td> "+percentFmt(getTotalRevenue(productProgress, incompletePred, bothPred))+"</td></tr>"+
                    "</table>" +
                    "<h1>Breakout</h1><table>" +
                    "<tr><td></td><td>UPC</td><td>PLU</td></tr>" +
                    "<tr><td>COMPLETE</td><td>"+percentFmt(getTotalRevenue(productProgress, completePred, upcPred))+"</td><td>"+percentFmt(getTotalRevenue(productProgress, completePred, pluPred))+"</td></tr>" +
                    "<tr><td>TODO</td><td>"+percentFmt(getTotalRevenue(productProgress, incompletePred, upcPred))+"</td><td>"+percentFmt(getTotalRevenue(productProgress, incompletePred, pluPred))+"</td></tr>" +
                    "</table>" +
                    "<h1>Department</h1>" + deptTable(productProgress)+
                    "<h1>Top 25 Products</h1>" + top25Products(productProgress);
        } else {
            return requestId + "not found!";
        }
    }

    private String top25Products(List<ProductProgress> productProgress) {
        StringBuilder mTable = new StringBuilder("<table><tr><td>UPC/PLU</td><td>ITEMID</td><td>DESC</td><td>MANUF</td></tr>");
        productProgress.stream().filter(incompletePred).sorted(Comparator.comparing(ProductProgress::getRevPortion).reversed()).limit(25).forEach(pp ->
                mTable.append("<tr>")
                        .append("<td>").append(pp.getIsUpc()).append("</td>")
                        .append("<td>").append(pp.getItemId()).append("</td>")
                        .append("<td>").append(pp.getDescription()).append("</td>")
                        .append("<td>").append(pp.getManufacturer()).append("</td>")
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
                            .append("<td>").append(dept).append("</td>")
                            .append("<td>").append(percentFmt(getTotalRevenue(deptProgress, completePred, bothPred))).append("</td>")
                            .append("<td>").append(percentFmt(getTotalRevenue(deptProgress, incompletePred, bothPred))).append("</td>")
                            .append("</tr>");
                });
        mTable.append("</table>");
        return mTable.toString();
    }

    private final Predicate<ProductProgress> completePred = ProductProgress::isComplete;
    private final Predicate<ProductProgress> incompletePred = ProductProgress::isNotComplete;

    private final Predicate<ProductProgress> bothPred = (ProductProgress pp) -> true;
    private final Predicate<ProductProgress> upcPred = (ProductProgress pp) -> pp.getIsUpc().equals("UPC");
    private final Predicate<ProductProgress> pluPred = (ProductProgress pp) -> pp.getIsUpc().equals("PLU");

    private BigDecimal getTotalRevenue(List<ProductProgress> productProgress, Predicate<ProductProgress> firstPred, Predicate<ProductProgress> secondPred) {
        return productProgress.stream()
                .filter(firstPred)
                .filter(secondPred)
                .map(ProductProgress::getRevPortion)
                .reduce(BigDecimal.ZERO, (x,y) -> x.add(y));
    }

    private List<ProductProgress> makeProgressList(BqTableData bqTableData) {
        if (bqTableData != null) {
            try {
                return bqTableData.getTableRowList().stream().map(ProductProgress::of).collect(Collectors.toList());
            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    private Optional<Retailer> matchRetailer(String requestId) {
        return retailers.stream().filter(r -> r.projectId().equals(requestId)).findFirst();
    }
}
