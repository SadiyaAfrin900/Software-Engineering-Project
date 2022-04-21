package com.rocket.jarapp.objects;

import com.rocket.jarapp.objects.exceptions.RocketInvalidDateException;
import com.rocket.jarapp.objects.exceptions.RocketInvalidDayException;

public class Date {
    public enum Month {
        JANUARY(1, 31), FEBRUARY(2, 28), MARCH(3, 31),
        APRIL(4, 30), MAY(5, 31), JUNE(6, 30),
        JULY(7, 31), AUGUST(8, 31), SEPTEMBER(9, 30),
        OCTOBER(10, 31), NOVEMBER(11, 30), DECEMBER(12, 31);

        private int monthNum;
        private int numDays;

        Month(int monthNum, int numDays) {
            this.monthNum = monthNum;
            this.numDays = numDays;
        }

        public int getMonthNum() {
            return monthNum;
        }

        public int getNumDays() {
            return numDays;
        }

        @Override
        public String toString() {
            return ""+monthNum;
        }
    }

    /**
     * dateFactory
     *
     * Return a Date object from the formatted string.
     *
     * dateStr should be in the format day/month/year
     */
    public static Date dateFactory(String dateStr) throws RocketInvalidDateException{
        if (dateStr == null || dateStr.isEmpty()) {
            return new Date();
        }

        String[] components = dateStr.split("/");
        Date date = new Date();

        try {
            if (components.length == 3) {
                int day = Integer.parseInt(components[0]);
                int monthNum = Integer.parseInt(components[1]);
                Month month = Month.values()[monthNum - 1];
                int year = Integer.parseInt(components[2]);
                date = new Date(day, month, year);
            }
        } catch (Exception e) {
            throw new RocketInvalidDateException(e);
        }

        return date;
    }

    private int day;
    private Month month;
    private int year;

    public Date(int day, Month month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date() {
        this(1, Month.JUNE, 1998);
    }

    public int getDay() {
        return day;
    }

    public Month getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int day) throws RocketInvalidDayException {
        if (day < 0 || day > month.getNumDays()) {
            throw new RocketInvalidDayException();
        }
        this.day = day;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public boolean setYear(int year) {
        this.year = year;
        return  true;
    }

    @Override
    public String toString() {
        return day+"/"+month+"/"+year;
    }
}
