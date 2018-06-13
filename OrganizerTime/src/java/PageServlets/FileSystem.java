/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageServlets;

import DbConnection.AjaxGetMap;
import PageElement.LongPlan;
import java.io.IOException;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet for LongPlans
 *
 * @author zimma
 */
@WebServlet(name = "FileSystem", urlPatterns = {"/FileSystem"})
public class FileSystem extends HttpServlet {

    @Resource(name = "timeorganizerReferences")
    private DataSource dbtimeorganizer;

    /**
     * Create box
     *
     * @return
     */
    public String createBox() {
        return "  <div style=\"display:none; margin-top:2%;\" id=\"create-new-plan-column\">\n"
                + "<input id=\"planval\" style=\"border-radius: 5px;border: 1px solid #a8aaad;float:left;\" type=\"text\">\n"
                + "<button onclick=\"CreateLongPlanConfirm('g')\"  placeholder='&#xf07b' id=\"create-lplan\"  type=\"button\" "
                + "class=\"button-create\" style=\"float: left;\"><i class=\"far fa-folder\" style=\"text-align: center;\"></i></button>\n"
                + "<button onclick=\"CreateLongPlanConfirm('f')\" id=\"create-ltask\" type=\"button\" class=\"button-create\">"
                + "<i class=\"far fa-sticky-note\" style=\"text-align: center;\"></i></button>\n"
                + "</div>";
    }

    /**
     * Post method
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HashMap<String, String> input = new AjaxGetMap().getAjaxInfo(request);
        String id = input.get("id");
        String user = input.get("user");
        StringBuilder builder = new StringBuilder();
        ArrayList<LongPlan> listPlans = new ArrayList<>();
        try (Connection conn = dbtimeorganizer.getConnection();
                PreparedStatement stm = conn.prepareStatement(id.equals("null") ? "select * from goalstable where parent_id is null and user_id=?"
                        : "select * from goalstable where parent_id=? and user_id=?");) {
            if (id.equals("null")) {
                stm.setInt(1, Integer.parseInt(user));
            } else {
                stm.setInt(1, Integer.parseInt(id));
                stm.setInt(2, Integer.parseInt(user));
            }
            try (ResultSet res = stm.executeQuery()) {
                while (res.next()) {
                    LongPlan var = new LongPlan(res.getString("VALUE"), res.getInt("ID"),
                            res.getString("TYPE").equals("g"), res.getInt("sequency"), res.getInt("transform"));
                    listPlans.add(var);
                }
            }
            listPlans.sort(new LongPlanComparator());
            listPlans.forEach(obj -> {
                builder.append(obj.toHtmlString());
            });
            builder.append(createBox());
            String result = builder.toString();

            response.setCharacterEncoding(
                    "UTF-8");
            response.getWriter()
                    .write(result);
        } catch (SQLException ex) {
            throw new ServerException(ex.getMessage());
        }
    }

    /**
     * ShortPlan comparator
     */
    class LongPlanComparator implements Comparator<LongPlan> {

        @Override
        public int compare(LongPlan t, LongPlan t1) {
            return Integer.compare(t.sequency, t1.sequency);
        }
    }

}
