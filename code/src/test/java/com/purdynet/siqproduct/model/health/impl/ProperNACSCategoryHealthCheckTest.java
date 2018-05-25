package com.purdynet.siqproduct.model.health.impl;

import com.purdynet.siqproduct.model.health.HealthCheckParams;
import com.purdynet.siqproduct.model.health.HealthResource;
import com.purdynet.siqproduct.model.items.CatalogItem;
import com.purdynet.siqproduct.service.CatalogService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.purdynet.siqproduct.model.health.HealthEnum.DEGRADED;
import static com.purdynet.siqproduct.model.health.HealthEnum.HEALTHY;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ProperNACSCategoryHealthCheckTest {

    private CatalogService catalogService = mock(CatalogService.class);
    private ProperNACSCategoryHealthCheck check = new ProperNACSCategoryHealthCheck(catalogService);

    @Test
    public void runCheck() throws Exception {
        HealthResource healthResource = new HealthResource("Test", "Test Desciption");

        given(catalogService.getCatalog()).willReturn(cleanCatalog());
        check.runCheck(healthResource, new HealthCheckParams());
        assertEquals(HEALTHY, healthResource.getHealthEnum());
        assertEquals("Test Desciption", healthResource.getDescription());

        given(catalogService.getCatalog()).willReturn(dirtyCatalog());
        check.runCheck(healthResource, new HealthCheckParams());
        assertEquals(DEGRADED, healthResource.getHealthEnum());
        assertEquals("Unknown NACS category code in catalog!", healthResource.getDescription());
    }

    private List<CatalogItem> cleanCatalog() {
        List<CatalogItem> catalogItemsList = new ArrayList<>();
        CatalogItem catalogItem1 = new CatalogItem();
        catalogItem1.setItemId("1234512345");
        catalogItem1.setManufacturer("Manu1");
        catalogItem1.setCategorySubCode("07-01-00");
        catalogItem1.setCategory("Packaged Beverages (Non-alcoholic)");
        catalogItem1.setCategorySubDescription("Carbonated Soft Drinks");
        catalogItemsList.add(catalogItem1);
        CatalogItem catalogItem2 = new CatalogItem();
        catalogItem2.setItemId("1234523456");
        catalogItem2.setManufacturer("Manu1");
        catalogItem2.setCategorySubCode("07-02-00");
        catalogItem2.setCategory("Packaged Beverages (Non-alcoholic)");
        catalogItem2.setCategorySubDescription("Iced Tea (Ready-to-drink)");
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