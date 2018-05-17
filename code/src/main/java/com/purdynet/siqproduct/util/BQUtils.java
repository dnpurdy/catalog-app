package com.purdynet.siqproduct.util;

import com.google.api.client.util.Data;
import com.google.api.services.bigquery.model.TableRow;

import java.math.BigDecimal;
import java.util.Date;

public class BQUtils {
    public static String getString(TableRow tableRow, int idx) {
        try {
            Object value = tableRow.getF().get(idx).getV();
            if (value == null | Data.isNull(value)) {
                return "";
            } else {
                return value.toString();
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static Integer getInteger(TableRow tableRow, int idx) {
        try {
            return new Integer(getString(tableRow, idx));
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getDate(TableRow tableRow, int idx) {
        try {
            return new Date(getBigDecimal(tableRow, idx).longValue()*1000);
        } catch (Exception e) {
            return null;
        }
    }

    public static BigDecimal getBigDecimal(TableRow tableRow, int idx) {
        try {
            return new BigDecimal(getString(tableRow, idx));
        } catch (Exception e) {
            return null;
        }
    }

}
