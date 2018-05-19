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
        StringBuilder sb = new StringBuilder("<html><body><head><style>h3 {padding-top:30px;}</style></head><table width=\"100%\">");

        sb.append("<tr><td>").append(retailersTable(retailers)).append("</td><td>").append(summaryTable(retailers)).append("</td></tr>");
        sb.append("<tr><td>").append(missingTable(retailers)).append("</td><td>").append(detailTable(retailers)).append("</td></tr>");
        sb.append("<tr><td>").append(categoryTable()).append("</td></tr>");

        sb.append("</table></body></html>");
        return sb.toString();
    }

    private String categoryTable() {
        StringBuilder sb = new StringBuilder("<h3>Product Type Analysis</h3><table><tr><th>Name</th><th>Link<s/th></tr>");
        sb.append("<tr><td>").append("All Missing").append("</td><td>").append("<a href=\"/missing\">/missing</a>").append("</td></tr>");
        sb.append("<tr><td>").append("Beer Missing").append("</td><td>").append("<a href=\"/missing-beer\">/missing-beer</a>").append("</td></tr>");
        sb.append("<tr><td>").append("Beverage Missing").append("</td><td>").append("<a href=\"/missing-beverage\">/missing-beverage</a>").append("</td></tr>");
        sb.append("<tr><td>").append("Tobacco Missing").append("</td><td>").append("<a href=\"/missing-tobacco\">/missing-tobacco</a>").append("</td></tr>");
        sb.append("</table>");
        return sb.toString();
    }

    private String detailTable(List<Retailer> retailers) {
        StringBuilder sb = new StringBuilder("<h3>All Items/Detail</h3><table><tr><th>Name</th><th>Summary Link</th></tr>");
        retailers.forEach(r -> sb.append("<tr><td>").append(r.name()).append("</td><td>")
                .append(String.format("<a href=\"/retailer/%s/detail\">/retailer/%s/detail</a>", r.projectId(), r.projectId())).append("</td></tr>"));
        sb.append("</table>");
        return sb.toString();
    }

    private String missingTable(List<Retailer> retailers) {
        StringBuilder sb = new StringBuilder("<h3>Missing Items</h3><table><tr><th>Name</th><th>Summary Link</th></tr>");
        retailers.forEach(r -> sb.append("<tr><td>").append(r.name()).append("</td><td>")
                .append(String.format("<a href=\"/retailer/%s/missing\">/retailer/%s/missing</a>", r.projectId(), r.projectId())).append("</td></tr>"));
        sb.append("</table>");
        return sb.toString();
    }

    private String summaryTable(List<Retailer> retailers) {
        StringBuilder sb = new StringBuilder("<h3>Summaries</h3><table><tr><th>Name</th><th>Summary Link</th></tr>");
        retailers.forEach(r -> sb.append("<tr><td>").append(r.name()).append("</td><td>")
                .append(String.format("<a href=\"/retailer/%s/summary\">/retailer/%s/summary</a>", r.projectId(), r.projectId())).append("</td></tr>"));
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
