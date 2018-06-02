package com.purdynet.siqproduct.model.health.impl;

import com.purdynet.siqproduct.biqquery.BQClient;
import com.purdynet.siqproduct.model.health.HealthCheck;
import com.purdynet.siqproduct.model.health.HealthCheckParams;
import com.purdynet.siqproduct.model.health.HealthResource;
import com.purdynet.siqproduct.model.retailer.Retailer;
import com.purdynet.siqproduct.service.RetailerService;
import com.purdynet.siqproduct.util.BQUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.purdynet.siqproduct.biqquery.BQClient.runQuerySync;

public abstract class AbstractCompletenessHealthCheck extends AbstractHealthCheck implements HealthCheck {

    private final NumberFormat percent = NumberFormat.getPercentInstance();

    private final RetailerService retailerService;

    public AbstractCompletenessHealthCheck(final RetailerService retailerService) {
        this.retailerService = retailerService;
        this.percent.setMaximumFractionDigits(3);
    }

    @Override
    public HealthResource generateResources() {
        return new HealthResource(getTypeName()+" Completeness", "Check completeness for "+getTypeName());
    }

    @Override
    public void runCheck(HealthResource healthResource, HealthCheckParams params) throws Exception {
        List<BigDecimal> scores = retailerService.getRetailers().parallelStream().map((retailer) -> {
            String sql = "SELECT SUM(completeRevenue) / SUM(completeRevenue+incompleteRevenue) precentComplete FROM (\n" +
                    retailerService.progressSql(retailer, null, "", getTypeClause(retailer)) +
                    ")";

            BQClient BQClient = runQuerySync("swiftiq-master", sql);
            BigDecimal bigDecimal = BQUtils.getBigDecimal(BQClient.getBqTableData().getTableRowList().get(0), 0);
            healthResource.addStat(retailer.name(), bigDecimal == null ? "n/a" : percent.format(bigDecimal));
            return bigDecimal == null ? BigDecimal.ONE : bigDecimal;
        }).collect(Collectors.toList());

        healthResource.setDescription("Minimum completeness: " + percent.format(Collections.min(scores)));
    }

    abstract String getTypeName();
    abstract String getTypeClause(Retailer retailer);
}
