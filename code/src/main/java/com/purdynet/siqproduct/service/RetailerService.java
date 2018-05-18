package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.retailer.Retailer;
import org.springframework.stereotype.Service;

@Service
public class RetailerService {
    public String progressSql(Retailer retailer, Integer limit, String where) {
        return "SELECT pp.itemId itemId, pp.manufacturer manufacturer, pp.retailerItemId retailerItemId, pp.per revPortion, " +
                "NVL(c.description,pp.description) description, pp.deptDescription as retailerDept, c.category as nacsCategory, " +
                " IF(c.description IS NULL,\"TODO\",\"COMPLETE\") as complete, IF(" + retailer.isUpcLogic() + ",\"UPC\",\"PLU\") isUpc," +
                " IF(c.description IS NULL,0,pp.per) as completeRevenue, IF(c.description IS NULL,pp.per,0) as incompleteRevenue, " +
                " IF(c.description IS NULL,0,1) as completeItems, IF(c.description IS NULL,1,0) as incompleteItems, " +
                " IF(c.description IS NULL,0,pp.perDept) as completeDeptRevenue, IF(c.description IS NULL,pp.perDept,0) as incompleteDeptRevenue, pp.lastDate lastDate FROM ( " +
                getMappedBase(retailer, "") +
                " ) pp LEFT OUTER JOIN [swiftiq-master:siq.Catalog] c ON pp.itemId = c.upc " +
                where +
                " ORDER BY pp.per DESC " + (limit != null ? " LIMIT " + limit + " " : "");
    }

    public String getMappedBase(Retailer retailer, String productWhere) {
        return "SELECT NVL(rm.siqId,rev.itemId) itemId, rev.retailerItemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal, rev.lastDate lastDate, " +
                "RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (" +
                getBaseRev(retailer, productWhere) +
                ") rev LEFT OUTER JOIN [" + retailer.projectId() + ":siq.retailerMap] rm ON rev.retailerMapJoinField=rm.retailerId ORDER BY 6 DESC";
    }

    public String getBaseRev(Retailer retailer, String productWhere) {
        return "SELECT a.itemId retailerItemId, "+retailer.fixedUpc()+" as itemId, "+retailer.retailerMapJoinField()+" as retailerMapJoinField, p.description as description, " +
                "p.manufacturer as manufacturer, p.deptDescription as deptDescription, a.totalVal as totalVal, a.lastDate as lastDate FROM (" +
                "SELECT itemId, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal, MAX(dateProcessed) lastDate FROM ["+retailer.projectId()+":default.LineItem] GROUP BY 1) a " +
                "JOIN ["+retailer.projectId()+":default.Product] p ON a.itemId=p.upc WHERE 1=1 " +
                productWhere;
    }
}
