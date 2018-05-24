package com.purdynet.siqproduct.controller;

import com.google.common.collect.Sets;
import com.purdynet.siqproduct.biqquery.BQClient;
import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.ProductProgress;
import com.purdynet.siqproduct.model.retailer.Retailer;
import com.purdynet.siqproduct.model.view.CompleteBreakoutEntry;
import com.purdynet.siqproduct.service.FreemarkerService;
import com.purdynet.siqproduct.service.RetailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.purdynet.siqproduct.biqquery.BQClient.convertTableRowToModel;
import static com.purdynet.siqproduct.biqquery.BQClient.runQuerySync;

@RestController
public class RetailerController {

    private final String projectId;
    private final RetailerService retailerService;
    private final FreemarkerService freemarkerService;
    @Autowired
    public RetailerController(@Value("${project.id}") String projectId,
                              final RetailerService retailerService,
                              final FreemarkerService freemarkerService) {
        this.projectId = projectId;
        this.retailerService = retailerService;
        this.freemarkerService = freemarkerService;
    }

    @RequestMapping(value = "/retailer/{id}/missing", produces = MediaType.TEXT_HTML_VALUE)
    public String missingHTML(@PathVariable("id") String requestId) {
        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("productProgressItems", missingJson(requestId));
        return freemarkerService.processTemplate("templates/RetailerDetailPage.ftl", dataModel);
    }

    @RequestMapping(value = "/ag/retailer/{id}/missing", produces = MediaType.TEXT_HTML_VALUE)
    public String missingAG(@PathVariable("id") String requestId) {
        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("agColumns", getCatalogAGCol());
        dataModel.put("dataUri", "/retailer/"+requestId+"/missing");
        return freemarkerService.processTemplate("templates/AGGridPage.ftl", dataModel);
    }

    @RequestMapping(value = "/retailer/{id}/missing", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductProgress> missingJson(@PathVariable("id") String requestId) {
        return runProductProgress(requestId, " WHERE c.description IS NULL ", 1000);
    }

    @RequestMapping(value = "/retailer/{id}/detail", produces = MediaType.TEXT_HTML_VALUE)
    public String detailHTML(@PathVariable("id") String requestId) {
        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("productProgressItems", detailJson(requestId));
        return freemarkerService.processTemplate("templates/RetailerDetailPage.ftl", dataModel);
    }

    @RequestMapping(value = "/ag/retailer/{id}/detail", produces = MediaType.TEXT_HTML_VALUE)
    public String detailAG(@PathVariable("id") String requestId) {
        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("agColumns", getCatalogAGCol());
        dataModel.put("dataUri", "/retailer/"+requestId+"/detail");
        return freemarkerService.processTemplate("templates/AGGridPage.ftl", dataModel);
    }

    @RequestMapping(value = "/retailer/{id}/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductProgress> detailJson(@PathVariable("id") String requestId) {
        return runProductProgress(requestId, "", 1000);
    }

    @RequestMapping(value = "/retailer/{id}/summary", produces = MediaType.TEXT_HTML_VALUE)
    public String summary(@PathVariable("id") String requestId) {
        return freemarkerService.processTemplate("templates/RetailerSummaryPage.ftl", summaryJson(requestId));
    }

    @RequestMapping(value = "/retailer/{id}/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> summaryJson(@PathVariable("id") String requestId) {
        List<ProductProgress> productProgressList = runProductProgress(requestId, "", null);

        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("overall", getTotalRevenueDept(productProgressList, (pp) -> "Total"));
        dataModel.put("upcPlu", getTotalRevenueDept(productProgressList, ProductProgress::getIsUpc));
        dataModel.put("dept", getTotalRevenueDept(productProgressList, ProductProgress::getRetailerDept));
        dataModel.put("topProducts", topNProducts(productProgressList, 25));
        return dataModel;
    }

    private List<CompleteBreakoutEntry> getTotalRevenueDept(List<ProductProgress> productProgressList, Function<ProductProgress,String> groupFunc) {
        Collector<ProductProgress, CompleteBreakoutEntry, CompleteBreakoutEntry> c1 = new Collector<ProductProgress, CompleteBreakoutEntry, CompleteBreakoutEntry>() {
            @Override
            public Supplier<CompleteBreakoutEntry> supplier() {
                return CompleteBreakoutEntry::new;
            }

            @Override
            public BiConsumer<CompleteBreakoutEntry, ProductProgress> accumulator() {
                return (c,pp) -> c.add(pp, groupFunc);
            }

            @Override
            public BinaryOperator<CompleteBreakoutEntry> combiner() {
                return CompleteBreakoutEntry::combine;
            }

            @Override
            public Function<CompleteBreakoutEntry, CompleteBreakoutEntry> finisher() {
                return (a) -> a;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Sets.immutableEnumSet(Characteristics.UNORDERED);
            }
        };

        Map<String, CompleteBreakoutEntry> collect = productProgressList.stream().collect(Collectors.groupingBy(groupFunc, c1));
        ArrayList<CompleteBreakoutEntry> completeBreakoutEntries = new ArrayList<>(collect.values());
        completeBreakoutEntries.sort(Comparator.comparing(CompleteBreakoutEntry::getIncompleteRevenue).reversed());
        return completeBreakoutEntries;
    }

    private List<ProductProgress> runProductProgress(final String requestId, final String where, final Integer limit) {
        return matchRetailer(requestId).map(retailer -> {
            BQClient BQClient = runQuerySync(projectId, retailerService.progressSql(retailer, limit, where));
            return makeProgressList(BQClient.getBqTableData());
        }).orElse(new ArrayList<>());
    }

    private List<ProductProgress> makeProgressList(BqTableData bqTableData) {
        return convertTableRowToModel(bqTableData, retailerService::productProgressOf);
    }

    private Optional<Retailer> matchRetailer(String requestId) {
        return retailerService.getRetailers().stream().filter(r -> r.projectId().equals(requestId)).findFirst();
    }

    private List<ProductProgress> topNProducts(List<ProductProgress> productProgress, Integer n) {
        return productProgress.stream()
                .filter(ProductProgress::isNotComplete)
                .sorted(Comparator.comparing(ProductProgress::getRevPortion).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    private String getCatalogAGCol() {
        return "    var columnDefs = [\n" +
                "      {headerName: \"itemId\", field: \"itemId\"},\n" +
                "      {headerName: \"manufacturer\", field: \"manufacturer\"},\n" +
                "      {headerName: \"retailerItemId\", field: \"retailerItemId\"},\n" +
                "      {headerName: \"revPortion\", field: \"revPortion\", valueFormatter: percentFormatter},\n" +
                "      {headerName: \"description\", field: \"description\"},\n" +
                "      {headerName: \"nacsCategory\", field: \"nacsCategory\"},\n" +
                "      {headerName: \"complete\", field: \"complete\"},\n" +
                "      {headerName: \"isUpc\", field: \"isUpc\"},\n" +
                "      {headerName: \"completeRevenue\", field: \"completeRevenue\", valueFormatter: percentFormatter},\n" +
                "      {headerName: \"incompleteRevenue\", field: \"incompleteRevenue\", valueFormatter: percentFormatter},\n" +
                "      {headerName: \"completeItems\", field: \"completeItems\"},\n" +
                "      {headerName: \"incompleteItems\", field: \"incompleteItems\"},\n" +
                "      {headerName: \"completeDeptRevenue\", field: \"completeDeptRevenue\", valueFormatter: percentFormatter},\n" +
                "      {headerName: \"incompleteDeptRevenue\", field: \"incompleteDeptRevenue\", valueFormatter: percentFormatter},\n" +
                "      {headerName: \"lastDate\", field: \"lastDate\", valueFormatter: dateFormatter},\n" +
                "    ];\n";
    }
}
