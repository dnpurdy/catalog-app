<!doctype html>
<html>
    <head>
        <link href="/style.css" rel="stylesheet"/>
    </head>
    <body>
        <table class="data">
            <tr><th>productId</th><th>itemId</th><th>description</th><th>department</th><th>deptDescription</th><th>categorySubCode</th><th>categorySubDescription</th><th>category</th><th>manufacturer</th><th>subSegmentDescription</th><th>subSegmentId</th><th>segmentDescription</th><th>segmentId</th><th>subCategoryDescription</th><th>subCategoryId</th><th>majorDepartmentDescription</th><th>majorDepartmentId</th><th>container</th><th>size</th><th>uom</th><th>active</th><th>privateLabelFlag</th><th>consumption</th><th>pkg</th><th>flavor</th><th>brand</th><th>brandType</th><th>height</th><th>width</th><th>depth</th><th>shapeId</th><th>family</th><th>trademark</th><th>country</th><th>color</th><th>alternateHeight</th><th>alternateWeight</th><th>alternateDepth</th><th>containerDescription</th><th>distributor</th><th>industryType</th><th>dateCreated<th></tr>
        <#list catalogItems as catalogItem>
            <tr>
                <td>${catalogItem.getProductId()!""}</td>
                <td>${catalogItem.getItemId()!""}</td>
                <td>${catalogItem.getDescription()!""}</td>
                <td>${catalogItem.getDepartment()!""}</td>
                <td>${catalogItem.getDeptDescription()!""}</td>
                <td>${catalogItem.getCategorySubCode()!""}</td>
                <td>${catalogItem.getCategorySubDescription()!""}</td>
                <td>${catalogItem.getCategory()!""}</td>
                <td>${catalogItem.getManufacturer()!""}</td>
                <td>${catalogItem.getSubSegmentDescription()!""}</td>
                <td>${catalogItem.getSubSegmentId()!""}</td>
                <td>${catalogItem.getSegmentDescription()!""}</td>
                <td>${catalogItem.getSegmentId()!""}</td>
                <td>${catalogItem.getSubCategoryDescription()!""}</td>
                <td>${catalogItem.getSubCategoryId()!""}</td>
                <td>${catalogItem.getMajorDepartmentDescription()!""}</td>
                <td>${catalogItem.getMajorDepartmentId()!""}</td>
                <td>${catalogItem.getContainer()!""}</td>
                <td>${catalogItem.getSize()!""}</td>
                <td>${catalogItem.getUom()!""}</td>
                <td>${catalogItem.getActive()!""}</td>
                <td>${catalogItem.getPrivateLabelFlag()!""}</td>
                <td>${catalogItem.getConsumption()!""}</td>
                <td>${catalogItem.getPkg()!""}</td>
                <td>${catalogItem.getFlavor()!""}</td>
                <td>${catalogItem.getBrand()!""}</td>
                <td>${catalogItem.getBrandType()!""}</td>
                <td>${catalogItem.getHeight()!""}</td>
                <td>${catalogItem.getWidth()!""}</td>
                <td>${catalogItem.getDepth()!""}</td>
                <td>${catalogItem.getShapeId()!""}</td>
                <td>${catalogItem.getFamily()!""}</td>
                <td>${catalogItem.getTrademark()!""}</td>
                <td>${catalogItem.getCountry()!""}</td>
                <td>${catalogItem.getColor()!""}</td>
                <td>${catalogItem.getAlternateHeight()!""}</td>
                <td>${catalogItem.getAlternateWeight()!""}</td>
                <td>${catalogItem.getAlternateDepth()!""}</td>
                <td>${catalogItem.getContainerDescription()!""}</td>
                <td>${catalogItem.getDistributor()!""}</td>
                <td>${catalogItem.getIndustryType()!""}</td>
                <td>${catalogItem.getDateCreatedString()!""}</td>
            </tr>
        </#list>
        </table>
    </body>
</html>