package com.purdynet.siqproduct.service;

import com.google.api.services.bigquery.model.TableDataInsertAllResponse;
import com.purdynet.siqproduct.biqquery.NamedRow;
import com.purdynet.siqproduct.model.items.CatalogItem;
import com.purdynet.siqproduct.model.items.EditItem;
import com.purdynet.siqproduct.model.items.NacsCategories;

import java.util.List;

public interface CatalogService {
    List<CatalogItem> getCatalog();
    List<CatalogItem> genNearMatches(final String upc);
    List<CatalogItem> getCatalogPartialItemId(final String partial);
    void updateCatalog();

    CatalogItem convert(EditItem editItem, NacsCategories nacsCategories);
    TableDataInsertAllResponse insertCatalogRow(CatalogItem catalogItem);

    CatalogItem catalogItemOf(NamedRow nr);

    boolean hasItemId(final String upc);
}
