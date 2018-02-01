SELECT dept1 as ProductDept, 
ROUND(SUM(IF(complete=1,revPercent,0)),3) as PercentRevComplete, SUM(IF(complete=1,1,0)) as NumberProductComplete, 
ROUND(SUM(IF(complete=0,revPercent,0)),3) as PercentRevToDo, SUM(IF(complete=0,1,0)) as NumberProductTodo,
ROUND(SUM(revPercent),3) as TotalRevenue, SUM(1) as TotalProduct
FROM (
SELECT p.upc+" "+p.description AS dept, p.deptDescription AS dept1, pp.per as revPercent, IF(c.description IS NULL,0,1) as complete FROM
(SELECT pp.itemId, pp.per, c.description FROM [siq.ProductPortion] pp LEFT OUTER JOIN [siq.Catalog] c ON pp.itemId = c.upc) a
LEFT OUTER JOIN [views.Product] p ON pp.itemId = p.upc
--WHERE p.deptDescription = 'CIGARETTES'
ORDER BY pp.per DESC
LIMIT 4000
) GROUP BY 1 ORDER BY 4 DESC
