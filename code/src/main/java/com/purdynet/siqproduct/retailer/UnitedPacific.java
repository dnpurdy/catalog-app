package com.purdynet.siqproduct.retailer;

import org.springframework.stereotype.Component;

@Component
public class UnitedPacific implements Retailer {
    @Override
    public String name() {
        return "UnitedPacific";
    }

    @Override
    public String projectId() {
        return "unitedpacific-siq";
    }

    @Override
    public String fixedUpc() {
        return "p.productId";
    }

    @Override
    public String retailerMapJoinField() {
        return "p.productId";
    }

    @Override
    public String beerClause() {
        return " AND p.categorySubCode = '4' ";
    }

    @Override
    public String beverageClause() {
        return " AND p.categorySubCode IN ('7','9') ";
    }

    @Override
    public String tobaccoClause() {
        return " AND p.categorySubCode IN ('3','2') ";
    }
}
