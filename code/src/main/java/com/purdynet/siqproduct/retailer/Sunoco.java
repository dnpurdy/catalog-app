package com.purdynet.siqproduct.retailer;

import org.springframework.stereotype.Component;

@Component
public class Sunoco implements Retailer {
    @Override
    public String name() {
        return "Sunoco";
    }

    @Override
    public String projectId() {
        return "sunoco-siq";
    }

    @Override
    public String beerClause() {
        return " WHERE p.department = 'R02' AND categorySubCode LIKE '4%' ";
    }

    @Override
    public String beverageClause() {
        return " WHERE p.department IN ('111','R05','104') ";
    }

    @Override
    public String tobaccoClause() {
        return " WHERE (p.department IN ('109','114','R03','R24') OR p.categorySubDescription IN ('SMOKELESS TOBACCO','PIPE AND CIGARETTE TOBACCO','CIGARS','OTHER TOBACCO PRODUCTS')) ";
    }
}
