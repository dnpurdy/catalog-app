package com.purdynet.siqproduct.model.health.impl;

import com.google.api.services.bigquery.model.Table;
import com.google.api.services.bigquery.model.TableFieldSchema;
import com.purdynet.siqproduct.model.health.HealthCheck;
import com.purdynet.siqproduct.model.health.HealthCheckParams;
import com.purdynet.siqproduct.model.health.HealthEnum;
import com.purdynet.siqproduct.model.health.HealthResource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractHealthCheck implements HealthCheck {

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
            healthResource.setHealthEnum(HealthEnum.HEALTHY);
            healthResource.setDescription("Health check marked for skip, trivially healthy.");
        }

        return healthResource;
    }

    abstract public HealthResource generateResources();
    abstract public void runCheck(HealthResource healthResource, HealthCheckParams params) throws Exception;

    protected static List<Pair<String,String>> checkNecessary(HealthResource healthResource, List<TableFieldSchema> fields, Table foundTable) {
        List<Pair<String,String>> missingMsgs = new ArrayList<>();

        for(TableFieldSchema rtfs : fields) {
            if (!checkFound(rtfs, foundTable.getSchema().getFields())) {
                setBroken(healthResource, "Missing fields identified!");
                missingMsgs.add(Pair.of(rtfs.getName(), foundTable.getId()));
            }
        }

        return missingMsgs;
    }

    protected static List<Pair<String,String>> checkExtra(HealthResource healthResource, List<TableFieldSchema> fields, Table foundTable) {
        List<Pair<String,String>> extraMsgs = new ArrayList<>();

        for(TableFieldSchema ftfs : foundTable.getSchema().getFields()) {
            if(!checkFound(ftfs, fields)) {
                setBroken(healthResource, "Extra or badly defined fields found!");
                extraMsgs.add(Pair.of(ftfs.getName(), foundTable.getId()));
            }
        }

        return extraMsgs;
    }

    private static boolean checkFound(TableFieldSchema searchItem, List<TableFieldSchema> searchSpace) {
        if (searchSpace.isEmpty()) {
            return false;
        } else if (isEqualFieldSchema(searchItem, searchSpace.get(0))) {
            return true;
        } else {
            return checkFound(searchItem, searchSpace.subList(1, searchSpace.size()));
        }
    }

    private static boolean isEqualFieldSchema(TableFieldSchema left, TableFieldSchema right) {
        try {
            return left.getName().equals(right.getName()) &&
                    left.getType().equals(right.getType()) &&
                    (left.getMode() == null && right.getMode() == null ||
                            (left.getMode() == null && right.getMode().equals("NULLABLE")) ||
                            (left.getMode().equals("NULLABLE") && right.getMode() == null) ||
                            left.getMode().equals(right.getMode()));
        } catch (NullPointerException e) {
            return false;
        }
    }

    protected void addStats(HealthResource healthResource, String requiredTableName, Table foundTable, List<Pair<String,String>> missingMsgs, List<Pair<String,String>> extraMsgs) {
        Date lastModified = new Date(foundTable.getLastModifiedTime().longValue());
        DecimalFormat formatter = new DecimalFormat("#,###");

        StringBuilder text = new StringBuilder(String.format("Present:\n\t%s rows\n\tlastMod %s", formatter.format(foundTable.getNumRows()), lastModified));

        for (Pair<String,String> missingMsg : missingMsgs) {
            text.append(String.format("\nField [%s] missing!", missingMsg.getLeft()));
        }
        for (Pair<String,String> extraMsg : extraMsgs) {
            text.append(String.format("\nField [%s] extra!", extraMsg.getLeft()));
        }

        healthResource.addStat(requiredTableName, text.toString());
    }

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
