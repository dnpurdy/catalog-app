package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.retailer.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceTest {

    private Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);

    private ProductService productService = new ProductService(new RetailerService());

    @Test
    public void productSql() {
        assertEquals("Caseys Missing Beer SQL doesn't match!",
                "\n" +
                        "   --Caseys\n" +
                        "   SELECT 'caseys-siq' projectId, itemId, description, manufacturer, deptDescription, lastDate, totalVal, per percentTotalVal FROM ( \n" +
                        "    SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, rev.lastDate lastDate, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.upc as retailerMapJoinField, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal, a.lastDate as lastDate FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal, MAX(dateProcessed) lastDate FROM [caseys-siq:default.LineItem] GROUP BY 1) a JOIN [caseys-siq:default.Product] p ON a.itemId=p.upc WHERE 1=1  AND p.department = '5' AND p.categorySubDescription NOT LIKE '%LIQUOR%' AND p.categorySubDescription NOT LIKE '%WINE%' ) rev LEFT OUTER JOIN [caseys-siq:siq.retailerMap] rm ON rev.retailerMapJoinField=rm.retailerId ORDER BY 6 DESC) WHERE 1=1 ",
                productService.productSql(new Caseys(), Retailer::beerClause,""));
        assertEquals("KumAndGo Missing Beer SQL doesn't match!",
                "\n" +
                        "   --Kum & Go\n" +
                        "   SELECT 'kg-siq' projectId, itemId, description, manufacturer, deptDescription, lastDate, totalVal, per percentTotalVal FROM ( \n" +
                        "    SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, rev.lastDate lastDate, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.upc as retailerMapJoinField, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal, a.lastDate as lastDate FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal, MAX(dateProcessed) lastDate FROM [kg-siq:default.LineItem] GROUP BY 1) a JOIN [kg-siq:default.Product] p ON a.itemId=p.upc WHERE 1=1  AND p.department IN ('4','104') ) rev LEFT OUTER JOIN [kg-siq:siq.retailerMap] rm ON rev.retailerMapJoinField=rm.retailerId ORDER BY 6 DESC) WHERE 1=1 ",
                productService.productSql(new KumAndGo(), Retailer::beerClause,""));

    }

    @Test
    public void productProgress() {
        assertEquals("Caseys Missing Beer SQL doesn't match!",
                "SELECT a.itemId itemId, a.projectId projectId, a.numProjects numProjects, a.manufacturer manufacturer, a.description description, a.rev.lastDate lastDate, a.rev.totalVal totalRevenue, a.per percentTotalRevenue FROM (\n" +
                        " SELECT rev.itemId itemId, rev.projectId projectId, rev.numProjects numProjects, manufacturer, description, rev.lastDate, rev.totalVal, rev.percentTotalVal as per FROM (\n" +
                        "  SELECT GROUP_CONCAT(projectId) projectId, EXACT_COUNT_DISTINCT(projectId) numProjects, itemId, GROUP_CONCAT(description) description, MAX(deptDescription) deptDescription, GROUP_CONCAT(manufacturer) manufacturer, MAX(lastDate) lastDate, AVG(totalVal) totalVal, SUM(percentTotalVal * totalVal)/SUM(totalVal) percentTotalVal FROM(\n" +
                        "   --Caseys\n" +
                        "   SELECT 'caseys-siq' projectId, itemId, description, manufacturer, deptDescription, lastDate, totalVal, per percentTotalVal FROM ( \n" +
                        "    SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, rev.lastDate lastDate, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.upc as retailerMapJoinField, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal, a.lastDate as lastDate FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal, MAX(dateProcessed) lastDate FROM [caseys-siq:default.LineItem] GROUP BY 1) a JOIN [caseys-siq:default.Product] p ON a.itemId=p.upc WHERE 1=1  AND p.department = '5' AND p.categorySubDescription NOT LIKE '%LIQUOR%' AND p.categorySubDescription NOT LIKE '%WINE%' ) rev LEFT OUTER JOIN [caseys-siq:siq.retailerMap] rm ON rev.retailerMapJoinField=rm.retailerId ORDER BY 6 DESC) WHERE 1=1 ) GROUP BY itemId\n" +
                        " ) rev \n" +
                        ") a LEFT OUTER JOIN [siq.Catalog] c ON a.itemId=c.upc\n" +
                        "WHERE 1=1\n" +
                        "AND c.upc IS NULL\n" +
                        "ORDER BY a.numProjects DESC, a.per DESC",
                productService.productProgress(ListUtils.asSingleton(new Caseys()), Retailer::beerClause,""));

        assertEquals("Caseys/KumAndGo Missing Beer SQL doesn't match!",
                "SELECT a.itemId itemId, a.projectId projectId, a.numProjects numProjects, a.manufacturer manufacturer, a.description description, a.rev.lastDate lastDate, a.rev.totalVal totalRevenue, a.per percentTotalRevenue FROM (\n" +
                        " SELECT rev.itemId itemId, rev.projectId projectId, rev.numProjects numProjects, manufacturer, description, rev.lastDate, rev.totalVal, rev.percentTotalVal as per FROM (\n" +
                        "  SELECT GROUP_CONCAT(projectId) projectId, EXACT_COUNT_DISTINCT(projectId) numProjects, itemId, GROUP_CONCAT(description) description, MAX(deptDescription) deptDescription, GROUP_CONCAT(manufacturer) manufacturer, MAX(lastDate) lastDate, AVG(totalVal) totalVal, SUM(percentTotalVal * totalVal)/SUM(totalVal) percentTotalVal FROM(\n" +
                        "   --Caseys\n" +
                        "   SELECT 'caseys-siq' projectId, itemId, description, manufacturer, deptDescription, lastDate, totalVal, per percentTotalVal FROM ( \n" +
                        "    SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, rev.lastDate lastDate, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.upc as retailerMapJoinField, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal, a.lastDate as lastDate FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal, MAX(dateProcessed) lastDate FROM [caseys-siq:default.LineItem] GROUP BY 1) a JOIN [caseys-siq:default.Product] p ON a.itemId=p.upc WHERE 1=1  AND p.department = '5' AND p.categorySubDescription NOT LIKE '%LIQUOR%' AND p.categorySubDescription NOT LIKE '%WINE%' ) rev LEFT OUTER JOIN [caseys-siq:siq.retailerMap] rm ON rev.retailerMapJoinField=rm.retailerId ORDER BY 6 DESC) WHERE 1=1 ),(\n" +
                        "   --Kum & Go\n" +
                        "   SELECT 'kg-siq' projectId, itemId, description, manufacturer, deptDescription, lastDate, totalVal, per percentTotalVal FROM ( \n" +
                        "    SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, rev.lastDate lastDate, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.upc as retailerMapJoinField, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal, a.lastDate as lastDate FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal, MAX(dateProcessed) lastDate FROM [kg-siq:default.LineItem] GROUP BY 1) a JOIN [kg-siq:default.Product] p ON a.itemId=p.upc WHERE 1=1  AND p.department IN ('4','104') ) rev LEFT OUTER JOIN [kg-siq:siq.retailerMap] rm ON rev.retailerMapJoinField=rm.retailerId ORDER BY 6 DESC) WHERE 1=1 ) GROUP BY itemId\n" +
                        " ) rev \n" +
                        ") a LEFT OUTER JOIN [siq.Catalog] c ON a.itemId=c.upc\n" +
                        "WHERE 1=1\n" +
                        "AND c.upc IS NULL\n" +
                        "ORDER BY a.numProjects DESC, a.per DESC",
                productService.productProgress(ListUtils.asList(new Caseys(), new KumAndGo()), Retailer::beerClause,""));
    }
}