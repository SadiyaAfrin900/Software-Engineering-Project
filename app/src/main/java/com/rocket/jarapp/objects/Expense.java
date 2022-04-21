package com.rocket.jarapp.objects;

import java.util.ArrayList;
import java.util.List;

public class Expense
{
    private int id;
    private String name;
    private String note;
    private double amount;
    private Date date;
    private Time time;
    private List<Tag> tags;

    public Expense(int id, String name, String note, double amount, Date date, Time time){
        this.id = id;
        this.name = name;
        this.note = note;
        this.amount = amount;
        this.date = date;
        this.time = time;
        tags = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getDateStr() {
        return date.toString();
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Time getTime() {
        return time;
    }

    public String getTimeStr() {
        return time.toString();
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag newTag) {
        if (!tags.contains(newTag)) {
            tags.add(newTag);
        }
    }

    public void removeTag(Tag tag) {
        if (tags.contains(tag)) {
            tags.remove(tag);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Expense && ((Expense)obj).id == id;
    }

    @Override
    public String toString() {
        return "{id: "+id+", amount:"+amount+", date:"+date+"}";
    }
}
