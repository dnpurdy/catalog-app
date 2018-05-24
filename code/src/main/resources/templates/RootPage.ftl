<!doctype html>
<html>
<head>
    <link href="/style.css" rel="stylesheet"/>
</head>
<body>

<div class="${health}"><h1>SwiftIQ Product Catalog App - <span class="${health}"><a href="/health">${health}</a></span></h1></div>

<div class="box"><h2>Catalog Information</h2></div>
<table width="100%">
    <tr>
        <td>
            <h3>Catalog Explorer</h3>
            <a href="/catalog">/catalog</a><br/>
            <a href="/ag/catalog">/ag/catalog</a>
        </td>
        <td>
            <h3>Product Type Analysis</h3>
            <table>
                <tr><th>Name</th><th>Link</th><th>AG Link</th></tr>
                <tr><td>All Missing</td><td><a href="/missing">/missing</a></td><td><a href="/ag/missing">/ag/missing</a></td></tr>
                <tr><td>Beer Missing</td><td><a href="/missing-beer">/missing-beer</a></td><td><a href="/ag/missing-beer">/ag/missing-beer</a></td></tr>
                <tr><td>Beverage Missing</td><td><a href="/missing-beverage">/missing-beverage</a></td><td><a href="/ag/missing-beverage">/ag/missing-beverage</a></td></tr>
                <tr><td>Tobacco Missing</td><td><a href="/missing-tobacco">/missing-tobacco</a></td><td><a href="/ag/missing-tobacco">/ag/missing-tobacco</a></td></tr>
                <tr><td>Salty Snack Missing</td><td><a href="/missing-saltysnack">/missing-saltysnack</a></td><td><a href="/ag/missing-saltysnack">/ag/missing-saltysnack</a></td></tr>
            </table>
        </td>
    </tr>
</table>

<div class="box"><h2>Retailer Information</h2></div>
<table width="100%">
    <tr>
        <td>
            <h3>Retailers</h3>
            <table>
                <tr><th>Name</th></tr>
                <#list retailers as retailer>
                    <tr><td>${retailer.name()}</td></tr>
                </#list>
            </table>
        </td>
        <td>
            <h3>Summaries</h3>
            <table>
                <tr><th>Name</th><th>Summary Link</th></tr>
                <#list retailers as retailer>
                    <tr><td>${retailer.name()}</td><td><a href="/retailer/${retailer.projectId()}/summary">/retailer/${retailer.projectId()}/summary</a></td></tr>
                </#list>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <h3>Missing Items</h3>
            <table>
                <tr><th>Name</th><th>Missing Link</th><th>Missing AG Link</th></tr>
                <#list retailers as retailer>
                    <tr>
                        <td>${retailer.name()}</td>
                        <td><a href="/retailer/${retailer.projectId()}/missing">/retailer/${retailer.projectId()}/missing</a></td>
                        <td><a href="/ag/retailer/${retailer.projectId()}/missing">/ag/retailer/${retailer.projectId()}/missing</a></td>
                    </tr>
                </#list>
            </table>
        </td>
        <td>
            <h3>All Items/Detail</h3>
            <table>
                <tr><th>Name</th><th>Detail Link</th><th>Detail AG Link</th></tr>
                <#list retailers as retailer>
                    <tr>
                        <td>${retailer.name()}</td>
                        <td><a href="/retailer/${retailer.projectId()}/detail">/retailer/${retailer.projectId()}/detail</a></td>
                        <td><a href="/ag/retailer/${retailer.projectId()}/detail">/ag/retailer/${retailer.projectId()}/detail</a></td>
                    </tr>
                </#list>
            </table>
        </td>
    </tr>
</table>

</body>
</html>