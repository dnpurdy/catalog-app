package com.purdynet.siqproduct.model.retailer;

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
        return " AND p.department = 'R02' AND categorySubCode LIKE '4%' ";
    }

    @Override
    public String beverageClause() {
        return " AND p.department IN ('111','R05','104') AND p.categorySubCode NOT IN ('78','79','80','81','612','930','931','932','933','934','935') ";
    }

    @Override
    public String tobaccoClause() {
        return " AND (p.department IN ('109','114','R03','R24') OR p.categorySubDescription IN ('SMOKELESS TOBACCO','PIPE AND CIGARETTE TOBACCO','CIGARS','OTHER TOBACCO PRODUCTS')) ";
    }

    @Override
    public String saltySnacksClause() {
        return " AND p.categorySubDescription IN ('OTHER SALTY SNACKS','POTATO CHIPS','TORTILLA/CORN CHIPS','CHIPS','NUTS/SEEDS','NUTS AND SEEDS','TORTILLA/CORN CHIPS','SALTY SNACKS OTHER','PRETZELS','CRACKERS','PACKAGED READY TO EAT POPCORN','DSD DIPS','POPCORN','PRETZELS') ";
    }
}
