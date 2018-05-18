package com.purdynet.siqproduct.util;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.MissingItem;
import com.purdynet.siqproduct.model.ProductProgress;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

public class HTMLUtils {
    public static String wrapHtmlBody(String content) {
        return "<html><head><link href=\"/style.css\" rel=\"stylesheet\"/></head><body>"+content+"</body></html>";
    }

    public static String toHTMLTableFromProgress(List<ProductProgress> productProgressList) {
        StringBuilder ret = new StringBuilder("<table class=\"data\">");
        ret.append("<tr><th>Item Id</th><th>Manufacturer</th><th>Retailer Item Id</th><th>Revenue %</th><th>Description</th><th>Reatiler Dept</th><th>NACS Category</th><th>Complete?</th>" +
                "<th>UPC?</th><th>Complete Revenue %</th><th>Incomplete Revenue %</th><th>Complete Department Rev</th><th>Incomplete Department Rev</th><th>Last Date</th></tr>");
        productProgressList.stream()
                .forEach(pp -> ret.append("<tr>")
                        .append(td(pp.getItemId()))
                        .append(td(pp.getManufacturer()))
                        .append("<td>").append(pp.getRetailerItemId()).append("</td>")
                        .append("<td align=\"right\">").append(percentFmt(pp.getRevPortion(), 6)).append("</td>")
                        .append("<td>").append(pp.getDescription()).append("</td>")
                        .append("<td>").append(pp.getRetailerDept()).append("</td>")
                        .append("<td>").append(pp.getNacsCategory()).append("</td>")
                        .append("<td>").append(pp.getComplete()).append("</td>")
                        .append("<td>").append(pp.getIsUpc()).append("</td>")
                        .append("<td align=\"right\">").append(percentFmt(pp.getCompleteRevenue(), 6)).append("</td>")
                        .append("<td align=\"right\">").append(percentFmt(pp.getIncompleteRevenue(), 6)).append("</td>")
                        .append("<td align=\"right\">").append(percentFmt(pp.getCompleteDeptRevenue(), 6)).append("</td>")
                        .append("<td align=\"right\">").append(percentFmt(pp.getIncompleteDeptRevenue(), 6)).append("</td>")
                        .append(td(pp.getLastDate().toString()))
                        .append("</tr>"));
        ret.append("</table>");
        return ret.toString();
    }

    public static String td(String val) {
        return td(val,"");
    }

    public static String td(String val, String t) {
        return "<td "+t+">"+val+"</td>";
    }

    public static String toHTMLTableFromMising(List<MissingItem> missingItems) {
        StringBuilder ret = new StringBuilder("<table class=\"data\">");
        ret.append("<tr><th>ItemId</th><th>ProjectId</th><th>NumProjects</th><th>Manufacturer</th><th>Description</th><th>LastDate</th><th>TotalRev</th><th>% TotalRev</th></tr>");
        missingItems.stream()
                .forEach(mi -> ret.append("<tr>")
                        .append(td(mi.getItemId()))
                        .append(td(mi.getProjectId()))
                        .append(td(mi.getNumProjects().toString()))
                        .append(td(mi.getManufacturer()))
                        .append(td(mi.getDescription()))
                        .append(td(mi.getLastDate().toString()))
                        .append(td(currencyFmt(mi.getTotalRevenue()), "align=\"right\""))
                        .append(td(percentFmt(mi.getPercentTotalRevenue(), 6),"align=\"right\""))
                .append("</tr>"));
        ret.append("</table>");
        return ret.toString();
    }

    public static String percentFmt(BigDecimal n) {
        return percentFmt(n,3);
    }

    public static String percentFmt(BigDecimal n, Integer numDigits) {
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(numDigits);
        if (n==null) {
            return "";
        } else {
            return format.format(n);
        }
    }

    public static String currencyFmt(BigDecimal n) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);
        if (n == null) {
            return "";
        } else {
            return format.format(n);
        }
    }
}
