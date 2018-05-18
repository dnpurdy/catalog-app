package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.MissingItem;
import com.purdynet.siqproduct.service.*;
import com.purdynet.siqproduct.biqquery.BigqueryUtils;
import com.purdynet.siqproduct.retailer.Retailer;
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
import static com.purdynet.siqproduct.util.HTMLUtils.toHTMLTableFromMising;
import static com.purdynet.siqproduct.util.HTMLUtils.wrapHtmlBody;

@RestController
public class MissingController {

    private final List<Retailer> retailers;
    private final ProductService productService;

    @Autowired
    public MissingController(List<Retailer> retailers, ProductService productService) {
        this.retailers = retailers;
        this.productService = productService;
    }

    @RequestMapping(value = {"/missing","/missing/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missing(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return makeTable(missingJson(upc));
    }

    @RequestMapping(value = {"/missing","/missing/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingJson(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return makeMissingItemList(upc, Retailer::allClause);
    }

    @RequestMapping(value = {"/missing-beer","/missing-beer/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingBeer(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return makeTable(missingBeerJson(upc));
    }

    @RequestMapping(value = {"/missing-beer","/missing-beer/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingBeerJson(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return makeMissingItemList(upc, Retailer::beerClause);
    }

    @RequestMapping(value = {"/missing-beverage","/missing-beverage/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingBeverage(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return makeTable(missingBeverageJson(upc));
    }

    @RequestMapping(value = {"/missing-beverage","/missing-beverage/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingBeverageJson(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return makeMissingItemList(upc, Retailer::beverageClause);
    }

    @RequestMapping(value = {"/missing-tobacco","/missing-tobacco/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingTobacco(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return makeTable(missingTobaccoJson(upc));
    }

    @RequestMapping(value = {"/missing-tobacco","/missing-tobacco/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingTobaccoJson(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        return makeMissingItemList(upc, Retailer::tobaccoClause);
    }

    private String makeTable(List<MissingItem> missingItems) {
        return wrapHtmlBody(toHTMLTableFromMising(missingItems));
    }

    private List<MissingItem> makeMissingItemList(String upc, Function<Retailer, String> productSelectFnc) throws IOException {
        BigqueryUtils bigqueryUtils = runQuerySync(productService.productProgress(retailers, productSelectFnc, upc));
        return makeMissingItemList(bigqueryUtils.getBqTableData());
    }

    private List<MissingItem> makeMissingItemList(BqTableData bqTableData) {
        return convertTableRowToModel(bqTableData, MissingItem::of);
    }
}
