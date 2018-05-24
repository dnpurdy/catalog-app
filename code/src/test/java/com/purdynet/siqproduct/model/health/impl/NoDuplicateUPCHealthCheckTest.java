package com.purdynet.siqproduct.model.health.impl;

import com.purdynet.siqproduct.model.health.HealthCheckParams;
import com.purdynet.siqproduct.model.health.HealthResource;
import com.purdynet.siqproduct.model.items.CatalogItem;
import com.purdynet.siqproduct.service.CatalogService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.purdynet.siqproduct.model.health.HealthEnum.BROKEN;
import static com.purdynet.siqproduct.model.health.HealthEnum.HEALTHY;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class NoDuplicateUPCHealthCheckTest {

    private CatalogService catalogService = mock(CatalogService.class);
    private NoDuplicateUPCHealthCheck check = new NoDuplicateUPCHealthCheck(catalogService);

    @Test
    public void runCheck() throws Exception {
        HealthResource healthResource = new HealthResource("Test", "Test Desciption");

        given(catalogService.getCatalog()).willReturn(cleanCatalog());
        check.runCheck(healthResource, new HealthCheckParams());
        assertEquals(healthResource.getHealthEnum(), HEALTHY);
        assertEquals(healthResource.getDescription(), "Test Desciption");

        given(catalogService.getCatalog()).willReturn(dirtyCatalog());
        check.runCheck(healthResource, new HealthCheckParams());
        assertEquals(healthResource.getHealthEnum(), BROKEN);
        assertEquals(healthResource.getDescription(), "Duplicate UPCs found!");
    }

    private List<CatalogItem> cleanCatalog() {
        List<CatalogItem> catalogItemsList = new ArrayList<>();
        CatalogItem catalogItem1 = new CatalogItem();
        catalogItem1.setItemId("1234512345");
        catalogItem1.setManufacturer("Manu1");
        catalogItemsList.add(catalogItem1);
        CatalogItem catalogItem2 = new CatalogItem();
        catalogItem2.setItemId("1234523456");
        catalogItem2.setManufacturer("Manu1");
        catalogItemsList.add(catalogItem2);
        return catalogItemsList;
    }

    private List<CatalogItem> dirtyCatalog() {
        List<CatalogItem> catalogItemsList = cleanCatalog();
        CatalogItem catalogItem1 = new CatalogItem();
        catalogItem1.setItemId("1234512345");
        catalogItem1.setManufacturer("Manu2");
        catalogItemsList.add(catalogItem1);
        return catalogItemsList;
    }
}