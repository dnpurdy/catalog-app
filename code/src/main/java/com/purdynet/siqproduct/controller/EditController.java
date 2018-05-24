package com.purdynet.siqproduct.controller;

import com.google.api.services.bigquery.model.TableDataInsertAllResponse;
import com.purdynet.siqproduct.model.items.CatalogItem;
import com.purdynet.siqproduct.model.items.EditItem;
import com.purdynet.siqproduct.model.items.NacsCategories;
import com.purdynet.siqproduct.service.CatalogService;
import com.purdynet.siqproduct.service.FreemarkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EditController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CatalogService catalogService;
    private final FreemarkerService freemarkerService;

    @Autowired
    public EditController(final CatalogService catalogService, final FreemarkerService freemarkerService) {
        this.catalogService = catalogService;
        this.freemarkerService = freemarkerService;
    }

    @GetMapping(value = "/edit")
    public String editPage(@ModelAttribute EditItem editItem) {
        Map<String,Object> dataModel = new HashMap<>();
        dataModel.put("nacsList", NacsCategories.values());
        dataModel.put("editItem", editItem);
        dataModel.put("nearMatches", catalogService.genNearMatches(editItem.getItemId()));
        return freemarkerService.processTemplate("templates/EditPage.ftl", dataModel);
    }

    @PostMapping(value = "/edit")
    public String editPagePost(@ModelAttribute EditItem editItem) throws Exception {
        NacsCategories nacsCategories = NacsCategories.fromName(editItem.getNacs());
        CatalogItem converted = catalogService.convert(editItem, nacsCategories);

        if (!catalogService.hasItemId(editItem.getItemId())) {
            TableDataInsertAllResponse tableDataInsertAllResponse = catalogService.insertCatalogRow(converted);
            logger.info(tableDataInsertAllResponse.toPrettyString());
            return converted.prettyPrint();
        } else {
            return "Product UPC "+editItem.getItemId()+" aleady in catalog!! not duplicating...";
        }
    }
}
