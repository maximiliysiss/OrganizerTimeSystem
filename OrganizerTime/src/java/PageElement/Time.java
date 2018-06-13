/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageElement;

/**
 * Time
 * @author zimma
 */
public class Time implements Comparable<Time> {

    /**
     * Hour, Minute
     */
    private int hour = 0;
    private int minutes = 0;

    /**
     * Get Hour
     * @return int
     */
    public int getHour() {
        return hour;
    }

    /**
     * Get Minute
     * @return int
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Constructor
     * @param time Format String
     */
    public Time(String time) {
        if (!"0".equals(time)) {
            this.hour = Integer.parseInt(time.split(":")[0]);
            this.minutes = Integer.parseInt(time.split(":")[1]);
        } else {
            this.hour = 0;
            this.minutes = 0;
        }
    }

    /**
     * Copy Constructor
     * @param t Time
     */
    public Time(Time t) {
        this.hour = t.hour;
        this.minutes = t.minutes;
    }

    /**
     * To Index for array
     * @return Index
     */
    public int toIndex() {
        return hour * 2 + (minutes == 30 ? 1 : 0);
    }

    /**
     * Round time
     */
    public void round() {
        if (minutes != 30 && minutes != 0) {
            hour += minutes > 30 ? minutes > 45 ? 1 : 0 : 0;
            minutes = minutes < 30 ? minutes < 15 ? 0 : 30 : minutes < 45 ? 30 : 0;
        }
    }

    /**
     * Round for calendar
     */
    public void roundCalendar() {
        minutes = minutes < 30 && hour == 0 ? 30 : minutes;
        this.round();
    }

    /**
     * Constructor
     * @param hour Time
     * @param minutes Minutes
     */
    public Time(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
        if (this.minutes >= 60) {
            this.hour += this.minutes / 60;
            this.minutes = this.minutes % 60;
        }
    }

    /**
     * Add
     * @param t Time
     */
    public void add(Time t) {
        this.hour += t.hour;
        this.minutes += t.minutes;
        if (this.minutes >= 60) {
            this.hour += this.minutes / 60;
            this.minutes = this.minutes % 60;
        }
        if (this.hour >= 24) {
            hour -= 24;
        }
    }

    /**
     * Constructor
     * @param index Index
     */
    public Time(int index) {
        this.hour = index / 2;
        this.minutes = index % 2 == 0 ? 0 : 30;
    }

    /**
     * Substruct
     * @param t Time
     */
    public void substuct(Time t) {
        int dur = hour * 60 + minutes;
        dur -= (t.hour * 60 + t.minutes);
        if (dur < 0) {
            dur = dur + 24 * 60;
        }
        hour = dur / 60;
        minutes = dur % 60;
    }

    /**
     * To String
     * @return String
     */
    @Override
    public String toString() {
        return (hour >= 10 ? hour : "0" + hour) + ":" + (minutes >= 10 ? minutes : "0" + minutes);
    }

    /**
     * Compare lower
     * @param t Time
     * @return bool
     */
    public boolean lower(Time t) {
        if (t.hour != hour) {
            return hour < t.hour;
        }
        return this.minutes < t.minutes;
    }

    /**
     * Compare
     * @param t Time
     * @return int
     */
    @Override
    public int compareTo(Time t) {
        if (this.hour != t.hour) {
            return Integer.compare(hour, t.hour);
        }
        return Integer.compare(minutes, t.minutes);
    }
}
