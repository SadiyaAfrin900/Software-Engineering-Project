package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class AccessTagsIT {
    private File tempDB;
    private AccessTags accessTags;

    @Before
    public void setUp() throws Exception{
        System.out.println("Starting tests for AccessTags...");
        tempDB = TestUtils.copyDB();
        accessTags = new AccessTags();
        assertNotNull(accessTags);
    }

    @Test
    public void testGetAllTags() {
        assertEquals(7, accessTags.getAllTags().size());
    }

    @Test
    public void testGetTagById() {
        Tag tag = accessTags.getTagById(0);
        assertEquals("food", tag.getName());
    }

    @Test
    public void testGetTagByName() {
        String tagName = "summer";
        Tag tag = accessTags.getTagByName(tagName);
        assertEquals(tag.getName(), tagName);
    }

    @After
    public void tearDown() {
        tempDB.delete();
        Services.clean();
        System.out.println("Finished AccessTags tests");
    }
}
