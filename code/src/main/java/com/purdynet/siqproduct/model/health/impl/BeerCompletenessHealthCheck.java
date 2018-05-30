package com.purdynet.siqproduct.model.health.impl;

import com.purdynet.siqproduct.model.health.HealthCheck;
import com.purdynet.siqproduct.model.retailer.Retailer;
import com.purdynet.siqproduct.service.RetailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeerCompletenessHealthCheck extends AbstractCompletenessHealthCheck implements HealthCheck {

    @Autowired
    public BeerCompletenessHealthCheck(final RetailerService retailerService) {
        super(retailerService);
    }

    @Override
    String getTypeName() {
        return "Beer";
    }

    @Override
    String getTypeClause(Retailer retailer) {
        return retailer.beerClause();
    }
}
