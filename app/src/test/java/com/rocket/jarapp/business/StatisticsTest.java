package com.rocket.jarapp.business;

import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.objects.Jar;
import com.rocket.jarapp.objects.Date;
import com.rocket.jarapp.objects.Time;

import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class StatisticsTest {
    @Test
    public void testTotalSavings(){
        System.out.println("Beginning testTotalSavings");

        List<Jar> jars = new ArrayList();
        Jar jar = new Jar(1,100,"school", Jar.Colour.BLUE);
        jars.add(jar);
        Jar jar2 = new Jar(2,50,"work", Jar.Colour.GREEN);
        jars.add(jar2);
        Jar jar3 = new Jar(3,150,"school", Jar.Colour.YELLOW);
        jars.add(jar3);

        Expense expense = new Expense(1,"expense1","note1" , 75, new Date(6, Date.Month.JANUARY, 2019), new Time(2, 15));
        Expense expense2 = new Expense(2,"expense2","note2" , 50, new Date(6, Date.Month.FEBRUARY, 2019), new Time(2, 20));
        Expense expense3 = new Expense(3,"expense3","note3" , 100, new Date(6, Date.Month.MARCH, 2019), new Time(2, 25));

        //jars have no expenses
        assertTrue(Statistics.getTotalSavings(jars) == 1.0);

        //jars have expenses
        jar.addExpense(expense);
        jar2.addExpense(expense2);
        jar3.addExpense(expense3);
        assertTrue(Statistics.getTotalSavings(jars) == 0.25);

        System.out.println("Completed testTotalSavings");
    }

    @Test
    public void testSavings(){
        System.out.println("Beginning testSavings");

        Jar jar = new Jar(1,100,"school", Jar.Colour.BLUE);
        Expense expense = new Expense(1,"expense1","note1" , 50, new Date(6, Date.Month.JANUARY, 2019), new Time(2, 15));

        //jar has no expenses
        assertTrue(Statistics.getSavings(jar) == 1.0);

        //jar has expenses
        jar.addExpense(expense);
        assertTrue(Statistics.getSavings(jar) == 0.5);

        System.out.println("Completed testSavings");
    }


    @Test
    public void testAverageExpense(){
        System.out.println("Beginning testAverageExpense");

        Jar jar = new Jar(1,100,"school", Jar.Colour.BLUE);
        Expense expense = new Expense(1,"expense1","note1" , 50, new Date(6, Date.Month.JANUARY, 2019), new Time(2, 15));
        Expense expense2 = new Expense(2,"expense2","note2" , 25, new Date(6, Date.Month.FEBRUARY, 2019), new Time(2, 20));

        //jar has no expense
        assertTrue(Statistics.getAverageExpense(jar) == 0);

        //jar has expense
        jar.addExpense(expense);
        jar.addExpense(expense2);
        assertTrue(Statistics.getAverageExpense(jar) == 37.5);

        System.out.println("Completed testAverageExpense");
    }
}
