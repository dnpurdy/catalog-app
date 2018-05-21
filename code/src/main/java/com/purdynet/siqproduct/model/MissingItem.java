package com.purdynet.siqproduct.model;

import com.google.api.services.bigquery.model.TableRow;
import com.purdynet.siqproduct.biqquery.NamedRow;

import java.math.BigDecimal;
import java.util.Date;

import static com.purdynet.siqproduct.util.BQUtils.*;

public class MissingItem extends AbstractCoreItem {
    private String projectId;
    private Integer numProjects;
    private Date lastDate;
    private BigDecimal totalRevenue;
    private BigDecimal percentTotalRevenue;

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

    public Date getLastDate() {
        return lastDate;
    }

    public String getLastDateString() {
        if (lastDate != null ) {
            return lastDate.toString();
        } else {
            return "";
        }
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
