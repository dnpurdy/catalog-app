package com.purdynet.siqproduct.model.health;

import org.apache.commons.lang3.exception.ExceptionUtils;

public interface HealthCheck {
    String getSkipKey();
    HealthResource runOrSkipResource(HealthCheckParams params);
}
