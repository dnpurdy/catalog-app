package com.purdynet.siqproduct.service;

import com.google.api.services.bigquery.model.TableReference;
import com.purdynet.siqproduct.biqquery.BigqueryUtils;
import com.purdynet.siqproduct.util.BQUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    @Scheduled(fixedRate = 86400000L, initialDelay = 3600000L)
    public void scheduleTaskWithCronExpression() {
        String fileName = "catalog_"+dateTimeFormatter.format(LocalDateTime.now())+".csv";
        logger.info("Cron Task :: Execution Time - {}", fileName);
        BigqueryUtils bigqueryUtils = new BigqueryUtils();

        TableReference catalog = new TableReference();
        catalog.setProjectId("swiftiq-master");
        catalog.setDatasetId("siq");
        catalog.setTableId("Catalog");

        bigqueryUtils.extractTable(catalog, "gs://swiftiq-master/catalog/"+fileName);
        bigqueryUtils.pollForCompletion();
    }
}
