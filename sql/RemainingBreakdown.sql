SELECT p.* FROM
(SELECT pp.itemId, pp.per, c.description FROM [siq.ProductPortion] pp LEFT OUTER JOIN [siq.Catalog] c ON pp.itemId = c.upc) a
JOIN [views.Product] p ON pp.itemId = p.upc
WHERE c.description IS NULL
ORDER BY pp.per DESC
