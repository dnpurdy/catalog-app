package com.purdynet.siqproduct.service;

import com.google.api.services.bigquery.model.TableReference;
import com.purdynet.siqproduct.biqquery.BQClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    private final String projectId;
    private final CatalogService catalogService;
    private final HealthService healthService;

    @Autowired
    public ScheduleService(@Value("${project.id}") String projectId,
                           final CatalogService catalogService, final HealthService healthService) {
        this.projectId = projectId;
        this.catalogService = catalogService;
        this.healthService = healthService;
    }

    @Scheduled(fixedRate = 86400000L, initialDelay = 3600000L)
    public void backupCatalog() {
        String fileName = "catalog_"+dateTimeFormatter.format(LocalDateTime.now())+".csv";
        logger.info("Cron Task :: Execution Time - {}", fileName);
        BQClient BQClient = new BQClient(projectId);

        TableReference catalog = new TableReference();
        catalog.setProjectId("swiftiq-master");
        catalog.setDatasetId("siq");
        catalog.setTableId("Catalog");

        BQClient.extractTable(catalog, "gs://swiftiq-master/catalog/"+fileName);
        BQClient.pollForCompletion();
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
