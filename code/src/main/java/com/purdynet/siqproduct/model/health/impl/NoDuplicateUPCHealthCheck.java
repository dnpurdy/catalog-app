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

@Component
public class NoDuplicateUPCHealthCheck extends AbstractHealthCheck implements HealthCheck {

    private final CatalogService catalogService;

    @Autowired
    public NoDuplicateUPCHealthCheck(final CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
    public HealthResource generateResources() {
        return new HealthResource("Duplicate UPC", "Make sure no duplicate UPCs in catalog");
    }

    @Override
    public void runCheck(HealthResource healthResource, HealthCheckParams params) throws Exception {
        Map<String, Long> duplicates = catalogService.getCatalog().stream()
                .collect(Collectors.groupingBy(AbstractCoreItem::getItemId, Collectors.counting())).entrySet().stream()
                .filter(entry -> entry.getValue() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (!duplicates.isEmpty()) {
            setBroken(healthResource, "Duplicate UPCs found!");
            duplicates.forEach((k,v) -> healthResource.addStat("UPC: " + k, String.valueOf(v)));
        }
    }
}
