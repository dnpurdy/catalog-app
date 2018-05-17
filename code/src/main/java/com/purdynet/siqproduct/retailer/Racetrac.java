package com.purdynet.siqproduct.retailer;

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
    public String upcLogic() {
        return " IF(rm.siqId IS NULL,SUBSTR(li.itemId,1,LENGTH(li.itemId)-1),rm.siqId) ";
    }

    @Override
    public String beerClause() {
        return " WHERE p.department = '7' ";
    }

    @Override
    public String beverageClause() {
        return " WHERE p.department IN ('2','5') ";
    }

    @Override
    public String tobaccoClause() {
        return " WHERE p.department IN ('9','19') ";
    }
}
