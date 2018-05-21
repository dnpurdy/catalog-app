package com.purdynet.siqproduct.service.impl;

import com.purdynet.siqproduct.biqquery.NamedRow;
import com.purdynet.siqproduct.model.MissingItem;
import com.purdynet.siqproduct.model.ProductProgress;
import com.purdynet.siqproduct.model.retailer.Retailer;
import com.purdynet.siqproduct.service.ProductService;
import com.purdynet.siqproduct.service.RetailerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RetailerService retailerService;

    @Autowired
    public ProductServiceImpl(RetailerService retailerService) {
        this.retailerService = retailerService;
    }

    @Override
    public String productSql(Retailer retailer, Function<Retailer,String> typeClause, String upcPortion) {
        return "\n   --"+retailer.name()+"\n" +
                "   SELECT '"+ retailer.projectId()+"' projectId, itemId, description, manufacturer, deptDescription, lastDate, totalVal, per percentTotalVal FROM ( \n    " +
                retailerService.getMappedBase(retailer, typeClause.apply(retailer)) +
                ") WHERE 1=1 " +
                (StringUtils.isEmpty(upcPortion) ? "" : " AND itemId LIKE '" + upcPortion + "%' ");
    }

    @Override
    public String productProgress(List<Retailer> retailers, Function<Retailer,String> productSelectFunc, String upcPortion) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.itemId itemId, a.projectId projectId, a.numProjects numProjects, a.manufacturer manufacturer, a.description description, a.rev.lastDate lastDate, a.rev.totalVal totalRevenue, a.per percentTotalRevenue FROM (\n" +
                " SELECT rev.itemId itemId, rev.projectId projectId, rev.numProjects numProjects, manufacturer, description, rev.lastDate, rev.totalVal, rev.percentTotalVal as per FROM (\n" +
                "  SELECT GROUP_CONCAT(projectId) projectId, EXACT_COUNT_DISTINCT(projectId) numProjects, itemId, GROUP_CONCAT(description) description, MAX(deptDescription) deptDescription, GROUP_CONCAT(manufacturer) manufacturer, MAX(lastDate) lastDate, AVG(totalVal) totalVal, SUM(percentTotalVal * totalVal)/SUM(totalVal) percentTotalVal FROM");

        sql.append(String.join(",",retailers.stream().map(r -> "("+productSql(r, productSelectFunc, upcPortion)+")").collect(Collectors.toList())));

        sql.append(" GROUP BY itemId\n" +
                " ) rev \n" +
                ") a LEFT OUTER JOIN [siq.Catalog] c ON a.itemId=c.upc\n" +
                "WHERE 1=1\n" +
                "AND c.upc IS NULL\n" +
                "ORDER BY a.numProjects DESC, a.per DESC");
        return sql.toString();
    }

    @Override
    public MissingItem missingItemOf(NamedRow nr) {
        MissingItem missingItem = new MissingItem();
        missingItem.setItemId(nr.getString("itemId"));
        missingItem.setProjectId(nr.getString("projectId"));
        missingItem.setNumProjects(nr.getInteger("numProjects"));
        missingItem.setManufacturer(nr.getString("manufacturer"));
        missingItem.setDescription(nr.getString("description"));
        missingItem.setLastDate(nr.getDate("lastDate"));
        missingItem.setTotalRevenue(nr.getBigDecimal("totalRevenue"));
        missingItem.setPercentTotalRevenue(nr.getBigDecimal("percentTotalRevenue"));

        return missingItem;
    }
}
