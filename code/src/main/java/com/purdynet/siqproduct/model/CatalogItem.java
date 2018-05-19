package com.purdynet.siqproduct.model;

import com.google.api.services.bigquery.model.TableRow;

import java.util.Date;

import static com.purdynet.siqproduct.util.BQUtils.getDate;
import static com.purdynet.siqproduct.util.BQUtils.getString;

public class CatalogItem extends AbstractCoreItem {
    private String productId;
    //itemId
    //description
    private String department;
    private String deptDescription;
    private String categorySubCode;
    private String categorySubDescription;
    private String category;
    //manufacturer
    private String subSegmentDescription;
    private String subSegmentId;
    private String segmentDescription;
    private String segmentId;
    private String subCategoryDescription;
    private String subCategoryId;
    private String majorDepartmentDescription;
    private String majorDepartmentId;
    private String container;
    private String size;
    private String uom;
    private String active;
    private String privateLabelFlag;
    private String consumption;
    private String pkg;
    private String flavor;
    private String brand;
    private String brandType;
    private String height;
    private String width;
    private String depth;
    private String shapeId;
    private String family;
    private String trademark;
    private String country;
    private String color;
    private String alternateHeight;
    private String alternateWeight;
    private String alternateDepth;
    private String containerDescription;
    private String distributor;
    private String industryType;
    private Date dateCreated;

    public static CatalogItem of(TableRow tableRow) {
        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setProductId(getString(tableRow, 0));
        catalogItem.setItemId(getString(tableRow, 1));
        catalogItem.setDescription(getString(tableRow, 2));
        catalogItem.setDepartment(getString(tableRow, 3));
        catalogItem.setDeptDescription(getString(tableRow, 4));
        catalogItem.setCategorySubCode(getString(tableRow, 5));
        catalogItem.setCategorySubDescription(getString(tableRow, 6));
        catalogItem.setCategory(getString(tableRow, 7));
        catalogItem.setManufacturer(getString(tableRow, 8));
        catalogItem.setSubSegmentDescription(getString(tableRow, 9));
        catalogItem.setSubSegmentId(getString(tableRow, 10));
        catalogItem.setSegmentDescription(getString(tableRow, 11));
        catalogItem.setSegmentId(getString(tableRow, 12));
        catalogItem.setSubCategoryDescription(getString(tableRow, 13));
        catalogItem.setSubCategoryId(getString(tableRow, 14));
        catalogItem.setMajorDepartmentDescription(getString(tableRow, 15));
        catalogItem.setMajorDepartmentId(getString(tableRow, 16));
        catalogItem.setContainer(getString(tableRow, 17));
        catalogItem.setSize(getString(tableRow, 18));
        catalogItem.setUom(getString(tableRow, 19));
        catalogItem.setActive(getString(tableRow, 20));
        catalogItem.setPrivateLabelFlag(getString(tableRow, 21));
        catalogItem.setConsumption(getString(tableRow, 22));
        catalogItem.setPkg(getString(tableRow, 23));
        catalogItem.setFlavor(getString(tableRow, 24));
        catalogItem.setBrand(getString(tableRow, 25));
        catalogItem.setBrandType(getString(tableRow, 26));
        catalogItem.setHeight(getString(tableRow, 27));
        catalogItem.setWidth(getString(tableRow, 28));
        catalogItem.setDepth(getString(tableRow, 29));
        catalogItem.setShapeId(getString(tableRow, 30));
        catalogItem.setFamily(getString(tableRow, 31));
        catalogItem.setTrademark(getString(tableRow, 32));
        catalogItem.setCountry(getString(tableRow, 33));
        catalogItem.setColor(getString(tableRow, 34));
        catalogItem.setAlternateHeight(getString(tableRow, 35));
        catalogItem.setAlternateWeight(getString(tableRow, 36));
        catalogItem.setAlternateDepth(getString(tableRow, 37));
        catalogItem.setContainerDescription(getString(tableRow, 38));
        catalogItem.setDistributor(getString(tableRow, 39));
        catalogItem.setIndustryType(getString(tableRow, 40));
        catalogItem.setDateCreated(getDate(tableRow, 41));
        return catalogItem;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDeptDescription() {
        return deptDescription;
    }

    public void setDeptDescription(String deptDescription) {
        this.deptDescription = deptDescription;
    }

    public String getCategorySubCode() {
        return categorySubCode;
    }

    public void setCategorySubCode(String categorySubCode) {
        this.categorySubCode = categorySubCode;
    }

    public String getCategorySubDescription() {
        return categorySubDescription;
    }

    public void setCategorySubDescription(String categorySubDescription) {
        this.categorySubDescription = categorySubDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubSegmentDescription() {
        return subSegmentDescription;
    }

    public void setSubSegmentDescription(String subSegmentDescription) {
        this.subSegmentDescription = subSegmentDescription;
    }

    public String getSubSegmentId() {
        return subSegmentId;
    }

    public void setSubSegmentId(String subSegmentId) {
        this.subSegmentId = subSegmentId;
    }

    public String getSegmentDescription() {
        return segmentDescription;
    }

    public void setSegmentDescription(String segmentDescription) {
        this.segmentDescription = segmentDescription;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public String getSubCategoryDescription() {
        return subCategoryDescription;
    }

    public void setSubCategoryDescription(String subCategoryDescription) {
        this.subCategoryDescription = subCategoryDescription;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getMajorDepartmentDescription() {
        return majorDepartmentDescription;
    }

    public void setMajorDepartmentDescription(String majorDepartmentDescription) {
        this.majorDepartmentDescription = majorDepartmentDescription;
    }

    public String getMajorDepartmentId() {
        return majorDepartmentId;
    }

    public void setMajorDepartmentId(String majorDepartmentId) {
        this.majorDepartmentId = majorDepartmentId;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getPrivateLabelFlag() {
        return privateLabelFlag;
    }

    public void setPrivateLabelFlag(String privateLabelFlag) {
        this.privateLabelFlag = privateLabelFlag;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandType() {
        return brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType = brandType;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getShapeId() {
        return shapeId;
    }

    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAlternateHeight() {
        return alternateHeight;
    }

    public void setAlternateHeight(String alternateHeight) {
        this.alternateHeight = alternateHeight;
    }

    public String getAlternateWeight() {
        return alternateWeight;
    }

    public void setAlternateWeight(String alternateWeight) {
        this.alternateWeight = alternateWeight;
    }

    public String getAlternateDepth() {
        return alternateDepth;
    }

    public void setAlternateDepth(String alternateDepth) {
        this.alternateDepth = alternateDepth;
    }

    public String getContainerDescription() {
        return containerDescription;
    }

    public void setContainerDescription(String containerDescription) {
        this.containerDescription = containerDescription;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
