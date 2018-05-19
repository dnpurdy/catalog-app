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
                "      {headername: \"itemId\", field: \"itemId\"},\n" +
                "      {headername: \"projectId\", field: \"projectId\"},\n" +
                "      {headername: \"numProjects\", field: \"numProjects\"},\n" +
                "      {headername: \"manufacturer\", field: \"manufacturer\"},\n" +
                "      {headername: \"description\", field: \"description\"},\n" +
                "      {headername: \"lastDate\", field: \"lastDate\"},\n" +
                "      {headername: \"totalRevenue\", field: \"totalRevenue\", cellStyle: { 'text-align': 'right' }, valueFormatter: currencyFormatter},\n" +
                "      {headername: \"percentTotalRevenue\", field: \"percentTotalRevenue\", valueFormatter: percentFormatter},\n" +
                "    ];\n";
    }
}
