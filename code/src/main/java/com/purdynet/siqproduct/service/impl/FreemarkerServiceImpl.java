package com.purdynet.siqproduct.service.impl;

import com.purdynet.siqproduct.service.FreemarkerService;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

@Service
public class FreemarkerServiceImpl implements FreemarkerService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Configuration configuration;

    @Autowired
    public FreemarkerServiceImpl() {
        this.configuration = new Configuration(Configuration.VERSION_2_3_20);

        // Where do we load the templates from:
        configuration.setClassForTemplateLoading(this.getClass(), "/");

        // Some other recommended settings:
        configuration.setIncompatibleImprovements(new Version(2, 3, 20));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.US);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public Template getTemplate(String templatePath) {
        try {
            return this.configuration.getTemplate(templatePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to get template!", e);
        }
    }

    @Override
    public String processTemplate(String templatePath, Map<String, Object> templateData) {
        return processTemplate(getTemplate(templatePath), templateData);
    }

    @Override
    public String processTemplate(String templatePath, Object templateData) {
        return processTemplate(getTemplate(templatePath), templateData);
    }

    @Override
    public String processTemplate(Template template, Object templateData) {
        try {
            StringWriter stringWriter = new StringWriter();
            template.process(templateData, stringWriter);
            return stringWriter.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Failed to process template!", e);
        }
    }
}
