package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.model.EditItem;
import com.purdynet.siqproduct.model.NacsCategories;
import com.purdynet.siqproduct.util.HTMLUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

@RestController
public class EditController {
    @GetMapping(value = "/edit")
    public String editPage(@ModelAttribute EditItem editItem) {
        return HTMLUtils.wrapHtmlBody(productForm(editItem));
    }

    @PostMapping(value = "/edit")
    public String editPagePost(@ModelAttribute EditItem editItem) {
        return HTMLUtils.wrapHtmlBody(editItem.prettyPrint());
    }

    private String productForm(EditItem editItem) {
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

    private String nacs() {
        StringBuilder sb = new StringBuilder();
        sb.append("<select>");
        for (NacsCategories nacsCategories : NacsCategories.values()) {
            sb.append("<option value=\""+nacsCategories.name()+"\">"+nacsCategories.getCategoryCode()+" - "+nacsCategories.getCategory()+" - "+nacsCategories.getSubCategory()+"</option>");
        }
        sb.append("</select></br>");
        return sb.toString();
    }

    private String textField(String id, String value) {
        return "<label for=\""+id+"\">"+id+"</label><input id=\""+id+"\" name=\""+id+"\" type=\"text\" "+(Strings.isNotEmpty(value) ? "value=\""+value+"\"" : "" ) +"></input><br/>";
    }
}
