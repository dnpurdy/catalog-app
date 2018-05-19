package com.purdynet.siqproduct;

import com.purdynet.siqproduct.retailer.Caseys;
import com.purdynet.siqproduct.retailer.Retailer;
import com.purdynet.siqproduct.service.ProductService;
import com.purdynet.siqproduct.service.RetailerService;
import com.purdynet.siqproduct.util.ListUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SiqproductApplicationTests {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        public List<Retailer> retailers() {
            return ListUtils.asSingleton(new Caseys());
        }

        @Bean
        public RetailerService retailerService() {
            return new RetailerService(retailers());
        }

        @Bean
        public ProductService productService() {
            return new ProductService(retailerService());
        }
    }
    
    @Test
    public void contextLoads() {
    }
}
