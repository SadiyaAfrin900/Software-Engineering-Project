package com.rocket.jarapp.persistence;

import com.rocket.jarapp.objects.Jar;
import com.rocket.jarapp.persistence.stubs.ExpensePersistenceStub;
import com.rocket.jarapp.persistence.stubs.JarPersistenceStub;
import com.rocket.jarapp.persistence.stubs.TagPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class JarPersistenceTest {
    private TagPersistence tagPersistence;
    private ExpensePersistence expensePersistence;
    private JarPersistence jarPersistence;

    @Before
    public void setup() {
        System.out.println("Starting tests for JarPersistence...");
        tagPersistence = new TagPersistenceStub();
        expensePersistence = new ExpensePersistenceStub(tagPersistence);
        jarPersistence = new JarPersistenceStub(expensePersistence);
    }

    @Test
    public void testGetAllJars() {
        assertEquals(3, jarPersistence.getAllJars().size());
    }

    @Test
    public void insertJarSuccess() {
        Jar jar = new Jar(3,100,"Travel", Jar.Colour.BLUE);
        assertTrue(jarPersistence.insertJar(jar));
    }

    @Test
    public void insertJarFail() {
        Jar jar = new Jar(2,100,"Travel", Jar.Colour.BLUE);
        boolean hasInsert = jarPersistence.insertJar(jar);
        assertFalse(hasInsert);
    }

    @Test
    public void updateJarSuccess() {
        Jar jar = jarPersistence.getAllJars().get(0);
        jar.setBudget(200.56);
        assertTrue(jarPersistence.updateJar(jar));
        assertTrue(jarPersistence.getAllJars().get(0).getBudget() == 200.56);
    }

    @Test
    public void updateJarFail() {
        Jar jar1 = new Jar(4,100,"Travel", Jar.Colour.BLUE);
        Jar jar2 = null;
        assertFalse(jarPersistence.updateJar(jar1));
        assertFalse(jarPersistence.updateJar(jar2));
    }


    @After
    public void clean() {
        System.out.println("Finished tests for JarPersistence...");
    }
}
