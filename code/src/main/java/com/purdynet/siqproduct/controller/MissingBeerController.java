package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.service.AllProductService;
import com.purdynet.siqproduct.service.BeerService;
import com.purdynet.siqproduct.biqquery.BigqueryUtils;
import com.purdynet.siqproduct.retailer.Retailer;
import com.purdynet.siqproduct.service.BeverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.purdynet.siqproduct.util.HTMLUtils.toHTMLTable;

@RestController
public class MissingBeerController {

    private final List<Retailer> retailers;
    private final AllProductService allProductService;
    private final BeerService beerService;
    private final BeverageService beverageService;

    @Autowired
    public MissingBeerController(List<Retailer> retailers, AllProductService allProductService,
                                 BeerService beerService, BeverageService beverageService) {
        this.retailers = retailers;
        this.allProductService = allProductService;
        this.beerService = beerService;
        this.beverageService = beverageService;
    }

    @RequestMapping("/missing")
    public String missing() throws IOException {
        BigqueryUtils bigqueryUtils = new BigqueryUtils();
        bigqueryUtils.beginQuery(allProductService.productProgress(retailers, ""));

        bigqueryUtils.pollForCompletion();

        return toHTMLTable(bigqueryUtils.getBqTableData());
    }

    @RequestMapping("/missing-beer")
    public String missingBeer() throws IOException {
        BigqueryUtils bigqueryUtils = new BigqueryUtils();
        bigqueryUtils.beginQuery(beerService.productProgress(retailers, ""));

        bigqueryUtils.pollForCompletion();

        return toHTMLTable(bigqueryUtils.getBqTableData());
    }

    @RequestMapping("/missing-beverage")
    public String missingBeverage() throws IOException {
        BigqueryUtils bigqueryUtils = new BigqueryUtils();
        bigqueryUtils.beginQuery(beverageService.productProgress(retailers, ""));

        bigqueryUtils.pollForCompletion();

        return toHTMLTable(bigqueryUtils.getBqTableData());
    }

    @RequestMapping("/missing-beverage/{upc}")
    public String missingBeverage(@PathVariable("upc") String upc) throws IOException {
        BigqueryUtils bigqueryUtils = new BigqueryUtils();
        bigqueryUtils.beginQuery(beverageService.productProgress(retailers, upc));

        bigqueryUtils.pollForCompletion();

        return toHTMLTable(bigqueryUtils.getBqTableData());
    }


}
