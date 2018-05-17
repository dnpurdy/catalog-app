package com.purdynet.siqproduct.retailer;

public interface Retailer {
    String name();
    String projectId();

    default String upcLogic() { return " NVL(rm.siqId,li.itemId) "; };
    default String productJoin() { return " li.itemId=rm.retailerId "; };
    default String productId() { return " itemId "; };

    default String allClause() { return ""; };
    String beerClause();
    String beverageClause();
    String tobaccoClause();
}
