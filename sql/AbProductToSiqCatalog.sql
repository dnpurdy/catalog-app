SELECT 
'' productId,
upc as upc1,
name as description,
'' as department, '' as deptDescription,
CASE 
WHEN category = 'Premium' THEN '04-02-00'
WHEN category = 'Sub Premium' THEN '04-03-00'
WHEN category = 'Imports' THEN '04-05-00'
ELSE ''
END as categorySubCode,
CASE 
WHEN category = 'Premium' THEN 'Premium'
WHEN category = 'Sub Premium' THEN 'Popular'
WHEN category = 'Imports' THEN 'Imports'
ELSE ''
END as categorySubDescription,
'Beer' as category,
manufacturer as manufacturer,
subSegment as subSegmentDescription, '' as subSegmentId,
segment as segment,
'' as segmentId, '' as subCategoryDescription, '' as subCategoryId, '' as majorDepartmentDescription, '' as majorDepartmentId,
container as container, size as size, uom as uom, '' as active,
'' as privateLabelFlag, '' as	consumption, containerDescription	as package, '' as	flavor,
brand as brand, brandType as brandType, ROUND(heigh,2) as height, ROUND(width,2) as width, ROUND(depth,2) as depth, shapeId as shapeId, family as family,
'' as trademark, country, color, ROUND(alternateHeight,2) as alternateHeight, ROUND(alternateWeight,2) as alternateWidth, ROUND(alternateDepth,2) as alternateDepth,
containerDescription, distributor, industryType, FORMAT_UTC_USEC(NOW()) as dateCreated
FROM [default.AbProduct]
