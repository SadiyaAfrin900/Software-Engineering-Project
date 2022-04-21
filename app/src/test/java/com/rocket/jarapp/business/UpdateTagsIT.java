package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class UpdateTagsIT {
    private File tempDB;
    private UpdateTags updateTags;
    private AccessTags accessTags;
    private int initialSize;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting tests for UpdateTags...");

        tempDB = TestUtils.copyDB();
        updateTags = new UpdateTags();
        accessTags = new AccessTags();
        assertNotNull(updateTags);
        assertNotNull(accessTags);

        initialSize = accessTags.getAllTags().size();
        assertEquals(7, initialSize);
    }

    @Test
    public void insertTagTest() {
        int nextId = updateTags.getNextId();
        Tag newTag = new Tag(nextId, "test-tag-1");

        assertTrue(updateTags.insert(newTag));
        assertEquals(8, accessTags.getAllTags().size());
    }

    @Test
    public void insertDuplicateTagTest() {
        int id = updateTags.getNextId();
        int sizeBefore = accessTags.getAllTags().size();
        Tag newTag = new Tag(id, "duplicate");

        assertTrue(updateTags.insert(newTag));
        assertEquals(8, accessTags.getAllTags().size());

        assertFalse(updateTags.insert(newTag));
        assertEquals(8, accessTags.getAllTags().size());
    }

    @After
    public void tearDown() {
        tempDB.delete();
        Services.clean();
        System.out.println("Finished UpdateTags tests");
    }
}
