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
ELSE MANUFACTURER
END as manufacturer,
CASE
WHEN CATEGORY = 'SSD' THEN 'Sparkling Soft Drink'
WHEN CATEGORY = 'ENERGY' THEN 'Energy'
WHEN CATEGORY = 'CONVENTIONAL DAIRY MILK' THEN 'Milk'
WHEN CATEGORY = 'SS BASE WATER' THEN 'Water'
WHEN CATEGORY = 'SS INGREDIENT ENHANCED WATER' THEN 'Enhanced Water'
ELSE CATEGORY 
END as subSegmentDescription,
'' as subSegmentId,
CASE
WHEN SPARKLING_NON_SPARKLING = 'NON-SPARKLING' THEN 'Non-Sparkling'
WHEN SPARKLING_NON_SPARKLING = 'SPARKLING' THEN 'Sparkling'
ELSE SPARKLING_NON_SPARKLING 
END as segmentDescription, 
'' as segmentId,
'' as subCategoryDescription, '' as subCategoryId, '' as majorDepartmentDescription, '' as majorDepartmentId,
INTEGER(MULTI_PACK_SIZE) as container, REGEXP_REPLACE(KEY_PACKAGE,'([A-Z])*','') as size, REGEXP_REPLACE(KEY_PACKAGE,'([0-9])*','') as uom,
'' active, '' privateLabelFlag, 
CASE
WHEN CONSUMPTION = 'FUTURE' THEN 'Future'
WHEN CONSUMPTION = 'IMMEDIATE' THEN 'Immediate'
ELSE CONSUMPTION
END as consumption,
CASE
WHEN KEY_CONTAINER = 'REMAINING CONTAINER' THEN ''
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
FROM [coke.raw] r LEFT OUTER JOIN (
SELECT 
'COCONUT WATER/JUICE' mc, '07-06-00' sc, 'Other Packaged Beverages (Non-alcoholic)' sd, 'Packaged Beverages (Non-alcoholic)' cc
FROM [default.Dual]
) m ON r.category = m.mc
) a JOIN ( SELECT upc,description FROM [views.Product]) b ON a.upc=b.upc
