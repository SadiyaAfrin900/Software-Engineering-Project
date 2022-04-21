package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.persistence.TagPersistence;
import com.rocket.jarapp.persistence.stubs.TagPersistenceStub;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TagValidatorTests {

    private TagPersistence tagPersistence;
    private List<Tag> tags;
    private List<String> tagNames;

    @Before
    public void setUp() {
        tagPersistence = new TagPersistenceStub();
        tags = tagPersistence.getAllTags();

        tagNames = new ArrayList<>();
        for (Tag tag : tags) {
            tagNames.add(tag.getName());
        }
    }

    @Test
    public void testValidTag() {
        String newTag = "valid";
        assertTrue(TagValidator.isNotEmpty(newTag));
        assertTrue(TagValidator.isNotDuplicate(newTag, tagNames));
    }

    @Test
    public void testInvalidTagNull() {
        //Null tag
        String newTag = null;
        assertFalse(TagValidator.isNotEmpty(newTag));
        assertTrue(TagValidator.isNotDuplicate(newTag, tagNames));
    }

    @Test
    public void testInvalidTagEmpty() {
        //Empty tag
        String newTag = "";
        assertFalse(TagValidator.isNotEmpty(newTag));
        assertTrue(TagValidator.isNotDuplicate(newTag, tagNames));
    }

    @Test
    public void testInvalidTagDuplicate() {
        //Duplicate tag
        String newTag = TagPersistenceStub.TAG_0_NAME;
        assertTrue(TagValidator.isNotEmpty(newTag));
        assertFalse(TagValidator.isNotDuplicate(newTag, tagNames));
    }
}