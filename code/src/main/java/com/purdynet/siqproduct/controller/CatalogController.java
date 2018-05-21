package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.model.CatalogItem;
import com.purdynet.siqproduct.service.CatalogService;
import com.purdynet.siqproduct.util.CSVUtils;
import com.purdynet.siqproduct.view.CatalogView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CatalogController {

    private final CatalogService catalogService;
    private final CatalogView catalogView;

    @Autowired
    public CatalogController(CatalogService catalogService, CatalogView catalogView) {
        this.catalogService = catalogService;
        this.catalogView = catalogView;
    }

    @GetMapping(value = "/catalog", produces = MediaType.TEXT_HTML_VALUE)
    public String catalogViewHTML() {
        return catalogView.makeTable(catalogService.getCatalog());
    }

    @GetMapping(value = "/catalog-csv", produces = MediaType.TEXT_PLAIN_VALUE)
    public String catalogCSV() {
        return CSVUtils.catalogItemtoCSV(catalogService.getCatalog());
    }

    @GetMapping(value = "/catalog-ag", produces = MediaType.TEXT_HTML_VALUE)
    public String catalogViewAG() {
        return catalogView.makeTableAG(catalogView::getCatalogAGCol, "/catalog");
    }

    @GetMapping(value = "/catalog", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CatalogItem> catalogViewJSON() {
        return catalogService.getCatalog();
    }

    @GetMapping(value = "/catalog/{upc}", produces = MediaType.TEXT_HTML_VALUE)
    public String catalogViewPartialHTML(@PathVariable(name = "upc", required = false) String upc) {
        return catalogView.makeTable(catalogService.getCatalogPartialItemId(upc));
    }

    @GetMapping(value = "/catalog-ag/{upc}", produces = MediaType.TEXT_HTML_VALUE)
    public String catalogViewPartialAG(@PathVariable(name = "upc", required = false) String upc) {
        return catalogView.makeTableAG(catalogView::getCatalogAGCol, "/catalog/"+upc);
    }

    @GetMapping(value = "/catalog/{upc}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CatalogItem> catalogViewPartialJSON(@PathVariable(name = "upc", required = false) String upc) {
        return catalogService.getCatalogPartialItemId(upc);
    }

    @PostMapping(value = "/catalog")
    public String catalogUpdate() {
        catalogService.updateCatalog();
        return "Update complete!";
    }

    @GetMapping(value = "/catalog-near/{upc}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CatalogItem> catalogNearJSON(@PathVariable(name = "upc", required = false) String upc) {
        return catalogService.genNearMatches(upc);
    }
}
