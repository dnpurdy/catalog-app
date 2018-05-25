package com.purdynet.siqproduct.model.health;

public interface HealthCheck {
    String getSkipKey();
    HealthResource runOrSkipResource(HealthCheckParams params);
}
