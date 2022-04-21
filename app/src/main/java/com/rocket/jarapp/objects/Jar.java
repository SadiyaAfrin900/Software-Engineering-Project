package com.rocket.jarapp.objects;
import java.util.ArrayList;
import java.util.List;

public class Jar {
    public enum Colour {
        BLUE, GREEN, RED, YELLOW, PURPLE;
    }

    private int id;
    private double budget;
    private double balance;
    private String name;
    private Colour theme;
    private List<Expense> expenses;

    public Jar(int id, double budget, String name, Colour theme){
        this.id = id;
        this.budget = budget;
        this.balance = budget;
        this.name = name;
        setTheme(theme);
        expenses = new ArrayList<>();
    }

    public int getId(){
        return id;
    }

    public double getBudget(){
        return budget;
    }

    public void setBudget(double budget){
        this.budget = budget;
        recalculateBalance();
    }

    public double getBalance(){
        return balance;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Colour getTheme(){
        return theme;
    }

    public void setTheme(Colour colour){
        this.theme = colour;
    }

    public List<Expense> getExpenses(){
        return expenses;
    }

    public void addExpense(Expense expense){
        expenses.add(expense);
        this.balance = this.balance - expense.getAmount();
    }

    public void deleteExpense(Expense expense){
        expenses.remove(expense);
        this.balance = this.balance + expense.getAmount();
    }

    public int getNumExpenses(){
        return expenses.size();
    }

    public void recalculateBalance() {
        balance = budget;
        for (Expense expense : expenses) {
            balance -= expense.getAmount();
        }
    }

    @Override
    public boolean equals(Object o){
        Jar jar;
        boolean result = false;
        if(o instanceof Jar) {
            jar = (Jar) o;
            result = (this.id == jar.getId());
        }
        return result;
    }

    @Override
    public String toString(){
        return "name = "+name+", id = "+id+", budget = "+budget+", balance = "
                +balance+ ", theme = "+theme;
    }
}
