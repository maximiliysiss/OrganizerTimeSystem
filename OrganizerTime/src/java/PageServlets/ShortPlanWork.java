/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageServlets;

import DbConnection.AjaxGetMap;
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
 * Servlet for ShortPlans
 * @author zimma
 */
@WebServlet(name = "ShortPlanWork", urlPatterns = {"/ShortPlanWork"})
public class ShortPlanWork extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HashMap<String, String> input = new AjaxGetMap().getAjaxInfo(request);
        try {
            switch (input.get("type")) {
                case "add":
                    new DbConnection.DBShortPlan().addShortPlan(input.get("value"),
                            new DbConnection.DBUser().getUserID(input.get("user")), input.get("priority"));
                    break;
                case "delete":
                    new DbConnection.DBShortPlan().deleteShortPlan(new DbConnection.DBUser().getUserID(input.get("user")),
                            Integer.parseInt(input.get("id")));
                    break;
                case "modify":
                    new DbConnection.DBShortPlan().modifyShortPlan(new DbConnection.DBUser().getUserID(input.get("user")),
                            input.get("value"), input.get("transform"), input.get("priority"), Integer.parseInt(input.get("id")));
                    break;
                case "timeRun":
                    new DbConnection.DBShortPlan().setTimeRun(Integer.valueOf(input.get("id")), input.get("time"),
                            new DbConnection.DBUser().getUserID(input.get("user")));
                    break;
                case "dateRun":
                    new DbConnection.DBShortPlan().setDateRun(Integer.valueOf(input.get("id")), input.get("date"),
                            new DbConnection.DBUser().getUserID(input.get("user")));
                    break;
                case "transformToFirst":
                    new DbConnection.DBShortPlan().transformToFirst(Integer.valueOf(input.get("id")),
                            new DbConnection.DBUser().getUserID(input.get("user")), input.get("parent"));
                    break;
                case "deleteAllEnd":
                    new DbConnection.DBShortPlan().deleteAllEnd(new DbConnection.DBUser().getUserID(input.get("user")));
                    break;
                case "lastSequency":
                    new DbConnection.DBShortPlan().setLastSequency(new DbConnection.DBUser().getUserID(input.get("user")), Integer.valueOf(input.get("id")));
                    break;
                case "newSequency":
                    new DbConnection.DBShortPlan().changeSequency(new DbConnection.DBUser().getUserID(input.get("user")), Integer.valueOf(input.get("id")),
                            Integer.parseInt(input.get("after")));
                    break;
                case "deleteAll":
                    new DbConnection.DBShortPlan().deleteAllShortPlans(Integer.parseInt(input.get("user")));
                    break;
                case "transformToToday":
                    new DbConnection.DBShortPlan().transformToToday(Integer.parseInt(input.get("id")), input.get("timeStart"),
                            new DbConnection.DBUser().getUserID(input.get("user")));
                    break;
            }
        } catch (NumberFormatException | SQLException | NamingException ex) {
            throw new ServletException("ShortPlanWork " + ex.getMessage());
        }
        response.getWriter().write("Yes");
    }
}
