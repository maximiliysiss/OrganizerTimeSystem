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
 * DAO User
 * @author zimma
 */
public class DBUser {

    /**
     * Get User ID by EMAIL
     * @param email Email
     * @return ID
     * @throws NamingException
     * @throws SQLException 
     */
    public int getUserID(String email) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from users where users.EMAIL=?");) {
            stm.setString(1, email);
            try (ResultSet resS = stm.executeQuery()) {
                if (resS.next()) {
                    return resS.getInt("ID");
                }
                resS.close();
            }
            return -1;
        }
    }

    /**
     * Try to add user
     * @param email Email
     * @param ident Identy
     * @param urlimg Image url
     * @throws NamingException
     * @throws SQLException 
     */
    public void tryAddUser(String email, String ident, String urlimg) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from users where EMAIL=?");) {
            stm.setString(1, email);
            try (ResultSet res = stm.executeQuery()) {
                if (res.next()) {
                    res.close();
                    return;
                }
                res.close();
            }
            try (PreparedStatement stm1 = conn.prepareStatement("insert into users (EMAIL, IDENT, IMGURL) values(?,?,?)");) {
                stm1.setString(1, email);
                stm1.setString(2, ident);
                stm1.setString(3, urlimg);
                stm1.execute();
            }
        }
    }

    /**
     * Set start day
     * @param id ID
     * @param time time
     * @throws NamingException
     * @throws SQLException 
     */
    public void setStartTime(int id, String time) throws NamingException, SQLException {
        Time t = new Time(time);
        t.round();
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("update users set morningstart=? where id=?");) {
            stm.setString(1, t.toString());
            stm.setInt(2, id);
            stm.execute();
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
