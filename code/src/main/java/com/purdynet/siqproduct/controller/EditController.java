package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.model.EditItem;
import com.purdynet.siqproduct.view.EditView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EditController {

    private final EditView editView;

    @Autowired
    public EditController(EditView editView) {
        this.editView = editView;
    }

    @GetMapping(value = "/edit")
    public String editPage(@ModelAttribute EditItem editItem) {
        return editView.wrapHtmlBody(editView.productForm(editItem));
    }

    @PostMapping(value = "/edit")
    public String editPagePost(@ModelAttribute EditItem editItem) {
        return editView.wrapHtmlBody(editItem.prettyPrint());
    }
}
