package com.purdynet.siqproduct.service.impl;

import com.purdynet.siqproduct.model.health.*;
import com.purdynet.siqproduct.service.FreemarkerService;
import com.purdynet.siqproduct.service.HealthService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class HealthServiceImpl implements HealthService {

    private final String applicationId;
    private final String buildVersion;
    private final String buildTimestamp;
    private final List<HealthCheck> healthChecks;
    private final FreemarkerService freemarkerService;

    private HealthReport healthReport = new HealthReport();

    @Autowired
    public HealthServiceImpl(@Value("${application.name}") String applicationId, @Value("${build.version}") String buildVersion, @Value("${build.timestamp}") String buildTimestamp,
                             final List<HealthCheck> healthChecks, final FreemarkerService freemarkerService) {
        this.applicationId = applicationId;
        this.buildVersion = buildVersion;
        this.buildTimestamp = buildTimestamp;
        this.healthChecks = healthChecks;
        this.freemarkerService = freemarkerService;
    }

    @Override
    public void generateHealthReport() {
        final HealthCheckParams params = new HealthCheckParams(
                applicationId, buildVersion, buildTimestamp,
                healthReport != null ? healthReport.getSkippedTests() : null);

        List<HealthResource> resourceList = healthChecks.stream().map(hc -> hc.runOrSkipResource(params)).collect(toList());

        setHealthReport(new HealthReport(params.getApplicationId(), params.getApplicationVersion(), params.getApplicationDeploymentDate(), new Date(), params.getSkippedTests(), resourceList));
    }

    private HealthReport getHealthReport() {
        return healthReport;
    }

    private void setHealthReport(HealthReport healthReport) {
        this.healthReport = healthReport;
    }

    @Override
    public HealthEnum getCurrentHealth() {
        return getCurrentHealthReport().getOverallHealth();
    }

    @Override
    public HealthReport getCurrentHealthReport() {
        return getHealthReport();
    }

    public String getCurrentHealthReportHtml() throws IOException,TemplateException {
        HealthReport currentHealth = getCurrentHealthReport();
        return getCurrentHealthReportHtml(currentHealth);
    }

    @Override
    public String getCurrentHealthReportHtml(HealthReport currentHealth) throws IOException,TemplateException {
        if (currentHealth != null ) {
            return freemarkerService.processTemplate("templates/HealthPage.ftl", currentHealth);
        } else {
            return "No health has been generated yet";
        }

    }

    @Override
    public String getCurrentVersion() {
        return getCurrentHealthReport().getApplicationVersion();
    }
}
