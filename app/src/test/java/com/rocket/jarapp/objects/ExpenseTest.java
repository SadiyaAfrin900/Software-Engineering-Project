package com.rocket.jarapp.objects;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExpenseTest {
    @Test
    public void testExpense() {
        Expense expense;

        System.out.println("Beginning testExpense");

        expense = new Expense(0,"Expense 1", "Note 1", 50.2, new Date(1, Date.Month.JANUARY, 2011), new Time(12, 12));
        assertNotNull(expense);
        assertTrue(expense.getId() == 0);
        assertTrue(expense.getName().equals("Expense 1"));
        assertTrue(expense.getNote().equals("Note 1"));
        assertTrue(expense.getAmount() == 50.2);

        expense.setName("Changed");
        expense.setNote("Note changed");
        expense.setAmount(25.5);

        assertTrue(expense.getName().equals("Changed"));
        assertTrue(expense.getNote().equals("Note changed"));
        assertTrue(expense.getAmount() == 25.5);
        assertTrue(expense.getDateStr().equals("1/1/2011"));
        assertTrue(expense.getTimeStr().equals("12:12"));

        System.out.println("Completed testExpense");
    }
}