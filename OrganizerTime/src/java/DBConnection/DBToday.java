/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * DAO TodayPlan
 * @author zimma
 */
public class DBToday {

    /**
     * Modify Condition
     *
     * @param id ID
     * @param user User
     * @param transform Condition
     * @throws NamingException
     * @throws SQLException
     */
    public void changeTransform(int id, int user, int transform) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("update todaytable set transform = ? where id=? and user_id=?");) {
            stm.setInt(1, transform);
            stm.setInt(2, id);
            stm.setInt(3, user);
            stm.execute();
        }
    }

    /**
     * Add record
     *
     * @param user User
     * @param value Value
     * @param time Time
     * @param duration duration
     * @throws NamingException
     * @throws SQLException
     */
    public void addRecord(int user, String value, String time, String duration) throws NamingException, SQLException {
        LocalDate curdate = LocalDate.now();
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("insert into todaytable (value,user_id,time,createdate, duration) values (?,?,?,?,?)");) {
            stm.setString(1, value);
            stm.setInt(2, user);
            stm.setString(3, time);
            stm.setString(4, curdate.toString());
            stm.setString(5, duration);
            stm.execute();
        }
    }

    /**
     * Delete
     *
     * @param id ID
     * @param user User
     * @throws NamingException
     * @throws SQLException
     */
    public void delete(int id, int user) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("delete from todaytable where id=? and user_id=?");) {
            stm.setInt(1, id);
            stm.setInt(2, user);
            stm.execute();
        }
    }

    /**
     * Delete all
     *
     * @param user User
     * @throws NamingException
     * @throws SQLException
     */
    public void deleteAll(int user) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("delete from todaytable where user_id=?");) {
            stm.setInt(1, user);
            stm.execute();
        }
    }

    /**
     * Modify
     *
     * @param id ID
     * @param user User
     * @param value Value
     * @throws NamingException
     * @throws SQLException
     */
    public void modify(int id, int user, String value) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("UPDATE todaytable set value=? where id=? and user_id=?");) {
            stm.setString(1, value);
            stm.setInt(2, id);
            stm.setInt(3, user);
            stm.execute();
        }
    }

    /**
     * Move
     *
     * @param id ID
     * @param newTime New time
     * @param user User
     * @throws NamingException
     * @throws SQLException
     */
    public void movement(int id, String newTime, int user) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm1 = conn.prepareStatement("update todaytable set time=? where id=? and user_id=?");) {
            stm1.setString(1, newTime);
            stm1.setInt(2, id);
            stm1.setInt(3, user);
            stm1.execute();
        }
    }

    /**
     * DB Connection
     * @return DataSource
     * @throws NamingException 
     */
    private DataSource getDbtimeorganizer() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/timeorganizerReferences");
    }
}
