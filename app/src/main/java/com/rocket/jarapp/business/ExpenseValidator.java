package com.rocket.jarapp.business;

public class ExpenseValidator {

    public static boolean isAmountNotZero(String amount) {
        if (amount == null || amount.equals("0") || Double.parseDouble(amount) == 0.0) {
            return false;
        }
        return true;
    }
}