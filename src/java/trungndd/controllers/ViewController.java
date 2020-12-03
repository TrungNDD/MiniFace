/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trungndd.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trungndd.daos.ArticleDAO;
import trungndd.daos.CommentDAO;
import trungndd.daos.ReactionDAO;
import trungndd.dtos.ArticleDTO;
import trungndd.resources.MyProperties;

/**
 *
 * @author Admin
 */
public class ViewController extends HttpServlet {

    private static final String ARTICLE = "articlePage";
    private static final String SHOWROOM = "showroomPage";

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
        String email = (String) session.getAttribute("EMAIL");
        MyProperties myProperties = new MyProperties(role);
        String url = myProperties.getMyProperty(SHOWROOM);

        try {
            int idArticle = Integer.parseInt(request.getParameter("idArticle"));
            System.out.println("IdArticle: " + idArticle);
            // get article
            ArticleDTO dto = ArticleDAO.getArticleById(idArticle);

            if (dto != null) {
                // get reaction counts
                int likeCount = ArticleDAO.getReactionCount(idArticle, "like");
                int dislikeCount = ArticleDAO.getReactionCount(idArticle, "dislike");
                dto.setLikeCount(likeCount);
                dto.setDislikeCount(dislikeCount);

                // get emotion to check if this user has interacted on this article yet
                String emotion = ReactionDAO.checkReaction(idArticle, email);
                
                // get all the comments
                dto.setComments(CommentDAO.getAllCommentsById(idArticle));
                
                request.setAttribute("DTO", dto);
                request.setAttribute("EMOTION", emotion);
                url = myProperties.getMyProperty(ARTICLE);
            } else {
                request.setAttribute("MESSAGE", "Article no longer available");
            }
        } catch (Exception e) {
            log("ERROR at ViewController: " + e.getMessage());
            request.setAttribute("MESSAGE", "Failed to load article");
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
