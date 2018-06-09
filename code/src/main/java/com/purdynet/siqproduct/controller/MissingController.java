package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.biqquery.BQClient;
import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.items.MissingItem;
import com.purdynet.siqproduct.model.retailer.Retailer;
import com.purdynet.siqproduct.service.FreemarkerService;
import com.purdynet.siqproduct.service.ProductService;
import com.purdynet.siqproduct.service.RetailerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.purdynet.siqproduct.biqquery.BQClient.convertTableRowToModel;
import static com.purdynet.siqproduct.biqquery.BQClient.runQuerySync;

@RestController
public class MissingController {

    private final String projectId;

    private final RetailerService retailerService;
    private final ProductService productService;
    private final FreemarkerService freemarkerService;

    @Autowired
    public MissingController(@Value("${project.id}") String projectId,
                             final RetailerService retailerService,
                             final ProductService productService,
                             final FreemarkerService freemarkerService) {
        this.projectId = projectId;
        this.retailerService = retailerService;
        this.productService = productService;
        this.freemarkerService = freemarkerService;
    }

    @RequestMapping(value = {"/missing","/missing/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingHTML(@PathVariable(name = "upc", required = false) String upc, @RequestParam(name = "retailers", required = false) String retailers) {
        return getAGHtml("/missing" + (upc != null ? "/"+upc : "") + (retailers !=null ? "?retailers="+retailers : ""));
    }

    @RequestMapping(value = {"/missing","/missing/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingJson(@PathVariable(name = "upc", required = false) String upc, @RequestParam(name = "retailers", required = false) String retailers) {
        return makeMissingItemList(upc, retailers, Retailer::allClause);
    }

    @RequestMapping(value = {"/missing-beer","/missing-beer/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingBeerHTML(@PathVariable(name = "upc", required = false) String upc, @RequestParam(name = "retailers", required = false) String retailers) {
        return getAGHtml("/missing-beer" + (upc != null ? "/"+upc : "") + (retailers !=null ? "?retailers="+retailers : ""));
    }

    @RequestMapping(value = {"/missing-beer","/missing-beer/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingBeerJson(@PathVariable(name = "upc", required = false) String upc, @RequestParam(name = "retailers", required = false) String retailers) {
        return makeMissingItemList(upc, retailers, Retailer::beerClause);
    }

    @RequestMapping(value = {"/missing-beverage","/missing-beverage/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingBeverageHTML(@PathVariable(name = "upc", required = false) String upc, @RequestParam(name = "retailers", required = false) String retailers) {
        return getAGHtml("/missing-beverage" + (upc != null ? "/"+upc : "") + (retailers !=null ? "?retailers="+retailers : ""));
    }

    @RequestMapping(value = {"/missing-beverage","/missing-beverage/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingBeverageJson(@PathVariable(name = "upc", required = false) String upc, @RequestParam(name = "retailers", required = false) String retailers) {
        return makeMissingItemList(upc, retailers, Retailer::beverageClause);
    }

    @RequestMapping(value = {"/missing-tobacco","/missing-tobacco/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingTobaccoHTML(@PathVariable(name = "upc", required = false) String upc, @RequestParam(name = "retailers", required = false) String retailers) {
        return getAGHtml("/missing-tobacco" + (upc != null ? "/"+upc : "") + (retailers !=null ? "?retailers="+retailers : ""));
    }

    @RequestMapping(value = {"/missing-tobacco","/missing-tobacco/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingTobaccoJson(@PathVariable(name = "upc", required = false) String upc, @RequestParam(name = "retailers", required = false) String retailers) {
        return makeMissingItemList(upc, retailers, Retailer::tobaccoClause);
    }

    @RequestMapping(value = {"/missing-saltysnack","/missing-saltysnack/{upc}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String missingSaltySnackHTML(@PathVariable(name = "upc", required = false) String upc, @RequestParam(name = "retailers", required = false) String retailers) {
        return getAGHtml("/missing-saltysnack" + (upc != null ? "/"+upc : "") + (retailers !=null ? "?retailers="+retailers : ""));
    }

    @RequestMapping(value = {"/missing-saltysnack","/missing-saltysnack/{upc}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MissingItem> missingSaltySnackJson(@PathVariable(name = "upc", required = false) String upc, @RequestParam(name = "retailers", required = false) String retailers) {
        return makeMissingItemList(upc, retailers, Retailer::saltySnacksClause);
    }

    private String getAGHtml(final String uri) {
        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("agColumns", getCatalogAGCol());
        dataModel.put("dataUri", uri);
        return freemarkerService.processTemplate("templates/AGGridPage.ftl", dataModel);
    }

    private List<MissingItem> makeMissingItemList(final String upc, final String retailers, final Function<Retailer, String> productSelectFnc) {
        BQClient BQClient = runQuerySync(projectId, productService.productProgress(filterRetailers(retailerService.getRetailers(),retailers), productSelectFnc, upc));
        return makeMissingItemList(BQClient.getBqTableData());
    }

    private List<Retailer> filterRetailers(final List<Retailer> retailerList, final String retailersPicked) {
        if (StringUtils.isEmpty(retailersPicked)) return retailerList;
        else return retailerList.stream().filter(r -> new HashSet<String>(Arrays.asList(retailersPicked.split(","))).contains(r.projectId())).collect(Collectors.toList());
    }

    private List<MissingItem> makeMissingItemList(BqTableData bqTableData) {
        return convertTableRowToModel(bqTableData, productService::missingItemOf);
    }

    private String getCatalogAGCol() {
        return "    var columnDefs = [\n" +
                "      {headerName: \"UPC\", field: \"itemId\", width: 110, cellRenderer: editLinkRenderer},\n" +
                "      {headerName: \"Project Id\", field: \"projectId\"},\n" +
                "      {headerName: \"# Projects\", field: \"numProjects\", width: 100},\n" +
                "      {headerName: \"Manufacturer\", field: \"manufacturer\", width: 450},\n" +
                "      {headerName: \"Description\", field: \"description\", width: 450},\n" +
                "      {headerName: \"last Sold Date\", field: \"lastDate\", valueFormatter: dateFormatter},\n" +
                "      {headerName: \"Total Revenue\", field: \"totalRevenue\", cellStyle: { 'text-align': 'right' }, valueFormatter: currencyFormatter},\n" +
                "      {headerName: \"% Total Revenue\", field: \"percentTotalRevenue\", valueFormatter: percentFormatter},\n" +
                "    ];\n";
    }
}
