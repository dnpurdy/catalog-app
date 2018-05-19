package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.CatalogItem;
import com.purdynet.siqproduct.model.EditItem;
import com.purdynet.siqproduct.model.ProductProgress;
import com.purdynet.siqproduct.service.CatalogService;
import com.purdynet.siqproduct.view.CatalogView;
import com.purdynet.siqproduct.view.EditView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.purdynet.siqproduct.biqquery.BigqueryUtils.convertTableRowToModel;

@RestController
public class CatalogController {

    private final CatalogService catalogService;
    private final CatalogView catalogView;

    @Autowired
    public CatalogController(CatalogService catalogService, CatalogView catalogView) {
        this.catalogService = catalogService;
        this.catalogView = catalogView;
    }

    @GetMapping(value = "/catalog")
    public String catalogView() {
        return catalogView.makeTable(catalogService.getCatalog());
    }

    @GetMapping(value = "/catalog/{upc}")
    public String catalogViewPartial(@PathVariable(name = "upc", required = false) String upc) {
        return catalogView.makeTable(catalogService.getCatalogPartialItemId(upc));
    }

    @PostMapping(value = "/catalog")
    public String catalogUpdate() {
        catalogService.updateCatalog();
        return "Update complete!";
    }
}
