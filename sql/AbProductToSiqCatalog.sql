SELECT 
'' productId,
upc as upc1,
REGEXP_REPLACE(REGEXP_REPLACE(name, ' [0-9]*/[0-9]*[A-Z]*', ''), ' [0-9]x[0-9]','')+" "+
CASE 
WHEN UOM = 'OZ' THEN container+" Pack " + size+" Ounce " +containerDescription  
WHEN UOM = 'ML' THEN container+" Pack " + size+" Milliliter " +containerDescription 
ELSE '' 
END as description,
'' as department, '' as deptDescription,
CASE 
WHEN category = 'Premium' THEN '04-02-00'
WHEN category = 'Sub Premium' THEN '04-03-00'
WHEN category = 'Imports' THEN '04-05-00'
WHEN category = 'Specialty' THEN '04-01-00'
WHEN category = 'Micro' THEN '04-06-00'
WHEN category = 'Wine' THEN '05-00-00'
WHEN category = 'FMB' THEN '04-09-00'
WHEN category = 'Malt Liquor' THEN '04-07-00'
WHEN category = 'Cider' THEN '04-07-00'
WHEN category = 'Liquor' THEN '06-00-00'
WHEN category = 'Cider' THEN '04-09-00'
WHEN category = 'Wine Cooler' THEN '05-03-00'
WHEN category = 'Malta' THEN '07-06-00'
WHEN category = 'Non Alcohol' THEN '04-08-00'
WHEN category = 'Specialty Soda' THEN '07-06-00'
ELSE CONCAT(' ?? ',category)
END as categorySubCode,
CASE 
WHEN category = 'Premium' THEN 'Premium'
WHEN category = 'Sub Premium' THEN 'Popular'
WHEN category = 'Imports' THEN 'Imports'
WHEN category = 'Specialty' THEN 'Super Premium'
WHEN category = 'Micro' THEN 'Microbrews/Craft'
WHEN category = 'Wine' THEN 'Wine'
WHEN category = 'FMB' THEN 'Flavored Malt'
WHEN category = 'Cider' THEN 'Flavored Malt'
WHEN category = 'Malt Liquor' THEN 'Malt Liquor'
WHEN category = 'Liquor' THEN 'Liquor'
WHEN category = 'Wine Cooler' THEN 'Coolers/Wine Cocktails'
WHEN category = 'Malta' THEN 'Other Packaged Beverages (Non-alcoholic)'
WHEN category = 'Non Alcohol' THEN 'Non-alcoholic'
WHEN category = 'Specialty Soda' THEN 'Other Packaged Beverages (Non-alcoholic)'
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
'' as trademark, country, '' as color, ROUND(alternateHeight,2) as alternateHeight, ROUND(alternateWeight,2) as alternateWidth, ROUND(alternateDepth,2) as alternateDepth,
containerDescription, distributor, industryType, FORMAT_UTC_USEC(NOW()) as dateCreated
FROM [default.AbProduct]
WHERE category NOT IN ('Energy','Flex','Mixer','Non-Applicable','Non-Beer','Tea','Water','CSD')
