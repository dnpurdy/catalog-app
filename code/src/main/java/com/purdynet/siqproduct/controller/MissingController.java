package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.MissingItem;
import com.purdynet.siqproduct.service.*;
import com.purdynet.siqproduct.biqquery.BigqueryUtils;
import com.purdynet.siqproduct.retailer.Retailer;
import com.purdynet.siqproduct.view.MissingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import static com.purdynet.siqproduct.biqquery.BigqueryUtils.convertTableRowToModel;
import static com.purdynet.siqproduct.biqquery.BigqueryUtils.runQuerySync;

@RestController
public class MissingController {

    private final RetailerService retailerService;
    private final ProductService productService;

    private final MissingView missingView;

    @Autowired
    public MissingController(RetailerService retailerService, ProductService productService, MissingView missingView) {
        this.retailerService = retailerService;
        this.productService = productService;
        this.missingView = missingView;
    }

    @RequestMapping(value = {"/missing","/missing/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missing(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return missingView.makeTable(missingJson(upc));
    }

    @RequestMapping(value = {"/missing","/missing/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingJson(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return makeMissingItemList(upc, Retailer::allClause);
    }

    @RequestMapping(value = {"/missing-beer","/missing-beer/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingBeer(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return missingView.makeTable(missingBeerJson(upc));
    }

    @RequestMapping(value = {"/missing-beer","/missing-beer/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingBeerJson(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return makeMissingItemList(upc, Retailer::beerClause);
    }

    @RequestMapping(value = {"/missing-beverage","/missing-beverage/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingBeverage(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return missingView.makeTable(missingBeverageJson(upc));
    }

    @RequestMapping(value = {"/missing-beverage","/missing-beverage/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingBeverageJson(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return makeMissingItemList(upc, Retailer::beverageClause);
    }

    @RequestMapping(value = {"/missing-tobacco","/missing-tobacco/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingTobacco(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return missingView.makeTable(missingTobaccoJson(upc));
    }

    @RequestMapping(value = {"/missing-tobacco","/missing-tobacco/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingTobaccoJson(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return makeMissingItemList(upc, Retailer::tobaccoClause);
    }

    private List<MissingItem> makeMissingItemList(String upc, Function<Retailer, String> productSelectFnc) {
        BigqueryUtils bigqueryUtils = runQuerySync(productService.productProgress(retailerService.getRetailers(), productSelectFnc, upc));
        return makeMissingItemList(bigqueryUtils.getBqTableData());
    }

    private List<MissingItem> makeMissingItemList(BqTableData bqTableData) {
        return convertTableRowToModel(bqTableData, MissingItem::of);
    }
}
