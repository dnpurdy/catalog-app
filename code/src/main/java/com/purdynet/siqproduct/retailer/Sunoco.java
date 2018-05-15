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
}
