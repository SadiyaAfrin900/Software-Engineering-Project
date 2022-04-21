package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.objects.Jar;
import com.rocket.jarapp.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UpdateJarsIT {
    private File tempDB;
    private UpdateJars updateJars;
    private AccessJars accessJars;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting tests for UpdateJars...");

        tempDB = TestUtils.copyDB();
        updateJars = new UpdateJars();
        accessJars = new AccessJars();
        assertNotNull(updateJars);
        assertNotNull(accessJars);
    }

    @Test
    public void testInsert() {
        int nextId = updateJars.getNextId();
        Jar newJar = new Jar(nextId, 2000, "new jar", Jar.Colour.BLUE);

        assertTrue(updateJars.insert(newJar));
        assertEquals(6, accessJars.getAllJars().size());
    }

    @Test
    public void testInsertDuplicate() {
        int id = updateJars.getNextId();
        Jar newJar = new Jar(id, 3034, "hello world", Jar.Colour.GREEN);

        assertTrue(updateJars.insert(newJar));
        assertFalse(updateJars.insert(newJar));
        assertEquals(6, accessJars.getAllJars().size());
    }

    @Test
    public void testDelete() {
        assertTrue(updateJars.delete(accessJars.getJarById(0)));
        assertEquals(4, accessJars.getAllJars().size());
    }

    @Test
    public void testDeleteDuplicate() {
        assertTrue(updateJars.delete(accessJars.getJarById(1)));
        assertEquals(4, accessJars.getAllJars().size());

        assertFalse(updateJars.delete(accessJars.getJarById(1)));
        assertEquals(4, accessJars.getAllJars().size());
    }

    @Test
    public void testUpdate() {
        Jar before = accessJars.getJarById(2);
        before.setBudget(2000);
        before.setTheme(Jar.Colour.BLUE);
        before.setName("RANDOM-stuff");

        assertTrue(updateJars.update(before));

        Jar after = accessJars.getJarById(2);
        assertEquals("RANDOM-stuff", after.getName());
        assertEquals(Jar.Colour.BLUE, after.getTheme());
        assertTrue(after.getBudget() == 2000);
    }


    @After
    public void tearDown() {
        tempDB.delete();
        Services.clean();
        System.out.println("Finished UpdateJars tests");
    }
}
