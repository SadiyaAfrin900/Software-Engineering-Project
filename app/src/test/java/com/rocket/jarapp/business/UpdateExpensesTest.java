package com.rocket.jarapp.business;

import com.rocket.jarapp.objects.Date;
import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.objects.Time;
import com.rocket.jarapp.persistence.ExpensePersistence;
import com.rocket.jarapp.persistence.TagPersistence;
import com.rocket.jarapp.persistence.stubs.ExpensePersistenceStub;
import com.rocket.jarapp.persistence.stubs.TagPersistenceStub;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UpdateExpensesTest {

    private UpdateExpenses updateExpenses;
    private AccessExpenses accessExpenses;

    private TagPersistence tagPersistence;
    private ExpensePersistence expensePersistence;

    @Before
    public void setUp() {
        tagPersistence = new TagPersistenceStub();
        expensePersistence = new ExpensePersistenceStub(tagPersistence);
        updateExpenses = new UpdateExpenses(expensePersistence);
        accessExpenses = new AccessExpenses(expensePersistence);

        assertNotNull(updateExpenses);
        assertNotNull(accessExpenses);
    }

    @Test
    public void testInsert() {
        int nextId = updateExpenses.getNextId();
        Expense newExpense = new Expense(nextId, "random", "", 12, new Date(1, Date.Month.JANUARY,2000), new Time(12, 12));

        assertTrue(updateExpenses.insert(newExpense));
        assertTrue(accessExpenses.getAllExpenses().contains(newExpense));
        assertNotNull(accessExpenses.getExpenseById(nextId));
    }

    @Test
    public void testInsertDuplicate() {
        int id = updateExpenses.getNextId();
        int sizeBefore = accessExpenses.getAllExpenses().size();
        Expense newExpense = new Expense(id, "random", "", 12, new Date(1, Date.Month.JANUARY,2000), new Time(12, 12));

        assertTrue(updateExpenses.insert(newExpense));
        assertFalse(updateExpenses.insert(newExpense));
        assertEquals(sizeBefore + 1, accessExpenses.getAllExpenses().size());
    }

    @Test
    public void testDelete() {
        assertTrue(updateExpenses.delete(accessExpenses.getExpenseById(0)));
    }

    @Test
    public void testDeleteDuplicate() {
        assertTrue(updateExpenses.delete(accessExpenses.getExpenseById(1)));
        assertFalse(updateExpenses.delete(accessExpenses.getExpenseById(1)));
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
}
