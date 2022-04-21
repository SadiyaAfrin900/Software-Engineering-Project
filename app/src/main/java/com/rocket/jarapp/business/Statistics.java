package com.rocket.jarapp.business;

import java.util.List;
import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.objects.Jar;


public class Statistics {

    /** getTotalSavings
     *
     * Returns total savings of all jars as a percentage.
     *
     * Parameters: A list of jars
     */
    public static double getTotalSavings(List<Jar> jars){
        double savings;
        double totalSumExpenses = 0;
        double totalBudget = 0;

        for(int x = 0; x < jars.size(); x++){
            List<Expense> expenses = jars.get(x).getExpenses();
            totalBudget += jars.get(x).getBudget();

            for(int i = 0; i < expenses.size(); i++){
                totalSumExpenses += expenses.get(i).getAmount();
            }
        }

        if(jars.size() > 0)
            savings = (1-(totalSumExpenses/totalBudget));
        else
            savings = 0;

        return savings;
    }

    /** getSavings
     *
     * Returns savings of a specific jar as a percentage.
     *
     * Parameters: The specific jar.
     */
    public static double getSavings(Jar jar){
        double savings;
        double sum = 0;
        List<Expense> expenses = jar.getExpenses();

        for(int i = 0; i < expenses.size(); i++){
            sum += expenses.get(i).getAmount();
        }

        savings = (1-(sum/jar.getBudget()));

        return savings;
    }

    /** getAverageExpense
     *
     * Returns average expense price of a specific jar.
     *
     * Parameters: The specific jar.
     */
    public static double getAverageExpense(Jar jar){
        double sum = 0;
        double average;
        List<Expense> expenses = jar.getExpenses();

        for(int i = 0; i < expenses.size(); i++){
            sum += expenses.get(i).getAmount();
        }

        if(expenses.size() > 0)
            average = sum/expenses.size();
        else
            average = 0;

        return average;
    }
}
