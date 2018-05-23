package com.purdynet.siqproduct.service;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.util.Map;

public interface FreemarkerService {
    Configuration getConfiguration();

    Template getTemplate(String templatePath);

    String processTemplate(String templatePath, Object templateData);
    String processTemplate(String templatePath, Map<String, Object> templateData);
    String processTemplate(Template template, Object templateData);
}