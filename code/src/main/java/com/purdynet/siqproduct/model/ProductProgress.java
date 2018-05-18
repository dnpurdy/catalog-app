package com.purdynet.siqproduct.model;

import com.google.api.services.bigquery.model.TableRow;

import java.math.BigDecimal;
import java.util.Date;

import static com.purdynet.siqproduct.util.BQUtils.*;

public class ProductProgress {
    private String itemId;
    private String manufacturer;
    private String retailerItemId;
    private BigDecimal revPortion;
    private String description;
    private String retailerDept;
    private String nacsCategory;
    private String complete;
    private String isUpc;
    private BigDecimal completeRevenue;
    private BigDecimal incompleteRevenue;
    private Integer completeItems;
    private Integer incompleteItems;
    private BigDecimal completeDeptRevenue;
    private BigDecimal incompleteDeptRevenue;
    private Date lastDate;

    public ProductProgress() {}

    public static ProductProgress of(TableRow tableRow) {
        ProductProgress pp = new ProductProgress();
        pp.setItemId(getString(tableRow,0));
        pp.setManufacturer(getString(tableRow,1));
        pp.setRetailerItemId(getString(tableRow,2));
        pp.setRevPortion(getBigDecimal(tableRow,3));
        pp.setDescription(getString(tableRow,4));
        pp.setRetailerDept(getString(tableRow,5));
        pp.setNacsCategory(getString(tableRow,6));
        pp.setComplete(getString(tableRow,7));
        pp.setIsUpc(getString(tableRow,8));
        pp.setCompleteRevenue(getBigDecimal(tableRow,9));
        pp.setIncompleteRevenue(getBigDecimal(tableRow,10));
        pp.setCompleteItems(getInteger(tableRow,11));
        pp.setIncompleteItems(getInteger(tableRow,12));
        pp.setCompleteDeptRevenue(getBigDecimal(tableRow,13));
        pp.setIncompleteDeptRevenue(getBigDecimal(tableRow,14));
        pp.setLastDate(getDate(tableRow, 15));
        return pp;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getRetailerItemId() {
        return retailerItemId;
    }

    public void setRetailerItemId(String retailerItemId) {
        this.retailerItemId = retailerItemId;
    }

    public BigDecimal getRevPortion() {
        return revPortion;
    }

    public void setRevPortion(BigDecimal revPortion) {
        this.revPortion = revPortion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRetailerDept() {
        return retailerDept;
    }

    public void setRetailerDept(String retailerDept) {
        this.retailerDept = retailerDept;
    }

    public String getNacsCategory() {
        return nacsCategory;
    }

    public void setNacsCategory(String nacsCategory) {
        this.nacsCategory = nacsCategory;
    }

    public String getComplete() {
        return complete;
    }

    public boolean isComplete() {
        return this.getComplete().equals("COMPLETE");
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getIsUpc() {
        return isUpc;
    }

    public void setIsUpc(String isUpc) {
        this.isUpc = isUpc;
    }

    public BigDecimal getCompleteRevenue() {
        return completeRevenue;
    }

    public void setCompleteRevenue(BigDecimal completeRevenue) {
        this.completeRevenue = completeRevenue;
    }

    public BigDecimal getIncompleteRevenue() {
        return incompleteRevenue;
    }

    public void setIncompleteRevenue(BigDecimal incompleteRevenue) {
        this.incompleteRevenue = incompleteRevenue;
    }

    public Integer getCompleteItems() {
        return completeItems;
    }

    public void setCompleteItems(Integer completeItems) {
        this.completeItems = completeItems;
    }

    public Integer getIncompleteItems() {
        return incompleteItems;
    }

    public void setIncompleteItems(Integer incompleteItems) {
        this.incompleteItems = incompleteItems;
    }

    public BigDecimal getCompleteDeptRevenue() {
        return completeDeptRevenue;
    }

    public void setCompleteDeptRevenue(BigDecimal completeDeptRevenue) {
        this.completeDeptRevenue = completeDeptRevenue;
    }

    public BigDecimal getIncompleteDeptRevenue() {
        return incompleteDeptRevenue;
    }

    public void setIncompleteDeptRevenue(BigDecimal incompleteDeptRevenue) {
        this.incompleteDeptRevenue = incompleteDeptRevenue;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public boolean isNotComplete() {
        return !isComplete();
    }
}
