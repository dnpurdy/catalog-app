SELECT itemId, description, totalVal, per, totalPer, ROW_NUMBER() OVER (ORDER BY totalPer ASC) as idx FROM 
(SELECT itemId, totalVal, per, SUM(per) OVER (ORDER BY totalVal DESC) totalPer FROM (SELECT itemId, totalVal, RATIO_TO_REPORT(totalVal) OVER (ORDER BY totalVal DESC) per FROM (SELECT itemId, SUM(quantity*extendedAmount) totalVal, FROM [default.LineItem] GROUP BY 1)))a
JOIN [views.Product] p ON a.itemId=p.upc
ORDER BY totalPer ASC