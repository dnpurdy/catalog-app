package com.purdynet.siqproduct.service;

import java.io.IOException;

import com.purdynet.siqproduct.model.health.HealthEnum;
import com.purdynet.siqproduct.model.health.HealthReport;

import freemarker.template.TemplateException;

public interface HealthService {
    HealthEnum getCurrentHealth();
    HealthReport getCurrentHealthReport();
    String getCurrentHealthReportHtml() throws IOException,TemplateException;
    String getCurrentHealthReportHtml(HealthReport currentHealth) throws IOException,TemplateException;
    String getCurrentVersion();
    void generateHealthReport();
}