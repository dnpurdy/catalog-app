SELECT 
z.per,
z.cataloged,
'' as productId,
z.upc,
z.description,
'' as department,
'' as deptDescription,
CASE 
WHEN h.cmg_type_of_candy = 'CHOCOLATE' THEN '08-03-00'
WHEN h.cmg_type_of_candy = 'GUM' THEN '08-01-00'
ELSE '???' END as categorySubCode,
z.categorySubDescription,
z.category,
z.manufacturer,
z.subSegmentDescription,
z.subSegmentId,
z.segmentDescription,
z.segmentId,
z.subCategoryDescription,
z.subCategoryId,
z.majorDepartmentDescription,
z.majorDepartmentId,
z.container,
h.base_size size,
h.base_size uom,
z.active,
z.privateLabelFlag,
z.consumption,
h.cmg_form package,
z.flavor,
z.brand,
z.brandType,
z.height,
z.width,
z.depth,
z.shapeId,
z.family,
z.trademark,
z.country,
z.color,
z.alternateHeight,
z.alternateWeight,
z.alternateDepth,
z.containerDescription,
z.distributor,
z.industryType,
z.dateCreated
FROM (
SELECT pp.per, IF(c.upc IS NULL,0,1) cataloged, p.* 
FROM 
(SELECT NVL(rm.siqId, a.itemId) itemId, description, dept, totalVal, RATIO_TO_REPORT(totalVal) OVER () as per FROM (
  SELECT itemId, p.description, p.deptDescription dept, SUM(quantity*extendedAmount) totalVal FROM [views.LineItem] li JOIN [views.Product] p ON li.itemId = p.upc GROUP BY 1,2,3
 ) a LEFT OUTER JOIN [siq.retailerMap] rm ON a.itemId=rm.retailerId ORDER BY 4 DESC
) pp 
JOIN [views.Product] p ON pp.itemId=p.upc
LEFT OUTER JOIN [siq.Catalog] c ON p.upc=c.upc
WHERE c.upc IS NULL
ORDER BY pp.per DESC
) z LEFT OUTER JOIN [hersheys.Product] h on z.upc=h.upc
WHERE p.deptDescription = 'SALTY SNACKS'
ORDER BY pp.per DESC 
