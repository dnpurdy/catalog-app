package com.purdynet.siqproduct.view;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.AbstractCoreItem;
import com.purdynet.siqproduct.model.Function;
import com.purdynet.siqproduct.model.MissingItem;
import com.purdynet.siqproduct.model.ProductProgress;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

public class AbstractView {
    public String toHTMLTable(BqTableData bqTableData) {
        StringBuilder ret = new StringBuilder("<table>");
        ret.append(toHTMLTable(bqTableData.getSchemaFieldNames()));
        bqTableData.getTableRowList().forEach(row -> ret.append(toHTMLTable(row)));
        ret.append("</table>");
        return ret.toString();
    }

    public String toHTMLTable(TableRow tableRow) {
        StringBuilder ret = new StringBuilder("<tr>");
        tableRow.getF().stream()
                .map(f -> f.getV().toString())
                .map(val -> "<td>"+val+"</td>")
                .forEach(r -> ret.append(r));
        ret.append("</tr>");
        return ret.toString();
    }

    public String toHTMLTable(List<TableFieldSchema> schemaFieldNames) {
        StringBuilder ret = new StringBuilder("<tr>");
        schemaFieldNames.stream()
                .map(tfs -> "<th>"+tfs.getName()+"</th>")
                .forEach(r -> ret.append(r));
        ret.append("</tr>");
        return ret.toString();
    }

    public String wrapHtmlBody(String content) {
        return "<html><head><link href=\"/style.css\" rel=\"stylesheet\"/></head><body>"+content+"</body></html>";
    }

    public String wrapAGHtmlBody(String content) {
        return "<html><head>\n" +
                "    <link href=\"/style.css\" rel=\"stylesheet\"/>\n" +
                "    <script src=\"https://unpkg.com/ag-grid/dist/ag-grid.min.noStyle.js\"></script>\n" +
                "    <link rel=\"stylesheet\" href=\"https://unpkg.com/ag-grid/dist/styles/ag-grid.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://unpkg.com/ag-grid/dist/styles/ag-theme-balham.css\">\n"+
                "</head><body>"+content+"</body></html>";
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
                        .append(td(mi.getLastDateString()))
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
        return "<td "+t+">"+(val != null ? val : "")+"</td>";
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

    public String makeTableAG(Function<String> colFunc, String uri) {
        return wrapAGHtmlBody("  <div id=\"myGrid\" class=\"ag-theme-balham\"></div>\n" +
                "\n" +
                "  <script type=\"text/javascript\" charset=\"utf-8\">\n" +
                "    // specify the columns\n" +
                colFunc.invoke() +
                "    \n" +
                "    // let the grid know which columns and what data to use\n" +
                "    var gridOptions = {\n" +
                "  columnDefs: columnDefs,\n" +
                "  enableSorting: true,\n" +
                "  enableFilter: true\n" +
                "};" +
                " function dateFormatter(params) {\n" +
                " console.log(params);\n"+
                " var dateobj = new Date(params.value);\n" +
                "  var mm = dateobj.getMonth() + 1; // getMonth() is zero-based\n" +
                "  var dd = dateobj.getDate();\n" +
                "\n" +
                "  return [dateobj.getFullYear(),\n" +
                "          (mm>9 ? '' : '0') + mm,\n" +
                "          (dd>9 ? '' : '0') + dd\n" +
                "         ].join('-');\n" +
                "};" +
                "function currencyFormatter(params) {\n" +
                        "    return '$' + formatNumber(params.value);\n" +
                        "}\n" +
                        "\n" +
                        "function formatNumber(number) {\n" +
                        "    // this puts commas into the number eg 1000 goes to 1,000,\n" +
                        "    // i pulled this from stack overflow, i have no idea how it works\n" +
                        "    return Number(number).toFixed(2).toString();\n" +
                        "}\n "+
                "function formatNumber2(number) {\n" +
                "    // this puts commas into the number eg 1000 goes to 1,000,\n" +
                "    // i pulled this from stack overflow, i have no idea how it works\n" +
                "    return Number(number).toFixed(6).toString();\n" +
                "}\n "+
                        "\n" +
                        "function percentFormatter(params) {\n" +
                        "    return formatNumber2(params.value*100)+'%';\n" +
                        "}\n" +
                        "\n"+
                "\n" +
                "  // lookup the container we want the Grid to use\n" +
                "  var eGridDiv = document.querySelector('#myGrid');\n" +
                "\n" +
                "  // create the grid passing in the div to use together with the columns & data we want to use\n" +
                "  new agGrid.Grid(eGridDiv, gridOptions);\n" +
                "\n" +
                "  fetch('"+uri+"').then(function(response) {\n" +
                "    return response.json();\n" +
                "  }).then(function(data) {\n" +
                "    gridOptions.api.setRowData(data);\n" +
                "  })" +
                "  </script>");
    }

}
