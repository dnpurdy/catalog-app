package com.purdynet.siqproduct.model.view;

import com.purdynet.siqproduct.model.ProductProgress;

import java.math.BigDecimal;
import java.util.function.Function;

public class CompleteBreakoutEntry {
    private String key;
    private BigDecimal completeRevenue = BigDecimal.ZERO;
    private BigDecimal incompleteRevenue = BigDecimal.ZERO;

    public String getKey() {
        return key;
    }

    public BigDecimal getCompleteRevenue() {
        return completeRevenue;
    }

    public BigDecimal getIncompleteRevenue() {
        return incompleteRevenue;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCompleteRevenue(BigDecimal completeRevenue) {
        this.completeRevenue = completeRevenue;
    }

    public void setIncompleteRevenue(BigDecimal incompleteRevenue) {
        this.incompleteRevenue = incompleteRevenue;
    }

    public CompleteBreakoutEntry add(ProductProgress article, Function<ProductProgress,String> keyFunc) {
        setKey(keyFunc.apply(article));
        setCompleteRevenue(this.completeRevenue.add(article.getCompleteRevenue()));
        setIncompleteRevenue(this.incompleteRevenue.add(article.getIncompleteRevenue()));
        return this;
    }

    public CompleteBreakoutEntry combine(CompleteBreakoutEntry r) {
        this.setKey(r.getKey());
        this.setCompleteRevenue(this.completeRevenue.add(r.getCompleteRevenue()));
        this.setIncompleteRevenue(this.incompleteRevenue.add(r.getIncompleteRevenue()));
        return this;
    }
}
