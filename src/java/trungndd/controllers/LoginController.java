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
public class LoginController extends HttpServlet {
    private static final String LOGIN = "loginPage";
    private static final String SHOWROOM = "showroomPage";
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        MyProperties myProperties = new MyProperties("member");
        String url = myProperties.getMyProperty(LOGIN);
               
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            AccountDTO dto = AccountDAO.checkLogin(email, password);
            
            if (dto != null) {
                session.setAttribute("EMAIL", dto.getEmail());
                session.setAttribute("ROLE", dto.getRole());
                url = myProperties.getMyProperty(SHOWROOM);
                
                // return the showroom page imediately
                response.sendRedirect(SHOWROOM);
            } else {
                request.setAttribute("MESSAGE", "Username or Password is invalid");
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (Exception e) {
            log("ERROR at LoginController: " + e.getMessage());
            request.setAttribute("MESSAGE", "Login Failed, try again later.");
            request.getRequestDispatcher(url).forward(request, response);
        } //finally {
//            request.getRequestDispatcher(url).forward(request, response);
//        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
