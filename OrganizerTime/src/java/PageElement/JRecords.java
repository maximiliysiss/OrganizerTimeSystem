/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageElement;

import java.util.ArrayList;

/**
 * JRecord
 * @author zimma
 */
public class JRecords implements IPageConstractor {
    /**
     * Time, Value, Index
     */
    private final String time;
    private final String value;
    private final String index;
    
    /**
     * Steps
     */
    ArrayList<String> steps = null;
    
    /**
     * Constructor
     * @param time Time
     * @param value Value
     * @param index Index
     * @param parent Catalog
     */
    public JRecords(String time, String value, String index, boolean parent) {
        this.time = time;
        this.value = value;
        this.index = index;
        if (parent) {
            steps = new ArrayList<>();
        }
    }
    
    /**
     * Get Index
     * @return Index
     */
    public String getIndex() {
        return index;
    }
    
    /**
     * Ad step
     * @param step Step
     */
    public void addStep(String step) {
        if (steps != null) {
            steps.add(step);
        }
    }
    
    /**
     * Constructor
     */
    public JRecords() {
        time = "00:00";
        value = "nameTemp";
        index = "0";
    }
    
    /**
     * Get Page object
     * @return String
     */
    @Override
    public String toHtmlString() {
        return Contractor(0);
    }
    
    /**
     * Is show list
     */
    private boolean show = false;
    
    /**
     * is Show List of steps?
     * @return bool
     */
    public boolean isShow() {
        return show;
    }
    
    /**
     * Set Showing list
     */
    public void ShowList() {
        show = true;
    }
    
    /**
     * Constructor element
     * @param type
     * @return String
     */
    private String Contractor(int type) {
        StringBuilder res = new StringBuilder();
        res.append("<div onclick=\"OpenHideJRec(event)\" class=\"record-second\" oncontextmenu=\"JTaskWork(event); return false;\" id=\"jrecid")
                .append(index)
                .append("\"><div id=\"jrecvalid").append(index)
                .append("\" class=\"second-inrecord\">► ").append(value)
                .append("</div><div id=\"jrectimeid").append(index)
                .append("\" class=\"time-second\">").append(time)
                .append("</div></div><div ").append(type == 1 ? " style=\"display:block\" " : "")
                .append(" id=\"jrecidl").append(index)
                .append("\" class = \"journal-list\">");
        if (steps != null) {
            steps.forEach((obj) -> {
                res.append(obj);
            });
        }
        res.append("</div>");
        return res.toString();
    }
    
    /**
     * Constructor HTML Object
     * @param i
     * @return String
     */
    public String toHtmlString(int i) {
        return Contractor(1);
    }
    
    /**
     * Create Box for JRecord
     * @return String
     */
    public String creationJRecordBox() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div onkeydown=\"AddJRecord(event)\" style=\"padding-left:5%; display:none\" class=\"record-second\" id=\"jreccreate")
                .append("\"><input id=\"jcreaterec\" style=\"width:90%\"")
                .append("\">").append("</div><div id=\"jcreateempty\"></div>");
        return sb.toString();
    }
    
    /**
     * Create step
     * @param stepIndex Index
     * @param stepValue Value
     * @param stepTime Time
     * @return String
     */
    public String createStep(int stepIndex, String stepValue, String stepTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div style=\"margin-left: 5%;\" id=\"jstepid")
                .append(stepIndex)
                .append("\" onclick=\"StartTimer(event)\" oncontextmenu=\"JStepWork(event); return false;\" class=\"step-style\"><div class=\"val-step\" id=\"jstepvalid")
                .append(stepIndex)
                .append("\">")
                .append(stepValue).append("</div><div class=\"time-step\" id=\"jsteptimeid")
                .append(stepIndex).append("\">")
                .append(new Time(stepTime).toString()).append("</div></div>");
        return sb.toString();
    }
    
}
