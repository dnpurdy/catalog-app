package com.purdynet.siqproduct.util;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.purdynet.siqproduct.biqquery.BqTableData;

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
}
