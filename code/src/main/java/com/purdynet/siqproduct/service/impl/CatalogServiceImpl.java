package com.purdynet.siqproduct.service.impl;

import com.google.api.services.bigquery.model.TableDataInsertAllRequest;
import com.google.api.services.bigquery.model.TableDataInsertAllResponse;
import com.purdynet.siqproduct.biqquery.BQClient;
import com.purdynet.siqproduct.biqquery.NamedRow;
import com.purdynet.siqproduct.model.items.CatalogItem;
import com.purdynet.siqproduct.model.items.EditItem;
import com.purdynet.siqproduct.model.items.NacsCategories;
import com.purdynet.siqproduct.service.CatalogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.HammingDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static com.purdynet.siqproduct.biqquery.BQClient.convertTableRowToModel;
import static org.apache.commons.lang3.StringUtils.rightPad;

@Service
public class CatalogServiceImpl implements CatalogService {
    private static final String SIQ_DATASET_ID = "siq";
    private static final String CATALOG_TABLE_ID = "Catalog";
    private static final String CATALOG_SELECT_SQL = "SELECT * FROM ["+SIQ_DATASET_ID+"."+CATALOG_TABLE_ID+"]";
    private static final int TRY_AGAIN_FOR_MORE = 5;
    private static final int MAX_RETURN = 15;

    private final String projectId;
    private List<CatalogItem> catalog;

    @Autowired
    public CatalogServiceImpl(@Value("${project.id}") String projectId) {
        this.projectId = projectId;
        this.catalog = new ArrayList<>();
    }

    @Override
    public List<CatalogItem> getCatalog() {
        return catalog;
    }

    @Override
    public List<CatalogItem> genNearMatches(final String upc) {
        if (upc != null ) return genNearMatches(upc, upc.length(), new ArrayList<>());
        else return new ArrayList<>();
    }

    private List<CatalogItem> genNearMatches(final String upc, final int length, final List<CatalogItem> bestGuess) {
        if (length == 2) return sort(bestGuess, upc);
        else {
            String partial = upc.substring(0,length);
            List<CatalogItem> nextBestGuess = getCatalogPartialItemId(partial);
            if (nextBestGuess.size() < TRY_AGAIN_FOR_MORE) {
                return genNearMatches(upc, length-1, nextBestGuess);
            } else {
                return sort(nextBestGuess, upc);
            }
        }
    }

    private List<CatalogItem> sort(final List<CatalogItem> in, final String upc) {
        Comparator<CatalogItem> hammingComparator = (c1, c2) -> {
            Integer c1D = hamming(c1.getItemId(), upc);
            Integer c2D = hamming(c2.getItemId(), upc);
            return c1D.compareTo(c2D);
        };
        Comparator<CatalogItem> linearComparator = (c1, c2) -> {
            BigInteger c1D = linear(c1.getItemId(), upc);
            BigInteger c2D = linear(c2.getItemId(), upc);
            return c1D.compareTo(c2D);
        };

        return in.stream().sorted(linearComparator).limit(MAX_RETURN).collect(Collectors.toList());
    }

    @Override
    public List<CatalogItem> getCatalogPartialItemId(final String partial) {
        return catalog.stream().filter(ci -> ci.getItemId().startsWith(partial)).collect(Collectors.toList());
    }

    @Override
    public void updateCatalog() {
        BQClient bqClient = BQClient.runQuerySync(projectId, CATALOG_SELECT_SQL);
        catalog = convertTableRowToModel(bqClient.getBqTableData(), this::catalogItemOf);
        Collections.sort(catalog);
    }

    protected static Integer hamming(final String a, final String b) {
        Integer maxLength = Math.max(a.length(), b.length());
        HammingDistance hammingDistance = new HammingDistance();
        return hammingDistance.apply(rightPad(a, maxLength),rightPad(b, maxLength));
    }

    private static BigInteger linear(final String a, final String b) {
        BigInteger intA = toInt(a.replaceAll("S",""));
        BigInteger intB = toInt(b.replaceAll("S",""));
        return intA.subtract(intB).abs();
    }

    private static BigInteger toInt(String s) {
        try {
            return new BigInteger(s);
        } catch (NumberFormatException e) {
            return BigInteger.ZERO;
        }
    }

    @Override
    public CatalogItem convert(EditItem editItem, NacsCategories nacsCategories) {
        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setItemId(editItem.getItemId());
        catalogItem.setDescription(editItem.getDescription());
        catalogItem.setCategorySubCode(nacsCategories.getCategoryCode());
        catalogItem.setCategorySubDescription(nacsCategories.getSubCategory());
        catalogItem.setCategory(nacsCategories.getCategory());
        catalogItem.setManufacturer(editItem.getManufacturer());
        catalogItem.setContainer(editItem.getContainer());
        catalogItem.setSize(editItem.getSize());
        catalogItem.setUom(editItem.getUom());
        catalogItem.setPkg(editItem.getPkg());
        catalogItem.setBrand(editItem.getBrand());
        catalogItem.setDateCreated(new Date());
        return catalogItem;
    }

    @Override
    public TableDataInsertAllResponse insertCatalogRow(CatalogItem catalogItem) {
        BQClient bqClient = new BQClient(projectId);
        TableDataInsertAllRequest content = new TableDataInsertAllRequest();
        List<TableDataInsertAllRequest.Rows> rows = new ArrayList<>();
        rows.add(genInsertRow(catalogItem));
        content.setRows(rows);
        TableDataInsertAllResponse tableDataInsertAllResponse = bqClient.insertAll(SIQ_DATASET_ID, CATALOG_TABLE_ID, content);
        if (tableDataInsertAllResponse.getInsertErrors() == null || tableDataInsertAllResponse.getInsertErrors().isEmpty()) catalog.add(catalogItem);
        return tableDataInsertAllResponse;
    }

    private TableDataInsertAllRequest.Rows genInsertRow(CatalogItem catalogItem) {
        TableDataInsertAllRequest.Rows row = new TableDataInsertAllRequest.Rows();
        Map<String, Object> json = new HashMap<>();
        safePut(json, "upc", catalogItem.getItemId());
        safePut(json, "description", catalogItem.getDescription());
        safePut(json, "categorySubCode", catalogItem.getCategorySubCode());
        safePut(json, "category", catalogItem.getCategory());
        safePut(json, "categorySubDescription", catalogItem.getCategorySubDescription());
        safePut(json, "manufacturer", catalogItem.getManufacturer());
        safePut(json, "container", catalogItem.getContainer());
        safePut(json, "size", catalogItem.getSize());
        safePut(json, "uom", catalogItem.getUom());
        safePut(json, "package", catalogItem.getPkg());
        safePut(json, "brand", catalogItem.getBrand());
        safePut(json, "dateCreated", Math.floor(catalogItem.getDateCreated().getTime()/1000));
        row.setJson(json);
        return row;
    }

    private void safePut(Map<String, Object> json, String key, Object value) {
        if (StringUtils.isNotEmpty(value.toString())) json.put(key, value);
    }

    public CatalogItem catalogItemOf(NamedRow nr) {
        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setProductId(nr.getString("productId"));
        catalogItem.setItemId(nr.getString("upc"));
        catalogItem.setDescription(nr.getString("description"));
        catalogItem.setDepartment(nr.getString("department"));
        catalogItem.setDeptDescription(nr.getString("deptDescription"));
        catalogItem.setCategorySubCode(nr.getString("categorySubCode"));
        catalogItem.setCategorySubDescription(nr.getString("categorySubDescription"));
        catalogItem.setCategory(nr.getString("category"));
        catalogItem.setManufacturer(nr.getString("manufacturer"));
        catalogItem.setSubSegmentDescription(nr.getString("subSegmentDescription"));
        catalogItem.setSubSegmentId(nr.getString("subSegmentId"));
        catalogItem.setSegmentDescription(nr.getString("segmentDescription"));
        catalogItem.setSegmentId(nr.getString("segmentId"));
        catalogItem.setSubCategoryDescription(nr.getString("subCategoryDescription"));
        catalogItem.setSubCategoryId(nr.getString("subCategoryId"));
        catalogItem.setMajorDepartmentDescription(nr.getString("majorDepartmentDescription"));
        catalogItem.setMajorDepartmentId(nr.getString("majorDepartmentId"));
        catalogItem.setContainer(nr.getString("container"));
        catalogItem.setSize(nr.getString("size"));
        catalogItem.setUom(nr.getString("uom"));
        catalogItem.setActive(nr.getString("active"));
        catalogItem.setPrivateLabelFlag(nr.getString("privateLabelFlag"));
        catalogItem.setConsumption(nr.getString("consumption"));
        catalogItem.setPkg(nr.getString("pkg"));
        catalogItem.setFlavor(nr.getString("flavor"));
        catalogItem.setBrand(nr.getString("brand"));
        catalogItem.setBrandType(nr.getString("brandType"));
        catalogItem.setHeight(nr.getString("height"));
        catalogItem.setWidth(nr.getString("width"));
        catalogItem.setDepth(nr.getString("depth"));
        catalogItem.setShapeId(nr.getString("shapeId"));
        catalogItem.setFamily(nr.getString("family"));
        catalogItem.setTrademark(nr.getString("trademark"));
        catalogItem.setCountry(nr.getString("country"));
        catalogItem.setColor(nr.getString("color"));
        catalogItem.setAlternateHeight(nr.getString("alternateHeight"));
        catalogItem.setAlternateWeight(nr.getString("alternateWeight"));
        catalogItem.setAlternateDepth(nr.getString("alternateDepth"));
        catalogItem.setContainerDescription(nr.getString("containerDescription"));
        catalogItem.setDistributor(nr.getString("distributor"));
        catalogItem.setIndustryType(nr.getString("industryType"));
        catalogItem.setDateCreated(nr.getDate("dateCreated"));
        return catalogItem;
    }

    @Override
    public boolean hasItemId(final String upc) {
        return catalog.stream().filter(ci -> ci.getItemId().equals(upc)).collect(Collectors.toList()).size() != 0;
    }
}
