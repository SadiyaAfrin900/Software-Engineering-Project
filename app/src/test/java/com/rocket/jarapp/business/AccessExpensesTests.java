package com.rocket.jarapp.business;

import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.persistence.ExpensePersistence;
import com.rocket.jarapp.persistence.TagPersistence;
import com.rocket.jarapp.persistence.stubs.ExpensePersistenceStub;
import com.rocket.jarapp.persistence.stubs.TagPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccessExpensesTests {

    private TagPersistence tagPersistence;
    private ExpensePersistence expensePersistence;
    private AccessExpenses accessExpenses;

    @Before
    public void setUp() {
        System.out.println("Starting tests for AccessExpenses...");
        tagPersistence = new TagPersistenceStub();
        expensePersistence = new ExpensePersistenceStub(tagPersistence);
        accessExpenses = new AccessExpenses(expensePersistence);
    }

    @Test
    public void testGetAllExpenses() {
        assertEquals(3, accessExpenses.getAllExpenses().size());
    }

    @Test
    public void getExpenseByIdSuccess() {
        Expense expense = accessExpenses.getExpenseById(0);
        assertEquals(ExpensePersistenceStub.EXPENSE_0_NAME, expense.getName());
    }

    @Test
    public void getExpenseByIdFail() {
        Expense expense = accessExpenses.getExpenseById(4);
        assertNull(expense);
    }

    @After
    public void tearDown() {
        System.out.println("Finished AccessExpenses tests");
    }
}
