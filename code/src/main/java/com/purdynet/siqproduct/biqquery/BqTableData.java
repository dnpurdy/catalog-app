package com.purdynet.siqproduct.biqquery;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableReference;
import com.google.api.services.bigquery.model.TableRow;

import java.util.ArrayList;
import java.util.List;

public class BqTableData {
    private List<TableRow> tableRowList = new ArrayList<>();
    private List<TableFieldSchema> schemaFieldNames = new ArrayList<>();

    public BqTableData() {}

    public List<TableRow> getTableRowList() {
        return tableRowList;
    }

    public void setTableRowList(List<TableRow> tableRowList) {
        this.tableRowList = tableRowList;
    }

    public List<TableFieldSchema> getSchemaFieldNames() {
        return schemaFieldNames;
    }

    public void setSchemaFieldNames(List<TableFieldSchema> schemaFieldNames) {
        this.schemaFieldNames = schemaFieldNames;
    }
}
