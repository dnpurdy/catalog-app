package com.purdynet.siqproduct.model.health.impl;

import com.purdynet.siqproduct.model.health.HealthCheck;
import com.purdynet.siqproduct.model.health.HealthCheckParams;
import com.purdynet.siqproduct.model.health.HealthResource;
import com.purdynet.siqproduct.model.items.CatalogItem;
import com.purdynet.siqproduct.service.CatalogService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class ConsistentManufacturerCodeHealthCheck extends AbstractHealthCheck implements HealthCheck {

    public static final int MANUFACTURER_PREFIX_LENGTH = 5;
    private final CatalogService catalogService;

    @Autowired
    public ConsistentManufacturerCodeHealthCheck(final CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
    public HealthResource generateResources() {
        return new HealthResource("Muliple Manufacturer Codes", "Make sure manufacturer codes are consistent");
    }

    @Override
    public void runCheck(HealthResource healthResource, HealthCheckParams params) throws Exception {
        List<CatalogItem> catalog = catalogService.getCatalog();
        List<String> skippedManufacturerCodes = readLinesFromFile("skipped-manufacturer-codes.txt");

        Map<String,Set<String>> manuMap = constructManufacturerMap(catalog, new HashSet<>(skippedManufacturerCodes));

        for (Map.Entry<String,Set<String>> entry : manuMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                setDegraded(healthResource,"Multiple manufacturers found for a manufacturer code!");
                healthResource.addStat(entry.getKey(), String.join(",", entry.getValue()));
            }
        }
    }

    private List<String> readLinesFromFile(String resourceName) {
            try {
                return Arrays.asList(IOUtils.resourceToString(resourceName, UTF_8, getClass().getClassLoader()).split("\n"));
            } catch (IOException e) {
                return new ArrayList<>();
            }
    }

    private Map<String, Set<String>> constructManufacturerMap(final List<CatalogItem> catalog, HashSet<String> skippedCodes) {
        Map<String, Set<String>> manufacturerMap = new HashMap<>();
        for (CatalogItem ci : catalog) {
            if (ci.getItemId().length() < MANUFACTURER_PREFIX_LENGTH) continue;

            String manuId = ci.getItemId().substring(0,MANUFACTURER_PREFIX_LENGTH);
            String manufacturer = ci.getManufacturer();

            if (skippedCodes.contains(manuId)) continue;

            mapSetAdd(manufacturerMap, manuId, manufacturer);
        }
        return manufacturerMap;
    }

    private <T,U> void mapSetAdd(Map<T, Set<U>> map, T key, U value) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            Set<U> manus = new HashSet<>();
            manus.add(value);
            map.put(key, manus);
        }
    }
}
