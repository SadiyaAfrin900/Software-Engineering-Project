package com.rocket.jarapp.persistence;

import com.rocket.jarapp.objects.Expense;
import java.util.List;

public interface ExpensePersistence {

    /**
     * getExpenses
     *
     * Return all Expenses
     */
    List<Expense> getAllExpenses();

    /**
     * insertExpense
     *
     * Save a new expense
     */
    boolean insertExpense(Expense expense);

    /**
     * deleteExpense
     *
     * Remove an expense completely from the system
     */
    boolean deleteExpense(Expense expense);

    /**
     * updateExpense
     *
     * Update an expense of the system
     */
    boolean updateExpense(Expense expense);

    /**
     * getNextId
     *
     * Returns the next unique id that can be used
     */
    int getNextId();
}

