/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageElement;

/**
 * Date
 * @author zimma
 */
public class Date implements Comparable<Date> {

    /**
     * Day, Month, Year
     */
    private final int day, month, year;

    /**
     * Constructor by string
     * @param date Date
     */
    public Date(String date) {
        if (!"0".equals(date)) {
            String[] info = date.split("-");
            year = Integer.parseInt(info[0]);
            month = Integer.parseInt(info[1]);
            day = Integer.parseInt(info[2]);
        } else {
            year = month = day = Integer.MAX_VALUE;
        }
    }

    /**
     * Constructor
     * @param day Day
     * @param month Month
     * @param year Year
     */
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Comparing
     * @param t Other date
     * @return int
     */
    @Override
    public int compareTo(Date t) {
        if (year != t.year) {
            return Integer.compare(year, t.year);
        }
        if (month != t.month) {
            return Integer.compare(month, t.month);
        }
        return Integer.compare(day, t.day);
    }
}
