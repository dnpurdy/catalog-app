package com.purdynet.siqproduct.view;

import com.purdynet.siqproduct.model.MissingItem;
import com.purdynet.siqproduct.model.ProductProgress;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.purdynet.siqproduct.util.BQUtils.*;

@Component
public class RetailerView extends AbstractView {

    private final Predicate<ProductProgress> completePred = ProductProgress::isComplete;
    private final Predicate<ProductProgress> incompletePred = ProductProgress::isNotComplete;

    private final Predicate<ProductProgress> bothPred = (ProductProgress pp) -> true;
    private final Predicate<ProductProgress> upcPred = (ProductProgress pp) -> pp.getIsUpc().equals("UPC");
    private final Predicate<ProductProgress> pluPred = (ProductProgress pp) -> pp.getIsUpc().equals("PLU");

    public String makeTable(List<ProductProgress> productProgressList) {
        return wrapHtmlBody(toHTMLTableFromProgress(productProgressList));
    }

    public String makeSummary(List<ProductProgress> productProgressList) {
        return wrapHtmlBody("<h1>Overall</h1><table>" +
                "<tr><td>COMPLETE</td><td>"+percentFmt(getTotalRevenue(productProgressList, completePred, bothPred))+"</td></tr>" +
                "<tr><td>TODO</td><td> "+percentFmt(getTotalRevenue(productProgressList, incompletePred, bothPred))+"</td></tr>"+
                "</table>" +
                "<h1>Breakout</h1><table>" +
                "<tr><td></td><td>UPC</td><td>PLU</td></tr>" +
                "<tr><td>COMPLETE</td><td>"+percentFmt(getTotalRevenue(productProgressList, completePred, upcPred))+"</td><td>"+percentFmt(getTotalRevenue(productProgressList, completePred, pluPred))+"</td></tr>" +
                "<tr><td>TODO</td><td>"+percentFmt(getTotalRevenue(productProgressList, incompletePred, upcPred))+"</td><td>"+percentFmt(getTotalRevenue(productProgressList, incompletePred, pluPred))+"</td></tr>" +
                "</table>" +
                "<h1>Department</h1>" + deptTable(productProgressList)+
                "<h1>Top 25 Products</h1>" + top25Products(productProgressList));
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

    public String getCatalogAGCol() {
        return "    var columnDefs = [\n" +
                "      {headername: \"itemId\", field: \"itemId\"},\n" +
                "      {headername: \"manufacturer\", field: \"manufacturer\"},\n" +
                "      {headername: \"retailerItemId\", field: \"retailerItemId\"},\n" +
                "      {headername: \"revPortion\", field: \"revPortion\", valueFormatter: percentFormatter},\n" +
                "      {headername: \"description\", field: \"description\"},\n" +
                "      {headername: \"nacsCategory\", field: \"nacsCategory\"},\n" +
                "      {headername: \"complete\", field: \"complete\"},\n" +
                "      {headername: \"isUpc\", field: \"isUpc\"},\n" +
                "      {headername: \"completeRevenue\", field: \"completeRevenue\", valueFormatter: percentFormatter},\n" +
                "      {headername: \"incompleteRevenue\", field: \"incompleteRevenue\", valueFormatter: percentFormatter},\n" +
                "      {headername: \"completeItems\", field: \"completeItems\"},\n" +
                "      {headername: \"incompleteItems\", field: \"incompleteItems\"},\n" +
                "      {headername: \"completeDeptRevenue\", field: \"completeDeptRevenue\", valueFormatter: percentFormatter},\n" +
                "      {headername: \"incompleteDeptRevenue\", field: \"incompleteDeptRevenue\", valueFormatter: percentFormatter},\n" +
                "      {headername: \"lastDate\", field: \"lastDate\"},\n" +
                "    ];\n";
    }
}
