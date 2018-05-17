package com.purdynet.siqproduct.retailer;

import org.springframework.stereotype.Component;

@Component
public class KumAndGo implements Retailer {
    @Override
    public String name() {
        return "Kum & Go";
    }

    @Override
    public String projectId() {
        return "kg-siq";
    }

    @Override
    public String beerClause() {
        return " WHERE p.department IN ('4','104') ";
    }

    @Override
    public String beverageClause() {
        return " WHERE p.deptDescription IN ('CSD DEPOSITS','ENERGY DRINKS','INVENTORY JUICES/TONICS','INVENTORY POP') ";
    }

    @Override
    public String tobaccoClause() {
        return " WHERE p.department IN ('3','28','103','128') ";
    }
}
