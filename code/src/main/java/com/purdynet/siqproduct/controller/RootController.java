package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.retailer.Retailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RootController {

    private final List<Retailer> retailers;

    @Autowired
    public RootController(List<Retailer> retailers) {
        this.retailers = retailers;
    }

    @GetMapping("/")
    public String hello() {
        StringBuilder sb = new StringBuilder("<html><body><head><style>h3 {padding-top:30px;}</style></head><table width=\"100%\">");

        sb.append("<tr><td>").append(retailersTable()).append("</td><td>").append(summaryTable()).append("</td></tr>");
        sb.append("<tr><td>").append(missingTable()).append("</td><td>").append(detailTable()).append("</td></tr>");
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

    private String detailTable() {
        StringBuilder sb = new StringBuilder("<h3>All Items/Detail</h3><table><tr><th>Name</th><th>Summary Link</th></tr>");
        retailers.forEach(r -> sb.append("<tr><td>").append(r.name()).append("</td><td>")
                .append(String.format("<a href=\"/retailer/%s/detail\">/retailer/%s/detail</a>", r.projectId(), r.projectId())).append("</td></tr>"));
        sb.append("</table>");
        return sb.toString();
    }

    private String missingTable() {
        StringBuilder sb = new StringBuilder("<h3>Missing Items</h3><table><tr><th>Name</th><th>Summary Link</th></tr>");
        retailers.forEach(r -> sb.append("<tr><td>").append(r.name()).append("</td><td>")
                .append(String.format("<a href=\"/retailer/%s/missing\">/retailer/%s/missing</a>", r.projectId(), r.projectId())).append("</td></tr>"));
        sb.append("</table>");
        return sb.toString();
    }

    private String summaryTable() {
        StringBuilder sb = new StringBuilder("<h3>Summaries</h3><table><tr><th>Name</th><th>Summary Link</th></tr>");
        retailers.forEach(r -> sb.append("<tr><td>").append(r.name()).append("</td><td>")
                .append(String.format("<a href=\"/retailer/%s/summary\">/retailer/%s/summary</a>", r.projectId(), r.projectId())).append("</td></tr>"));
        sb.append("</table>");
        return sb.toString();
    }

    private String retailersTable() {
        StringBuilder sb = new StringBuilder("<h3>Retailers</h3><table><tr><th>Name</th></tr>");
        retailers.forEach(r -> sb.append("<tr><td>").append(r.name()).append("</td></tr>"));
        sb.append("</table>");
        return sb.toString();
    }
}
