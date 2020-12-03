/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trungndd.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trungndd.daos.AccountDAO;
import trungndd.db.MyConnection;
import trungndd.resources.MyEncryption;
import trungndd.resources.MyProperties;

/**
 *
 * @author Admin
 */
public class VerifyController extends HttpServlet {

    private static final String VERIFY = "verifyPage";

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
        MyProperties myProperties;
        String url = null;

        try {
            // first login the user
            String email = (String) session.getAttribute("EMAIL");
            String role = "guest";
            if (email == null) {
                email = request.getParameter("email");
                role = request.getParameter("role");
                session.setAttribute("EMAIL", email);
                session.setAttribute("ROLE", role);
            }
            
            myProperties = new MyProperties(role);
            url = myProperties.getMyProperty(VERIFY);

            String action = request.getParameter("action") + "";
            String verifyCode = "";
            switch (action) {
                default:
                    // generate code
                    verifyCode = MyEncryption.generateCode(4);
                    session.setAttribute("CODE", verifyCode);

                    // send email
                    sendEmail(email, verifyCode);
                    break;
                    
                case "validate":
                    // check code
                    String inputCode = request.getParameter("verify-code");
                    verifyCode = (String) session.getAttribute("CODE");
                    System.out.println(inputCode + "-" + verifyCode);
                    if (inputCode.matches(verifyCode)){
                        request.setAttribute("MESSAGE", "Verified successfully!");
                        // set the status to active
                        AccountDAO.activate(email);
                    } else {
                        request.setAttribute("MESSAGE", "Failed to verify!");
                    }
            }
        } catch (Exception e) {
            log("ERROR at VerifyController: " + e.getMessage());
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    protected void sendEmail(String to, String code) throws Exception {
        String host = "smtp.gmail.com";
        final String user = "trungnddse140190@fpt.edu.vn";  
        final String password = "uvydyesbdbliwvzw";

        //Get the session object  
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        //Compose the message  
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("MiniFace - VERIFICATION CODE");
        message.setText("Your verification code is: " + code + 
                "\nThis code will expire in 30 minutes.");

        //send the message  
        Transport.send(message);
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
