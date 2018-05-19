package com.purdynet.siqproduct.view;

import com.purdynet.siqproduct.model.EditItem;
import com.purdynet.siqproduct.model.NacsCategories;
import com.purdynet.siqproduct.retailer.Retailer;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RootView extends AbstractView {

    public String summaryPage(List<Retailer> retailers) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h1>SwiftIQ Product Catalog App</h1>");

        sb.append("<h2>Catalog Information</h2>");
        sb.append("<table width=\"100%\">");
        sb.append("<tr>").append(td(catalogExplorer())).append(td(categoryTable())).append("</tr>");
        sb.append("</table>");

        sb.append("<h2>Retailer Information</h2>");
        sb.append("<table width=\"100%\">");
        sb.append("<tr><td>").append(retailersTable(retailers)).append("</td><td>").append(summaryTable(retailers)).append("</td></tr>");
        sb.append("<tr><td>").append(missingTable(retailers)).append("</td><td>").append(detailTable(retailers)).append("</td></tr>");
        sb.append("</table>");

        return sb.toString();
    }

    private String catalogExplorer() {
        StringBuilder sb = new StringBuilder("<h3>Catalog Explorer</h3>");
        sb.append("<a href=\"/catalog\">/catalog</a><br/>");
        sb.append("<a href=\"/catalog-ag\">/catalog-ag</a>");
        return sb.toString();
    }

    private String categoryTable() {
        StringBuilder sb = new StringBuilder("<h3>Product Type Analysis</h3><table><tr><th>Name</th><th>Link</th><th>AG Link</th></tr>");
        sb.append("<tr>").append(td("All Missing")).append(td("<a href=\"/missing\">/missing</a>")).append(td("<a href=\"/missing-ag\">/missing-ag</a>")).append("</tr>");
        sb.append("<tr>").append(td("Beer Missing")).append(td("<a href=\"/missing-beer\">/missing-beer</a>")).append(td("<a href=\"/missing-ag-beer\">/missing-ag-beer</a>")).append("</tr>");
        sb.append("<tr>").append(td("Beverage Missing")).append(td("<a href=\"/missing-beverage\">/missing-beverage</a>")).append(td("<a href=\"/missing-ag-beverage\">/missing-ag-beverage</a>")).append("</tr>");
        sb.append("<tr>").append(td("Tobacco Missing")).append(td("<a href=\"/missing-tobacco\">/missing-tobacco</a>")).append(td("<a href=\"/missing-ag-tobacco\">/missing-ag-tobacco</a>")).append("</tr>");
        sb.append("</table>");
        return sb.toString();
    }

    private String summaryTable(List<Retailer> retailers) {
        StringBuilder sb = new StringBuilder("<h3>Summaries</h3><table>");

        sb.append("<tr><th>Name</th><th>Summary Link</th></tr>");
        retailers.forEach(r -> sb.append("<tr>")
                .append(td(r.name()))
                .append(td(String.format("<a href=\"/retailer/%s/summary\">/retailer/%s/summary</a>", r.projectId(), r.projectId())))
                .append("</tr>"));
        sb.append("</table>");
        return sb.toString();
    }

    private String missingTable(List<Retailer> retailers) {
        StringBuilder sb = new StringBuilder("<h3>Missing Items</h3>");

        sb.append("<table><tr><th>Name</th><th>Missing Link</th><th>Missing AG Link</th></tr>");
        retailers.forEach(r -> sb.append("<tr>")
                .append(td(r.name()))
                .append(td(String.format("<a href=\"/retailer/%s/missing\">/retailer/%s/missing</a>", r.projectId(), r.projectId())))
                .append(td(String.format("<a href=\"/retailer/%s/missing-ag\">/retailer/%s/missing-ag</a>", r.projectId(), r.projectId())))
                .append("</tr>"));
        sb.append("</table>");
        return sb.toString();
    }

    private String detailTable(List<Retailer> retailers) {
        StringBuilder sb = new StringBuilder("<h3>All Items/Detail</h3>");

        sb.append("<table><tr><th>Name</th><th>Detail Link</th><th>Detail AG Link</th></tr>");
        retailers.forEach(r -> sb.append("<tr>")
                .append(td(r.name()))
                .append(td(String.format("<a href=\"/retailer/%s/detail\">/retailer/%s/detail</a>", r.projectId(), r.projectId())))
                .append(td(String.format("<a href=\"/retailer/%s/detail-ag\">/retailer/%s/detail-ag</a>", r.projectId(), r.projectId())))
                .append("</tr>"));
        sb.append("</table>");
        return sb.toString();
    }

    private String retailersTable(List<Retailer> retailers) {
        StringBuilder sb = new StringBuilder("<h3>Retailers</h3><table><tr><th>Name</th></tr>");
        retailers.forEach(r -> sb.append("<tr><td>").append(r.name()).append("</td></tr>"));
        sb.append("</table>");
        return sb.toString();
    }
}
