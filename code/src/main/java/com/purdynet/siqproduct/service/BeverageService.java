package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.retailer.Retailer;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BeverageService extends AbstractProductService {
    @Override
    public Function<Retailer,String> productSelectFunc() {
        return Retailer::beverageClause;
    }
}
