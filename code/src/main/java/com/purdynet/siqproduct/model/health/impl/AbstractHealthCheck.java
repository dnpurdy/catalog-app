package com.purdynet.siqproduct.model.health.impl;

import com.purdynet.siqproduct.model.health.HealthCheck;
import com.purdynet.siqproduct.model.health.HealthCheckParams;
import com.purdynet.siqproduct.model.health.HealthEnum;
import com.purdynet.siqproduct.model.health.HealthResource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHealthCheck implements HealthCheck {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getSkipKey() {
        return this.getClass().getSimpleName();
    }

    @Override
    public HealthResource runOrSkipResource(HealthCheckParams params) {
        HealthResource healthResource = generateResources();

        try {
            runCheck(healthResource, params);
        } catch (Exception healthException) {
            setNeedsAttention(healthResource, String.format(getSkipKey()+" throwing exception! [%s]", ExceptionUtils.getStackTrace(healthException)));
        }

        if (params.getSkippedTests() != null && params.getSkippedTests().contains(getSkipKey())) {
            setHealthy(healthResource, "Health check marked for skip, trivially healthy.");
        }

        return healthResource;
    }

    abstract public HealthResource generateResources();
    abstract public void runCheck(HealthResource healthResource, HealthCheckParams params) throws Exception;

    public static void setHealthy(final HealthResource healthResource, final String message) {
        setResourceLevel(healthResource, HealthEnum.HEALTHY, message);
    }

    public static void setNeedsAttention(final HealthResource healthResource, final String message) {
        setResourceLevel(healthResource, HealthEnum.NEEDS_ATTENTION, message);
    }

    public static void setDegraded(final HealthResource healthResource, final String message) {
        setResourceLevel(healthResource, HealthEnum.DEGRADED, message);
    }

    public static void setBroken(final HealthResource healthResource, final String message) {
        setResourceLevel(healthResource, HealthEnum.BROKEN, message);
    }

    public static void setResourceLevel(final HealthResource healthResource, final HealthEnum level, final String message) {
        healthResource.setHealthEnum(level);
        healthResource.setDescription(message);
    }
}
