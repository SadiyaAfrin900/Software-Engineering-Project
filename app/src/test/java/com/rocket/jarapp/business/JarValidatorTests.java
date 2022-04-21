package com.rocket.jarapp.business;

import com.rocket.jarapp.objects.Jar;

import static org.junit.Assert.*;
import org.junit.Test;

public class JarValidatorTests {
    @Test
    public void testNameValidator(){
        //incorrect inputs
        assertFalse(JarValidator.isNameValid(null));
        assertFalse(JarValidator.isNameValid(""));

        //correct inputs
        assertTrue(JarValidator.isNameValid("School"));
    }

    @Test
    public void testBudgetValidator(){
        //incorrect inputs
        assertFalse(JarValidator.isBudgetValid(null));
        assertFalse(JarValidator.isBudgetValid("0"));
        assertFalse(JarValidator.isBudgetValid("-10"));

        //correct inputs
        assertTrue(JarValidator.isBudgetValid("20"));
        assertTrue(JarValidator.isBudgetValid("20.22"));
    }

    @Test
    public void testThemeValidator(){
        //incorrect inputs
        assertFalse(JarValidator.isThemeValid(null));
        assertFalse(JarValidator.isThemeValid("black"));

        //correct inputs
        assertTrue(JarValidator.isThemeValid("blue"));
        assertTrue(JarValidator.isThemeValid("green"));
        assertTrue(JarValidator.isThemeValid("red"));
        assertTrue(JarValidator.isThemeValid("yellow"));
        assertTrue(JarValidator.isThemeValid("purple"));
    }
}
