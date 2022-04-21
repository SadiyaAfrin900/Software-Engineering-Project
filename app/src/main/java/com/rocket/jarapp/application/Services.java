package com.rocket.jarapp.application;

import com.rocket.jarapp.persistence.ExpensePersistence;
import com.rocket.jarapp.persistence.JarPersistence;
import com.rocket.jarapp.persistence.TagPersistence;
import com.rocket.jarapp.persistence.hsqldb.ExpensePersistenceHSQLDB;
import com.rocket.jarapp.persistence.hsqldb.JarPersistenceHSQLDB;
import com.rocket.jarapp.persistence.hsqldb.TagPersistenceHSQLDB;

public class Services {
    private static JarPersistence jarPersistence = null;
    private static ExpensePersistence expensePersistence = null;
    private static TagPersistence tagPersistence = null;

    /**
     * getTagPersistence
     *
     * Singleton design for accessing the tag persistence class from anywhere in the code
     */
    public static synchronized TagPersistence getTagPersistence() {
        if (tagPersistence == null) {
            tagPersistence = new TagPersistenceHSQLDB(Main.getDBPathName());
        }
        return tagPersistence;
    }

    /**
     * getJarPersistence
     */
    public static synchronized JarPersistence getJarPersistence() {
        if (jarPersistence == null) {
            jarPersistence = new JarPersistenceHSQLDB(Main.getDBPathName(), getExpensePersistence());
        }
        return jarPersistence;
    }

    /**
     * getExpensePersistence
     *
     * Singleton design for accessing the expense persistence class from anywhere in the code
     */
    public static synchronized ExpensePersistence getExpensePersistence() {
        if (expensePersistence == null) {
            expensePersistence = new ExpensePersistenceHSQLDB(Main.getDBPathName(), getTagPersistence());
        }
        return expensePersistence;
    }

    /**
     * clean
     *
     * Reset all services so to be reloaded from scratch next time they are referenced
     */
    public static synchronized void clean() {
        tagPersistence = null;
        expensePersistence = null;
        jarPersistence = null;
    }
}
