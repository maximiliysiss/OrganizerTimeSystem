/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageServlets;

import DbConnection.AjaxGetMap;
import DbConnection.DBUser;
import PageElement.ShortPlans;
import PageElement.Time;
import PageElement.TodayPlanRecord;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import PageElement.Date;
import PageElement.Today;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Servlet for TodayPlans
 *
 * @author zimma
 */
@WebServlet(name = "TodayPlanWork", urlPatterns = {"/TodayPlanWork"})
public class TodayPlanWork extends HttpServlet {

    @Resource(name = "timeorganizerReferences")
    private DataSource dbtimeorganizer;

    /**
     * Post
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String result = "";
        try {
            HashMap<String, String> input = new AjaxGetMap().getAjaxInfo(request);
            switch (input.get("type")) {
                case "getTodayPlans":
                    result = getTodayPlans(input);
                    break;
                case "generateTodayPlans":
                    result = generateTodayPlans(input);
                    break;
            }
        } catch (NamingException | SQLException ex) {
            throw new ServletException("TodayPlanWork " + ex.getMessage());
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }

    /**
     * Get calendar tasks from format string
     *
     * @param str Format String
     * @return ArrayList<TodayPlanRecord>
     */
    public ArrayList<TodayPlanRecord> getCalendarArray(String str) {
        ArrayList<TodayPlanRecord> result = new ArrayList<>();
        String[] events = str.split("/");
        for (String event : events) {
            if (event.split("T").length < 3) {
                continue;
            }
            String value = event.substring(0, event.indexOf("(")).trim();
            event = event.substring(event.indexOf("("));
            String time = event.split("T")[1].substring(0, 5);
            String end = event.split("T")[2].substring(0, 5);
            Time start = new Time(time);
            start.roundCalendar();
            TodayPlanRecord eventPlan = new TodayPlanRecord(value, start.toString(), String.valueOf(0), 3);
            eventPlan.setTrueToday(true);
            Time t = new Time(end);
            t.substuct(new Time(time));
            t.roundCalendar();
            eventPlan.setTimeset(t.toString());
            result.add(eventPlan);
        }
        return result;
    }

    /**
     * Add plans to default plans
     *
     * @param plans Plans
     * @param tplans Real Plans
     * @return ArrayList<Today>
     */
    public ArrayList<Today> addTodayPlanToDefault(ArrayList<Today> plans, ArrayList<TodayPlanRecord> tplans) {
        for (TodayPlanRecord obj : tplans) {
            int startIndex = new Time(obj.time).toIndex();
            Time t = new Time(obj.timeset);
            t.add(new Time(obj.time));
            int endIndex = t.toIndex();
            if (endIndex < startIndex) {
                endIndex = 48;
            }
            for (int i = startIndex; i < endIndex; i++) {
                plans.get(i).listRecords.add(new TodayPlanRecord(obj));
            }
        }
        return plans;
    }

    /**
     * Get TodayPlans
     *
     * @param user User
     * @return ArrayList
     * @throws NamingException
     * @throws SQLException
     */
    public ArrayList<TodayPlanRecord> getTodayPlansArray(int user) throws NamingException, SQLException {
        ArrayList<TodayPlanRecord> result = new ArrayList<>();
        try (Connection conn = dbtimeorganizer.getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from todaytable where user_id=?");) {
            stm.setInt(1, user);
            try (ResultSet res = stm.executeQuery()) {
                while (res.next()) {
                    TodayPlanRecord tmp = new TodayPlanRecord(res.getString("value"), res.getString("time"), String.valueOf(res.getInt("id")), res.getInt("transform"));
                    tmp.setTimeset(res.getString("duration"));
                    result.add(tmp);
                }
            }
            return result;
        }
    }

    /**
     * Add calendar tasks
     *
     * @param plans Plans
     * @param calendar Calendar Tasks
     * @return
     */
    public ArrayList<Today> addGoogleEvents(ArrayList<Today> plans, ArrayList<TodayPlanRecord> calendar) {
        if (calendar.isEmpty()) {
            return plans;
        }
        for (TodayPlanRecord event : calendar) {
            Time b = new Time(event.time), e = new Time(event.timeset);
            e.add(b);
            int indexStart = b.getHour() * 2 + (b.getMinutes() == 30 ? 1 : 0);
            int indexEnd = e.getHour() * 2 + (b.getMinutes() == 30 ? 1 : 0);
            for (int i = indexStart; i < indexEnd; i++) {
                plans.get(i).listRecords.add(new TodayPlanRecord(event));
            }
        }
        return plans;
    }

    /**
     * Get ShortPlan List
     *
     * @param user User
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public ArrayList<ShortPlans> getShortPlans(int user) throws NamingException, SQLException {
        ArrayList<ShortPlans> result = new ArrayList<>();
        try (Connection conn = dbtimeorganizer.getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from shortgoalstable where user_id=?");) {
            stm.setInt(1, user);
            try (ResultSet res = stm.executeQuery()) {
                while (res.next()) {
                    if (res.getInt("transform") != 2) {
                        continue;
                    }
                    ShortPlans shortplan = new ShortPlans(res.getString("value"), res.getString("type"),
                            res.getString("daterun"), res.getString("timerun"), res.getInt("id"),
                            res.getInt("sequency"), res.getInt("transform"));
                    result.add(shortplan);
                }
            }
            return result;
        }
    }

    /**
     * Is calendar task?
     *
     * @param index Index
     * @param calendar Calendar
     * @return bool
     */
    boolean isTrueToday(int index, ArrayList<TodayPlanRecord> calendar) {
        for (TodayPlanRecord obj : calendar) {
            int startIndex = new Time(obj.time).toIndex();
            int endIndex = new Time(obj.timeset).toIndex();
            if (index >= startIndex && index < endIndex) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add shortplans
     *
     * @param plans Plans
     * @param shortplans ShortPlans
     * @param userID User
     * @return ArrayList
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<TodayPlanRecord> addShortPlans(ArrayList<TodayPlanRecord> plans, ArrayList<ShortPlans> shortplans, int userID) throws SQLException, NamingException {
        Time t = new Time(0, 0);
        try (Connection conn = dbtimeorganizer.getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from users where id=?");) {
            stm.setInt(1, userID);
            try (ResultSet res = stm.executeQuery()) {
                if (res.next()) {
                    t = new Time(res.getString("morningstart"));
                }
            }
            int currentIndex = t.toIndex();
            for (ShortPlans plan : shortplans) {
                Time timeEvent = new Time(plan.time);
                int duration = timeEvent.toIndex();
                TodayPlanRecord tmp = new TodayPlanRecord(plan, new Time(currentIndex).toString());
                tmp.timeset = duration == 0 ? "00:30" : new Time(duration).toString();
                plans.add(tmp);
                currentIndex += duration == 0 ? 1 : duration;
            }
            return plans;
        }
    }

    /**
     * *
     * GetTodayPlans
     *
     * @param input Input Data
     * @return String
     * @throws NamingException
     * @throws SQLException
     */
    public String getTodayPlans(HashMap<String, String> input) throws NamingException, SQLException {
        int userID = new DBUser().getUserID(input.get("user"));
        ArrayList<TodayPlanRecord> tplans = getTodayPlansArray(userID);
        ArrayList<TodayPlanRecord> calendar = getCalendarArray(input.get("googlecalapi"));
        ArrayList<Today> plans = new Today().getDefaultList();
        plans = addTodayPlanToDefault(plans, tplans);
        plans = addGoogleEvents(plans, calendar);
        StringBuilder sb = new StringBuilder();
        Today.emptyIndex = -1;
        plans.forEach(obj -> {
            sb.append(obj.toHtmlString());
        });
        return sb.toString();
    }

    /**
     * Generate Plans
     *
     * @param input Input data
     * @return String
     * @throws NamingException
     * @throws SQLException
     */
    public String generateTodayPlans(HashMap<String, String> input) throws NamingException, SQLException {
        int userID = new DBUser().getUserID(input.get("user"));
        try (Connection conn = dbtimeorganizer.getConnection();
                PreparedStatement stm = conn.prepareStatement("delete from todaytable where user_id=?");) {
            stm.setInt(1, userID);
            stm.execute();
            ArrayList<TodayPlanRecord> calendar = getCalendarArray(input.get("googlecalapi"));
            ArrayList<TodayPlanRecord> plans = new ArrayList<>();
            ArrayList<ShortPlans> shortPlans = getShortPlans(userID);
            shortPlans.sort(new ShortPlaneComparator());
            plans = addShortPlans(plans, shortPlans, userID);
            for (int i = 0; i < plans.size(); i++) {
                Time dur = new Time(plans.get(i).timeset);
                new DbConnection.DBToday().addRecord(userID, plans.get(i).value, plans.get(i).time, dur.toString());
            }
            return getTodayPlans(input);
        }
    }

    /**
     * ShortPlan Comparator
     */
    class ShortPlaneComparator implements Comparator<ShortPlans> {

        @Override
        public int compare(ShortPlans t, ShortPlans t1) {
            if (!t.type.equals(t1.type)) {
                if (t.type.equals("E")) {
                    return 1;
                }
                if (t1.type.equals("E")) {
                    return -1;
                }
                return t.type.compareTo(t1.type);
            }
            if (!t.time.equals(t1.time)) {
                return new Time(t.time).compareTo(new Time(t1.time));
            }
            return new Date(t.date).compareTo(new Date(t1.date));
        }
    }

    /**
     * TodayPlan Comparator
     */
    class TodayPlanComparator implements Comparator<TodayPlanRecord> {

        @Override
        public int compare(TodayPlanRecord t, TodayPlanRecord t1) {
            return new Time(t.time).compareTo(new Time(t1.time));
        }

    }
}
