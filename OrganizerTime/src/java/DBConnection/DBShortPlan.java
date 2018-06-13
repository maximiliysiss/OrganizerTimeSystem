/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnection;

import PageElement.ShortPlans;
import PageElement.Time;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * DAO Short-temp plan
 *
 * @author zimma
 */
public class DBShortPlan {

    /**
     * Add record
     *
     * @param value Value
     * @param user User
     * @param type Type
     * @throws NamingException
     * @throws SQLException
     */
    public void addShortPlan(String value, int user, String type) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select sequency from shortgoalstable where user_id=? order by sequency desc");) {
            stm.setInt(1, user);
            try (ResultSet res = stm.executeQuery()) {
                int seq = 0;
                if (res.next()) {
                    seq = res.getInt("sequency") + 1;
                }
                try (PreparedStatement stm1 = conn.prepareStatement("insert into shortgoalstable (VALUE,TYPE,TRANSFORM,USER_ID,SEQUENCY) values(?,?,2,?,?)");) {
                    stm1.setString(1, value);
                    stm1.setString(2, type);
                    stm1.setInt(3, user);
                    stm1.setInt(4, seq);
                    stm1.execute();
                }
            }
        }
    }

    /**
     * Delete all records
     *
     * @param user User
     * @throws NamingException
     * @throws SQLException
     */
    public void deleteAllShortPlans(int user) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("delete from shortgoalstable where user_id=?");) {
            stm.setInt(1, user);
            stm.execute();
        }
    }

    /**
     * Page Constructor
     *
     * @param user User
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public String ShortPlanPageConstractor(String user) throws NamingException, SQLException {
        ArrayList<String> list = new ArrayList<>();
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from shortgoalstable where USER_ID=?");) {
            stm.setInt(1, Integer.parseInt(user));
            try (ResultSet resS = stm.executeQuery()) {
                while (resS.next()) {
                    int id = resS.getInt("iD");
                    String value = resS.getString("VALUE");
                    String type = resS.getString("TYPE");
                    list.add(new ShortPlans(value, type, id).toHtmlString());
                }
                resS.close();
            }
        }
        StringBuilder sb = new StringBuilder();
        list.forEach(obj -> sb.append(obj));
        return sb.toString();
    }

    /**
     * Transform to TodayPlan
     *
     * @param id ID
     * @param timeStart Start time
     * @param user User
     * @throws NamingException
     * @throws SQLException
     */
    public void transformToToday(int id, String timeStart, int user) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from shortgoalstable where id=? and user_id=?");) {
            stm.setInt(1, id);
            stm.setInt(2, user);
            try (ResultSet res = stm.executeQuery()) {
                if (res.next()) {
                    Time b = new Time(timeStart), e = new Time(res.getString("TIMERUN").equals("0") ? "00:30" : res.getString("TIMERUN"));
                    new DBToday().addRecord(user, res.getString("value"), b.toString(), e.toString());
                }
            }
        }
    }

    /**
     * Delete Record
     *
     * @param user User
     * @param id ID
     * @throws NamingException
     * @throws SQLException
     */
    public void deleteShortPlan(int user, int id) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("delete from shortgoalstable where USER_ID=? and id=?");) {
            stm.setInt(1, user);
            stm.setInt(2, id);
            stm.execute();
        }
    }

    /**
     * Modify record
     *
     * @param user User
     * @param value Value
     * @param transform Condition
     * @param type Type
     * @param id ID
     * @throws NamingException
     * @throws SQLException
     */
    public void modifyShortPlan(int user, String value, String transform, String type, int id) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("UPDATE shortgoalstable set VALUE=?, TRANSFORM=?, TYPE=? where user_id=? and id=?");) {
            stm.setString(1, value);
            stm.setInt(2, Integer.parseInt(transform));
            stm.setString(3, type);
            stm.setInt(4, user);
            stm.setInt(5, id);
            stm.execute();
        }
    }

    /**
     * Set Last Sequency
     *
     * @param user User
     * @param id ID
     * @throws NamingException
     * @throws SQLException
     */
    public void setLastSequency(int user, int id) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from shortgoalstable where user_id=? order by sequency desc");) {
            stm.setInt(1, user);
            try (ResultSet res = stm.executeQuery()) {
                if (res.next()) {
                    try (PreparedStatement stm1 = conn.prepareStatement("update shortgoalstable set sequency = ? where id=? and user_id=?");) {
                        stm1.setInt(1, res.getInt("sequency") + 1);
                        stm1.setInt(2, id);
                        stm1.setInt(3, user);
                        stm1.execute();
                    }
                }
            }
        }
    }

    /**
     * Modify Sequency
     *
     * @param user User
     * @param id ID
     * @param before Before element
     * @throws NamingException
     * @throws SQLException
     */
    public void changeSequency(int user, int id, int before) throws NamingException, SQLException {
        int seqAfter;
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from shortgoalstable where sequency >= (select sequency from shortgoalstable where id=? and user_id=?) order by sequency");) {
            stm.setInt(1, before);
            stm.setInt(2, user);
            try (ResultSet res = stm.executeQuery()) {
                res.next();
                seqAfter = res.getInt("sequency");
                while (res.next()) {
                    try (PreparedStatement stm1 = conn.prepareStatement("update shortgoalstable set sequency = sequency+1 where id = ? and user_id=?");) {
                        stm1.setInt(1, res.getInt("id"));
                        stm1.setInt(2, user);
                        stm1.execute();
                    }
                }
            }
            try (PreparedStatement stm2 = conn.prepareStatement("update shortgoalstable set sequency = ? where id=? and user_id=?");) {
                stm2.setInt(1, seqAfter);
                stm2.setInt(2, id);
                stm2.setInt(3, user);
                stm2.execute();
                stm2.setInt(1, seqAfter + 1);
                stm2.setInt(2, before);
                stm2.setInt(3, user);
                stm2.execute();
            }
        }
    }

    /**
     * Transform to LongPlan
     *
     * @param id ID
     * @param user User
     * @param parent Catalog
     * @throws NamingException
     * @throws SQLException
     */
    public void transformToFirst(int id, int user, String parent) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from shortgoalstable where id=? and user_id=?");) {
            stm.setInt(1, id);
            stm.setInt(2, user);
            try (ResultSet res = stm.executeQuery()) {
                res.next();
                String value = res.getString("value");
                res.close();
                new DBLongPlan().addLongPlan(user, value, parent, "f", "1");
                res.close();
            }
        }
    }

    /**
     * Modify Image
     *
     * @param user User
     * @param type Type
     * @param id ID
     * @throws NamingException
     * @throws SQLException
     */
    public void modifyImage(int user, String type, int id) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("UPDATE shortgoalstable set type=? where user_id=? and id=?");) {
            stm.setString(1, type);
            stm.setInt(2, user);
            stm.setInt(3, id);
            stm.execute();
        }
    }

    /**
     * Set duration
     *
     * @param id ID
     * @param time Time
     * @param user User
     * @throws NamingException
     * @throws SQLException
     */
    public void setTimeRun(int id, String time, int user) throws NamingException, SQLException {
        int minutes = Integer.parseInt(time.substring(time.indexOf(":") + 1));
        if (minutes != 30 && minutes != 0) {
            minutes = minutes < 30 ? (minutes < 15 ? 0 : 30) : (minutes < 45 ? 30 : 0);
            time = time.substring(0, time.indexOf(":") + 1) + String.valueOf(minutes);
            time = new Time(time).toString();
        }
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("UPDATE shortgoalstable set TIMERUN=? where id=? and user_id=?");) {
            stm.setString(1, time);
            stm.setInt(2, id);
            stm.setInt(3, user);
            stm.execute();
        }
    }

    /**
     * Set date
     *
     * @param id ID
     * @param date date
     * @param user user
     * @throws NamingException
     * @throws SQLException
     */
    public void setDateRun(int id, String date, int user) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("UPDATE shortgoalstable set DATERUN=? where id=? and user_id=?");) {
            stm.setString(1, date);
            stm.setInt(2, id);
            stm.setInt(3, user);
            stm.execute();
        }
    }

    /**
     * DB Connection
     *
     * @return DataSource
     * @throws NamingException
     */
    private DataSource getDbtimeorganizer() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/timeorganizerReferences");
    }

    /**
     * Delete all finished records
     *
     * @param user User
     * @throws SQLException
     * @throws NamingException
     */
    public void deleteAllEnd(int user) throws SQLException, NamingException {
        Connection conn = getDbtimeorganizer().getConnection();
        PreparedStatement stm = conn.prepareStatement("delete from shortgoalstable where user_id=? and transform=4");
        stm.setInt(1, user);
        stm.execute();
    }

}
