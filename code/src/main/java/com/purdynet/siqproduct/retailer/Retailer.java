package com.purdynet.siqproduct.retailer;

public interface Retailer {
    String name();
    String projectId();

    default String upcLogic() {
        return " NVL(rm.siqId,li.itemId) ";
    };

    String beerClause();
}
