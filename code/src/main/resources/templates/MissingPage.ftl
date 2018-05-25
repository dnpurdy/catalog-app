<!doctype html>
<html>
    <head>
        <link href="/style.css" rel="stylesheet"/>
    </head>
    <body>
        <table class="data">
            <tr>
                <th>ItemId</th>
                <th>Project Id</th>
                <th># Projects</th>
                <th>Manufacturer</th>
                <th>Description</th>
                <th>Last Date</th>
                <th>Total Revenue</th>
                <th>% Total Revenue</th>
            </tr>
        <#list missingItems as missingItem>
            <tr>
                <td>
                    <a href="/edit?${missingItem.toQueryParams()}" target="_blank">${missingItem.getItemId()}</a>
                </td>
                <td>${missingItem.getProjectId()}</td>
                <td>${missingItem.getNumProjects()}</td>
                <td>${missingItem.getManufacturer()}</td>
                <td>${missingItem.getDescription()}</td>
                <td>${missingItem.getLastDate()?date?iso_utc}</td>
                <td align="right">${missingItem.getTotalRevenue()?string.currency}</td>
                <td align="right">${missingItem.getPercentTotalRevenue()?string(",##0.000000%")}</td>
            </tr>
        </#list>
        </table>
    </body>
</html>