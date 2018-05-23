package com.purdynet.siqproduct.model.health;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HealthResource implements Serializable {
    private static final long serialVersionUID = 201506171308001L;

    private String name;
    private String description;
    private HealthEnum healthEnum;
    private List<HealthStat> stats;

    public HealthResource(String name, String description) {
        this(name, description, HealthEnum.HEALTHY);
    }

    public HealthResource(String name, String description, HealthEnum healthEnum) {
        this.name = name;
        this.description = description;
        this.healthEnum = healthEnum;
        this.stats = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HealthEnum getHealthEnum() {
        return healthEnum;
    }

    public void setHealthEnum(HealthEnum healthEnum) {
        this.healthEnum = healthEnum;
    }

    public List<HealthStat> getStats() {
        return stats;
    }

    public void setStats(List<HealthStat> stats) {
        this.stats = stats;
    }

    public void addStat(String statKey, String statValue) {
        stats.add(new HealthStat(statKey, statValue));
    }

    public String prettyPrint() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s -- %s (%s)", name, healthEnum.name(), description));
        for (HealthStat stat : stats) {
            sb.append(String.format("\n%s -> %s", stat.getKey(), stat.getValue()));
        }
        return sb.toString();
    }
}