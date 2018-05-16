package com.purdynet.siqproduct.model;

import com.google.api.services.bigquery.model.TableRow;

import java.math.BigDecimal;

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

    public ProductProgress() {}

    public ProductProgress(TableRow tableRow) {
        setItemId(tableRow.getF().get(0).getV().toString());
        setManufacturer(tableRow.getF().get(1).getV().toString());
        setRetailerItemId(tableRow.getF().get(2).getV().toString());
        setRevPortion(new BigDecimal(tableRow.getF().get(3).getV().toString()));
        setDescription(tableRow.getF().get(4).getV().toString());
        setRetailerDept(tableRow.getF().get(5).getV().toString());
        setNacsCategory(tableRow.getF().get(6).getV().toString());
        setComplete(tableRow.getF().get(7).getV().toString());
        setIsUpc(tableRow.getF().get(8).getV().toString());
        setCompleteRevenue(new BigDecimal(tableRow.getF().get(9).getV().toString()));
        setIncompleteRevenue(new BigDecimal(tableRow.getF().get(10).getV().toString()));
        setCompleteItems(new Integer(tableRow.getF().get(11).getV().toString()));
        setIncompleteItems(new Integer(tableRow.getF().get(12).getV().toString()));
        setCompleteDeptRevenue(new BigDecimal(tableRow.getF().get(13).getV().toString()));
        setIncompleteDeptRevenue(new BigDecimal(tableRow.getF().get(14).getV().toString()));

        int i = 1;
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

    public boolean isNotComplete() {
        return !isComplete();
    }
}
