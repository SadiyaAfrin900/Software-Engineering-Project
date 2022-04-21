package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.objects.Date;
import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.objects.Time;
import com.rocket.jarapp.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UpdateExpensesIT {
    private File tempDB;
    private UpdateExpenses updateExpenses;
    private AccessExpenses accessExpenses;

    @Before
    public void setUp() throws Exception {
        System.out.println("Starting tests for UpdateExpenses...");

        tempDB = TestUtils.copyDB();
        updateExpenses = new UpdateExpenses();
        accessExpenses = new AccessExpenses();

        assertNotNull(updateExpenses);
        assertNotNull(accessExpenses);
    }

    @Test
    public void testInsert() {
        int nextId = updateExpenses.getNextId();
        Expense newExpense = new Expense(nextId, "random", "", 12, new Date(1, Date.Month.JANUARY, 2000), new Time(12, 12));

        assertTrue(updateExpenses.insert(newExpense));
        assertEquals(17, accessExpenses.getAllExpenses().size());
        assertTrue(accessExpenses.getAllExpenses().contains(newExpense));
    }

    @Test
    public void testInsertDuplicate() {
        int id = updateExpenses.getNextId();
        Expense newExpense = new Expense(id, "random", "", 12, new Date(1, Date.Month.JANUARY, 2000), new Time(12, 12));

        assertTrue(updateExpenses.insert(newExpense));
        assertEquals(17, accessExpenses.getAllExpenses().size());

        assertFalse(updateExpenses.insert(newExpense));
        assertEquals(17, accessExpenses.getAllExpenses().size());
    }

    @Test
    public void testDelete() {
        assertTrue(updateExpenses.delete(accessExpenses.getExpenseById(0)));
        assertEquals(15, accessExpenses.getAllExpenses().size());
    }

    @Test
    public void testDeleteDuplicate() {
        assertTrue(updateExpenses.delete(accessExpenses.getExpenseById(1)));
        assertEquals(15, accessExpenses.getAllExpenses().size());
        assertFalse(updateExpenses.delete(accessExpenses.getExpenseById(1)));
        assertEquals(15, accessExpenses.getAllExpenses().size());
    }

    @Test
    public void testUpdate() {
        final int ID = 2;
        final int NEW_AMOUNT = 2000;
        final String NEW_NAME = "RANDOM-stuff";
        final String NEW_NOTE = "hello world";
        Expense before = accessExpenses.getExpenseById(ID);
        before.setNote(NEW_NOTE);
        before.setAmount(NEW_AMOUNT);
        before.setName(NEW_NAME);
        assertTrue(updateExpenses.update(before));
        Expense after = accessExpenses.getExpenseById(ID);
        assertEquals(after.getName(), NEW_NAME);
        assertEquals(after.getNote(), NEW_NOTE);
        assertTrue(after.getAmount() == NEW_AMOUNT);
    }

    @After
    public void tearDown() {
        tempDB.delete();
        Services.clean();
        System.out.println("Finished UpdateExpenses tests");
    }
}
