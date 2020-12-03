/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trungndd.controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trungndd.daos.ArticleDAO;
import trungndd.dtos.ArticleDTO;

/**
 *
 * @author Admin
 */
public class SearchController extends HttpServlet {
    private static final int PAGE_SIZE = 5;
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String url;
        PrintWriter out = response.getWriter();
               
        try {
            String txtSearch = request.getParameter("txtSearch");
            String pageNoParam = request.getParameter("pageNo");
            String pageSizeParam = request.getParameter("pageSize");
            int pageNo = Integer.parseInt(pageNoParam);
            int pageSize = Integer.parseInt(pageSizeParam);
            
            List<ArticleDTO> dtos = ArticleDAO.search(txtSearch, pageNo, pageSize);
            // set total pages
            int totalRecords = ArticleDAO.getTotalSearchRecords(txtSearch);
            
            // create json string
            Gson gson = new Gson();
            String json = gson.toJson(dtos);
            // write to file json
            out.write("{\"articles\" : " +  json + ", ");
            out.write("\"length\" : " + totalRecords + "}");
            
            System.out.println(totalRecords);
        } catch (Exception e) {
            log("ERROR at SearchController: " + e.getMessage());
        } finally {
            //request.getRequestDispatcher(url).forward(request, response);
        }
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
