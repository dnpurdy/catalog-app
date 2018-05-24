package com.purdynet.siqproduct.controller;

import com.google.api.services.bigquery.model.TableDataInsertAllResponse;
import com.purdynet.siqproduct.model.CatalogItem;
import com.purdynet.siqproduct.model.EditItem;
import com.purdynet.siqproduct.model.NacsCategories;
import com.purdynet.siqproduct.service.CatalogService;
import com.purdynet.siqproduct.service.HealthService;
import com.purdynet.siqproduct.view.EditView;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class HealthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HealthService healthService;

    @Autowired
    public HealthController(final HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping(value = "/health")
    public String showHealth(@RequestParam(value="type", defaultValue="html") String type) {
        try {
            switch (type) {
                case "code":
                    return healthService.getCurrentHealth().toString();
                case "version":
                    return healthService.getCurrentVersion();
                default:
                    return healthService.getCurrentHealthReportHtml();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "Failed to return health!";
        }
    }

    @PostMapping(value = "/health")
    public String editPagePost() {
        logger.info("Updating the health...");
        healthService.generateHealthReport();
        logger.info("...update health complete.");
        return "Update Health Complete!";
    }
}
