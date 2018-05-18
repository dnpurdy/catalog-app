package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.retailer.Retailer;
import org.springframework.stereotype.Service;

@Service
public class RetailerService {
    public String progressSql(Retailer retailer) {
        return progressSql(retailer, null, "");
    }

    public String progressSql(Retailer retailer, Integer limit) {
        return progressSql(retailer, limit, "");
    }

    public String progressSql(Retailer retailer, Integer limit, String where) {
        return "SELECT pp.itemId itemId, pp.manufacturer manufacturer, pp.retailerItemId retailerItemId, pp.per revPortion, NVL(c.description,pp.description) description, pp.deptDescription as retailerDept, c.category as nacsCategory, \n"+
                " IF(c.description IS NULL,\"TODO\",\"COMPLETE\") as complete, IF("+retailer.isUpcLogic()+",\"UPC\",\"PLU\") isUpc,\n"+
                " IF(c.description IS NULL,0,pp.per) as completeRevenue, IF(c.description IS NULL,pp.per,0) as incompleteRevenue, \n"+
                " IF(c.description IS NULL,0,1) as completeItems, IF(c.description IS NULL,1,0) as incompleteItems,\n"+
                " IF(c.description IS NULL,0,pp.perDept) as completeDeptRevenue, IF(c.description IS NULL,pp.perDept,0) as incompleteDeptRevenue FROM ( \n"+
                "  SELECT NVL(rm.siqId, rev.itemId) itemId, rev.itemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal,\n"+
                "  RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (\n"+
                "   SELECT "+retailer.productId()+" as itemId, p.description, p.manufacturer, p.deptDescription, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal FROM ["+retailer.projectId()+":default.LineItem] li JOIN ["+retailer.projectId()+":default.Product] p ON li.itemId = p.upc GROUP BY 1,2,3,4\n"+
                "  ) rev LEFT OUTER JOIN ["+retailer.projectId()+":siq.retailerMap] rm ON rev.itemId=rm.retailerId ORDER BY 4 DESC\n"+
                " ) pp LEFT OUTER JOIN [swiftiq-master:siq.Catalog] c ON pp.itemId = c.upc\n" +
                where +
                " ORDER BY pp.per DESC " + (limit != null ? " LIMIT " + limit + " " : "");
    }
}
