SELECT 
'' as productId, p.upc as upc, p.description as description, '' as department, '' as deptDescription,
CASE
WHEN p.categorySubDescription = 'CIGARETTES' THEN '02-01-00'
WHEN p.categorySubDescription = 'OFF INVOICE CIGS' THEN '02-01-00'
WHEN p.categorySubDescription = 'SMOKELESS' THEN '03-01-00'
WHEN p.categorySubDescription = 'E-CIGARETTES' THEN '03-07-00'
ELSE '???'
END AS categorySubCode,
CASE
WHEN p.categorySubDescription = 'CIGARETTES' THEN 'Premium'
WHEN p.categorySubDescription = 'OFF INVOICE CIGS' THEN 'Premium'
WHEN p.categorySubDescription = 'SMOKELESS' THEN 'Smokeless'
WHEN p.categorySubDescription = 'E-CIGARETTES' THEN 'E-cigarettes'
ELSE '???'
END AS categorySubDescription,
CASE
WHEN p.categorySubDescription = 'CIGARETTES' THEN 'Cigarettes'
WHEN p.categorySubDescription = 'OFF INVOICE CIGS' THEN 'Cigarettes'
WHEN p.categorySubDescription = 'SMOKELESS' THEN 'Other Tobacco Products'
WHEN p.categorySubDescription = 'E-CIGARETTES' THEN 'Other Tobacco Products'
ELSE '???'
END AS category,
CASE
WHEN manufacturer = 'ALTRIA' THEN 'Altria'
WHEN manufacturer = 'REYNOLDS AMERICAN' THEN 'Reynolds American'
WHEN manufacturer = 'LORILLARD TOBACCO CO' THEN 'Lorillard Tobacco Company'
WHEN manufacturer = 'U.S. SMOKELESS TOBACCO COMPANY' THEN 'U.S. Smokeless Tobacco Company'
ELSE CONCAT('??',manufacturer)
END as manufacturer,
'' as subSegmentDescription, '' as subSegmentId, '' as segmentDescription, '' as segmentId, '' as subCategoryDescription, '' as subCategoryId, 
'' as majorDepartmentDescription, '' as majorDepartmentId, '' as container, '' as size, '' as uom, '' as active, '' as privateLabelFlag, 
'' as consumption, '' as package, '' as flavor,
CONCAT(SUBSTR(brand,1,1),SUBSTR(LOWER(brand),2,LENGTH(brand))) as brand,
'' as brandType, '' as height, '' as width, '' as depth, '' as shapeId, '' as family, '' as trademark, '' as country, '' as color,
'' as alternateHeight, '' as alternateWeight, '' as alternateDepth, '' as containerDescription, '' as distributor, '' as industryType,
FORMAT_UTC_USEC(NOW()) as dateCreated
FROM
(SELECT * FROM [views.Product] WHERE department IN ('02','03')) p
JOIN 
(SELECT itemId, description, SUM(extendedAmount*quantity) amt FROM [views.LineItem] GROUP BY 1,2) a
ON a.itemId=p.upc
ORDER BY a.amt DESC
