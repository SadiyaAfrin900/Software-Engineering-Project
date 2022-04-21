package com.rocket.jarapp.business;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpenseValidatorTests {
    @Test
    public void testValidAmount() {
        String amount = "21.52";
        assertTrue(ExpenseValidator.isAmountNotZero(amount));
    }

    @Test
    public void testInvalidAmount() {
        String amount2 = "0";
        String amount3 = "0.000";
        assertFalse(ExpenseValidator.isAmountNotZero(amount2));
        assertFalse(ExpenseValidator.isAmountNotZero(amount3));
    }
}
