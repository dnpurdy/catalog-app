package com.purdynet.siqproduct.util;

import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.purdynet.siqproduct.biqquery.BqTableData;
import com.purdynet.siqproduct.model.items.CatalogItem;
import org.junit.Test;

import static org.junit.Assert.*;

public class CSVUtilsTest {

    @Test
    public void toCSV_TableDataToStringList() {
        BqTableData bqTableData = new BqTableData();
        TableRow tr = new TableRow();
        tr.setF(ListUtils.asSingleton(new TableCell().setV("David")));
        bqTableData.setTableRowList(ListUtils.asSingleton(tr));
        TableFieldSchema testSchemaFieldNames = new TableFieldSchema().setName("Name");
        bqTableData.setSchemaFieldNames(ListUtils.asSingleton(testSchemaFieldNames));

        assertEquals(ListUtils.asList("Name","David"), CSVUtils.toCSV(bqTableData));
    }

    @Test
    public void catalogItemtoCSV() {
        CatalogItem testCatalogItem = new CatalogItem();
        testCatalogItem.setItemId("12345");
        testCatalogItem.setDescription("test description");
        testCatalogItem.setManufacturer("test manufacturer");
        testCatalogItem.setBrand("brand1234");
        testCatalogItem.setContainer("3");

        assertEquals("productId,itemId,description,department,deptDescription,categorySubCode,categorySubDescription,category,manufacturer,subSegmentDescription,subSegmentId,segmentDescription,segmentId,subCategoryDescription,subCategoryId,majorDepartmentDescription,majorDepartmentId,container,size,uom,active,privateLabelFlag,consumption,package,flavor,brand,brandType,height,width,depth,shapeId,family,trademark,country,color,alternateHeight,alternateWeight,alternateDepth,containerDescription,distributor,industryType,dateCreatedBQ\n" +
                "\"\",\"12345\",\"test description\",\"\",\"\",\"\",\"\",\"\",\"test manufacturer\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"3\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"brand1234\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"1525132800\"\n", CSVUtils.catalogItemtoCSV(ListUtils.asSingleton(testCatalogItem)));
    }
}