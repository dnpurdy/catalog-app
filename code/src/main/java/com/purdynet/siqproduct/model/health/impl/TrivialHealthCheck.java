package com.purdynet.siqproduct.model.health.impl;

import com.purdynet.siqproduct.model.health.HealthCheck;
import com.purdynet.siqproduct.model.health.HealthCheckParams;
import com.purdynet.siqproduct.model.health.HealthResource;
import org.springframework.stereotype.Component;

@Component
public class TrivialHealthCheck extends AbstractHealthCheck implements HealthCheck {
    @Override
    public HealthResource generateResources() {
        return new HealthResource("Trivial Healthcheck", "Just a No-Op health check :)");
    }

    @Override
    public void runCheck(HealthResource healthResource, HealthCheckParams params) throws Exception {

    }
}
