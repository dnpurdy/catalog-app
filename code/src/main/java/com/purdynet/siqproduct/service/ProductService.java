package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.biqquery.NamedRow;
import com.purdynet.siqproduct.model.MissingItem;
import com.purdynet.siqproduct.model.ProductProgress;
import com.purdynet.siqproduct.model.retailer.Retailer;

import java.util.List;
import java.util.function.Function;

public interface ProductService {
    String productSql(Retailer retailer, Function<Retailer,String> typeClause, String upcPortion);
    String productProgress(List<Retailer> retailers, Function<Retailer,String> productSelectFunc, String upcPortion);

    MissingItem missingItemOf(NamedRow nr);
}
