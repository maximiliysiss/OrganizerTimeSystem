/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageServlets;

import DbConnection.AjaxGetMap;
import DbConnection.DBJournal;
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
 * Servlet for Journal Records
 * @author zimma
 */
@WebServlet(name = "JournalWork", urlPatterns = {"/JournalWork"})
public class JournalWork extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HashMap<String, String> inputData = new AjaxGetMap().getAjaxInfo(request);
        try {
            switch (inputData.get("type_req")) {
                case "add":
                    new DBJournal().addJournalRecord(new DBUser().getUserID(inputData.get("user")),
                            inputData.get("value"), inputData.get("time"), inputData.get("parent"));
                    break;
                case "delete":
                    new DBJournal().deleteJournalRecord(new DBUser().getUserID(inputData.get("user")),
                            Integer.parseInt(inputData.get("id")));
                    break;
                case "modify":
                    new DBJournal().modifyJournalRecord(new DBUser().getUserID(inputData.get("user")),
                            inputData.get("value"), inputData.get("time"), Integer.parseInt(inputData.get("id")));
                    break;
            }
        } catch (NamingException | SQLException ex) {
            throw new ServletException("JournalWork " + ex.getMessage());
        }
        response.getWriter().write("Yes");
    }

}
