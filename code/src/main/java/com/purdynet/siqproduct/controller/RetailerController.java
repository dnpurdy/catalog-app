package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.biqquery.BigqueryUtils;
import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.ProductProgress;
import com.purdynet.siqproduct.retailer.Retailer;
import com.purdynet.siqproduct.service.RetailerService;
import com.purdynet.siqproduct.view.RetailerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.purdynet.siqproduct.biqquery.BigqueryUtils.convertTableRowToModel;
import static com.purdynet.siqproduct.biqquery.BigqueryUtils.runQuerySync;

@RestController
public class RetailerController {

    private final RetailerService retailerService;
    private final RetailerView retailerView;

    @Autowired
    public RetailerController(RetailerService retailerService, RetailerView retailerView) {
        this.retailerService = retailerService;
        this.retailerView = retailerView;
    }

    @RequestMapping(value = "/retailer/{id}/missing", produces = MediaType.TEXT_HTML_VALUE)
    public String missingHTML(@PathVariable("id") String requestId) {
        return retailerView.makeTable(missingJson(requestId));
    }

    @RequestMapping(value = "/retailer/{id}/missing-ag", produces = MediaType.TEXT_HTML_VALUE)
    public String missingAG(@PathVariable("id") String requestId) {
        return retailerView.makeTableAG(retailerView::getCatalogAGCol, "/retailer/"+requestId+"/missing");
    }

    @RequestMapping(value = "/retailer/{id}/missing", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductProgress> missingJson(@PathVariable("id") String requestId) {
        return runProductProgress(requestId, " WHERE c.description IS NULL ", 1000).orElse(new ArrayList<>());
    }

    @RequestMapping(value = "/retailer/{id}/detail", produces = MediaType.TEXT_HTML_VALUE)
    public String detailHTML(@PathVariable("id") String requestId) {
        return retailerView.makeTable(detailJson(requestId));
    }

    @RequestMapping(value = "/retailer/{id}/detail-ag", produces = MediaType.TEXT_HTML_VALUE)
    public String detailAG(@PathVariable("id") String requestId) {
        return retailerView.makeTableAG(retailerView::getCatalogAGCol, "/retailer/"+requestId+"/detail");
    }

    @RequestMapping(value = "/retailer/{id}/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductProgress> detailJson(@PathVariable("id") String requestId) {
        return runProductProgress(requestId, "", 1000).orElse(new ArrayList<>());
    }

    @RequestMapping(value = "/retailer/{id}/summary", produces = MediaType.TEXT_HTML_VALUE)
    public String summary(@PathVariable("id") String requestId) {
        return runProductProgress(requestId, "", null).map(retailerView::makeSummary).orElse("");
    }

    private Optional<List<ProductProgress>> runProductProgress(final String requestId, final String where, final Integer limit) {
        return matchRetailer(requestId).map(projectId -> {
            BigqueryUtils bigqueryUtils = runQuerySync(retailerService.progressSql(projectId, limit, where));
            return makeProgressList(bigqueryUtils.getBqTableData());
        });
    }

    private List<ProductProgress> makeProgressList(BqTableData bqTableData) {
        return convertTableRowToModel(bqTableData, ProductProgress::of);
    }

    private Optional<Retailer> matchRetailer(String requestId) {
        return retailerService.getRetailers().stream().filter(r -> r.projectId().equals(requestId)).findFirst();
    }
}
