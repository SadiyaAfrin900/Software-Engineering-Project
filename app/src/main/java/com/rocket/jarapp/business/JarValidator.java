package com.rocket.jarapp.business;

import com.rocket.jarapp.business.utils.StringUtils;
import com.rocket.jarapp.objects.Jar;

public class JarValidator {

    /** isNameValid
     *
     * Returns true if name is valid, false if not.
     *
     * No parameters.
     */
    public static boolean isNameValid(String name){
        boolean result = false;

        if(name != null && name != ""){
            result = true;
        }

        return result;
    }

    /** isBudgetValid
     *
     * Returns true if budget is valid, false if not.
     *
     * No parameters.
     */
    public static boolean isBudgetValid(String budget){
        boolean result = false;

        if(budget != null && StringUtils.isStringNumeric(budget)) {
            if(Double.parseDouble(budget) > 0 )
                result = true;
        }

        return result;
    }

    /** isThemeValid
     *
     * Returns true if theme is valid, false if not.
     *
     * No parameters.
     */
    public static boolean isThemeValid(String theme){
        if (theme == null) {
            return false;
        }

        for (Jar.Colour colour : Jar.Colour.values()) {
            if (colour.name().toLowerCase().equals(theme.toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}
