package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.retailer.KumAndGo;
import com.purdynet.siqproduct.retailer.Retailer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceTest {

    private Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);

    private ProductService productService = new ProductService(new RetailerService());

    @Test
    public void productSql() {
        logger.info(productService.productSql(new KumAndGo(), Retailer::beerClause, ""));
    }
}