/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageElement;

import java.util.ArrayList;

/**
 * Plans for today
 *
 * @author zimma
 */
public class Today implements IPageConstractor {

    /**
     * List of plans
     */
    public ArrayList<TodayPlanRecord> listRecords = new ArrayList<>();

    /**
     * Time
     */
    Time time;

    /**
     * Index
     */
    public static int emptyIndex = -1;

    /**
     * Constructor
     *
     * @param time Time
     */
    public Today(Time time) {
        this.time = time;
    }

    /**
     * Constructor
     */
    public Today() {

    }

    /**
     * Get Default Record list
     *
     * @return
     */
    public ArrayList<Today> getDefaultList() {
        ArrayList<Today> result = new ArrayList<>();
        Time t = new Time(0, 0);
        for (; t.lower(new Time(23, 30)); t.add(new Time(0, 30))) {
            result.add(new Today(new Time(t)));
        }
        result.add(new Today(new Time(t)));
        return result;
    }

    /**
     * to html object
     *
     * @return String
     */
    @Override
    public String toHtmlString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"record\" ")
                .append(this.listRecords.isEmpty() ? "style=\"height:10%\"" : "").append(">");
        sb.append("<div class=\"time-tp\">").append(time.toString()).append("</div>");
        if (listRecords.isEmpty()) {
            TodayPlanRecord tmp = new TodayPlanRecord();
            tmp.index = String.valueOf(emptyIndex--);
            tmp.value = "&nbsp;";
            listRecords.add(tmp);
        }
        for (int i = 0; i < listRecords.size() - 1; i++) {
            if (Integer.parseInt(listRecords.get(i).index) < 0) {
                sb.append(listRecords.get(i).toHtmlString());
            } else {
                listRecords.get(i).value += ",";
                sb.append(listRecords.get(i).toHtmlString());
            }
        }
        sb.append(listRecords.get(listRecords.size() - 1).toHtmlString());
        sb.append("</div>");
        return sb.toString();
    }
}
