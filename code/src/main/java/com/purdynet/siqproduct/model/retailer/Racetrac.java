package com.purdynet.siqproduct.model.retailer;

import org.springframework.stereotype.Component;

@Component
public class Racetrac implements Retailer {
    @Override
    public String name() {
        return "Racetrac";
    }

    @Override
    public String projectId() {
        return "racetrac-siq";
    }

    @Override
    public String fixedUpc() {
        return "SUBSTR(p.upc,1,LENGTH(p.upc)-1)";
    }

    @Override
    public String beerClause() {
        return " AND p.department = '7' ";
    }

    @Override
    public String beverageClause() {
        return " AND p.department IN ('2','5') ";
    }

    @Override
    public String tobaccoClause() {
        return " AND p.department IN ('9','19') ";
    }

    @Override
    public String saltySnacksClause() {
        return " AND p.categorySubDescription IN ('SALTY SNACKS','NUTS-SEEDS-FRUIT') ";
    }
}
