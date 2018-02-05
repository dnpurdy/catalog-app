SELECT p.* FROM 
(
SELECT pp.idx, IF(c.upc IS NULL,0,1) cataloged, p.* 
FROM [siq.ProductPortion] pp 
JOIN [views.Product] p ON pp.itemId=p.upc
LEFT OUTER JOIN [siq.Catalog] c ON p.upc=c.upc
WHERE c.upc IS NULL
ORDER BY pp.idx ASC
)
WHERE p.department IN ('03','02')