package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.service.FreemarkerService;
import com.purdynet.siqproduct.service.HealthService;
import com.purdynet.siqproduct.service.RetailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {

    private final RetailerService retailerService;
    private final HealthService healthService;
    private final FreemarkerService freemarkerService;

    @Autowired
    public RootController(final RetailerService retailerService, final HealthService healthService, final FreemarkerService freemarkerService) {
        this.retailerService = retailerService;
        this.healthService = healthService;
        this.freemarkerService = freemarkerService;
    }

    @GetMapping("/")
    public String summary() {
        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("retailers", retailerService.getRetailers());
        dataModel.put("health", healthService.getCurrentHealth());
        return freemarkerService.processTemplate("templates/RootPage.ftl", dataModel);
    }
}
