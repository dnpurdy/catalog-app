<!doctype html>
<html>
    <head>
        <link href="/style.css" rel="stylesheet"/>
    </head>
    <body>
        <table class="data">
            <tr>
                <th>Item Id</th>
                <th>Manufacturer</th>
                <th>Retailer Item Id</th>
                <th>Revenue %</th>
                <th>Description</th>
                <th>Reatiler Dept</th>
                <th>NACS Category</th>
                <th>Complete?</th>
                <th>UPC?</th>
                <th>Complete Revenue %</th>
                <th>Incomplete Revenue %</th>
                <th>Complete Department Rev</th>
                <th>Incomplete Department Rev</th>
                <th>Last Date</th>
            </tr>
        <#list productProgressItems as productProgressItem>
            <tr>
                <td>
                    <#if productProgressItem.complete>
                        ${productProgressItem.itemId}
                    <#else>
                        <a href="/edit?${productProgressItem.toQueryParams()}">
                            ${productProgressItem.itemId}
                        </a>
                    </#if>
                </td>
                <td>${productProgressItem.manufacturer}</td>
                <td>${productProgressItem.retailerItemId}</td>
                <td align="right">${productProgressItem.revPortion?string(",##0.000000%")}</td>
                <td>${productProgressItem.description}</td>
                <td>${productProgressItem.retailerDept}</td>
                <td>${productProgressItem.nacsCategory}</td>
                <td>${productProgressItem.complete?c}</td>
                <td>${productProgressItem.isUpc?string}</td>
                <td align="right">${productProgressItem.completeRevenue?string(",##0.000000%")}</td>
                <td align="right">${productProgressItem.incompleteRevenue?string(",##0.000000%")}</td>
                <td align="right">${productProgressItem.completeDeptRevenue?string(",##0.000000%")}</td>
                <td align="right">${productProgressItem.incompleteDeptRevenue?string(",##0.000000%")}</td>
                <td>${productProgressItem.lastDate?datetime?iso_utc}</td>
            </tr>
        </#list>
        </table>
    </body>
</html>