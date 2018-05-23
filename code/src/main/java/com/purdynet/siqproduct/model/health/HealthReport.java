package com.purdynet.siqproduct.model.health;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HealthReport {
    private String applicationId;
    private String applicationVersion;
    private String applicationDeploymentDate;
    private Date creationTime;
    private String overallHealth;
    private String skippedTests;
    private List<HealthResource> healthResourcesList;

    public HealthReport() {
    }

    public HealthReport(String applicationId, String applicationVersion, String applicationDeploymentDate) {
        this(applicationId, applicationVersion, applicationDeploymentDate, new Date());
    }

    public HealthReport(String applicationId, String applicationVersion, String applicationDeploymentDate, Date creationTime) {
        this(applicationId, applicationVersion, applicationDeploymentDate, creationTime, new ArrayList<HealthResource>());
    }

    public HealthReport(String applicationId, String applicationVersion, String applicationDeploymentDate, Date creationTime, List<HealthResource> healthResourcesList) {
        this(applicationId, applicationVersion, applicationDeploymentDate, creationTime, null, healthResourcesList);
    }

    public HealthReport(String applicationId, String applicationVersion, String applicationDeploymentDate, Date creationTime, String skippedTests, List<HealthResource> healthResourcesList) {
        this.applicationId = applicationId;
        this.applicationVersion = applicationVersion;
        this.applicationDeploymentDate = applicationDeploymentDate;
        this.creationTime = creationTime;
        this.healthResourcesList = healthResourcesList;
        this.skippedTests = skippedTests;
        this.overallHealth = getOverallHealth().name();
    }

    public void addResource(HealthResource healthResource) {
        this.healthResourcesList.add(healthResource);
        this.overallHealth = getOverallHealth().name();
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public String getApplicationDeploymentDate() {
        return applicationDeploymentDate;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public String getSkippedTests() { return skippedTests; }

    public List<HealthResource> getHealthResourcesList() {
        return healthResourcesList;
    }

    public HealthEnum getOverallHealth() {
        HealthEnum overallHealth = HealthEnum.HEALTHY;
        for (HealthResource res : this.healthResourcesList) {
            if (res.getHealthEnum().compareTo(overallHealth) > 0) overallHealth = res.getHealthEnum();
        }
        return overallHealth;
    }
}
