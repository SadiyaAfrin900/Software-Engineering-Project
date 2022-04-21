package com.rocket.jarapp.objects;

import org.junit.Test;
import static org.junit.Assert.*;

public class JarTest {
    @Test
    public void testJar(){
        Jar jar;

        System.out.println("Beginning testJar");

        jar = new Jar(0,10,"school", Jar.Colour.BLUE);
        assertNotNull(jar);
        assertTrue(jar.getId() == 0);
        assertTrue(jar.getBudget() == 10);
        assertTrue(jar.getBalance() == 10);
        assertTrue(jar.getName().equals("school"));
        assertTrue(jar.getTheme().equals(Jar.Colour.BLUE));

        jar.setBalance(0);
        jar.setBudget(0);
        jar.setName("other");
        jar.setTheme(Jar.Colour.YELLOW);

        assertTrue(jar.getBudget() == 0);
        assertTrue(jar.getBalance() == 0);
        assertTrue(jar.getName().equals("other"));
        assertTrue(jar.getTheme().equals(Jar.Colour.YELLOW));


        System.out.println("Completed testJar");
    }

    @Test
    public void testJarExpenses(){
        Jar jar = new Jar(0,10,"school", Jar.Colour.BLUE);
        Expense expense = new Expense(0, "book", "note", 5.5, new Date(1, Date.Month.JANUARY, 2011), new Time(11, 11));

        System.out.println("Beginning testJarExpenses");

        assertTrue(jar.getNumExpenses() == 0);

        jar.addExpense(expense);
        assertTrue(jar.getExpenses().contains(expense));
        assertTrue(jar.getBalance() == (jar.getBudget() - expense.getAmount()));
        assertTrue(jar.getNumExpenses() == 1);

        jar.deleteExpense(expense);
        assertFalse(jar.getExpenses().contains(expense));
        assertTrue(jar.getBalance() == jar.getBudget());

        System.out.println("Completed testJarExpenses");

    }

}
