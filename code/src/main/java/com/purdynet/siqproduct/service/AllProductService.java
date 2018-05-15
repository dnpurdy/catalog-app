package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.retailer.Retailer;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AllProductService extends AbstractProductService {
    @Override
    public Function<Retailer,String> productSelectFunc() {
        return new Function<Retailer, String>() {
            @Override
            public String apply(Retailer retailer) {
                return "";
            }
        };
    }
}
