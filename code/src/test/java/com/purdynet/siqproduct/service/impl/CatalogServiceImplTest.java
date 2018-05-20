package com.purdynet.siqproduct.service.impl;

import com.purdynet.siqproduct.BaseTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class CatalogServiceImplTest extends BaseTest {
    @Test
    public void hamming() throws Exception {
        assertEquals("Hamming distance incorrect!", 3L, CatalogServiceImpl.hamming("ABCDEF", "ABC").longValue());
    }
}