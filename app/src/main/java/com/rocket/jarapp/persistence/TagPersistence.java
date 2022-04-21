package com.rocket.jarapp.persistence;

import com.rocket.jarapp.objects.Tag;

import java.util.List;

public interface TagPersistence {

    /**
     * getTags
     *
     * Return all tags throughout the entire application
     */
    List<Tag> getAllTags();

    /**
     * insertTag
     *
     * Save a new tag
     */
    boolean insertTag(Tag tag);

    /**
     * getNextId
     *
     * Returns the next unique id that can be used
     */
    int getNextId();
}
