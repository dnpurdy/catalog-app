package com.purdynet.siqproduct.view;

import com.purdynet.siqproduct.model.CatalogItem;
import com.purdynet.siqproduct.model.EditItem;
import com.purdynet.siqproduct.model.NacsCategories;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EditView extends AbstractView {
    public String productFormOld(EditItem editItem) {
        StringBuilder sb = new StringBuilder();
        sb.append("<form method=\"post\" action=\"/edit\">")
                .append(textField("itemId", editItem.getItemId()))
                .append(textField("description", editItem.getDescription()))
                .append(nacs())
                .append(textField("manufacturer", editItem.getManufacturer()))
                .append(textField("container", editItem.getContainer()))
                .append(textField("size", editItem.getSize()))
                .append(textField("uom", editItem.getUom()))
                .append(textField("pkg", editItem.getPkg()))
                .append(textField("brand", editItem.getBrand()))
                .append("<p><input type=\"submit\" value=\"Submit\" /> <input type=\"reset\" value=\"Reset\" /></p>")
                .append("</form>");
        return sb.toString();
    }

    public String productForm(EditItem editItem) {
        return "<form method=\"post\" action=\"edit\">\n" +
                "<ul class=\"form-style-1\">\n" +
                textField("itemId", editItem.getItemId(), true) +
                textField("description", editItem.getDescription(), true) +
                nacs() +
                textField("manufacturer", editItem.getManufacturer(), true) +
                textField("container", editItem.getContainer()) +
                textField("size", editItem.getSize()) +
                textField("uom", editItem.getUom()) +
                textField("pkg", editItem.getPkg()) +
                textField("brand", editItem.getBrand()) +
                "    <li><input type=\"submit\" value=\"Submit\" /> <input type=\"reset\" value=\"Reset\" /></li>\n" +
                "</ul>\n" +
                "</form>";
    }

    private String nacs() {
        StringBuilder sb = new StringBuilder();
        sb.append("<li><select name=\"nacs\" class=\"field-select\">");
        for (NacsCategories nacsCategories : NacsCategories.values()) {
            sb.append("<option value=\""+nacsCategories.name()+"\">"+nacsCategories.getCategoryCode()+" - "+nacsCategories.getCategory()+" - "+nacsCategories.getSubCategory()+"</option>");
        }
        sb.append("</select></li>");
        return sb.toString();
    }

    private String textField(String id, String value) {
        return textField(id, value, false);
    }

    private String textField(String id, String value, boolean required) {
        return "<li><label for=\""+id+"\">"+id +
                (required ? "<span class=\"required\">*</span>" : "") +
                "<input class=\"field-long\" id=\""+id+"\" name=\""+id+"\" type=\"text\" "+(Strings.isNotEmpty(value) ? "value=\""+value+"\"" : "" ) +"></input></li>";
        //return "<label for=\""+id+"\">"+id+"</label><br/>";
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

    public String getEditAGCol() {
        return "    var columnDefs = [\n" +
                "      {headerName: \"UPC\", field: \"itemId\", width: 110},\n" +
                "      {headerName: \"Description\", field: \"description\", width: 400},\n" +
                "      {headerName: \"Code\", field: \"categorySubCode\", width: 75},\n" +
                "      {headerName: \"Category\", field: \"category\"},\n" +
                "      {headerName: \"Sub Category\", field: \"categorySubDescription\"},\n" +
                "      {headerName: \"manufacturer\", field: \"manufacturer\"},\n" +
                "      {headerName: \"container\", field: \"container\", width: 110},\n" +
                "      {headerName: \"size\", field: \"size\", width: 110},\n" +
                "      {headerName: \"uom\", field: \"uom\", width: 110},\n" +
                "      {headerName: \"pkg\", field: \"pkg\"},\n" +
                "      {headerName: \"brand\", field: \"brand\"},\n" +
                "    ];\n";
    }
}
