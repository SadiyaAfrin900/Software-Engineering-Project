package com.rocket.jarapp;

import com.rocket.jarapp.business.AccessExpensesTests;
import com.rocket.jarapp.business.AccessJarTests;
import com.rocket.jarapp.business.AccessTagsTests;
import com.rocket.jarapp.business.ExpenseValidatorTests;
import com.rocket.jarapp.business.JarValidatorTests;
import com.rocket.jarapp.business.StatisticsTest;
import com.rocket.jarapp.business.StringUtilsTests;
import com.rocket.jarapp.business.TagValidatorTests;
import com.rocket.jarapp.business.UpdateExpenses;
import com.rocket.jarapp.business.UpdateExpensesTest;
import com.rocket.jarapp.business.UpdateJars;
import com.rocket.jarapp.business.UpdateJarsTests;
import com.rocket.jarapp.business.UpdateTags;
import com.rocket.jarapp.business.UpdateTagsTests;
import com.rocket.jarapp.business.utils.StringUtils;
import com.rocket.jarapp.objects.ExpenseTest;
import com.rocket.jarapp.objects.JarTest;
import com.rocket.jarapp.objects.TagTest;
import com.rocket.jarapp.persistence.JarPersistenceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// How to use this class.
//
//  - Each logical grouping of tests should be in their own class
//  - Once you have created a test, include it in the list of suite classes below

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TagTest.class,
        JarTest.class,
        ExpenseTest.class,
        AccessTagsTests.class,
        AccessExpensesTests.class,
        AccessJarTests.class,
        ExpenseValidatorTests.class,
        JarValidatorTests.class,
        TagValidatorTests.class,
        JarPersistenceTest.class,
        StatisticsTest.class,
        UpdateTagsTests.class,
        UpdateExpensesTest.class,
        UpdateJarsTests.class,
        StringUtilsTests.class
})

public class AllUnitTests { }
