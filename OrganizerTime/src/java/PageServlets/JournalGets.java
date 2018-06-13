/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageServlets;

import DbConnection.AjaxGetMap;
import DbConnection.DBJournal;
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
 * Servlet for Journal Get
 * @author zimma
 */
@WebServlet(name = "JournalGets", urlPatterns = {"/JournalGets"})
public class JournalGets extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     * Post
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HashMap<String, String> input = new AjaxGetMap().getAjaxInfo(request);
        response.setCharacterEncoding("UTF-8");
        DBJournal journal = new DBJournal();
        try {
            response.getWriter().write(journal.journalPageConstract(Integer.valueOf(input.get("user")), input.get("conditions"),
                    getTimeStart(input.get("timerCond"))));
        } catch (NamingException | SQLException ex) {
            throw new ServletException("JournalGets " + ex.getMessage());
        }
    }
    
    
    /**
     * Get time start
     * @param input
     * @return 
     */
    private HashMap<Integer, String> getTimeStart(String input) {
        HashMap<Integer, String> times = new HashMap<>();
        if (input.equals("none")) {
            return times;
        }
        String[] block = input.split("/");
        for (String elem : block) {
            String[] values = elem.split(";");
            times.put(Integer.parseInt(values[0]), values[1]);
        }
        return times;
    }
}
