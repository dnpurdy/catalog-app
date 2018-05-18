package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.retailer.KumAndGo;
import com.purdynet.siqproduct.retailer.Retailer;
import org.junit.Test;

import static org.junit.Assert.*;

public class RetailerServiceTest {

    private RetailerService retailerService = new RetailerService();

    @Test
    public void progressSql() {
        String progressSql = retailerService.progressSql(getTestRetailer(), null, "");
        assertEquals("SQL dpesn't match!", "SELECT pp.itemId itemId, pp.manufacturer manufacturer, pp.retailerItemId retailerItemId, pp.per revPortion, NVL(c.description,pp.description) description, pp.deptDescription as retailerDept, c.category as nacsCategory,  IF(c.description IS NULL,\"TODO\",\"COMPLETE\") as complete, IF( (LENGTH(pp.itemId) = 10 OR LENGTH(pp.itemId) == 11) ,\"UPC\",\"PLU\") isUpc, IF(c.description IS NULL,0,pp.per) as completeRevenue, IF(c.description IS NULL,pp.per,0) as incompleteRevenue,  IF(c.description IS NULL,0,1) as completeItems, IF(c.description IS NULL,1,0) as incompleteItems,  IF(c.description IS NULL,0,pp.perDept) as completeDeptRevenue, IF(c.description IS NULL,pp.perDept,0) as incompleteDeptRevenue FROM ( SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.upc as mapJoin, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal FROM [testretailer-siq:default.LineItem] GROUP BY 1) a JOIN [testretailer-siq:default.Product] p ON a.itemId=p.upc) rev LEFT OUTER JOIN [testretailer-siq:siq.retailerMap] rm ON rev.mapJoin=rm.retailerId ORDER BY 6 DESC ) pp LEFT OUTER JOIN [swiftiq-master:siq.Catalog] c ON pp.itemId = c.upc  ORDER BY pp.per DESC ", progressSql);

        String progressSql2 = retailerService.progressSql(getTestRetailer2(), null, "");
        assertEquals("SQL dpesn't match!", "SELECT pp.itemId itemId, pp.manufacturer manufacturer, pp.retailerItemId retailerItemId, pp.per revPortion, NVL(c.description,pp.description) description, pp.deptDescription as retailerDept, c.category as nacsCategory,  IF(c.description IS NULL,\"TODO\",\"COMPLETE\") as complete, IF( (LENGTH(pp.itemId) = 10 OR LENGTH(pp.itemId) == 11) ,\"UPC\",\"PLU\") isUpc, IF(c.description IS NULL,0,pp.per) as completeRevenue, IF(c.description IS NULL,pp.per,0) as incompleteRevenue,  IF(c.description IS NULL,0,1) as completeItems, IF(c.description IS NULL,1,0) as incompleteItems,  IF(c.description IS NULL,0,pp.perDept) as completeDeptRevenue, IF(c.description IS NULL,pp.perDept,0) as incompleteDeptRevenue FROM ( SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.productId as mapJoin, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal FROM [testretailer-siq:default.LineItem] GROUP BY 1) a JOIN [testretailer-siq:default.Product] p ON a.itemId=p.upc) rev LEFT OUTER JOIN [testretailer-siq:siq.retailerMap] rm ON rev.mapJoin=rm.retailerId ORDER BY 6 DESC ) pp LEFT OUTER JOIN [swiftiq-master:siq.Catalog] c ON pp.itemId = c.upc  ORDER BY pp.per DESC ", progressSql2);
    }

    @Test
    public void progressSql1() {
        String progressSql = retailerService.progressSql(getTestRetailer(), 1000, "");
        assertEquals("SQL dpesn't match!", "SELECT pp.itemId itemId, pp.manufacturer manufacturer, pp.retailerItemId retailerItemId, pp.per revPortion, NVL(c.description,pp.description) description, pp.deptDescription as retailerDept, c.category as nacsCategory,  IF(c.description IS NULL,\"TODO\",\"COMPLETE\") as complete, IF( (LENGTH(pp.itemId) = 10 OR LENGTH(pp.itemId) == 11) ,\"UPC\",\"PLU\") isUpc, IF(c.description IS NULL,0,pp.per) as completeRevenue, IF(c.description IS NULL,pp.per,0) as incompleteRevenue,  IF(c.description IS NULL,0,1) as completeItems, IF(c.description IS NULL,1,0) as incompleteItems,  IF(c.description IS NULL,0,pp.perDept) as completeDeptRevenue, IF(c.description IS NULL,pp.perDept,0) as incompleteDeptRevenue FROM ( SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.upc as mapJoin, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal FROM [testretailer-siq:default.LineItem] GROUP BY 1) a JOIN [testretailer-siq:default.Product] p ON a.itemId=p.upc) rev LEFT OUTER JOIN [testretailer-siq:siq.retailerMap] rm ON rev.mapJoin=rm.retailerId ORDER BY 6 DESC ) pp LEFT OUTER JOIN [swiftiq-master:siq.Catalog] c ON pp.itemId = c.upc  ORDER BY pp.per DESC  LIMIT 1000 ", progressSql);

        String progressSql2 = retailerService.progressSql(getTestRetailer2(), 1000, "");
        assertEquals("SQL dpesn't match!", "SELECT pp.itemId itemId, pp.manufacturer manufacturer, pp.retailerItemId retailerItemId, pp.per revPortion, NVL(c.description,pp.description) description, pp.deptDescription as retailerDept, c.category as nacsCategory,  IF(c.description IS NULL,\"TODO\",\"COMPLETE\") as complete, IF( (LENGTH(pp.itemId) = 10 OR LENGTH(pp.itemId) == 11) ,\"UPC\",\"PLU\") isUpc, IF(c.description IS NULL,0,pp.per) as completeRevenue, IF(c.description IS NULL,pp.per,0) as incompleteRevenue,  IF(c.description IS NULL,0,1) as completeItems, IF(c.description IS NULL,1,0) as incompleteItems,  IF(c.description IS NULL,0,pp.perDept) as completeDeptRevenue, IF(c.description IS NULL,pp.perDept,0) as incompleteDeptRevenue FROM ( SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.productId as mapJoin, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal FROM [testretailer-siq:default.LineItem] GROUP BY 1) a JOIN [testretailer-siq:default.Product] p ON a.itemId=p.upc) rev LEFT OUTER JOIN [testretailer-siq:siq.retailerMap] rm ON rev.mapJoin=rm.retailerId ORDER BY 6 DESC ) pp LEFT OUTER JOIN [swiftiq-master:siq.Catalog] c ON pp.itemId = c.upc  ORDER BY pp.per DESC  LIMIT 1000 ", progressSql2);
    }

    private Retailer getTestRetailer() {
        return new Retailer() {
            @Override
            public String name() {
                return "TestRetailer";
            }

            @Override
            public String projectId() {
                return "testretailer-siq";
            }

            @Override
            public String beerClause() {
                return "";
            }

            @Override
            public String beverageClause() {
                return "";
            }

            @Override
            public String tobaccoClause() {
                return "";
            }
        };
    }

    private Retailer getTestRetailer2() {
        return new Retailer() {
            @Override
            public String name() {
                return "TestRetailer";
            }

            @Override
            public String projectId() {
                return "testretailer-siq";
            }

            @Override
            public String upcLogic() {
                return " NVL(rm.siqId,p.productId) ";
            }

            @Override
            public String productJoin() {
                return " p.productId=rm.retailerId ";
            }

            @Override
            public String productId() {
                return " li.productId ";
            }

            @Override
            public String beerClause() {
                return "";
            }

            @Override
            public String beverageClause() {
                return "";
            }

            @Override
            public String tobaccoClause() {
                return "";
            }

            @Override
            public String mapJoin() {
                return "p.productId";
            }
        };
    }
}