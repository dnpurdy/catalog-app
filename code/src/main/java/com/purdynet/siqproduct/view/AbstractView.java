package com.purdynet.siqproduct.view;

import com.purdynet.siqproduct.model.AbstractCoreItem;
import com.purdynet.siqproduct.model.MissingItem;
import com.purdynet.siqproduct.model.ProductProgress;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

public class AbstractView {
    public String wrapHtmlBody(String content) {
        return "<html><head><link href=\"/style.css\" rel=\"stylesheet\"/></head><body>"+content+"</body></html>";
    }

    public String toHTMLTableFromProgress(List<ProductProgress> productProgressList) {
        StringBuilder ret = new StringBuilder("<table class=\"data\">");
        ret.append("<tr><th>Item Id</th><th>Manufacturer</th><th>Retailer Item Id</th><th>Revenue %</th><th>Description</th><th>Reatiler Dept</th><th>NACS Category</th><th>Complete?</th>" +
                "<th>UPC?</th><th>Complete Revenue %</th><th>Incomplete Revenue %</th><th>Complete Department Rev</th><th>Incomplete Department Rev</th><th>Last Date</th></tr>");
        productProgressList.stream()
                .forEach(pp -> ret.append("<tr>")
                        .append("<td><a href=\"").append(missingLink(pp)).append("\">").append(pp.getItemId()).append("</a></td>")
                        .append(td(pp.getManufacturer()))
                        .append(td(pp.getRetailerItemId()))
                        .append(tdRight(percentFmt(pp.getRevPortion(), 6)))
                        .append(td(pp.getDescription()))
                        .append(td(pp.getRetailerDept()))
                        .append(td(pp.getNacsCategory()))
                        .append(td(pp.getComplete()))
                        .append(td(pp.getIsUpc()))
                        .append(tdRight(percentFmt(pp.getCompleteRevenue(), 6)))
                        .append(tdRight(percentFmt(pp.getIncompleteRevenue(), 6)))
                        .append(tdRight(percentFmt(pp.getCompleteDeptRevenue(), 6)))
                        .append(tdRight(percentFmt(pp.getIncompleteDeptRevenue(), 6)))
                        .append(td(pp.getLastDate().toString()))
                        .append("</tr>"));
        ret.append("</table>");
        return ret.toString();
    }

    public String toHTMLTableFromMising(List<MissingItem> missingItems) {
        StringBuilder ret = new StringBuilder("<table class=\"data\">");
        ret.append("<tr><th>ItemId</th><th>ProjectId</th><th>NumProjects</th><th>Manufacturer</th><th>Description</th><th>LastDate</th><th>TotalRev</th><th>% TotalRev</th></tr>");
        missingItems.stream()
                .forEach(mi -> ret.append("<tr>")
                        .append("<td><a href=\"").append(missingLink(mi)).append("\">").append(mi.getItemId()).append("</a></td>")
                        .append(td(mi.getProjectId()))
                        .append(td(mi.getNumProjects().toString()))
                        .append(td(mi.getManufacturer()))
                        .append(td(mi.getDescription()))
                        .append(td(mi.getLastDate().toString()))
                        .append(tdRight(currencyFmt(mi.getTotalRevenue())))
                        .append(tdRight(percentFmt(mi.getPercentTotalRevenue(), 6)))
                        .append("</tr>"));
        ret.append("</table>");
        return ret.toString();
    }

    public String missingLink(AbstractCoreItem item) {
        return "/edit?"+item.toQueryParams();
    }

    public String td(String val) {
        return td(val,"");
    }

    public String tdRight(String val) {
        return td(val,"align=\"right\"");
    }

    public String td(String val, String t) {
        return "<td "+t+">"+val+"</td>";
    }

    public String percentFmt(BigDecimal n) {
        return percentFmt(n,3);
    }

    public String percentFmt(BigDecimal n, Integer numDigits) {
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(numDigits);
        if (n==null) {
            return "";
        } else {
            return format.format(n);
        }
    }

    public String currencyFmt(BigDecimal n) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);
        if (n == null) {
            return "";
        } else {
            return format.format(n);
        }
    }
}
