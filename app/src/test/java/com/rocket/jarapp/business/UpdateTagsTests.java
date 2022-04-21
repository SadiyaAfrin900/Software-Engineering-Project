package com.rocket.jarapp.business;

import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.persistence.TagPersistence;
import com.rocket.jarapp.persistence.stubs.TagPersistenceStub;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class UpdateTagsTests {

    private UpdateTags updateTags;
    private AccessTags accessTags;
    private int initialSize;

    @Before
    public void setUp() {
        TagPersistence tagPersistence = new TagPersistenceStub();
        updateTags = new UpdateTags(tagPersistence);
        accessTags = new AccessTags(tagPersistence);

        assertNotNull(updateTags);
        assertNotNull(accessTags);

        initialSize = accessTags.getAllTags().size();
    }

    @Test
    public void insertTagTest() {
        int nextId = updateTags.getNextId();
        Tag newTag = new Tag(nextId, "test-tag-1");

        assertTrue(updateTags.insert(newTag));
        assertTrue(accessTags.getAllTags().contains(newTag));
        assertNotNull(accessTags.getTagById(nextId));
    }

    @Test
    public void insertDuplicateTagTest() {
        int id = updateTags.getNextId();
        int sizeBefore = accessTags.getAllTags().size();
        Tag newTag = new Tag(id, "duplicate");

        assertTrue(updateTags.insert(newTag));
        assertFalse(updateTags.insert(newTag));
        assertEquals(sizeBefore + 1, accessTags.getAllTags().size());
    }

}
