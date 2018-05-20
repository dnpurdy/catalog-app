package com.purdynet.siqproduct.view;

import com.purdynet.siqproduct.model.MissingItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MissingView extends AbstractView {
    public String makeTable(List<MissingItem> missingItems) {
        return wrapHtmlBody(toHTMLTableFromMising(missingItems));
    }

    public String getCatalogAGCol() {
        return "    var columnDefs = [\n" +
                "      {headerName: \"UPC\", field: \"itemId\", width: 110},\n" +
                "      {headerName: \"projectId\", field: \"projectId\"},\n" +
                "      {headerName: \"# Projects\", field: \"numProjects\", width: 100},\n" +
                "      {headerName: \"manufacturer\", field: \"manufacturer\", width: 450},\n" +
                "      {headerName: \"description\", field: \"description\", width: 450},\n" +
                "      {headerName: \"lastDate\", field: \"lastDate\"},\n" +
                "      {headerName: \"totalRevenue\", field: \"totalRevenue\", cellStyle: { 'text-align': 'right' }, valueFormatter: currencyFormatter},\n" +
                "      {headerName: \"percentTotalRevenue\", field: \"percentTotalRevenue\", valueFormatter: percentFormatter},\n" +
                "    ];\n";
    }
}
