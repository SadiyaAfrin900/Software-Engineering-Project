package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.persistence.ExpensePersistence;

import java.util.List;

public class AccessExpenses {

    private ExpensePersistence expensePersistence;

    public AccessExpenses() {
        expensePersistence = Services.getExpensePersistence();
    }

    public AccessExpenses(ExpensePersistence persistence) {
        expensePersistence = persistence;
    }

    public List<Expense> getAllExpenses() {
        return expensePersistence.getAllExpenses();
    }

    public Expense getExpenseById(int id) {
        for (Expense expense : expensePersistence.getAllExpenses()) {
            if (expense.getId() == id) {
                return expense;
            }
        }
        return null;
    }
}