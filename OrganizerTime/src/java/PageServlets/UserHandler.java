/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageServlets;

import DbConnection.AjaxGetMap;
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
 * Servlet for User
 * @author zimma
 */
@WebServlet(name = "UserHandler", urlPatterns = {"/UserHandler"})
public class UserHandler extends HttpServlet {

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
                case "setStartTime":
                    new DbConnection.DBUser().setStartTime(new DBUser().getUserID(input.get("id")), input.get("time"));
                    break;
            }
        } catch (NamingException | SQLException ex) {
            throw new ServletException("UserHandler " + ex.getMessage());
        }
    }
}
