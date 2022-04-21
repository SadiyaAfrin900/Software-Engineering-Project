package com.rocket.jarapp.objects;

public class Tag {
    private String name;
    private int id;

    public Tag(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Tag && ((Tag)(obj)).id == id;
    }

    @Override
    public String toString() {
        return "{name = "+name+", id = "+id+"}";
    }
}
