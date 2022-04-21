package com.rocket.jarapp.business.utils;

public class StringUtils {

    /** isStringNumeric
     *
     * Returns true if string is a number, false if not.
     *
     * Parameters: The string object to check.
     */
    public static boolean isStringNumeric(String num) {
        boolean result = true;
        try {
            double value = Double.parseDouble(num);
        }
        catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

}
