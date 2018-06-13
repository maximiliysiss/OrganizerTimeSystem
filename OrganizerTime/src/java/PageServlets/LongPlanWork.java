/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageServlets;

import DbConnection.AjaxGetMap;
import DbConnection.DBLongPlan;
import DbConnection.DBUser;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for Longplans
 * @author zimma
 */
@WebServlet(name = "LongPlanWork", urlPatterns = {"/LongPlanWork"})
public class LongPlanWork extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HashMap<String, String> input = new AjaxGetMap().getAjaxInfo(request);
        try {
            switch (input.get("type")) {
                case "add":
                    new DBLongPlan().addLongPlan(new DBUser().getUserID(input.get("user")),
                            input.get("value"), input.get("parent"), input.get("transform"), "1");
                    break;
                case "delete":
                    new DBLongPlan().deleteLongPlan(new DBUser().getUserID(input.get("user")),
                            Integer.parseInt(input.get("id")));
                    break;
                case "modify":
                    new DBLongPlan().modifyLongPlan(new DBUser().getUserID(input.get("user")),
                            input.get("value"), input.get("transform"), Integer.parseInt(input.get("id")));
                    break;
                case "transformToSecond":
                    new DBLongPlan().transformToSecond(new DBUser().getUserID(input.get("user")),
                            Integer.parseInt(input.get("id")));
                    break;
                case "changeParent":
                    String parent = input.get("parent");
                    new DBLongPlan().changeParent(new DBUser().getUserID(input.get("user")),
                            Integer.parseInt(input.get("id")), parent);
                    break;
                case "newSequency":
                    new DBLongPlan().modifySequency(new DBUser().getUserID(input.get("user")),
                            Integer.parseInt(input.get("id")), Integer.parseInt(input.get("after")));
                    break;
                case "lastSequency":
                    new DBLongPlan().setLastSequency(new DBUser().getUserID(input.get("user")),
                            Integer.parseInt(input.get("id")));
                    break;
                case "deleteAll":
                    new DBLongPlan().deleteAllLongPlans(Integer.parseInt(input.get("user")),
                            Integer.parseInt(input.get("id").equals("null") ? "-1" : input.get("id")));
                    break;
                case "transformToToday":
                    new DBLongPlan().transformToToday(new DBUser().getUserID(input.get("user")),
                            input.get("timeStart"), Integer.parseInt(input.get("id")));
                    break;
            }
        } catch (NamingException | SQLException ex) {
            throw new ServletException("LongPlanWork " + ex.getMessage());
        }
        response.getWriter().write("Yes");
    }

}
