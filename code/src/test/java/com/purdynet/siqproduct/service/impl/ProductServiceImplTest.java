package com.purdynet.siqproduct.service.impl;

import com.purdynet.siqproduct.BaseTest;
import com.purdynet.siqproduct.model.retailer.Caseys;
import com.purdynet.siqproduct.model.retailer.KumAndGo;
import com.purdynet.siqproduct.model.retailer.Retailer;
import com.purdynet.siqproduct.service.RetailerService;
import com.purdynet.siqproduct.util.ListUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ProductServiceImplTest extends BaseTest {

    private final RetailerService retailerService = mock(RetailerService.class);

    private final ProductServiceImpl productServiceImpl = new ProductServiceImpl(retailerService);

    @Override
    public void setUp() {
        super.setUp();
        given(retailerService.getMappedBase(any(Caseys.class), any())).willReturn("SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, rev.lastDate lastDate, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.upc as retailerMapJoinField, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal, a.lastDate as lastDate FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal, MAX(dateProcessed) lastDate FROM [caseys-siq:default.LineItem] GROUP BY 1) a JOIN [caseys-siq:default.Product] p ON a.itemId=p.upc WHERE 1=1  AND p.department = '5' AND p.categorySubDescription NOT LIKE '%LIQUOR%' AND p.categorySubDescription NOT LIKE '%WINE%' ) rev LEFT OUTER JOIN [caseys-siq:siq.retailerMap] rm ON rev.retailerMapJoinField=rm.retailerId ORDER BY 6 DESC");
        given(retailerService.getMappedBase(any(KumAndGo.class), any())).willReturn("SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, rev.lastDate lastDate, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.upc as retailerMapJoinField, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal, a.lastDate as lastDate FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal, MAX(dateProcessed) lastDate FROM [kg-siq:default.LineItem] GROUP BY 1) a JOIN [kg-siq:default.Product] p ON a.itemId=p.upc WHERE 1=1  AND p.department IN ('4','104') ) rev LEFT OUTER JOIN [kg-siq:siq.retailerMap] rm ON rev.retailerMapJoinField=rm.retailerId ORDER BY 6 DESC");
    }

    @Test
    public void productSql() {
        assertEquals("Caseys Missing Beer SQL doesn't match!",
                "\n" +
                        "   --Caseys\n" +
                        "   SELECT 'caseys-siq' projectId, itemId, description, manufacturer, deptDescription, lastDate, totalVal, per percentTotalVal FROM ( \n" +
                        "    SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, rev.lastDate lastDate, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.upc as retailerMapJoinField, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal, a.lastDate as lastDate FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal, MAX(dateProcessed) lastDate FROM [caseys-siq:default.LineItem] GROUP BY 1) a JOIN [caseys-siq:default.Product] p ON a.itemId=p.upc WHERE 1=1  AND p.department = '5' AND p.categorySubDescription NOT LIKE '%LIQUOR%' AND p.categorySubDescription NOT LIKE '%WINE%' ) rev LEFT OUTER JOIN [caseys-siq:siq.retailerMap] rm ON rev.retailerMapJoinField=rm.retailerId ORDER BY 6 DESC) WHERE 1=1 ",
                productServiceImpl.productSql(new Caseys(), Retailer::beerClause,""));

        assertEquals("KumAndGo Missing Beer SQL doesn't match!",
                "\n" +
                        "   --Kum & Go\n" +
                        "   SELECT 'kg-siq' projectId, itemId, description, manufacturer, deptDescription, lastDate, totalVal, per percentTotalVal FROM ( \n" +
                        "    SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, rev.lastDate lastDate, RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (SELECT a.itemId retailerItemId, p.upc as itemId, p.upc as retailerMapJoinField, p.description as description, p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal, a.lastDate as lastDate FROM (SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal, MAX(dateProcessed) lastDate FROM [kg-siq:default.LineItem] GROUP BY 1) a JOIN [kg-siq:default.Product] p ON a.itemId=p.upc WHERE 1=1  AND p.department IN ('4','104') ) rev LEFT OUTER JOIN [kg-siq:siq.retailerMap] rm ON rev.retailerMapJoinField=rm.retailerId ORDER BY 6 DESC) WHERE 1=1 ",
                productServiceImpl.productSql(new KumAndGo(), Retailer::beerClause,""));

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
                productServiceImpl.productProgress(ListUtils.asSingleton(new Caseys()), Retailer::beerClause,""));

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
                productServiceImpl.productProgress(ListUtils.asList(new Caseys(), new KumAndGo()), Retailer::beerClause,""));
    }
}