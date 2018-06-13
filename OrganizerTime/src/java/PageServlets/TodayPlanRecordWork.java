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
 * Servlet for TodayPlanRecord
 * @author zimma
 */
@WebServlet(name = "TodayPlanRecordWork", urlPatterns = {"/TodayPlanRecordWork"})
public class TodayPlanRecordWork extends HttpServlet {

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
        try {
            switch (input.get("type")) {
                case "modify":
                    new DbConnection.DBToday().modify(Integer.parseInt(input.get("id")),
                            new DbConnection.DBUser().getUserID(input.get("user")),
                            input.get("value").equals("None") ? "" : input.get("value"));
                    break;
                case "transformToToday":
                    new DbConnection.DBToday().movement(Integer.parseInt(input.get("id")),
                            input.get("timeStart"), new DbConnection.DBUser().getUserID(input.get("user")));
                    break;
                case "modifyByTime":
                    new DbConnection.DBToday().addRecord(new DbConnection.DBUser().getUserID(input.get("user")),
                            input.get("value"), input.get("time"), "00:30");
                    break;
                case "delete":
                    if (!input.get("id").equals("all")) {
                        new DbConnection.DBToday().delete(Integer.parseInt(input.get("id")),
                                new DbConnection.DBUser().getUserID(input.get("user")));
                    } else {
                        new DbConnection.DBToday().deleteAll(new DbConnection.DBUser().getUserID(input.get("user")));
                    }
                    break;
                case "closePlan":
                    new DbConnection.DBToday().changeTransform(Integer.parseInt(input.get("id")),
                            new DbConnection.DBUser().getUserID(input.get("user")), 5);
                    break;
                case "actualPlan":
                    new DbConnection.DBToday().changeTransform(Integer.parseInt(input.get("id")),
                            new DbConnection.DBUser().getUserID(input.get("user")), 3);
                    break;
            }
        } catch (SQLException | NamingException ex) {
            throw new ServletException("TodayPlanRecWork " + ex.getMessage());
        }
        response.getWriter().write("Yes");
    }
}
