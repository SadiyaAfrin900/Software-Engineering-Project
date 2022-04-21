package com.rocket.jarapp.objects;

import org.junit.Test;
import static org.junit.Assert.*;

public class TagTest {
    @Test
    public void testTag() {
        Tag tag;

        System.out.println("Beginning testTag");

        tag = new Tag(0, "emergency");
        assertNotNull(tag);
        assertTrue(tag.getId() == 0);
        assertTrue(tag.getName().equals("emergency"));

        System.out.println("Completed testTag");
    }
}
