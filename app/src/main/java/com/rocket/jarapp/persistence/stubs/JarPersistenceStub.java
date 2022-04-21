package com.rocket.jarapp.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.objects.Jar;
import com.rocket.jarapp.persistence.ExpensePersistence;
import com.rocket.jarapp.persistence.JarPersistence;

public class JarPersistenceStub implements JarPersistence {
    private List<Jar> jars;
    private ExpensePersistence expensePersistence;

    public JarPersistenceStub(ExpensePersistence expensePersistence) {
        this.expensePersistence = expensePersistence;
        this.jars = new ArrayList<>();

        jars.add(new Jar(0, 500, "Grocery", Jar.Colour.BLUE));
        jars.add(new Jar(1, 3000, "School", Jar.Colour.GREEN));
        jars.add(new Jar(2, 1500, "Travel", Jar.Colour.YELLOW));

        //There are no expenses created in expensesPersistenceStub for now
        List<Expense> expenses = expensePersistence.getAllExpenses();
        if (expenses.size() >= 3) {
            for (Expense expense : expenses) {
                jars.get(0).addExpense(expense);
            }
            jars.get(1).addExpense(expenses.get(1));
            jars.get(2).addExpense(expenses.get(2));
        }
    }

    @Override
    public List<Jar> getAllJars() {
        return Collections.unmodifiableList(jars);
    }

    @Override
    public boolean insertJar(Jar jar) {
        if (jars.contains(jar)) {
            return false;
        }

        jars.add(jar);
        return true;
    }

    @Override
    public boolean updateJar(Jar jar) {
        int index = jars.indexOf(jar);

        if (index >= 0) {
            jars.set(index, jar);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteJar(Jar jar) {
        int index = jars.indexOf(jar);

        if (index >= 0) {
            jars.remove(index);
            return true;
        }
        return false;
    }

    @Override
    public int getNextId() {
        return jars.size();
    }
}
