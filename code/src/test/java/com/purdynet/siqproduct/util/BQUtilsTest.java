package com.purdynet.siqproduct.util;

import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableRow;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class BQUtilsTest {
    @Test
    public void testGetString() {
        TableRow tr = new TableRow();
        tr.setF(ListUtils.asSingleton(new TableCell().setV("David")));

        assertEquals("Not equal!", "David", BQUtils.getString(tr,0));
        assertEquals("Not equal!", "", BQUtils.getString(tr, 1));
    }

    @Test
    public void getInteger() {
        TableRow tr = new TableRow();
        tr.setF(ListUtils.asSingleton(new TableCell().setV("1")));

        assertEquals("Not equal!", Integer.valueOf("1"), BQUtils.getInteger(tr,0));
        assertNull("Not equal!", BQUtils.getInteger(tr, 1));
    }

    @Test
    public void getDate() {
        TableRow tr = new TableRow();
        tr.setF(ListUtils.asSingleton(new TableCell().setV("1")));

        assertEquals("Not equal!", new Date(1000L), BQUtils.getDate(tr,0));
        assertNull("Not equal!", BQUtils.getDate(tr, 1));
    }

    @Test
    public void getBigDecimal() {
        TableRow tr = new TableRow();
        tr.setF(ListUtils.asSingleton(new TableCell().setV("1")));

        assertEquals("Not equal!", BigDecimal.ONE, BQUtils.getBigDecimal(tr,0));
        assertNull("Not equal!", BQUtils.getBigDecimal(tr, 1));
    }

}