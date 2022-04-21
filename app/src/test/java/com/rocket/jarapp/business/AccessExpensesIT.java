package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.utils.TestUtils;
import com.rocket.jarapp.objects.Expense;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccessExpensesIT {
    private File tempDB;
    private AccessExpenses accessExpenses;

    @Before
    public void setUp() throws Exception{
        System.out.println("Starting tests for AccessExpenses...");
        tempDB = TestUtils.copyDB();
        accessExpenses = new AccessExpenses();
        assertNotNull(accessExpenses);
    }

    @Test
    public void testGetAllExpenses() {
        assertEquals(16, accessExpenses.getAllExpenses().size());
    }

    @Test
    public void getExpenseByIdSuccess() {
        Expense expense = accessExpenses.getExpenseById(0);
        assertEquals("text books", expense.getName());
    }

    @Test
    public void getExpenseByIdFail() {
        Expense expense = accessExpenses.getExpenseById(40);
        assertNull(expense);
    }

    @After
    public void tearDown() {
        tempDB.delete();
        Services.clean();
        System.out.println("Finished AccessExpenses tests");
    }
}
