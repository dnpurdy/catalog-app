package com.purdynet.siqproduct.retailer;

import org.springframework.stereotype.Component;

@Component
public class SaveALot implements Retailer {
    @Override
    public String name() {
        return "SaveALot";
    }

    @Override
    public String projectId() {
        return "savealot-siq";
    }

    @Override
    public String isUpcLogic() {
        return " ((LENGTH(pp.itemId) = 10 OR LENGTH(pp.itemId) == 11) AND NOT(pp.itemId LIKE '51933%')) ";
    }

    @Override
    public String beerClause() {
        return " WHERE 1=0 ";
    }

    @Override
    public String beverageClause() {
        return " WHERE 1=0 ";
    }

    @Override
    public String tobaccoClause() {
        return " WHERE 1=0 ";
    }
}
