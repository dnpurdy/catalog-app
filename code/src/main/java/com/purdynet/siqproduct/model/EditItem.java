package com.purdynet.siqproduct.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EditItem extends AbstractCoreItem {
    private String container;
    private String size;
    private String uom;
    private String pkg;
    private String brand;
    private String nacs;

    public EditItem() {}

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

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getNacs() {
        return nacs;
    }

    public void setNacs(String nacs) {
        this.nacs = nacs;
    }
}
