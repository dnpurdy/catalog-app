package com.purdynet.siqproduct.biqquery;

import com.google.api.client.util.Data;
import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.purdynet.siqproduct.util.BQUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NamedRow {
    final private Map<String,Object> rowData = new HashMap<>();

    static NamedRow of(List<TableFieldSchema> schema, TableRow tableRow) {
        NamedRow out = new NamedRow();
        for(int i=0; i<schema.size(); i++) {
            TableFieldSchema tableFieldSchema = schema.get(i);
            out.put(tableFieldSchema.getName(), tableRow.getF().get(i).getV());
        }
        return out;
    }

    public Map<String,Object> getRowData() {
        return this.rowData;
    }

    public Object get(String colName) {
        return this.rowData.get(colName);
    }

    public void put(String colName, Object value) {
        this.rowData.put(colName, value);
    }

    public String getString(String colName) {
        try {
            Object value = get(colName);
            return Data.isNull(value) ? "" : value.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public Date getDate(String colName) {
        try {
            return new Date(getBigDecimal(colName).longValue()*1000);
        } catch (Exception e) {
            return null;
        }
    }

    public BigDecimal getBigDecimal(String colName) {
        try {
            return new BigDecimal(getString(colName));
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getInteger(String colName) {
        try {
            return new Integer(getString(colName));
        } catch (Exception e) {
            return null;
        }
    }
}
