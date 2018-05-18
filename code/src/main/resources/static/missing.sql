SELECT pp.itemId itemId, pp.manufacturer manufacturer, pp.retailerItemId retailerItemId, pp.per revPortion, NVL(c.description,pp.description) description, pp.deptDescription as retailerDept, c.category as nacsCategory,
 IF(c.description IS NULL,"TODO","COMPLETE") as complete, IF( (LENGTH(pp.itemId) = 10 OR LENGTH(pp.itemId) == 11) ,"UPC","PLU") isUpc,
 IF(c.description IS NULL,0,pp.per) as completeRevenue, IF(c.description IS NULL,pp.per,0) as incompleteRevenue,
 IF(c.description IS NULL,0,1) as completeItems, IF(c.description IS NULL,1,0) as incompleteItems,
 IF(c.description IS NULL,0,pp.perDept) as completeDeptRevenue, IF(c.description IS NULL,pp.perDept,0) as incompleteDeptRevenue FROM (
  SELECT NVL(rm.siqId, rev.itemId) itemId, rev.itemId retailerItemId, manufacturer, description, rev.deptDescription deptDescription, rev.totalVal,
  RATIO_TO_REPORT(rev.totalVal) OVER () as per, RATIO_TO_REPORT(rev.totalVal) OVER (PARTITION BY deptDescription) as perDept FROM (
   SELECT  itemId  as itemId, p.description, p.manufacturer, p.deptDescription, SUM(ABS(quantity)*ABS(extendedAmount/1000)) totalVal FROM [kg-siq:default.LineItem] li JOIN [kg-siq:default.Product] p ON li.itemId = p.upc GROUP BY 1,2,3,4
  ) rev LEFT OUTER JOIN [kg-siq:siq.retailerMap] rm ON rev.itemId=rm.retailerId ORDER BY 4 DESC
 ) pp LEFT OUTER JOIN [swiftiq-master:siq.Catalog] c ON pp.itemId = c.upc
 WHERE c.description IS NULL  ORDER BY pp.per DESC  LIMIT 1000