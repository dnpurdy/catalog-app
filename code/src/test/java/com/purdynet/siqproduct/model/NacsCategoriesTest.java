package com.purdynet.siqproduct.model;

import com.purdynet.siqproduct.model.items.NacsCategories;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class NacsCategoriesTest {

    @Test
    public void matchCategoryCode() {
        assertEquals("Not Equal!", Optional.of(NacsCategories.C_01_00_00), NacsCategories.matchCategoryCode("01-00-00"));
        assertEquals("Not Equal!", Optional.of(NacsCategories.C_05_01_00), NacsCategories.matchCategoryCode("05-01-00"));
    }
}