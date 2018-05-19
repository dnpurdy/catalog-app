package com.purdynet.siqproduct.service;

import com.purdynet.siqproduct.SiqproductApplicationTests;
import org.junit.Test;

import static org.junit.Assert.*;

public class CatalogServiceTest extends SiqproductApplicationTests {
    @Test
    public void hamming() throws Exception {
        assertEquals("Hamming distance incorrect!", 3L, CatalogService.hamming("ABCDEF", "ABC").longValue());
    }
}