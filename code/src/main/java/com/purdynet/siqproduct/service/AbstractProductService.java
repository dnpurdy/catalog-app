package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.retailer.Retailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractProductService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public abstract Function<Retailer,String> productSelectFunc();

    protected String productSql(Retailer retailer, Function<Retailer,String> typeClause, String upcPortion) {
        return "SELECT '"+ retailer.projectId()+"' projectId, "+retailer.upcLogic()+" itemId, p.description descript, p.manufacturer manufacturer, p.deptDescription deptDescript, lastDate, itemTotalVal totalVal, percentTotalVal percentTotalVal FROM (\n" +
                "    SELECT itemId, itemTotalVal, lastDate, itemTotalVal/totalVal AS percentTotalVal FROM ( SELECT itemId, itemTotalVal, lastDate, SUM(itemTotalVal) OVER () totalVal FROM (\n" +
                "    SELECT itemId, SUM(ABS(quantity*extendedAmount))/1000 itemTotalVal, MAX(dateProcessed) lastDate FROM ["+retailer.projectId()+":default.LineItem] GROUP BY 1 )) ORDER BY 3 DESC ) li JOIN ["+retailer.projectId()+":default.Product] p ON li.itemId = p.upc \n" +
                "  LEFT OUTER JOIN ["+retailer.projectId()+":siq.retailerMap] rm ON "+retailer.productJoin()+" " + typeClause.apply(retailer) +
                (StringUtils.isEmpty(upcPortion) ? "" : " AND " + retailer.upcLogic() + " LIKE '" + upcPortion + "%' ");
    }

    public String productProgress(List<Retailer> retailers, String upcPortion) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.itemId itemId, a.projectId projectId, a.numProjects numProjects, a.manufacturer manufacturer, a.descript description, a.rev.lastDate lastDate, a.rev.totalVal totalRevenue, a.per percentTotalRevenue FROM (\n" +
                " SELECT rev.itemId itemId, rev.projectId projectId, rev.numProjects numProjects, manufacturer, descript, rev.lastDate, rev.totalVal, rev.percentTotalVal as per FROM (\n" +
                "  SELECT GROUP_CONCAT(projectId) projectId, EXACT_COUNT_DISTINCT(projectId) numProjects, itemId, GROUP_CONCAT(descript) descript, MAX(deptDescript) deptDescript, GROUP_CONCAT(manufacturer) manufacturer, MAX(lastDate) lastDate, AVG(totalVal) totalVal, SUM(percentTotalVal * totalVal)/SUM(totalVal) percentTotalVal FROM");

        sql.append(String.join(",",retailers.stream().map(r -> "("+productSql(r, productSelectFunc(), upcPortion)+")").collect(Collectors.toList())));

        sql.append(" GROUP BY itemId\n" +
                " ) rev \n" +
                ") a LEFT OUTER JOIN [siq.Catalog] c ON a.itemId=c.upc\n" +
                "WHERE 1=1\n" +
                "AND c.upc IS NULL\n" +
                "ORDER BY a.numProjects DESC, a.per DESC");

        return sql.toString();
    }
}
