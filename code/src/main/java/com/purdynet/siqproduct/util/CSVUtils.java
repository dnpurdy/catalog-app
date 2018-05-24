package com.purdynet.siqproduct.util;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.google.common.base.Joiner;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.items.CatalogItem;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
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

    public static String catalogItemtoCSV(final List<CatalogItem> catalog) {
        try {
            catalog.sort(Comparator.comparing(CatalogItem::getItemId));
            ColumnPositionMappingStrategy<CatalogItem> strat = new ColumnPositionMappingStrategy<>();
            strat.setType(CatalogItem.class);
            String[] columns = new String[] {"productId", "itemId", "description", "department", "deptDescription", "categorySubCode", "categorySubDescription", "category", "manufacturer", "subSegmentDescription", "subSegmentId", "segmentDescription", "segmentId", "subCategoryDescription", "subCategoryId", "majorDepartmentDescription", "majorDepartmentId", "container", "size", "uom", "active", "privateLabelFlag", "consumption", "package", "flavor", "brand", "brandType", "height", "width", "depth", "shapeId", "family", "trademark", "country", "color", "alternateHeight", "alternateWeight", "alternateDepth", "containerDescription", "distributor", "industryType", "dateCreatedBQ"}; // the fields to bind do in your JavaBean
            strat.setColumnMapping(columns);

            StringWriter stringWriter = new StringWriter();
            Joiner joiner = Joiner.on(",");
            stringWriter.write(joiner.join(columns)+"\n");
            StatefulBeanToCsv<CatalogItem> beanToCsv = new StatefulBeanToCsvBuilder<CatalogItem>(stringWriter).withMappingStrategy(strat).build();
            beanToCsv.write(catalog);
            stringWriter.close();
            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
