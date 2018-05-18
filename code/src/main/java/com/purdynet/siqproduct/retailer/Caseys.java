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
        return " AND p.department = '5' AND p.categorySubDescription NOT LIKE '%LIQUOR%' AND p.categorySubDescription NOT LIKE '%WINE%' ";
    }

    @Override
    public String beverageClause() {
        return " AND p.department IN ('10') ";
    }

    @Override
    public String tobaccoClause() {
        return " AND ((p.department = '3' AND p.categorySubCode IN ('CHEW & MST','ALTERNATIVE TOBACCO','CHEW & MST SPECIALS','1 PACK CIGARS','2 PACK CIGARS','ROLL YOUR OWN','5 PACK CIGARS','20 PACK CIGARS','3 PACK CIGARS','LITTLE CIGARS','CARTON CIGARS','10 PACK CIGARS','DISCONTINUED CIGARS','LIGHTERS & ACCESSORIES','2 PACK SPECIALS','1 PACK SPECIALS','MISC NOVELTIES - DSD','2/5 PACK CIGARS','5 PACK SPECIALS','6 PACK CIGARS','2/6 PACK SPECIALS','4 PACK CIGARS','DISCONTINUED SPECIALS','2/5 PACK SPECIALS','7 PACK CIGARS','2/1 PACK SPECIALS')) OR (p.department = '11')) ";
    }
}
