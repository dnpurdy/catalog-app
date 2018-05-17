package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.MissingItem;
import com.purdynet.siqproduct.service.AllProductService;
import com.purdynet.siqproduct.service.BeerService;
import com.purdynet.siqproduct.biqquery.BigqueryUtils;
import com.purdynet.siqproduct.retailer.Retailer;
import com.purdynet.siqproduct.service.BeverageService;
import com.purdynet.siqproduct.service.TobaccoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.purdynet.siqproduct.util.HTMLUtils.toHTMLTableFromMising;

@RestController
public class MissingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final List<Retailer> retailers;
    private final AllProductService allProductService;
    private final BeerService beerService;
    private final BeverageService beverageService;
    private final TobaccoService tobaccoService;

    @Autowired
    public MissingController(List<Retailer> retailers, AllProductService allProductService,
                             BeerService beerService, BeverageService beverageService, TobaccoService tobaccoService) {
        this.retailers = retailers;
        this.allProductService = allProductService;
        this.beerService = beerService;
        this.beverageService = beverageService;
        this.tobaccoService = tobaccoService;
    }

    @RequestMapping(value = {"/missing","/missing/{upc}"})
    public String missing(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        BiFunction<List<Retailer>,String,String> test = allProductService::productProgress;
        return makeTable(test.apply(retailers, upc));
    }

    @RequestMapping(value = {"/missing-beer","/missing-beer/{upc}"})
    public String missingBeer(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        BiFunction<List<Retailer>,String,String> progressFunc = beerService::productProgress;
        return makeTable(progressFunc.apply(retailers, upc));
    }

    @RequestMapping(value = {"/missing-beverage","/missing-beverage/{upc}"})
    public String missingBeverage(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        BiFunction<List<Retailer>,String,String> progressFunc = beverageService::productProgress;
        return makeTable(progressFunc.apply(retailers, upc));
    }

    @RequestMapping(value = {"/missing-tobacco","/missing-tobacco/{upc}"})
    public String missingTobacco(@PathVariable(name = "upc", required = false) String upc) throws IOException {
        BiFunction<List<Retailer>,String,String> progressFunc = tobaccoService::productProgress;
        return makeTable(progressFunc.apply(retailers, upc));
    }

    private String makeTable(String sql) throws IOException {
        BigqueryUtils bigqueryUtils = new BigqueryUtils();

        logger.info(sql);

        bigqueryUtils.beginQuery(sql);

        bigqueryUtils.pollForCompletion();

        List<MissingItem> missingItems = makeMissingItemList(bigqueryUtils.getBqTableData());

        return toHTMLTableFromMising(missingItems);
    }

    private List<MissingItem> makeMissingItemList(BqTableData bqTableData) {
        if (bqTableData != null) {
            try {
                return bqTableData.getTableRowList().stream().map(MissingItem::of).collect(Collectors.toList());
            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }
}
