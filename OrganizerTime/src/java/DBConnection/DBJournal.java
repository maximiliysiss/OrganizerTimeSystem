/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnection;

import PageElement.JRecords;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * DAO Journal
 * @author zimma
 */
public class DBJournal {

    /**
     * Modify Record
     * @param user User
     * @param value Value
     * @param time Time
     * @param id ID
     * @throws NamingException
     * @throws SQLException
     */
    public void modifyJournalRecord(int user, String value, String time, int id)
            throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("UPDATE journalrecords set VALUE=?, TIME=? where user_id=? and id=?");) {
            stm.setString(1, value);
            stm.setString(2, time);
            stm.setInt(3, user);
            stm.setInt(4, id);
            stm.execute();
        }
    }

    /**
     * is this step?
     *
     * @param id ID
     * @return bool
     * @throws NamingException
     * @throws SQLException
     */
    private boolean isStep(int id) throws NamingException, SQLException {
        boolean resId = false;
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from journalrecords where id=?");) {
            stm.setInt(1, id);
            try (ResultSet resS = stm.executeQuery()) {
                if (resS.next()) {
                    resId = resS.getInt("PARENT_ID") != 0;
                }
                resS.close();
            }
        }
        return resId;
    }

    /**
     * Delete Record
     *
     * @param user User
     * @param id ID
     * @throws NamingException
     * @throws SQLException
     */
    public void deleteJournalRecord(int user, int id) throws NamingException, SQLException {
        boolean stepIs = this.isStep(id);
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("delete from journalrecords where PARENT_ID=? and user_id=?");
                PreparedStatement stm1 = conn.prepareStatement("delete from journalrecords where ID=? and user_id=?");) {
            stm.setInt(1, id);
            stm.setInt(2, user);
            stm1.setInt(1, id);
            stm1.setInt(2, user);
            if (!stepIs) {
                stm.execute();
            }
            stm1.execute();
        }
    }

    /**
     * Add record
     *
     * @param user User
     * @param value Value
     * @param time Time
     * @param parent Catalog
     * @throws NamingException
     * @throws SQLException
     */
    public void addJournalRecord(int user, String value, String time, String parent) throws NamingException, SQLException {
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("insert into journalrecords (USER_ID,TIME,VALUE"
                        + (!"null".equals(parent) ? ",PARENT_ID)" : ")") + " values(?,?,?" + (!"null".equals(parent) ? ",?)" : ")"));) {
            stm.setInt(1, user);
            stm.setString(2, time);
            stm.setString(3, value);
            if (!parent.equals("null")) {
                stm.setInt(4, Integer.parseInt(parent));
            }
            stm.execute();
        }
    }

    /**
     * Journal's constructor
     *
     * @param ident ID
     * @param condition Condition
     * @param timers Active timers
     * @return String
     * @throws NamingException
     * @throws SQLException
     */
    public String journalPageConstract(int ident, String condition, HashMap<Integer, String> timers) throws NamingException, SQLException {
        ArrayList<JRecords> list = new ArrayList<>();
        try (Connection conn = getDbtimeorganizer().getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from journalrecords where USER_ID=?");) {
            stm.setInt(1, ident);
            int index = 0;
            try (ResultSet resS = stm.executeQuery()) {
                while (resS.next()) {
                    String time = resS.getString("TIME");
                    String value = resS.getString("VALUE");
                    int parent = resS.getInt("PARENT_ID");
                    int id = resS.getInt("ID");
                    if (parent == 0) {
                        JRecords tmp = new JRecords(time, value, String.valueOf(id), true);
                        if (!condition.equals("none") && index < condition.length() && condition.charAt(index++) == '1') {
                            tmp.ShowList();
                        }
                        list.add(tmp);
                    } else {
                        for (JRecords obj : list) {
                            if (obj.getIndex().equals(String.valueOf(parent))) {
                                obj.addStep(new JRecords().createStep(id, value,
                                        (timers.isEmpty() ? time : timers.get(id) == null ? time : timers.get(id))));
                            }
                        }
                    }
                }
                resS.close();
            }
        }
        return journalConstractor(list);
    }

    
    /**
     * Journal's Constructor from list of records
     * @param records List
     * @return String
     */
    private String journalConstractor(ArrayList<JRecords> records) {
        ArrayList<JRecords> fCol = new ArrayList<>(),
                sCol = new ArrayList<>(), tCol = new ArrayList<>(), frCol = new ArrayList<>();
        int i = 0;
        for (JRecords obj : records) {
            switch (i) {
                case 0:
                    fCol.add(obj);
                    break;
                case 1:
                    sCol.add(obj);
                    break;
                case 2:
                    tCol.add(obj);
                    break;
                case 3:
                    frCol.add(obj);
                    break;
            }
            i++;
            i %= 4;
        }

        StringBuilder page = new StringBuilder();
        page.append("<div id=\"column0\" class=\"column-second\">");
        fCol.forEach((obj) -> {
            page.append(obj.isShow() ? obj.toHtmlString(0) : obj.toHtmlString());
        });
        if (i == 0) {
            page.append(new JRecords().creationJRecordBox());
        }
        page.append("    </div>");
        page.append("<div id=\"column1\" class=\"column-second\">");
        sCol.forEach((obj) -> {
            page.append(obj.isShow() ? obj.toHtmlString(0) : obj.toHtmlString());
        });
        if (i == 1) {
            page.append(new JRecords().creationJRecordBox());
        }
        page.append("    </div>");
        page.append("<div id=\"column2\" class=\"column-second\">");
        tCol.forEach((obj) -> {
            page.append(obj.isShow() ? obj.toHtmlString(0) : obj.toHtmlString());
        });
        if (i == 2) {
            page.append(new JRecords().creationJRecordBox());
        }
        page.append("    </div>");
        page.append("<div id=\"column3\" class=\"column-second\">");
        frCol.forEach((obj) -> {
            page.append(obj.isShow() ? obj.toHtmlString(0) : obj.toHtmlString());
        });
        if (i == 3) {
            page.append(new JRecords().creationJRecordBox());
        }
        page.append("    </div>");
        return page.toString();
    }

    /**
     * DB Connection
     *
     * @return Data Source
     * @throws NamingException
     */
    private DataSource getDbtimeorganizer() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/timeorganizerReferences");
    }

}
