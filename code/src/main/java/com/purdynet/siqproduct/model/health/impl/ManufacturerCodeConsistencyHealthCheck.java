package com.purdynet.siqproduct.model.health.impl;

import com.purdynet.siqproduct.model.AbstractCoreItem;
import com.purdynet.siqproduct.model.CatalogItem;
import com.purdynet.siqproduct.model.health.HealthCheck;
import com.purdynet.siqproduct.model.health.HealthCheckParams;
import com.purdynet.siqproduct.model.health.HealthResource;
import com.purdynet.siqproduct.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManufacturerCodeConsistencyHealthCheck extends AbstractHealthCheck implements HealthCheck {

    private final CatalogService catalogService;

    @Autowired
    public ManufacturerCodeConsistencyHealthCheck(final CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
    public HealthResource generateResources() {
        return new HealthResource("Consistent Manufacturer Code", "Make sure each manufacturer code has same manufacturer");
    }

    @Override
    public void runCheck(HealthResource healthResource, HealthCheckParams params) throws Exception {
        List<CatalogItem> catalog = catalogService.getCatalog();
        Map<String, Long> duplicates = catalog.stream().collect(Collectors.groupingBy(AbstractCoreItem::getItemId, Collectors.counting())).entrySet().stream()
                .filter(entry -> entry.getValue() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (!duplicates.isEmpty()) {
            setDegraded(healthResource, "Duplicate UPCs found!");
            duplicates.forEach((k,v) -> healthResource.addStat("UPC: " + k, String.valueOf(v)));
        }
    }
}
