package com.purdynet.siqproduct;

import com.purdynet.siqproduct.biqquery.BigqueryUtils;
import com.purdynet.siqproduct.retailer.Retailer;
import com.purdynet.siqproduct.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import java.util.List;

import static com.purdynet.siqproduct.util.CSVUtils.toCSV;

//@SpringBootApplication
public class CommandLineApplication //implements CommandLineRunner
{

    @Autowired
    private List<Retailer> retailers;
    @Autowired
    private BeerService beerService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CommandLineApplication.class, args);
    }

    //access command line arguments
    //@Override
    public void run(String... args) throws Exception {
        if (System.getProperty("com.google.api.client.sample.bigquery.appengine.dashboard.projectId") == null) {
            System.setProperty("com.google.api.client.sample.bigquery.appengine.dashboard.projectId", "swiftiq-master");
        }

        BigqueryUtils bigqueryUtils = new BigqueryUtils();
        bigqueryUtils.beginQuery(beerService.productProgress(retailers));
        System.out.println(bigqueryUtils.getJobStatus());

        bigqueryUtils.pollForCompletion();
        System.out.println(bigqueryUtils.getJobStatus());

        printRows(toCSV(bigqueryUtils.getBqTableData()));

    }

    private void printRows(List<String> data) {
        data.forEach(row -> System.out.println(row));
    }
}
