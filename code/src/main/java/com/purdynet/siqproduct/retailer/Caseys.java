package com.purdynet.siqproduct.retailer;

import org.springframework.stereotype.Component;

@Component
public class Caseys implements Retailer {
    @Override
    public String name() {
        return "Caseys";
    }

    @Override
    public String projectId() {
        return "caseys-siq";
    }

    @Override
    public String beerClause() {
        return " WHERE p.department = '5' AND p.categorySubDescription NOT LIKE '%LIQUOR%' AND p.categorySubDescription NOT LIKE '%WINE%' ";
    }

    @Override
    public String beverageClause() {
        return " WHERE p.department IN ('10') ";
    }
}
