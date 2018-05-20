package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.model.EditItem;
import com.purdynet.siqproduct.service.CatalogService;
import com.purdynet.siqproduct.view.EditView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EditController {

    private final EditView editView;

    private final CatalogService catalogService;

    @Autowired
    public EditController(EditView editView, CatalogService catalogService) {
        this.editView = editView;
        this.catalogService = catalogService;
    }

    @GetMapping(value = "/edit")
    public String editPage(@ModelAttribute EditItem editItem) {
        return editView.wrapHtmlBody(editView.productForm(editItem) +
                "<hr/>" +
                editView.toEditTable(catalogService.genNearMatches(editItem.getItemId()), editItem.getItemId()));
    }

    @GetMapping(value = "/edit-ag")
    public String editPageAG(@ModelAttribute EditItem editItem) {
        return editView.wrapHtmlBody(editView.productForm(editItem) +
                "<hr/>" +
                editView.makeTableAG(editView::getEditAGCol, "/catalog-near/"+editItem.getItemId()));
    }

    @PostMapping(value = "/edit")
    public String editPagePost(@ModelAttribute EditItem editItem) {
        return editView.wrapHtmlBody(editItem.prettyPrint());
    }
}
