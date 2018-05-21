package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.MissingItem;
import com.purdynet.siqproduct.service.*;
import com.purdynet.siqproduct.biqquery.BQClient;
import com.purdynet.siqproduct.model.retailer.Retailer;
import com.purdynet.siqproduct.view.MissingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

import static com.purdynet.siqproduct.biqquery.BQClient.convertTableRowToModel;
import static com.purdynet.siqproduct.biqquery.BQClient.runQuerySync;

@RestController
public class MissingController {

    private final String projectId;

    private final RetailerService retailerService;
    private final ProductService productService;

    private final MissingView missingView;

    @Autowired
    public MissingController(@Value("${project.id}") String projectId, RetailerService retailerService, ProductService productService, MissingView missingView) {
        this.projectId = projectId;
        this.retailerService = retailerService;
        this.productService = productService;
        this.missingView = missingView;
    }

    @RequestMapping(value = {"/missing","/missing/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingHTML(@PathVariable(name = "upc", required = false) String upc) {
        return missingView.makeTable(missingJson(upc));
    }

    @RequestMapping(value = {"/missing-ag","/missing-ag/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingAG(@PathVariable(name = "upc", required = false) String upc) {
        return missingView.makeTableAG(missingView::getCatalogAGCol, "/missing" + (upc != null ? "/"+upc : ""));
    }

    @RequestMapping(value = {"/missing","/missing/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingJson(@PathVariable(name = "upc", required = false) String upc) {
        return makeMissingItemList(upc, Retailer::allClause);
    }

    @RequestMapping(value = {"/missing-beer","/missing-beer/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingBeerHTML(@PathVariable(name = "upc", required = false) String upc) {
        return missingView.makeTable(missingBeerJson(upc));
    }

    @RequestMapping(value = {"/missing-ag-beer","/missing-ag-beer/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingBeerAG(@PathVariable(name = "upc", required = false) String upc) {
        return missingView.makeTableAG(missingView::getCatalogAGCol, "/missing-beer" + (upc != null ? "/"+upc : ""));
    }

    @RequestMapping(value = {"/missing-beer","/missing-beer/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingBeerJson(@PathVariable(name = "upc", required = false) String upc) {
        return makeMissingItemList(upc, Retailer::beerClause);
    }

    @RequestMapping(value = {"/missing-beverage","/missing-beverage/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingBeverageHTML(@PathVariable(name = "upc", required = false) String upc) {
        return missingView.makeTable(missingBeverageJson(upc));
    }

    @RequestMapping(value = {"/missing-ag-beverage","/missing-ag-beverage/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingBeverageAG(@PathVariable(name = "upc", required = false) String upc) {
        return missingView.makeTableAG(missingView::getCatalogAGCol, "/missing-beverage" + (upc != null ? "/"+upc : ""));
    }

    @RequestMapping(value = {"/missing-beverage","/missing-beverage/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingBeverageJson(@PathVariable(name = "upc", required = false) String upc) {
        return makeMissingItemList(upc, Retailer::beverageClause);
    }

    @RequestMapping(value = {"/missing-tobacco","/missing-tobacco/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingTobaccoHTML(@PathVariable(name = "upc", required = false) String upc) {
        return missingView.makeTable(missingTobaccoJson(upc));
    }

    @RequestMapping(value = {"/missing-ag-tobacco","/missing-ag-tobacco/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingTobaccoAG(@PathVariable(name = "upc", required = false) String upc) {
        return missingView.makeTableAG(missingView::getCatalogAGCol, "/missing-tobacco" + (upc != null ? "/"+upc : ""));
    }

    @RequestMapping(value = {"/missing-tobacco","/missing-tobacco/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingTobaccoJson(@PathVariable(name = "upc", required = false) String upc) {
        return makeMissingItemList(upc, Retailer::tobaccoClause);
    }

    private List<MissingItem> makeMissingItemList(String upc, Function<Retailer, String> productSelectFnc) {
        BQClient BQClient = runQuerySync(projectId, productService.productProgress(retailerService.getRetailers(), productSelectFnc, upc));
        return makeMissingItemList(BQClient.getBqTableData());
    }

    private List<MissingItem> makeMissingItemList(BqTableData bqTableData) {
        return convertTableRowToModel(bqTableData, productService::missingItemOf);
    }
}
