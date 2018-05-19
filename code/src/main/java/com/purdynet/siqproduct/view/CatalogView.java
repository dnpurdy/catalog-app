package com.purdynet.siqproduct.view;

import com.purdynet.siqproduct.model.CatalogItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CatalogView extends AbstractView {
    public String makeTable(List<CatalogItem> catalogItems) {
        return wrapHtmlBody(toHTMLTableFromCatalog(catalogItems));
    }

    public String toHTMLTableFromCatalog(List<CatalogItem> catalogItems) {
        StringBuilder ret = new StringBuilder("<table class=\"data\">");
        ret.append("<tr><th>productId</th><th>itemId</th><th>description</th><th>department</th><th>deptDescription</th><th>categorySubCode</th><th>categorySubDescription</th><th>category</th><th>manufacturer</th><th>subSegmentDescription</th><th>subSegmentId</th><th>segmentDescription</th><th>segmentId</th><th>subCategoryDescription</th><th>subCategoryId</th><th>majorDepartmentDescription</th><th>majorDepartmentId</th><th>container</th><th>size</th><th>uom</th><th>active</th><th>privateLabelFlag</th><th>consumption</th><th>pkg</th><th>flavor</th><th>brand</th><th>brandType</th><th>height</th><th>width</th><th>depth</th><th>shapeId</th><th>family</th><th>trademark</th><th>country</th><th>color</th><th>alternateHeight</th><th>alternateWeight</th><th>alternateDepth</th><th>containerDescription</th><th>distributor</th><th>industryType</th><th>dateCreated<th></tr>");
        catalogItems.stream()
                .forEach(ci -> ret.append("<tr>")
                        .append(td(ci.getProductId()))
                        .append(td(ci.getItemId()))
                        .append(td(ci.getDescription()))
                        .append(td(ci.getDepartment()))
                        .append(td(ci.getDeptDescription()))
                        .append(td(ci.getCategorySubCode()))
                        .append(td(ci.getCategorySubDescription()))
                        .append(td(ci.getCategory()))
                        .append(td(ci.getManufacturer()))
                        .append(td(ci.getSubSegmentDescription()))
                        .append(td(ci.getSubSegmentId()))
                        .append(td(ci.getSegmentDescription()))
                        .append(td(ci.getSegmentId()))
                        .append(td(ci.getSubCategoryDescription()))
                        .append(td(ci.getSubCategoryId()))
                        .append(td(ci.getMajorDepartmentDescription()))
                        .append(td(ci.getMajorDepartmentId()))
                        .append(td(ci.getContainer()))
                        .append(td(ci.getSize()))
                        .append(td(ci.getUom()))
                        .append(td(ci.getActive()))
                        .append(td(ci.getPrivateLabelFlag()))
                        .append(td(ci.getConsumption()))
                        .append(td(ci.getPkg()))
                        .append(td(ci.getFlavor()))
                        .append(td(ci.getBrand()))
                        .append(td(ci.getBrandType()))
                        .append(td(ci.getHeight()))
                        .append(td(ci.getWidth()))
                        .append(td(ci.getDepth()))
                        .append(td(ci.getShapeId()))
                        .append(td(ci.getFamily()))
                        .append(td(ci.getTrademark()))
                        .append(td(ci.getCountry()))
                        .append(td(ci.getColor()))
                        .append(td(ci.getAlternateHeight()))
                        .append(td(ci.getAlternateWeight()))
                        .append(td(ci.getAlternateDepth()))
                        .append(td(ci.getContainerDescription()))
                        .append(td(ci.getDistributor()))
                        .append(td(ci.getIndustryType()))
                        .append(td(ci.getDateCreatedString()))
                        .append("</tr>"));
        ret.append("</table>");
        return ret.toString();
    }

    public String toEditTable(List<CatalogItem> catalogItems) {
        return toEditTable(catalogItems, null);
    }

    public String toEditTable(List<CatalogItem> catalogItems, String upc) {
        StringBuilder ret = new StringBuilder("<table class=\"data\">");
        ret.append("<tr><th>ItemId</th><th>Description</th><th>NACSCode</th><th>Manufacturer</th><th>Container</th><th>Size</th><th>UOM</th><th>Package</th><th>Brand</th></tr>");
        catalogItems.stream()
                .forEach(ci -> ret.append("<tr>")
                        .append(td(ci.getItemId() + (upc != null ?  " " : "" )))
                        .append(td(ci.getDescription()))
                        .append(td(ci.getCategorySubCode()+" "+ci.getCategory()+" "+ci.getCategorySubDescription()))
                        .append(td(ci.getManufacturer()))
                        .append(td(ci.getContainer()))
                        .append(td(ci.getSize()))
                        .append(td(ci.getUom()))
                        .append(td(ci.getPkg()))
                        .append(td(ci.getBrand()))
                        .append("</tr>"));
        ret.append("</table>");
        return ret.toString();
    }
}
