package com.rocket.jarapp.persistence.stubs;

import com.rocket.jarapp.objects.*;
import com.rocket.jarapp.persistence.ExpensePersistence;
import com.rocket.jarapp.persistence.TagPersistence;

import java.util.ArrayList;
import java.util.List;

public class ExpensePersistenceStub implements ExpensePersistence
{
    private List<Expense> expenses;

    public static final String EXPENSE_0_NAME = "Expense 0";
    public static final String EXPENSE_1_NAME = "Expense 1";
    public static final String EXPENSE_2_NAME = "Expense 2";

    public ExpensePersistenceStub(TagPersistence tagPersistence) {
        expenses = new ArrayList<>();

        expenses.add(new Expense(0, EXPENSE_0_NAME, "Note 1", 20.2, new Date(5, Date.Month.NOVEMBER, 2018), new Time(13, 0)));
        expenses.add(new Expense(1, EXPENSE_1_NAME, "Note 2", 50.5, new Date(5, Date.Month.DECEMBER, 2018), new Time(12,5)));
        expenses.add(new Expense(2, EXPENSE_2_NAME, "Note 3", 25, new Date(12, Date.Month.JANUARY, 2019), new Time(9,30)));

        List<Tag> tags = tagPersistence.getAllTags();
        if (tags.size() >= 3) {
            for (int i = 0; i < 3; i++) {
                expenses.get(i).addTag(tags.get(i));
            }
        }
    }

    @Override
    public boolean insertExpense(Expense expense) {
        boolean inserted = false;
        if(!expenses.contains(expense)) {
            expenses.add(expense);
            inserted = true;
        }
        return inserted;
    }

    @Override
    public boolean deleteExpense(Expense expense) {
        int index = expenses.indexOf(expense);
        if (index >= 0) {
            expenses.remove(index);
            return true;
        }

        return false;
    }

    @Override
    public boolean updateExpense(Expense expense) {
        int index = expenses.indexOf(expense);

        if (index >= 0) {
            expenses.set(index, expense);
            return true;
        }

        return false;
    }

    @Override
    public int getNextId() {
        return expenses.size();
    }

    @Override
    public List<Expense> getAllExpenses() { return expenses; }
}
