package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.retailer.Retailer;
import org.junit.Test;

import static org.junit.Assert.*;

public class RetailerServiceTest {

    RetailerService retailerService = new RetailerService();

    @Test
    public void progressSql() {
        String progressSql = retailerService.progressSql(getTestRetailer());
        assertEquals("SQL dpesn't match!", "SELECT pp.itemId itemId, pp.manufacturer manufacturer, pp.retailerItemId retailerItemId, pp.per revPortion, NVL(c.description,pp.description) description, pp.deptDescription as retailerDept, c.category as nacsCategory, \n" +
                " IF(c.description IS NULL,\"TODO\",\"COMPLETE\") as complete, IF(LENGTH(pp.itemId) = 10 OR LENGTH(pp.itemId) == 11,\"UPC\",\"PLU\") isUpc,\n" +
                " IF(c.description IS NULL,0,pp.per) as completeRevenue, IF(c.description IS NULL,pp.per,0) as incompleteRevenue, \n" +
                " IF(c.description IS NULL,0,1) as completeItems, IF(c.description IS NULL,1,0) as incompleteItems,\n" +
                " IF(c.description IS NULL,0,pp.perDept) as completeDeptRevenue, IF(c.description IS NULL,pp.perDept,0) as incompleteDeptRevenue FROM ( \n" +
                "  SELECT NVL(rm.siqId, rev.itemId) itemId, rev.itemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal,\n" +
                "  RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (\n" +
                "   SELECT  itemId  as itemId, p.description, p.manufacturer, p.deptDescription, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal FROM [testretailer-siq:default.LineItem] li JOIN [testretailer-siq:default.Product] p ON li.itemId = p.upc GROUP BY 1,2,3,4\n" +
                "  ) rev LEFT OUTER JOIN [testretailer-siq:siq.retailerMap] rm ON rev.itemId=rm.retailerId ORDER BY 4 DESC\n" +
                " ) pp LEFT OUTER JOIN [swiftiq-master:siq.Catalog] c ON pp.itemId = c.upc\n" +
                " ORDER BY pp.per DESC ", progressSql);

        String progressSql2 = retailerService.progressSql(getTestRetailer2());
        assertEquals("SQL dpesn't match!", "SELECT pp.itemId itemId, pp.manufacturer manufacturer, pp.retailerItemId retailerItemId, pp.per revPortion, NVL(c.description,pp.description) description, pp.deptDescription as retailerDept, c.category as nacsCategory, \n" +
                " IF(c.description IS NULL,\"TODO\",\"COMPLETE\") as complete, IF(LENGTH(pp.itemId) = 10 OR LENGTH(pp.itemId) == 11,\"UPC\",\"PLU\") isUpc,\n" +
                " IF(c.description IS NULL,0,pp.per) as completeRevenue, IF(c.description IS NULL,pp.per,0) as incompleteRevenue, \n" +
                " IF(c.description IS NULL,0,1) as completeItems, IF(c.description IS NULL,1,0) as incompleteItems,\n" +
                " IF(c.description IS NULL,0,pp.perDept) as completeDeptRevenue, IF(c.description IS NULL,pp.perDept,0) as incompleteDeptRevenue FROM ( \n" +
                "  SELECT NVL(rm.siqId, rev.itemId) itemId, rev.itemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal,\n" +
                "  RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (\n" +
                "   SELECT  li.productId  as itemId, p.description, p.manufacturer, p.deptDescription, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal FROM [testretailer-siq:default.LineItem] li JOIN [testretailer-siq:default.Product] p ON li.itemId = p.upc GROUP BY 1,2,3,4\n" +
                "  ) rev LEFT OUTER JOIN [testretailer-siq:siq.retailerMap] rm ON rev.itemId=rm.retailerId ORDER BY 4 DESC\n" +
                " ) pp LEFT OUTER JOIN [swiftiq-master:siq.Catalog] c ON pp.itemId = c.upc\n" +
                " ORDER BY pp.per DESC ", progressSql2);
    }

    @Test
    public void progressSql1() {
        String progressSql = retailerService.progressSql(getTestRetailer(), 1000);
        assertEquals("SQL dpesn't match!", "SELECT pp.itemId itemId, pp.manufacturer manufacturer, pp.retailerItemId retailerItemId, pp.per revPortion, NVL(c.description,pp.description) description, pp.deptDescription as retailerDept, c.category as nacsCategory, \n" +
                " IF(c.description IS NULL,\"TODO\",\"COMPLETE\") as complete, IF(LENGTH(pp.itemId) = 10 OR LENGTH(pp.itemId) == 11,\"UPC\",\"PLU\") isUpc,\n" +
                " IF(c.description IS NULL,0,pp.per) as completeRevenue, IF(c.description IS NULL,pp.per,0) as incompleteRevenue, \n" +
                " IF(c.description IS NULL,0,1) as completeItems, IF(c.description IS NULL,1,0) as incompleteItems,\n" +
                " IF(c.description IS NULL,0,pp.perDept) as completeDeptRevenue, IF(c.description IS NULL,pp.perDept,0) as incompleteDeptRevenue FROM ( \n" +
                "  SELECT NVL(rm.siqId, rev.itemId) itemId, rev.itemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal,\n" +
                "  RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (\n" +
                "   SELECT  itemId  as itemId, p.description, p.manufacturer, p.deptDescription, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal FROM [testretailer-siq:default.LineItem] li JOIN [testretailer-siq:default.Product] p ON li.itemId = p.upc GROUP BY 1,2,3,4\n" +
                "  ) rev LEFT OUTER JOIN [testretailer-siq:siq.retailerMap] rm ON rev.itemId=rm.retailerId ORDER BY 4 DESC\n" +
                " ) pp LEFT OUTER JOIN [swiftiq-master:siq.Catalog] c ON pp.itemId = c.upc\n" +
                " ORDER BY pp.per DESC  LIMIT 1000 ", progressSql);

        String progressSql2 = retailerService.progressSql(getTestRetailer2(), 1000);
        assertEquals("SQL dpesn't match!", "SELECT pp.itemId itemId, pp.manufacturer manufacturer, pp.retailerItemId retailerItemId, pp.per revPortion, NVL(c.description,pp.description) description, pp.deptDescription as retailerDept, c.category as nacsCategory, \n" +
                " IF(c.description IS NULL,\"TODO\",\"COMPLETE\") as complete, IF(LENGTH(pp.itemId) = 10 OR LENGTH(pp.itemId) == 11,\"UPC\",\"PLU\") isUpc,\n" +
                " IF(c.description IS NULL,0,pp.per) as completeRevenue, IF(c.description IS NULL,pp.per,0) as incompleteRevenue, \n" +
                " IF(c.description IS NULL,0,1) as completeItems, IF(c.description IS NULL,1,0) as incompleteItems,\n" +
                " IF(c.description IS NULL,0,pp.perDept) as completeDeptRevenue, IF(c.description IS NULL,pp.perDept,0) as incompleteDeptRevenue FROM ( \n" +
                "  SELECT NVL(rm.siqId, rev.itemId) itemId, rev.itemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal,\n" +
                "  RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (\n" +
                "   SELECT  li.productId  as itemId, p.description, p.manufacturer, p.deptDescription, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal FROM [testretailer-siq:default.LineItem] li JOIN [testretailer-siq:default.Product] p ON li.itemId = p.upc GROUP BY 1,2,3,4\n" +
                "  ) rev LEFT OUTER JOIN [testretailer-siq:siq.retailerMap] rm ON rev.itemId=rm.retailerId ORDER BY 4 DESC\n" +
                " ) pp LEFT OUTER JOIN [swiftiq-master:siq.Catalog] c ON pp.itemId = c.upc\n" +
                " ORDER BY pp.per DESC  LIMIT 1000 ", progressSql2);
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
        };
    }
}