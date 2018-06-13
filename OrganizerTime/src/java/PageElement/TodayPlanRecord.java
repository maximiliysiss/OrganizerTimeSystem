/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageElement;

/**
 * Plan for today
 *
 * @author zimma
 */
public class TodayPlanRecord implements IPageConstractor {

    /**
     * Copy Constructor
     *
     * @param todayplan Plan
     */
    public TodayPlanRecord(TodayPlanRecord todayplan) {
        this.index = todayplan.index;
        this.time = todayplan.time;
        this.value = todayplan.value;
        this.timeset = todayplan.timeset;
        this.trueToday = todayplan.trueToday;
        this.transform = todayplan.transform;
    }

    /**
     * Constructor by ShortPlan
     *
     * @param shorts ShortPlan
     * @param time Time
     */
    public TodayPlanRecord(ShortPlans shorts, String time) {
        this.index = String.valueOf(shorts.index);
        this.time = time;
        this.value = shorts.value;
        this.transform = 3;
    }

    /**
     * Value, time, index, duration, condition
     */
    public String value;
    public String time;
    public String index;
    public String timeset;
    public int transform;

    /**
     * Constructor
     *
     * @param value Value
     * @param time Time
     * @param index Index
     * @param transform Condition
     */
    public TodayPlanRecord(String value, String time, String index, int transform) {
        this.transform = transform;
        this.value = value;
        this.time = time;
        this.index = index;
    }

    /**
     * Set Duration
     *
     * @param timeset duration
     */
    public void setTimeset(String timeset) {
        this.timeset = timeset;
    }

    /**
     * is this calendar record?
     */
    private boolean trueToday = false;

    /**
     * is calendar record?
     *
     * @return bool
     */
    public boolean isTrueToday() {
        return trueToday;
    }

    /**
     * Set calendar actual
     *
     * @param trueToday is calendar record?
     */
    public void setTrueToday(boolean trueToday) {
        this.trueToday = trueToday;
    }

    /**
     * Constructor
     */
    public TodayPlanRecord() {
        this.value = "valueTemp";
        this.time = "00:00";
        index = "0";
        transform = 3;
    }

    /**
     * to html object
     *
     * @return String
     */
    private String toCalendareRecord() {
        return "<div class=\"value-tp\" ondragstart=\"drag(event)\" style=\"color:green\" id=\"today" + index + "\">"
                + value + "</div>";
    }

    /**
     * to html string
     *
     * @return String
     */
    @Override
    public String toHtmlString() {
        if (isTrueToday()) {
            return toCalendareRecord();
        }
        if (transform == 3) {
            return "<div draggable=\"true\" ondragstart=\"drag(event)\" class=\"value-tp\" onclick=\"changeTodayPlan(event)\" oncontextmenu=\"workWithToday(event);return false;\" id=\"today" + index + "\">"
                    + value + "</div>";
        }
        if (Integer.parseInt(index) < 0) {
            return toHtmlString(1);
        }
        return toHtmlString(true);
    }

    /**
     * to html string
     *
     * @param t
     * @return String
     */
    private String toHtmlString(int t) {
        return "<div onclick=\"changeTodayPlan(event)\" class=\"empty-tp\" id=\"today" + index + "\">"
                + value + "</div>";
    }

    /**
     * to html string
     *
     * @param t
     * @return String
     */
    private String toHtmlString(boolean t) {
        return "<div style=\"color:#bb747c;\" oncontextmenu=\"MenuEndsToday(event); return false;\" class=\"value-tp\" id=\"today" + index + "\">"
                + value + "</div>";
    }
}
