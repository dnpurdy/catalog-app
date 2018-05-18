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
        return " WHERE p.categorySubCode = '4' ";
    }

    @Override
    public String beverageClause() {
        return " WHERE p.categorySubCode IN ('7','9') ";
    }

    @Override
    public String tobaccoClause() {
        return " WHERE p.categorySubCode IN ('3','2') ";
    }

    @Override
    public String fixedUpc() {
        return "p.productId";
    }

    @Override
    public String mapJoin() {
        return "p.productId";
    }
}
