package com.purdynet.siqproduct.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    private final CatalogService catalogService;
    private final HealthService healthService;

    @Autowired
    public ScheduleService(final CatalogService catalogService, final HealthService healthService) {
        this.catalogService = catalogService;
        this.healthService = healthService;
    }

    @Scheduled(fixedRate = 86400000L, initialDelay = 3600000L)
    public void backupCatalog() {
        logger.info("Automated catalog backup starting...");
        catalogService.backupCatalog();
        logger.info("...automated catalog backup complete");
    }

    @Scheduled(fixedRate = 21600000L)
    public void updateInMemCatalog() {
        logger.info("Updating in memory catalog...");
        catalogService.updateCatalog();
    }

    @Scheduled(fixedRate = 3600000L, initialDelay = 10000L)
    public void updateHealth() {
        logger.info("Updating the health...");
        healthService.generateHealthReport();
        logger.info("...update health complete.");
    }
}
