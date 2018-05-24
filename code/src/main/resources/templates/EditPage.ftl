<!doctype html>
<html>
<head>
    <link href="/style.css" rel="stylesheet"/>
</head>
<body>
<form method="post" action="edit">
    <ul class="form-style-1">
        <li>
            <label for="itemId">itemId<span class="required">*</span>
                <input class="field-long" id="itemId" name="itemId" type="text" <#if editItem.getItemId()?has_content>value="${editItem.getItemId()}"</#if> />
        </li>
        <li>
            <label for="description">description<span class="required">*</span>
                <input class="field-long" id="description" name="description" type="text" <#if editItem.getDescription()?has_content>value="${editItem.getDescription()}"</#if> /></li>
        <li>
            <select name="nacs" class="field-select">
                <#list nacsList as nacsItem>
                    <option value="${nacsItem.name()}">${nacsItem.getCategoryCode()} - ${nacsItem.getCategory()} - ${nacsItem.getSubCategory()}</option>
                </#list>
            </select>
        </li>
        <li>
            <label for="manufacturer">manufacturer<span class="required">*</span>
                <input class="field-long" id="manufacturer" name="manufacturer" type="text" <#if editItem.getManufacturer()?has_content>value="${editItem.getManufacturer()}"</#if> />
        </li>
        <li><label for="container">container<input class="field-long" id="container" name="container" type="text" /></li>
        <li><label for="size">size<input class="field-long" id="size" name="size" type="text" /></li>
        <li><label for="uom">uom<input class="field-long" id="uom" name="uom" type="text" /></li>
        <li><label for="brand">brand<input class="field-long" id="brand" name="brand" type="text" /></li>
        <li><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></li>
    </ul>
</form>

<hr/>

<table class="data">
    <tr><th>ItemId</th><th>Description</th><th>NACSCode</th><th>Manufacturer</th><th>Container</th><th>Size</th><th>UOM</th><th>Brand</th></tr>
    <#list nearMatches as nearMatch>
        <tr>
            <td>${nearMatch.getItemId()}</td>
            <td>${nearMatch.getDescription()}</td>
            <td>${nearMatch.getCategorySubCode()} - ${nearMatch.getCategory()} - ${nearMatch.getCategorySubDescription()}</td>
            <td>${nearMatch.getManufacturer()}</td>
            <td>${nearMatch.getContainer()}</td>
            <td>${nearMatch.getSize()}</td>
            <td>${nearMatch.getUom()}</td>
            <td>${nearMatch.getBrand()}</td>
        </tr>
    </#list>
</table>

</body>
</html>