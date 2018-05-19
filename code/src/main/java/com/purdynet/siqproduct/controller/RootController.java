package com.purdynet.siqproduct.controller;

import com.purdynet.siqproduct.service.RetailerService;
import com.purdynet.siqproduct.view.RootView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    private final RetailerService retailerService;
    private final RootView rootView;

    @Autowired
    public RootController(RetailerService retailerService, RootView rootView) {
        this.retailerService = retailerService;
        this.rootView = rootView;
    }

    @GetMapping("/")
    public String summary() {
        return rootView.summaryPage(retailerService.getRetailers());
    }
}
