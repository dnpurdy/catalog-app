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
}
