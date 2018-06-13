/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnection;

import PageElement.Time;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * DAO Long-term plan
 *
 * @author zimma
 */
public class DBLongPlan {

    /**
     * Modify Record
     *
     * @param user User
     * @param value Value
     * @param transform Condition
     * @param id ID
     * @throws NamingException
     * @throws SQLException
     */
    public void modifyLongPlan(int user, String value, String transform, int id) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();) {
            StringBuilder query = new StringBuilder();
            if (transform.equals("0")) {
                try (PreparedStatement stm = conn.prepareStatement("UPDATE goalstable set VALUE=? where id=? and user_id=?");) {
                    stm.setString(1, value);
                    stm.setInt(2, id);
                    stm.setInt(3, user);
                    stm.execute();
                }
            } else {
                try (PreparedStatement stm = conn.prepareStatement("UPDATE goalstable set VALUE=?, TRANSFORM=? where id=? and user_id=?");) {
                    stm.setString(1, value);
                    stm.setString(2, transform);
                    stm.setInt(3, id);
                    stm.setInt(4, user);
                    stm.execute();
                }
            }
            cascadeClose(id, transform);
        }
    }

    /**
     * Delete all records
     *
     * @param user User
     * @param currentLevelID Current level of catalog
     * @throws SQLException
     * @throws NamingException
     */
    public void deleteAllLongPlans(int user, int currentLevelID) throws SQLException, NamingException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from goalstable where PARENT_ID "
                        + (currentLevelID == -1 ? "is null" : "=?") + " and user_id=?");) {
            if (currentLevelID != -1) {
                stm.setInt(1, currentLevelID);
                stm.setInt(2, user);
            } else {
                stm.setInt(1, user);
            }
            try (ResultSet res = stm.executeQuery()) {
                while (res.next()) {
                    if (res.getString("type").equals("f")) {
                        try (PreparedStatement stm1 = conn.prepareStatement("delete from goalstable where id=? and user_id=?");) {
                            stm1.setInt(1, res.getInt("id"));
                            stm1.setInt(2, user);
                            stm1.execute();
                        }
                    } else {
                        cascadeDelete(res.getInt("id"));
                    }
                }
                res.close();
            }
        }
    }

    /**
     * Transform to TodayPlan
     *
     * @param user User
     * @param timeStart Start time
     * @param id ID
     * @throws NamingException
     * @throws SQLException
     */
    public void transformToToday(int user, String timeStart, int id) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from goalstable where id=? and user_id=?");) {
            stm.setInt(1, id);
            stm.setInt(2, user);
            try (ResultSet res = stm.executeQuery()) {
                if (res.next()) {
                    Time b = new Time(timeStart);
                    new DBToday().addRecord(user, res.getString("value"), b.toString(), "00:30");
                }
            }
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
                PreparedStatement stm = conn.prepareStatement("select * from goalstable where id=? and user_id=?");) {
            stm.setInt(1, id);
            stm.setInt(2, user);
            try (ResultSet res = stm.executeQuery()) {
                if (res.next()) {
                    int parent = res.getInt("parent_id");
                    try (PreparedStatement stm1 = conn.prepareStatement("select * from goalstable where parent_id "
                            + (parent == 0 ? "is null" : "=?") + " and user_id=? order by sequency desc limit 1");) {
                        if (parent != 0) {
                            stm1.setInt(1, parent);
                            stm1.setInt(2, user);
                        } else {
                            stm1.setInt(1, user);
                        }
                        try (ResultSet result = stm1.executeQuery()) {
                            if (result.next()) {
                                try (PreparedStatement stm2 = conn.prepareStatement("update goalstable set sequency = ? where id=? and user_id=?");) {
                                    stm2.setInt(1, result.getInt("sequency") + 1);
                                    stm2.setInt(2, id);
                                    stm2.setInt(3, user);
                                    stm2.execute();
                                }
                            }
                        }
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
     * @param before Before Element
     * @throws NamingException
     * @throws SQLException
     */
    public void modifySequency(int user, int id, int before) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from goalstable where id=? and user_id=?");) {
            stm.setInt(1, before);
            stm.setInt(2, user);
            try (ResultSet res = stm.executeQuery()) {
                if (res.next()) {
                    int parent = res.getInt("parent_id");
                    int seq = res.getInt("sequency");
                    try (PreparedStatement stm1 = conn.prepareStatement("select * from goalstable where parent_id "
                            + (parent == 0 ? "is null" : "=?") + " and sequency>? and user_id=?");) {
                        if (parent == 0) {
                            stm1.setInt(1, seq);
                            stm1.setInt(2, user);
                        } else {
                            stm1.setInt(1, parent);
                            stm1.setInt(2, seq);
                            stm1.setInt(3, user);
                        }
                        try (ResultSet result = stm1.executeQuery()) {
                            while (result.next()) {
                                int ident = result.getInt("id");
                                int idSeq = result.getInt("sequency") + 1;
                                try (PreparedStatement stm2 = conn.prepareStatement("update goalstable set sequency=? where id = ? and user_id=?");) {
                                    stm2.setInt(1, idSeq);
                                    stm2.setInt(2, ident);
                                    stm2.setInt(3, user);
                                    stm2.execute();
                                }
                            }
                        }
                        try (PreparedStatement stm3 = conn.prepareStatement("update goalstable set sequency=? where id=? and user_id=?");) {
                            stm3.setInt(1, seq);
                            stm3.setInt(2, id);
                            stm3.setInt(3, user);
                            stm3.execute();
                            stm3.setInt(1, seq + 1);
                            stm3.setInt(2, before);
                            stm3.setInt(3, user);
                            stm3.execute();
                        }
                    }
                }
            }
        }
    }

    /**
     * Cascade CLose
     *
     * @param id ID
     * @param transform Condition
     * @throws NamingException
     * @throws SQLException
     */
    private void cascadeClose(int id, String transform) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from goalstable where parent_id=?");) {
            stm.setInt(1, id);
            try (ResultSet res = stm.executeQuery()) {
                while (res.next()) {
                    try (PreparedStatement stm1 = conn.prepareStatement("UPDATE goalstable set TRANSFORM=? where id=?");) {
                        stm1.setInt(1, Integer.parseInt(transform));
                        stm1.setInt(2, res.getInt("id"));
                        stm.execute();
                        if (res.getString("type").equals("g")) {
                            cascadeClose(res.getInt("id"), transform);
                        }
                    }
                }
                res.close();
            }
        }
    }

    /**
     * Cascade Delete
     *
     * @param id ID
     * @throws NamingException
     * @throws SQLException
     */
    private void cascadeDelete(int id) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from goalstable where PARENT_ID=?");) {
            stm.setInt(1, id);
            try (ResultSet res = stm.executeQuery()) {
                while (res.next()) {
                    if (res.getString("type").equals("f")) {
                        try (PreparedStatement stm1 = conn.prepareStatement("delete from goalstable where id=?");) {
                            stm1.setInt(1, res.getInt("id"));
                            stm1.execute();
                        }
                    } else {
                        cascadeDelete(res.getInt("id"));
                    }
                }
                res.close();
            }
            try (PreparedStatement stm3 = conn.prepareStatement("delete from goalstable where id=?");) {
                stm3.setInt(1, id);
                stm3.execute();
            }
        }
    }

    /**
     * Delete record
     *
     * @param user User
     * @param id ID
     * @throws NamingException
     * @throws SQLException
     */
    public void deleteLongPlan(int user, int id) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from goalstable where USER_ID=? and id=?");) {
            stm.setInt(1, user);
            stm.setInt(2, id);
            try (ResultSet resS = stm.executeQuery()) {
                if (resS.next()) {
                    if (resS.getString("TYPE").equals("f")) {
                        try (PreparedStatement stm1 = conn.prepareStatement("delete from goalstable where id=? and user_id=?");) {
                            stm1.setInt(1, resS.getInt("id"));
                            stm1.setInt(2, user);
                            stm1.execute();
                        }
                    } else {
                        cascadeDelete(resS.getInt("id"));
                    }
                }
                resS.close();
            }
        }
    }

    /**
     * Add record
     *
     * @param user User
     * @param value Value
     * @param parent Catalog
     * @param type Type
     * @param transform Condition
     * @throws NamingException
     * @throws SQLException
     */
    public void addLongPlan(int user, String value, String parent, String type, String transform) throws NamingException, SQLException {
        int seq = 0;
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from goalstable where parent_id "
                        + (parent.equals("null") ? "is null" : "=?") + " and user_id=? order by sequency desc");) {
            if (parent.equals("null")) {
                stm.setInt(1, user);
            } else {
                stm.setInt(1, Integer.parseInt(parent));
                stm.setInt(2, user);
            }
            try (ResultSet res = stm.executeQuery()) {
                if (res.next()) {
                    seq = res.getInt("sequency") + 1;
                }
            }
            try (PreparedStatement stm1 = conn.prepareStatement("insert into goalstable (SEQUENCY,USER_ID,VALUE,TYPE,TRANSFORM"
                    + (!"null".equals(parent) ? ",PARENT_ID)" : ")") + "values(?,?,?,?,?" + (!"null".equals(parent) ? ",?)" : ")"));) {
                stm1.setInt(1, seq);
                stm1.setInt(2, user);
                stm1.setString(3, value);
                stm1.setString(4, type);
                stm1.setInt(5, Integer.parseInt(transform));
                if (!parent.equals("null")) {
                    stm1.setInt(6, Integer.parseInt(parent));
                }
                stm1.execute();
            }
        }
    }

    /**
     * Change parent
     *
     * @param user User
     * @param id ID
     * @param parent New parent
     * @throws NamingException
     * @throws SQLException
     */
    public void changeParent(int user, int id, String parent) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("UPDATE goalstable set PARENT_ID" + (parent.equals("null") ? " = null" : "=?") + " where id=? and user_id=?");) {
            if (parent.equals("null")) {
                stm.setInt(1, id);
                stm.setInt(2, user);
            } else {
                stm.setInt(1, Integer.parseInt(parent));
                stm.setInt(2, id);
                stm.setInt(3, user);
            }
            stm.execute();
        }
    }

    /**
     * Transform to ShortPlan
     *
     * @param user User
     * @param id ID
     * @throws NamingException
     * @throws SQLException
     */
    public void transformToSecond(int user, int id) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from goalstable where id=? and user_id=?");) {
            stm.setInt(1, id);
            stm.setInt(2, user);
            try (ResultSet res = stm.executeQuery()) {
                if (res.next()) {
                    String value = res.getString("VALUE");
                    new DBShortPlan().addShortPlan(value, user, "E");
                }
            }
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

}
