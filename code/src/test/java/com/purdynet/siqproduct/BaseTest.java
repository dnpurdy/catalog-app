package com.purdynet.siqproduct;

import com.purdynet.siqproduct.model.retailer.Caseys;
import com.purdynet.siqproduct.model.retailer.Retailer;
import com.purdynet.siqproduct.service.CatalogService;
import com.purdynet.siqproduct.service.ProductService;
import com.purdynet.siqproduct.service.RetailerService;
import com.purdynet.siqproduct.service.impl.CatalogServiceImpl;
import com.purdynet.siqproduct.service.impl.ProductServiceImpl;
import com.purdynet.siqproduct.service.impl.RetailerServiceImpl;
import com.purdynet.siqproduct.util.ListUtils;
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
public abstract class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        public List<Retailer> retailers() {
            return ListUtils.asSingleton(new Caseys());
        }

        @Bean
        public RetailerService retailerService() {
            return new RetailerServiceImpl(projectId(), retailers());
        }

        @Bean
        public ProductService productService() {
            return new ProductServiceImpl(retailerService());
        }

        @Bean
        public CatalogService catalogService() { return new CatalogServiceImpl(projectId()); }

        public String projectId() { return "test-siq"; }
    }
}
