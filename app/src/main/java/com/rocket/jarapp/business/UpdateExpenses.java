package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.persistence.ExpensePersistence;

public class UpdateExpenses {
    private ExpensePersistence expensePersistence;

    public UpdateExpenses(ExpensePersistence expensePersistence) {
        this.expensePersistence = expensePersistence;
    }

    public UpdateExpenses() {
        this.expensePersistence = Services.getExpensePersistence();
    }

    public boolean insert(Expense expense) {
        return expensePersistence.insertExpense(expense);
    }

    public boolean delete(Expense expense) {
        return expensePersistence.deleteExpense(expense);
    }

    public boolean update(Expense expense) {
        return expensePersistence.updateExpense(expense);
    }

    public int getNextId() {
        return expensePersistence.getNextId();
    }
}
