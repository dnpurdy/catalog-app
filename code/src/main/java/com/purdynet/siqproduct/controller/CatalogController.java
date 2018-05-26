package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.model.items.CatalogItem;
import com.purdynet.siqproduct.service.CatalogService;
import com.purdynet.siqproduct.service.FreemarkerService;
import com.purdynet.siqproduct.util.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CatalogController {

    private final CatalogService catalogService;
    private final FreemarkerService freemarkerService;

    @Autowired
    public CatalogController(final CatalogService catalogService, final FreemarkerService freemarkerService) {
        this.catalogService = catalogService;
        this.freemarkerService = freemarkerService;
    }

    @GetMapping(value = "/catalog", produces = MediaType.TEXT_HTML_VALUE)
    public String catalogViewHTML() {
        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("catalogItems", catalogViewJSON());
        return freemarkerService.processTemplate("templates/CatalogPage.ftl", dataModel);
    }

    @GetMapping(value = "/ag/catalog", produces = MediaType.TEXT_HTML_VALUE)
    public String catalogViewAG() {
        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("agColumns", getCatalogAGCol());
        dataModel.put("dataUri", "/catalog");
        return freemarkerService.processTemplate("templates/AGGridPage.ftl", dataModel);
    }

    @GetMapping(value = "/catalog", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CatalogItem> catalogViewJSON() {
        return catalogService.getCatalog();
    }

    @GetMapping(value = "/catalog/{upc}", produces = MediaType.TEXT_HTML_VALUE)
    public String catalogViewPartialHTML(@PathVariable(name = "upc", required = false) String upc) {
        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("catalogItems", catalogViewPartialJSON(upc));
        return freemarkerService.processTemplate("templates/CatalogPage.ftl", dataModel);
    }

    @GetMapping(value = "/ag/catalog/{upc}", produces = MediaType.TEXT_HTML_VALUE)
    public String catalogViewPartialAG(@PathVariable(name = "upc", required = false) String upc) {
        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("agColumns", getCatalogAGCol());
        dataModel.put("dataUri", "/catalog"+upc);
        return freemarkerService.processTemplate("templates/AGGridPage.ftl", dataModel);
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

    @GetMapping(value = "/catalog-csv", produces = MediaType.TEXT_PLAIN_VALUE)
    public String catalogCSV() {
        return CSVUtils.catalogItemtoCSV(catalogService.getCatalog());
    }

    @PostMapping(value = "/catalog/backup", produces = MediaType.TEXT_PLAIN_VALUE)
    public String catalogBackup() {
        return catalogService.backupCatalog();
    }


    public String getCatalogAGCol() {
        return "    var columnDefs = [\n" +
                "      {headerName: \"productId\", field: \"productId\"},\n" +
                "      {headerName: \"itemId\", field: \"itemId\"},\n" +
                "      {headerName: \"description\", field: \"description\"},\n" +
                "      {headerName: \"department\", field: \"department\"},\n" +
                "      {headerName: \"deptDescription\", field: \"deptDescription\"},\n" +
                "      {headerName: \"categorySubCode\", field: \"categorySubCode\"},\n" +
                "      {headerName: \"categorySubDescription\", field: \"categorySubDescription\"},\n" +
                "      {headerName: \"category\", field: \"category\"},\n" +
                "      {headerName: \"manufacturer\", field: \"manufacturer\"},\n" +
                "      {headerName: \"subSegmentDescription\", field: \"subSegmentDescription\"},\n" +
                "      {headerName: \"subSegmentId\", field: \"subSegmentId\"},\n" +
                "      {headerName: \"segmentDescription\", field: \"segmentDescription\"},\n" +
                "      {headerName: \"segmentId\", field: \"segmentId\"},\n" +
                "      {headerName: \"subCategoryDescription\", field: \"subCategoryDescription\"},\n" +
                "      {headerName: \"subCategoryId\", field: \"subCategoryId\"},\n" +
                "      {headerName: \"majorDepartmentDescription\", field: \"majorDepartmentDescription\"},\n" +
                "      {headerName: \"majorDepartmentId\", field: \"majorDepartmentId\"},\n" +
                "      {headerName: \"container\", field: \"container\"},\n" +
                "      {headerName: \"size\", field: \"size\"},\n" +
                "      {headerName: \"uom\", field: \"uom\"},\n" +
                "      {headerName: \"active\", field: \"active\"},\n" +
                "      {headerName: \"privateLabelFlag\", field: \"privateLabelFlag\"},\n" +
                "      {headerName: \"consumption\", field: \"consumption\"},\n" +
                "      {headerName: \"pkg\", field: \"pkg\"},\n" +
                "      {headerName: \"flavor\", field: \"flavor\"},\n" +
                "      {headerName: \"brand\", field: \"brand\"},\n" +
                "      {headerName: \"brandType\", field: \"brandType\"},\n" +
                "      {headerName: \"height\", field: \"height\"},\n" +
                "      {headerName: \"width\", field: \"width\"},\n" +
                "      {headerName: \"depth\", field: \"depth\"},\n" +
                "      {headerName: \"shapeId\", field: \"shapeId\"},\n" +
                "      {headerName: \"family\", field: \"family\"},\n" +
                "      {headerName: \"trademark\", field: \"trademark\"},\n" +
                "      {headerName: \"country\", field: \"country\"},\n" +
                "      {headerName: \"color\", field: \"color\"},\n" +
                "      {headerName: \"alternateHeight\", field: \"alternateHeight\"},\n" +
                "      {headerName: \"alternateWeight\", field: \"alternateWeight\"},\n" +
                "      {headerName: \"alternateDepth\", field: \"alternateDepth\"},\n" +
                "      {headerName: \"containerDescription\", field: \"containerDescription\"},\n" +
                "      {headerName: \"distributor\", field: \"distributor\"},\n" +
                "      {headerName: \"industryType\", field: \"industryType\"},\n" +
                "      {headerName: \"dateCreated\", field: \"dateCreated\"},\n" +
                "    ];\n";
    }
}
