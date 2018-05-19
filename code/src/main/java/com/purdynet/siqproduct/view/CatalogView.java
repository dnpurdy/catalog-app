package com.purdynet.siqproduct.view;

import com.purdynet.siqproduct.model.CatalogItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.HammingDistance;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.rightPad;

@Component
public class CatalogView extends AbstractView {
    public String makeTable(List<CatalogItem> catalogItems) {
        return wrapHtmlBody(toHTMLTableFromCatalog(catalogItems));
    }

    public String toHTMLTableFromCatalog(List<CatalogItem> catalogItems) {
        StringBuilder ret = new StringBuilder("<table class=\"data\">");
        ret.append("<tr><th>ProductId</th><th>ItemId</th><th>Description</th><th>Department</th></tr>");
        catalogItems.stream()
                .forEach(ci -> ret.append("<tr>")
                        .append(td(ci.getProductId()))
                        .append(td(ci.getItemId()))
                        .append(td(ci.getDescription()))
                        .append(td(ci.getDepartment()))
                        .append("</tr>"));
        ret.append("</table>");
        return ret.toString();
    }

    public String toEditTable(List<CatalogItem> catalogItems) {
        return toEditTable(catalogItems, null);
    }

    public String toEditTable(List<CatalogItem> catalogItems, String upc) {
        StringBuilder ret = new StringBuilder("<table class=\"data\">");
        ret.append("<tr><th>ItemId</th><th>Description</th><th>NACSCode</th><th>Manufacturer</th><th>Container</th><th>Size</th><th>UOM</th><th>Package</th><th>Brand</th></tr>");
        catalogItems.stream()
                .forEach(ci -> ret.append("<tr>")
                        .append(td(ci.getItemId() + (upc != null ?  " " : "" )))
                        .append(td(ci.getDescription()))
                        .append(td(ci.getCategorySubCode()+" "+ci.getCategory()+" "+ci.getCategorySubDescription()))
                        .append(td(ci.getManufacturer()))
                        .append(td(ci.getContainer()))
                        .append(td(ci.getSize()))
                        .append(td(ci.getUom()))
                        .append(td(ci.getPkg()))
                        .append(td(ci.getBrand()))
                        .append("</tr>"));
        ret.append("</table>");
        return ret.toString();
    }
}
