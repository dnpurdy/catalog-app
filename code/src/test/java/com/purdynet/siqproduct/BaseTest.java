package com.purdynet.siqproduct;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void setUp() {}
}
