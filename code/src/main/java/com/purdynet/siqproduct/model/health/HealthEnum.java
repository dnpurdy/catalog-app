package com.purdynet.siqproduct.model.health;

public enum HealthEnum {
    HEALTHY("Healthy"),
    DEGRADED("Degraded"),
    NEEDS_ATTENTION("Needs Immediate Attention"),
    BROKEN("Broken");

    private String displayName;

    HealthEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
