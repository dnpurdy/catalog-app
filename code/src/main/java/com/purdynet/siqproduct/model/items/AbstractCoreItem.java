package com.purdynet.siqproduct.model.items;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCoreItem implements Comparable<AbstractCoreItem> {
    private String itemId;
    private String description;
    private String manufacturer;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String prettyPrint() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getQueryParams() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("itemId", getItemId()));
        params.add(new BasicNameValuePair("description", getDescription()));
        params.add(new BasicNameValuePair("manufacturer", getManufacturer()));
        return URLEncodedUtils.format(params,"UTF-8");
    }

    @Override
    public int compareTo(AbstractCoreItem i1) {
        return this.getItemId().compareTo(i1.getItemId());
    }
}
