package com.rocket.jarapp.objects;

public class Time {
    public static final int HOURS_IN_DAY = 24;
    public static final int MINUTES_IN_HOUR = 60;

    private int minute;
    private int hour;

    public static Time timeFactory(String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) {
            return new Time();
        }

        String[] components = timeStr.split(":");
        Time time = new Time();

        if (components.length == 2) {
            int hour = Integer.parseInt(components[0]);
            int minute = Integer.parseInt(components[1]);
            time = new Time(hour, minute);
        }

        return time;
    }

    public Time() {
        this(0, 0);
    }

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean setHour(int hour) {
        if (hour >= 0 && hour <= HOURS_IN_DAY) {
            this.hour = hour;
            return  true;
        }
        return false;
    }

    public boolean setMinute(int minute) {
        if (minute >= 0 && minute < MINUTES_IN_HOUR) {
            this.minute = minute;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String minuteStr = (minute < 10) ? "0"+minute : ""+minute;
        return hour+":"+minuteStr;
    }
}
