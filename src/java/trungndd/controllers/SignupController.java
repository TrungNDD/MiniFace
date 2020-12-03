/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trungndd.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trungndd.daos.AccountDAO;
import trungndd.dtos.AccountDTO;
import trungndd.resources.MyProperties;

/**
 *
 * @author Admin
 */
public class SignupController extends HttpServlet {

    private static final String SIGNUP = "signupPage";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        MyProperties myProperties = new MyProperties("guest");
        String url = myProperties.getMyProperty(SIGNUP);
        AccountDTO dto = null;
        HttpSession session = request.getSession();

        try {
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("pass");
            System.out.println(password);
            
            dto = new AccountDTO(email, username, password, "member", "New");

            if (AccountDAO.add(dto)) {
                request.setAttribute("MESSAGE", "Signup successfully. Redirecting...");
                request.setAttribute("VERIFICATION", true);
                session.setAttribute("EMAIL", email);
                session.setAttribute("ROLE", "member");
            }
        } catch (Exception e) {
            log("ERROR at SignupController: " + e.getMessage());
            if (e.getMessage().contains("duplicate")) {
                request.setAttribute("MESSAGE", "Duplicate Email");
            } else {
                request.setAttribute("MESSAGE", "Failed to signup");
            }
        } finally {
            request.setAttribute("DTO", dto);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
