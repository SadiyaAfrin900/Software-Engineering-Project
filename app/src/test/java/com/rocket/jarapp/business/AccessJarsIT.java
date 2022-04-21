package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccessJarsIT {
    private File tempDB;
    private AccessJars accessJars;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting tests for AccessJars...");

        tempDB = TestUtils.copyDB();
        accessJars = new AccessJars();
        assertNotNull(accessJars);
    }

    @Test
    public void testGetAllJars() {
        System.out.println("Testing to get all Jars from the database");
        assertEquals(5, accessJars.getAllJars().size());
    }

    @Test
    public void getJarByIdSuccess() {
        assertEquals("Utilities", accessJars.getJarById(0).getName());
    }

    @Test
    public void getJarByIdFail() {
        assertNull(accessJars.getJarById(14));
    }

    @After
    public void tearDown() {
        tempDB.delete();
        Services.clean();
        System.out.println("Finished AccessJars tests");
    }
}
