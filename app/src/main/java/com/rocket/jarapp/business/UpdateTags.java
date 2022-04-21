package com.rocket.jarapp.business;

import com.rocket.jarapp.application.Services;
import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.persistence.TagPersistence;

public class UpdateTags {

    private TagPersistence tagPersistence;

    public UpdateTags(TagPersistence tagPersistence) {
        this.tagPersistence = tagPersistence;
    }

    public UpdateTags() {
        this.tagPersistence = Services.getTagPersistence();
    }

    public boolean insert(Tag tag) {
        return tagPersistence.insertTag(tag);
    }

    public int getNextId() {
        return tagPersistence.getNextId();
    }
}
