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
import trungndd.daos.ArticleDAO;
import trungndd.daos.CommentDAO;
import trungndd.resources.MyProperties;

/**
 *
 * @author Admin
 */
public class DeleteController extends HttpServlet {

    private static final String SHOWROOM = "showroomPage";
    private static final String ARTICLE_PAGE = "viewArticle";

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
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("ROLE");
        MyProperties myProperties = new MyProperties(role);
        String url = myProperties.getMyProperty(SHOWROOM);

        try {
            String action = request.getParameter("action");
            int idArticle = Integer.parseInt(request.getParameter("idArticle"));

            switch (action) {
                case "article":
                    // if delete successful then redirect back to showroom
                    if(ArticleDAO.delete(idArticle)){
                        url = myProperties.getMyProperty(SHOWROOM);
                    }
                    break;

                case "comment":
                    int idComment = Integer.parseInt(request.getParameter("idComment"));
                    if (CommentDAO.delete(idComment)) {
                        url = myProperties.getMyProperty(ARTICLE_PAGE);
                        request.setAttribute("idArticle", idArticle);
                    }
                    break;
            }
        } catch (Exception e) {
            log("ERROR at DeleteController: " + e.getMessage());
        } finally {
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
