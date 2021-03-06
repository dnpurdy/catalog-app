package com.purdynet.siqproduct.model.retailer;

public interface Retailer {
    String name();
    String projectId();

    default String isUpcLogic() { return " (LENGTH(pp.itemId) = 10 OR LENGTH(pp.itemId) == 11) "; }

    default String allClause() { return ""; }

    default String fixedUpc() { return "p.upc"; }

    default String retailerMapJoinField() { return "p.upc"; }

    String beerClause();
    String beverageClause();
    String tobaccoClause();
    String saltySnacksClause();
}
