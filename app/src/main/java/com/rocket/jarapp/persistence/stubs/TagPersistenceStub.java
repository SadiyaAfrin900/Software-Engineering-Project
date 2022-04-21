package com.rocket.jarapp.persistence.stubs;

import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.persistence.TagPersistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TagPersistenceStub implements TagPersistence {
    private List<Tag> tags;

    public static final String TAG_0_NAME = "fun";
    public static final String TAG_1_NAME = "random";
    public static final String TAG_2_NAME = "party";

    public TagPersistenceStub() {
        tags = new ArrayList<>();
        tags.add(new Tag(0, TAG_0_NAME));
        tags.add(new Tag(1, TAG_1_NAME));
        tags.add(new Tag(2, TAG_2_NAME));
    }

    @Override
    public List<Tag> getAllTags() {
        return Collections.unmodifiableList(tags);
    }

    @Override
    public boolean insertTag(Tag tag) {
        if (tags.contains(tag))
            return false;

        tags.add(tag);
        return true;
    }

    @Override
    public int getNextId() {
        return tags.size();
    }
}
