package com.purdynet.siqproduct.util;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.purdynet.siqproduct.biqquery.BqTableData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSVUtils {
    public static List<String> toCSV(BqTableData bqTableData) {
        List<String> ret = new ArrayList<>();
        ret.add(toCSV(bqTableData.getSchemaFieldNames()));
        bqTableData.getTableRowList().forEach(tableRow -> ret.add(toCSV(tableRow)));
        return ret;
    }

    public static String toCSV(TableRow tableRow) {
        return String.join(",", tableRow.getF().stream().map(f -> f.getV().toString()).collect(Collectors.toList()));
    }

    public static String toCSV(List<TableFieldSchema> schemaFieldNames) {
        return String.join(",", schemaFieldNames.stream().map(tfs -> tfs.getName()).collect(Collectors.toList()));
    }
}
