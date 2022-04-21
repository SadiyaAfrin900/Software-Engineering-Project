package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.persistence.ExpensePersistence;
import com.rocket.jarapp.persistence.JarPersistence;
import com.rocket.jarapp.persistence.TagPersistence;
import com.rocket.jarapp.persistence.stubs.ExpensePersistenceStub;
import com.rocket.jarapp.persistence.stubs.JarPersistenceStub;
import com.rocket.jarapp.persistence.stubs.TagPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccessJarTests{

    private TagPersistence tagPersistence;
    private ExpensePersistence expensePersistence;
    private JarPersistence jarPersistence;
    private AccessJars accessJars;

    @Before
    public void setUp() {
        System.out.println("Starting tests for AccessJars...");
        tagPersistence = new TagPersistenceStub();
        expensePersistence = new ExpensePersistenceStub(tagPersistence);
        jarPersistence = new JarPersistenceStub(expensePersistence);
        accessJars = new AccessJars(jarPersistence);
    }

    @Test
    public void testGetAllJars() {
        assertEquals(3, accessJars.getAllJars().size());
    }

    @Test
    public void getJarByIdSuccess() {
        assertEquals("Grocery", accessJars.getJarById(0).getName());
    }

    @Test
    public void getJarByIdFail() {
        assertNull(accessJars.getJarById(4));
    }


    @After
    public void tearDown() {
        System.out.println("Finished AccessJars tests");
        Services.clean();
    }
}
