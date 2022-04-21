package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.persistence.TagPersistence;

import java.util.ArrayList;
import java.util.List;

public class AccessTags {
    private TagPersistence tagsPersistence;

    public AccessTags() {
        tagsPersistence = Services.getTagPersistence();
    }

    public AccessTags(TagPersistence persistence) {
        tagsPersistence = persistence;
    }

    /**
     * getTags
     *
     * Returns all tags found in the system
     */
    public List<Tag> getAllTags() {
        return tagsPersistence.getAllTags();
    }

    /**
     * getAllTagNames
     *
     * Returns a list of all the tag's names
     */
    public List<String> getAllTagNames() {
        List<String> names = new ArrayList<>();

        for (Tag tag : getAllTags()) {
            names.add(tag.getName());
        }

        return names;
    }

    /**
     * getTagByName
     *
     * Return the tag with a matching name, NULL otherwise
     */
    public Tag getTagByName(String name) {
        for (Tag tag : tagsPersistence.getAllTags()) {
            if (tag.getName().equals(name)) {
                return tag;
            }
        }
        return null;
    }

    /**
     * getTagByName
     *
     * Return the tag with a matching id, NULL otherwise
     */
    public Tag getTagById(int id) {
        for (Tag tag : tagsPersistence.getAllTags()) {
            if (tag.getId() == id) {
                return tag;
            }
        }
        return null;
    }
}
