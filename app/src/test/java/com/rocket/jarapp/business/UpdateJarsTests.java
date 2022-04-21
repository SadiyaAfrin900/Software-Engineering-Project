package com.rocket.jarapp.business;

import com.rocket.jarapp.objects.Jar;
import com.rocket.jarapp.persistence.ExpensePersistence;
import com.rocket.jarapp.persistence.JarPersistence;
import com.rocket.jarapp.persistence.TagPersistence;
import com.rocket.jarapp.persistence.stubs.ExpensePersistenceStub;
import com.rocket.jarapp.persistence.stubs.JarPersistenceStub;
import com.rocket.jarapp.persistence.stubs.TagPersistenceStub;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UpdateJarsTests {

    private UpdateJars updateJars;
    private AccessJars accessJars;

    private TagPersistence tagPersistence;
    private ExpensePersistence expensePersistence;
    private JarPersistence jarPersistence;

    @Before
    public void setUp() {
        tagPersistence = new TagPersistenceStub();
        expensePersistence = new ExpensePersistenceStub(tagPersistence);
        jarPersistence = new JarPersistenceStub(expensePersistence);
        updateJars = new UpdateJars(jarPersistence);
        accessJars = new AccessJars(jarPersistence);

        assertNotNull(updateJars);
        assertNotNull(accessJars);
    }

    @Test
    public void testInsert() {
        int nextId = updateJars.getNextId();
        Jar newJar = new Jar(nextId, 2000, "new jar", Jar.Colour.BLUE);

        assertTrue(updateJars.insert(newJar));
        assertTrue(accessJars.getAllJars().contains(newJar));
        assertNotNull(accessJars.getJarById(nextId));
    }

    @Test
    public void testInsertDuplicate() {
        int id = updateJars.getNextId();
        int sizeBefore = accessJars.getAllJars().size();
        Jar newJar = new Jar(id, 3034, "hello world", Jar.Colour.GREEN);

        assertTrue(updateJars.insert(newJar));
        assertFalse(updateJars.insert(newJar));
        assertEquals(sizeBefore + 1, accessJars.getAllJars().size());
    }

    @Test
    public void testDelete() {
        assertTrue(updateJars.delete(accessJars.getJarById(0)));
    }

    @Test
    public void testDeleteDuplicate() {
        assertTrue(updateJars.delete(accessJars.getJarById(1)));
        assertFalse(updateJars.delete(accessJars.getJarById(1)));
    }

    @Test
    public void testUpdate() {
        final int ID = 2;
        final int NEW_BUDGET = 2000;
        final String NEW_NAME = "RANDOM-stuff";
        final Jar.Colour NEW_THEME = Jar.Colour.BLUE;
        Jar before = accessJars.getJarById(ID);
        before.setBudget(NEW_BUDGET);
        before.setTheme(NEW_THEME);
        before.setName(NEW_NAME);
        assertTrue(updateJars.update(before));
        Jar after = accessJars.getJarById(ID);
        assertEquals(after.getName(), NEW_NAME);
        assertEquals(after.getTheme(), NEW_THEME);
        assertTrue(after.getBudget() == NEW_BUDGET);
    }
}

