SELECT productId,b.upc as upc,NVL(b.description,'') as description,department,deptDescription,categorySubCode,categorySubDescription,category,manufacturer,subSegmentDescription,subSegmentId,segmentDescription,segmentId,subCategoryDescription,subCategoryId,majorDepartmentDescription,majorDepartmentId,container,size,uom,active,privateLabelFlag,consumption,package,flavor,brand,brandType,height,width,depth,shapeId,family,trademark,country,color,alternateHeight,alternateWeight,alternateDepth,containerDescription,distributor,industryType,dateCreated
FROM (
SELECT 
'' as productId,
STRING(INTEGER(r.UNIVERSAL_PRODUCT_CODE)) as upc,
'' as department, '' as deptDescription,
NVL(m.sc,'') as categorySubCode, NVL(m.sd,'') as categorySubDescription, NVL(m.cc,'') as category,
CASE
WHEN MANUFACTURER='CCNA' THEN 'The Coca-Cola Company'
WHEN MANUFACTURER='PBNA' THEN 'PepsiCo'
WHEN MANUFACTURER='MONSTER BEVERAGE CORPORATION' THEN 'Monster Beverage Corporation'
WHEN MANUFACTURER='DR PEPPER SNAPPLE GRP' THEN 'Dr Pepper Snapple Group'
WHEN MANUFACTURER='RED BULL NORTH AMERICA INC' THEN 'Red Bull North America, Inc.'
WHEN MANUFACTURER='ROCKSTAR INTERNATIONAL' THEN 'Rockstar International'
ELSE MANUFACTURER
END as manufacturer,
CASE
WHEN CATEGORY = 'SSD' THEN 'Sparkling Soft Drink'
WHEN CATEGORY = 'SPORTS DRINK' THEN 'Sports Drink'
WHEN CATEGORY = 'ENERGY' THEN 'Energy'
WHEN CATEGORY = 'CONVENTIONAL DAIRY MILK' THEN 'Milk'
WHEN CATEGORY = 'SS BASE WATER' THEN 'Water'
WHEN CATEGORY = 'SS INGREDIENT ENHANCED WATER' THEN 'Enhanced Water'
WHEN CATEGORY = 'TEA' THEN 'Tea'
ELSE CATEGORY 
END as subSegmentDescription,
'' as subSegmentId,
CASE
WHEN SPARKLING = 'NON-SPARKLING' THEN 'Non-Sparkling'
WHEN SPARKLING = 'SPARKLING' THEN 'Sparkling'
ELSE NVL(SPARKLING,'')
END as segmentDescription, 
'' as segmentId,
'' as subCategoryDescription, '' as subCategoryId, '' as majorDepartmentDescription, '' as majorDepartmentId,
INTEGER(MULTI_PACK_SIZE) as container, REGEXP_REPLACE(KEY_PACKAGE,'([A-Z])*','') as size, 
REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(KEY_PACKAGE,'([0-9])*',''),' SINGLE',''),' BOTTLE',''),' PK CAN',''),' SNG','') as uom,
'' active, '' privateLabelFlag, 
CASE
WHEN CONSUMPTION = 'FUTURE' THEN 'Future'
WHEN CONSUMPTION = 'IMMEDIATE' THEN 'Immediate'
ELSE CONSUMPTION
END as consumption,
CASE
WHEN KEY_CONTAINER = 'REMAINING CONTAINER' THEN ''
WHEN KEY_CONTAINER = 'PLASTIC BOTTLE' THEN 'Bottle'
WHEN KEY_CONTAINER = 'BOTTLE' THEN 'Bottle'
WHEN KEY_CONTAINER = 'CAN' THEN 'Can'
ELSE KEY_CONTAINER
END as package, KEY_FLAVOR as flavor, brand as brand, '' as brandType,
'' as height, '' as width, '' as depth, '' as shapeId, '' as family,
CASE
WHEN KEY_TRADEMARK = 'REMAINING TRADEMARK' THEN ''
ELSE REGEXP_REPLACE(KEY_TRADEMARK, ' TRADEMARK','')
END as trademark,
'' as country, '' as color, '' as alternateHeight, '' as alternateWeight, '' as alternateDepth, '' as containerDescription, '' as distributor, '' as industryType,
FORMAT_UTC_USEC(NOW()) as dateCreated
FROM [coke.CokeProduct_raw] r LEFT OUTER JOIN ( SELECT * FROM 
(SELECT 'COCONUT WATER/JUICE' mc, '07-06-00' sc, 'Other Packaged Beverages (Non-alcoholic)' sd, 'Packaged Beverages (Non-alcoholic)' cc FROM [default.Dual]),
(SELECT 'SSD' mc, '07-01-00' sc, 'Carbonated Soft Drinks' sd, 'Packaged Beverages (Non-alcoholic)' cc FROM [default.Dual]),
(SELECT 'SS BASE WATER' mc, '07-05-00' sc, 'Bottled Water' sd, 'Packaged Beverages (Non-alcoholic)' cc FROM [default.Dual]),
(SELECT 'SS IMPORT WATER' mc, '07-05-00' sc, 'Bottled Water' sd, 'Packaged Beverages (Non-alcoholic)' cc FROM [default.Dual]),
(SELECT 'SPORTS DRINK' mc, '07-03-00' sc, 'Sports Drinks' sd, 'Packaged Beverages (Non-alcoholic)' cc FROM [default.Dual]),
(SELECT 'ENERGY' mc, '07-07-00' sc, 'Energy Drinks' sd, 'Packaged Beverages (Non-alcoholic)' cc FROM [default.Dual]),
(SELECT 'TEA' mc, '07-02-00' sc, 'Iced Tea (Ready-to-drink)' sd, 'Packaged Beverages (Non-alcoholic)' cc FROM [default.Dual]),
(SELECT 'CONVENTIONAL DAIRY MILK' mc, '??? 09-' sc, '???' sd, 'Fluid Milk Products' cc FROM [default.Dual]),
(SELECT 'JCD' mc, '07-04-00' sc, 'Juice/Juice Drinks' sd, 'Packaged Beverages (Non-alcoholic)' cc FROM [default.Dual]),
(SELECT 'COFFEE' mc, '07-06-00' sc, 'Other Packaged Beverages (Non-alcoholic)' sd, 'Packaged Beverages (Non-alcoholic)' cc FROM [default.Dual]),
(SELECT 'SS INGREDIENT ENHANCED WATER' mc, '07-08-00' sc, 'Enhanced Water' sd, 'Packaged Beverages (Non-alcoholic)' cc FROM [default.Dual]),
) m ON r.category = m.mc
) a JOIN ( SELECT upc,description FROM [views.Product]) b ON a.upc=b.upc
JOIN (SELECT pp.per zper, p.upc zupc FROM
(SELECT pp.itemId, pp.per, c.description FROM [siq.ProductPortion] pp LEFT OUTER JOIN [siq.Catalog] c ON pp.itemId = c.upc) a
JOIN [views.Product] p ON pp.itemId = p.upc WHERE 1=1
AND c.description IS NULL
ORDER BY pp.per DESC) z ON a.upc = z.zupc ORDER BY z.zper DESC
