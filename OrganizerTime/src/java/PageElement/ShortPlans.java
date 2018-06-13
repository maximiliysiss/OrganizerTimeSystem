/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageElement;

/**
 * Shortplan
 *
 * @author zimma
 */
public class ShortPlans implements IPageConstractor {

    /**
     * Value, type, date, time, index, sequency, condition
     */
    public final String value;
    public final String type;
    public final String date;
    public final String time;
    public final int index;
    public final int sequency;
    public final int transform;

    /**
     * Construct
     *
     * @param value Value
     * @param type Type
     * @param date Date
     * @param time Time
     * @param index Index
     * @param sequency Sequency
     * @param transform Condition
     */
    public ShortPlans(String value, String type, String date, String time, int index, int sequency, int transform) {
        this.value = value;
        this.type = type;
        this.date = date;
        this.time = time;
        this.index = index;
        this.sequency = sequency;
        this.transform = transform;
    }

    /**
     * Constructor
     *
     * @param value Value
     * @param type Type
     * @param index Index
     */
    public ShortPlans(String value, String type, int index) {
        this.value = value;
        this.type = type;
        this.date = "0";
        this.time = "0";
        this.index = index;
        this.sequency = 0;
        this.transform = 2;
    }

    /**
     * Constructor
     */
    public ShortPlans() {
        this.date = this.time = "0";
        this.type = "typeTemp";
        this.value = "valueTemp";
        index = sequency = 0;
        this.transform = 2;
    }

    /**
     * selected
     *
     * @return String
     */
    private String selected() {
        StringBuilder sb = new StringBuilder();
        sb.append("<option ").append("A".equals(type) ? "selected=\"selected\"" : "")
                .append(" value=\"A\">A</option><option ")
                .append("B".equals(type) ? "selected=\"selected\"" : "")
                .append(" value=\"B\">B</option><option ")
                .append("C".equals(type) ? "selected=\"selected\"" : "")
                .append(" value=\"C\">C</option><option ")
                .append("D".equals(type) ? "selected=\"selected\"" : "")
                .append(" value=\"D\">D</option>")
                .append("<option ")
                .append("E".equals(type) ? "selected=\"selected\"" : "")
                .append(" value=\"E\"> </option>");
        return sb.toString();
    }

    /**
     * selected
     *
     * @param t
     * @return String
     */
    private String selected(int t) {
        StringBuilder sb = new StringBuilder();
        sb.append("<option selected=\"selected\"")
                .append(" value=\"").append(type).append("\">").append(type.equals("E") ? " " : type)
                .append("</option> ");
        return sb.toString();
    }

    @Override
    public String toHtmlString() {
        switch (transform) {
            case 2:
                return toHtmlStringA();
            case 4:
                return toHtmlString(1);
            default:
                return toHtmlString(true);
        }
    }

    /**
     * to Html String
     *
     * @return String
     */
    private String toHtmlStringA() {
        String result = "<div class=\"record\" draggable=\"true\" ondragstart=\"drag(event)\" id=\"idsrg" + index + "\" oncontextmenu=\"ShPlanWorks(event);return false;\">"
                + "<div id=\"idvalsp" + index + "\" class=\"record-kr-plans\">" + value + "</div>"
                + "<select " + "style=\"border-radius:5px;border: 1px solid;height:6%;" + (type.equals("E") ? "border-color:#a8aaad" : "") + "\" onchange=\"ChangeTypeSh(event);\" id=\"typeshid" + index + "\" class=\"type-kr-plans\">" + selected() + "</select>"
                + (!time.equals("0") ? "<img onclick=\"TimeRunSet(event)\" id=\"clockimg" + index + "\" "
                + "class=\"img-kr-plans\" src=\"img/AddingStyleElement/Clock_Icon - Black.png\">"
                : "<img onclick=\"TimeRunSet(event)\" id=\"clockimg" + index + "\" class=\"img-kr-plans\" src=\"img/AddingStyleElement/Clock_Icon.png\">")
                + (!date.equals("0") ? "<img onclick=\"EventSys(event)\" id=\"ringimg" + index + "\" class=\"img-kr-plans\" src=\"img/AddingStyleElement/Ring_Icon - Black.png\">"
                : "<img onclick=\"EventSys(event)\" id=\"ringimg" + index + "\" class=\"img-kr-plans\" src=\"img/AddingStyleElement/Ring_Icon.png\">") + "</div>";
        return result;
    }

    /**
     * to html string
     *
     * @param t
     * @return String
     */
    private String toHtmlString(int t) {
        String result = "<div class=\"record\" id=\"idsrg" + index + "\" oncontextmenu=\"MenuEndsShP(event);return false;\">"
                + "<div style=\"text-decoration:line-through\" id=\"idvalsp" + index + "\" class=\"record-kr-plans\">" + value + "</div>"
                + "<select disabled " + "style=\"border-radius:5px;border: 1px solid;height:6%;" + (type.equals("E") ? "border-color:#a8aaad" : "") + "\" onchange=\"ChangeTypeSh(event);\" id=\"typeshid" + index + "\" class=\"type-kr-plans\">" + selected(1) + "</select>"
                + (!time.equals("0") ? "<img id=\"clockimg" + index + "\" "
                + "class=\"img-kr-plans\" src=\"img/AddingStyleElement/Clock_Icon - Black.png\">"
                : "<img id=\"clockimg" + index + "\" class=\"img-kr-plans\" src=\"img/AddingStyleElement/Clock_Icon.png\">")
                + (!date.equals("0") ? "<img id=\"ringimg" + index + "\" class=\"img-kr-plans\" src=\"img/AddingStyleElement/Ring_Icon - Black.png\">"
                : "<img id=\"ringimg" + index + "\" class=\"img-kr-plans\" src=\"img/AddingStyleElement/Ring_Icon.png\">") + "</div>";
        return result;
    }

    /**
     * to html String
     *
     * @param t
     * @return String
     */
    private String toHtmlString(boolean t) {
        String result = "<div class=\"record\" id=\"idsrg" + index + "\" oncontextmenu=\"MenuEndsShP(event);return false;\" style=\"color:#bb747c;\">"
                + "<div id=\"idvalsp" + index + "\" class=\"record-kr-plans\">" + value + "</div>"
                + "<select disabled " + "style=\"border-radius:5px;border: 1px solid;height:6%;" + (type.equals("E") ? "border-color:#a8aaad" : "") + "\" onchange=\"ChangeTypeSh(event);\" id=\"typeshid" + index + "\" class=\"type-kr-plans\">" + selected(1) + "</select>"
                + (!time.equals("0") ? "<img id=\"clockimg" + index + "\" "
                + "class=\"img-kr-plans\" src=\"img/AddingStyleElement/Clock_Icon - Black.png\">"
                : "<img id=\"clockimg" + index + "\" class=\"img-kr-plans\" src=\"img/AddingStyleElement/Clock_Icon.png\">")
                + (!date.equals("0") ? "<img id=\"ringimg" + index + "\" class=\"img-kr-plans\" src=\"img/AddingStyleElement/Ring_Icon - Black.png\">"
                : "<img id=\"ringimg" + index + "\" class=\"img-kr-plans\" src=\"img/AddingStyleElement/Ring_Icon.png\">") + "</div>";
        return result;
    }
}
