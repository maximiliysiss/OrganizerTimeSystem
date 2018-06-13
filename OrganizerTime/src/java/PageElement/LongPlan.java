/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageElement;

/**
 * LongPlan
 *
 * @author zimma
 */
public class LongPlan implements IPageConstractor {

    /**
     * Value, index, catalog, sequency, condition
     */
    public final String value;
    public final int index;
    public final boolean catalog;
    public final int sequency;
    public final int transform;

    /**
     * Constructor
     *
     * @param value Value
     * @param index Index
     * @param catalog Catalog
     * @param sequency Sequency
     * @param transform Condition
     */
    public LongPlan(String value, int index, boolean catalog, int sequency, int transform) {
        this.value = value;
        this.index = index;
        this.catalog = catalog;
        this.sequency = sequency;
        this.transform = transform;
    }

    /**
     * Constructor
     */
    public LongPlan() {
        this.value = "valueTemp";
        this.index = 0;
        this.catalog = false;
        this.sequency = 0;
        this.transform = 1;
    }

    /**
     * To html string
     *
     * @param t
     * @return String
     */
    private String toHtmlString(boolean t) {
        return catalog ? toGoalHtmlString(t) : toTaskHtmlString(t);
    }

    /**
     * to html string
     *
     * @param i
     * @return String
     */
    private String toHtmlString(int i) {
        return catalog ? toGoalHtmlString(i) : toTaskHtmlString(i);
    }

    /**
     * to html string
     *
     * @return String
     */
    private String toGoalHtmlString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"idlp").append(index)
                .append("\" style=\"font-weight: bold;\" draggable=\"true\" ondragstart=\"drag(event)\" ondblclick=\"NextLevel(event)\" oncontextmenu=\"LongPlanWork(event); return false;\" class=\"record\"><div id=\"lpvalid").append(index)
                .append("\">").append(value).append("</div></div>");
        return sb.toString();
    }

    /**
     * to html String
     *
     * @param t
     * @return String
     */
    private String toTaskHtmlString(boolean t) {
        return "<div id=\"idlt" + index
                + "\" style=\"color:#bb747c\" oncontextmenu=\"MenuEndsLP(event,'task'); return false;\" class=\"record\">"
                + value + "</div>";
    }

    /**
     * to html String
     *
     * @param i
     * @return String
     */
    private String toGoalHtmlString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"idlp").append(index)
                .append("\" ondblclick=\"NextLevel(event)\" oncontextmenu=\"MenuEndsLP(event,'plan'); return false;\" class=\"record\" style=\"text-decoration:line-through;font-weight: bold;\"><div id=\"lpvalid").append(index)
                .append("\">").append(value).append("</div></div>");
        return sb.toString();
    }

    /**
     * to html String
     *
     * @param t
     * @return String
     */
    private String toGoalHtmlString(boolean t) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"idlp").append(index)
                .append("\" ondblclick=\"NextLevel(event)\" oncontextmenu=\"MenuEndsLP(event,'plan'); return false;\" class=\"record\" style=\"font-weight: bold;color:#bb747c;\"><div id=\"lpvalid").append(index)
                .append("\">").append(value).append("</div></div>");
        return sb.toString();
    }

    /**
     * To html String
     *
     * @return String
     */
    private String toTaskHtmlString() {
        return "<div draggable=\"true\" ondragstart=\"drag(event)\" id=\"idlt" + index
                + "\" oncontextmenu=\"LongTaskWork(event); return false;\" class=\"record\">"
                + value + "</div>";
    }

    /**
     * to html String
     *
     * @param t
     * @return String
     */
    private String toTaskHtmlString(int t) {
        return "<div id=\"idlt" + index
                + "\" oncontextmenu=\"MenuEndsLP(event,'task'); return false;\" style=\"text-decoration:line-through\" class=\"record\">"
                + value + "</div>";
    }

    /**
     * to html String
     *
     * @return String
     */
    @Override
    public String toHtmlString() {
        switch (transform) {
            case 1:
                return catalog ? toGoalHtmlString() : toTaskHtmlString();
            case 4:
                return toHtmlString(1);
            default:
                return toHtmlString(true);
        }
    }

}
