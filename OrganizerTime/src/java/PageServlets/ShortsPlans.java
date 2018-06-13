/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageServlets;

import DbConnection.AjaxGetMap;
import PageElement.ShortPlans;
import java.io.IOException;
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
 * Servlet for ShotPlanGets
 *
 * @author zimma
 */
@WebServlet(name = "ShortsPlans", urlPatterns = {"/ShortsPlans"})
public class ShortsPlans extends HttpServlet {

    @Resource(name = "timeorganizerReferences")
    private DataSource dbtimeorganizer;

    /**
     * Create box
     *
     * @return
     */
    public String createBlock() {
        return "<div id=\"create-new-sh\" style=\"display: none; margin-top: 2%;\">"
                + "<input onkeydown=\"AddShPlan(event)\" style=\"margin-right: 2%;width:95%\" class=\"create-textbox\" id=\"shplanval\" type=\"text\">"
                + "</div>";
    }

    /**
     * Post
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HashMap<String, String> input = new AjaxGetMap().getAjaxInfo(request);
        String user = input.get("user");
        StringBuilder sb = new StringBuilder();
        ArrayList<ShortPlans> list = new ArrayList<>();
        try (Connection conn = dbtimeorganizer.getConnection();
                PreparedStatement stm = conn.prepareStatement("select * from shortgoalstable where user_id=?");) {
            stm.setInt(1, Integer.parseInt(user));
            try (ResultSet res = stm.executeQuery()) {
                while (res.next()) {
                    String value = res.getString("value");
                    String type = res.getString("type");
                    int id = res.getInt("ID");
                    int transform = res.getInt("TRANSFORM");
                    ShortPlans var = new ShortPlans(value, type, res.getString("DATERUN"), res.getString("TIMERUN"),
                            id, res.getInt("sequency"), res.getInt("transform"));
                    list.add(var);
                }
                res.close();
            }
            list.sort(new ShortPlanComparator());
            list.forEach(obj -> {
                sb.append(obj.toHtmlString());
            });
            response.setCharacterEncoding("UTF-8");
            sb.append(createBlock());
            response.getWriter().write(sb.toString());
        } catch (SQLException ex) {
            throw new ServletException(ex.getMessage());
        }
    }

    /**
     * ShortPlan Comparator
     */
    class ShortPlanComparator implements Comparator<ShortPlans> {

        @Override
        public int compare(ShortPlans t, ShortPlans t1) {
            return Integer.compare(t.sequency, t1.sequency);
        }

    }

}
