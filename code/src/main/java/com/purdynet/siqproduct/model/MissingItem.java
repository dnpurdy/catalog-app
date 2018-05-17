package com.purdynet.siqproduct.model;

import com.google.api.services.bigquery.model.TableRow;

import java.math.BigDecimal;
import java.util.Date;

import static com.purdynet.siqproduct.util.BQUtils.*;

public class MissingItem {
    private String itemId;
    private String projectId;
    private Integer numProjects;
    private String manufacturer;
    private String description;
    private Date lastDate;
    private BigDecimal totalRevenue;
    private BigDecimal percentTotalRevenue;

    public MissingItem() {}

    public static MissingItem of(TableRow tableRow) {
        MissingItem missingItem = new MissingItem();
        missingItem.setItemId(getString(tableRow, 0));
        missingItem.setProjectId(getString(tableRow, 1));
        missingItem.setNumProjects(getInteger(tableRow, 2));
        missingItem.setManufacturer(getString(tableRow, 3));
        missingItem.setDescription(getString(tableRow, 4));
        missingItem.setLastDate(getDate(tableRow, 5));
        missingItem.setTotalRevenue(getBigDecimal(tableRow, 6));
        missingItem.setPercentTotalRevenue(getBigDecimal(tableRow, 7));
        return missingItem;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getNumProjects() {
        return numProjects;
    }

    public void setNumProjects(Integer numProjects) {
        this.numProjects = numProjects;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getPercentTotalRevenue() {
        return percentTotalRevenue;
    }

    public void setPercentTotalRevenue(BigDecimal percentTotalRevenue) {
        this.percentTotalRevenue = percentTotalRevenue;
    }
}
