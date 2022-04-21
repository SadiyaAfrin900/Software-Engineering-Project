package com.rocket.jarapp.business;

import com.rocket.jarapp.business.utils.StringUtils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsTests {

    @Test
    public void testIsNumericSuccessful(){
        assertTrue(StringUtils.isStringNumeric("10"));
        assertTrue(StringUtils.isStringNumeric("0"));
        assertTrue(StringUtils.isStringNumeric("10.55"));
    }

    @Test
    public void testIsNumericUnSuccessful() {
        assertFalse(StringUtils.isStringNumeric("no"));
    }

}
