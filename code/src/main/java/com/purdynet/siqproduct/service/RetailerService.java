package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.model.retailer.Retailer;

import java.util.List;

public interface RetailerService {
   List<Retailer> getRetailers();
   String progressSql(Retailer retailer, Integer limit, String where);
   String getMappedBase(Retailer retailer, String productWhere);
   String getBaseRev(Retailer retailer, String productWhere);
}
