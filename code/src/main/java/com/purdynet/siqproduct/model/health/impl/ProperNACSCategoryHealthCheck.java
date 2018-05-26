package com.purdynet.siqproduct.model.health.impl;

import com.purdynet.siqproduct.model.health.HealthCheck;
import com.purdynet.siqproduct.model.health.HealthCheckParams;
import com.purdynet.siqproduct.model.health.HealthResource;
import com.purdynet.siqproduct.model.items.CatalogItem;
import com.purdynet.siqproduct.model.items.NacsCategories;
import com.purdynet.siqproduct.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.purdynet.siqproduct.util.ValueUtils.nvl;

@Component
public class ProperNACSCategoryHealthCheck extends AbstractHealthCheck implements HealthCheck {

    public static final int MANUFACTURER_PREFIX_LENGTH = 5;
    private final CatalogService catalogService;

    @Autowired
    public ProperNACSCategoryHealthCheck(final CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
    public HealthResource generateResources() {
        return new HealthResource("Proper NACS Categories", "Make sure catalogs has correct NACS categories");
    }

    @Override
    public void runCheck(HealthResource healthResource, HealthCheckParams params) throws Exception {
        List<CatalogItem> catalog = catalogService.getCatalog();

        for (CatalogItem catalogItem : catalog) {
            Optional<NacsCategories> nacsCategories = NacsCategories.matchCategoryCode(catalogItem.getCategorySubCode());
            if (!nacsCategories.isPresent()) {
                setDegraded(healthResource,"Unknown NACS category code in catalog!");
                healthResource.addStat(String.format("[%s] Unknown NACS Code", catalogItem.getItemId()), nvl(catalogItem.getCategorySubCode(),"Code Missing!"));
            } else {
                NacsCategories nacsCategories1 = nacsCategories.get();
                if (!nacsCategories1.getCategory().equals(catalogItem.getCategory())) {
                    setDegraded(healthResource,"Unknown NACS Category in catalog!");
                    healthResource.addStat(String.format("[%s] Unknown NACS Category", catalogItem.getItemId()), nvl(catalogItem.getCategory(), "Category Missing!"));
                }
                if (!nacsCategories1.getSubCategory().equals(catalogItem.getCategorySubDescription())) {
                    setDegraded(healthResource,"Unknown NACS Sub Category in catalog!");
                    healthResource.addStat(String.format("[%s] Unknown NACS Sub Category", catalogItem.getItemId()), nvl(catalogItem.getCategorySubDescription(), "Sub-Category Missing!"));
                }
            }
        }
    }
}
