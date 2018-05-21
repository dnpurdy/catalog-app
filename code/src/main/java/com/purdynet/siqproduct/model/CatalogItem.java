package com.purdynet.siqproduct.model;

import com.google.api.services.bigquery.model.TableRow;
import com.opencsv.bean.CsvDate;
import com.purdynet.siqproduct.biqquery.NamedRow;

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
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    private Date dateCreated;

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

    public String getDateCreatedString() {
        if (dateCreated != null ) {
            return dateCreated.toString();
        } else {
            return "";
        }
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
