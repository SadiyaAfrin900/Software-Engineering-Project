package com.rocket.jarapp.business;

import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.persistence.TagPersistence;
import com.rocket.jarapp.persistence.stubs.TagPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccessTagsTests {
    private TagPersistence tagPersistence;
    private AccessTags accessTags;

    @Before
    public void setUp() {
        System.out.println("Starting tests for AccessTags...");
        tagPersistence = new TagPersistenceStub();
        accessTags = new AccessTags(tagPersistence);
    }

    @Test
    public void testGetAllTags() {
        assertEquals(3, accessTags.getAllTags().size());
    }

    @Test
    public void testGetTagById() {
        Tag tag = accessTags.getTagById(0);
        assertEquals(tag.getName(), TagPersistenceStub.TAG_0_NAME);
        assertEquals(tag.getId(), 0);
    }

    @Test
    public void testGetTagByName() {
        Tag tag = accessTags.getTagByName(TagPersistenceStub.TAG_1_NAME);
        assertEquals(tag.getName(), TagPersistenceStub.TAG_1_NAME);
        assertEquals(tag.getId(), 1);
    }

    @After
    public void tearDown() {
        System.out.println("Finished AccessTags tests");
    }
}
