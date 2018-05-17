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
    public static String toHTMLTable(BqTableData bqTableData) {
        StringBuilder ret = new StringBuilder("<table>");
        ret.append(toHTMLTable(bqTableData.getSchemaFieldNames()));
        bqTableData.getTableRowList().forEach(row -> ret.append(toHTMLTable(row)));
        ret.append("</table>");
        return ret.toString();
    }

    public static String toHTMLTable(TableRow tableRow) {
        StringBuilder ret = new StringBuilder("<tr>");
        tableRow.getF().stream()
                .map(f -> f.getV().toString())
                .map(val -> "<td>"+val+"</td>")
                .forEach(r -> ret.append(r));
        ret.append("</tr>");
        return ret.toString();
    }

    public static String toHTMLTable(List<TableFieldSchema> schemaFieldNames) {
        StringBuilder ret = new StringBuilder("<tr>");
        schemaFieldNames.stream()
                .map(tfs -> "<th>"+tfs.getName()+"</th>")
                .forEach(r -> ret.append(r));
        ret.append("</tr>");
        return ret.toString();
    }

    public static String toHTMLTableFromProgress(List<ProductProgress> productProgressList) {
        StringBuilder ret = new StringBuilder("<table>");
        ret.append("<tr><th>ItemId</th><th>Manufacturer</th><th>RetailerItemId</th><th>Revenue %</th><th>Description</th><th>ReatilerDept</th><th>NACSCategory</th><th>isComplete</th>" +
                "<th>isUPC</th><th>completeRev</th><th>incompleteRev</th><th>completeItemCnt</th><th>incompleteItemCnt</th><th>completeDeptRev</th><th>incompleteDeptRev</th></tr>");
        productProgressList.stream()
                .forEach(pp -> ret.append("<tr>")
                        .append("<td>").append(pp.getItemId()).append("</td>")
                        .append("<td>").append(pp.getManufacturer()).append("</td>")
                        .append("<td>").append(pp.getRetailerItemId()).append("</td>")
                        .append("<td align=\"right\">").append(percentFmt(pp.getRevPortion(), 6)).append("</td>")
                        .append("<td>").append(pp.getDescription()).append("</td>")
                        .append("<td>").append(pp.getRetailerDept()).append("</td>")
                        .append("<td>").append(pp.getNacsCategory()).append("</td>")
                        .append("<td>").append(pp.getComplete()).append("</td>")
                        .append("<td>").append(pp.getIsUpc()).append("</td>")
                        .append("<td align=\"right\">").append(percentFmt(pp.getCompleteRevenue(), 6)).append("</td>")
                        .append("<td align=\"right\">").append(percentFmt(pp.getIncompleteRevenue(), 6)).append("</td>")
                        .append("<td>").append(pp.getCompleteItems()).append("</td>")
                        .append("<td>").append(pp.getIncompleteItems()).append("</td>")
                        .append("<td align=\"right\">").append(percentFmt(pp.getCompleteDeptRevenue(), 6)).append("</td>")
                        .append("<td align=\"right\">").append(percentFmt(pp.getIncompleteDeptRevenue(), 6)).append("</td>")
                        .append("</tr>"));
        ret.append("</table>");
        return ret.toString();
    }

    public static String toHTMLTableFromMising(List<MissingItem> missingItems) {
        StringBuilder ret = new StringBuilder("<table>");
        ret.append("<tr><th>ItemId</th><th>ProjectId</th><th>NumProjects</th><th>Manufacturer</th><th>Description</th><th>LastDate</th><th>TotalRev</th><th>% TotalRev</th></tr>");
        missingItems.stream()
                .forEach(mi -> ret.append("<tr>")
                        .append("<td>").append(mi.getItemId()).append("</td>")
                        .append("<td>").append(mi.getProjectId()).append("</td>")
                        .append("<td>").append(mi.getNumProjects()).append("</td>")
                        .append("<td>").append(mi.getManufacturer()).append("</td>")
                        .append("<td>").append(mi.getDescription()).append("</td>")
                        .append("<td>").append(mi.getLastDate()).append("</td>")
                        .append("<td align=\"right\">").append(currencyFmt(mi.getTotalRevenue())).append("</td>")
                        .append("<td align=\"right\">").append(percentFmt(mi.getPercentTotalRevenue(), 6)).append("</td>")
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
