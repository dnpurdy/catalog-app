<!doctype html>
<html>
<head>
    <link href="/style.css" rel="stylesheet"/>
</head>
<body>
<h1>Overall</h1>
<table class="data" id="overall">
    <tr><th>COMPLETE</th><th>TODO</th></tr>
    <#list overall as overallItem>
    <tr>
        <td align="right">${overallItem.getCompleteRevenue()?string(",##0.000%")}</td>
        <td align="right">${overallItem.getIncompleteRevenue()?string(",##0.000%")}</td>
    </tr>
    </#list>
</table>
<h1>Breakout</h1>
<table class="data" id="breakout">
    <tr><th>TYPE</th><th>COMPLETE</th><th>TODO</th></tr>
    <#list upcPlu as upcPluItem>
    <tr>
        <td>${upcPluItem.getKey()}</td>
        <td align="right">${upcPluItem.getCompleteRevenue()?string(",##0.000%")}</td>
        <td align="right">${upcPluItem.getIncompleteRevenue()?string(",##0.000%")}</td>
    </tr>
    </#list>
</table>
<h1>Department</h1>
<table class="data" id="breakout">
    <tr><th>TYPE</th><th>COMPLETE</th><th>TODO</th></tr>
    <#list dept as deptItem>
        <tr>
            <td>${deptItem.getKey()}</td>
            <td align="right">${deptItem.getCompleteRevenue()?string(",##0.000%")}</td>
            <td align="right">${deptItem.getIncompleteRevenue()?string(",##0.000%")}</td>
        </tr>
    </#list>
</table>
<h1>Top ${topProducts?size} Products</h1>
<table class="data" id="products">
    <tr><th>UPC/PLU</th><th>ITEMID</th><th>DESC</th><th>MANUF</th></tr>
    <#list topProducts as topProductsItem>
    <tr>
        <td>${topProductsItem.getIsUpc()}</td>
        <td><a href="/edit?${topProductsItem.toQueryParams()}" target="_blank">${topProductsItem.getItemId()}</a></td>
        <td>${topProductsItem.getDescription()}</td>
        <td>${topProductsItem.getManufacturer()}</td>
    </tr>
    </#list>
</table>
</body>
</html>