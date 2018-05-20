package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.model.CatalogItem;

import java.util.List;

public interface CatalogService {
    List<CatalogItem> getCatalog();
    List<CatalogItem> genNearMatches(final String upc);
    List<CatalogItem> getCatalogPartialItemId(final String partial);
    void updateCatalog();
}
