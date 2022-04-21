package com.rocket.jarapp.business;

import java.util.List;

public class TagValidator {

    public static boolean isNotEmpty(String tag) {
        if (tag != null && !tag.equals("")) {
            return true;
        }

        return false;
    }

    public static boolean isNotDuplicate(String tag, List<String> tags) {
        return !tags.contains(tag);
    }
}
