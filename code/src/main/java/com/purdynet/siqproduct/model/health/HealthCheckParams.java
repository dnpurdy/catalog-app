package com.purdynet.siqproduct.model.health;

public class HealthCheckParams {
    private String applicationId;
    private String applicationVersion;
    private String applicationDeploymentDate;
    private String skippedTests;

    public HealthCheckParams() {
    }

    public HealthCheckParams(String applicationId, String applicationVersion, String applicationDeploymentDate, String skippedTests) {
        this.applicationId = applicationId;
        this.applicationVersion = applicationVersion;
        this.applicationDeploymentDate = applicationDeploymentDate;
        this.skippedTests = skippedTests;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getApplicationDeploymentDate() {
        return applicationDeploymentDate;
    }

    public void setApplicationDeploymentDate(String applicationDeploymentDate) {
        this.applicationDeploymentDate = applicationDeploymentDate;
    }

    public String getSkippedTests() {
        return skippedTests;
    }

    public void setSkippedTests(String skippedTests) {
        this.skippedTests = skippedTests;
    }
}
