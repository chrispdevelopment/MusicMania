/*
 * Copyright (C) 2014 Chris Pratt <www.chrispdevelopment.co.uk>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package controllers;

import entities.beans.UserSessionBean;
import entities.users.Account;
import entities.users.SubscriptionList;
import entities.users.User;
import entities.users.UserList;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.DbConnection;
import models.Users;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
@WebServlet(name = "AuthServlet", urlPatterns = {"/AuthServlet"})
public class AuthServlet extends HttpServlet {
    protected String title = null;
    protected String action = null;

    public void init(ServletConfig servletConfig) throws ServletException{
        this.title = servletConfig.getInitParameter("title");
        this.action = servletConfig.getInitParameter("action");
    }
    
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
        String userPath;
        String url;
        
        
        if (this.action.equals("login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            DbConnection db = new DbConnection();
            Users users = new Users();
            UserList userList;
            Account account;

            try {
                db.createConnection();
                userList = users.getAllUsers(db.conn);
                db.closeConnection();
                boolean found = false;

                for (int ct = 0; ct < userList.size(); ct++) {
                    User user = userList.getUserAt(ct);
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        HttpSession session = request.getSession();
                        db.createConnection();
                        account = users.getAccount(db.conn, user.getUserID());
                        db.closeConnection();
                        UserSessionBean userSession = this.setSession(user.getUserID(), 
                                user.getUsername(), user.getAccessLevel(), 
                                user.getSubscriptions(), account.getBalance());
                        session.setAttribute("userSession", userSession);
                        found = true;
                        break;
                    } 
                }
                
                if (found) {
                    userPath = "/index";
                } else {
                    response.setHeader("loginError", "Username and password not found");
                    userPath = "/index";
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setHeader("dbError", "Unable to connect to the database");
                userPath = "/index";
            }
        } else {
            HttpSession session = request.getSession();
            session.invalidate();
            userPath = "/index";
        }

        if (userPath.equals("/index")) {
            url = "/" + userPath + ".jsp";
            response.setHeader("title", "Home");
        } else {
            url = "/WEB-INF/views" + userPath + ".jsp";
            response.setHeader("title", this.title);
        }

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
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

    private UserSessionBean setSession(int userID, String username, 
            String accessLevel, SubscriptionList subs, double balance) {
        UserSessionBean userSession;
        
        userSession = new UserSessionBean(userID, username, 
                accessLevel, true, subs, balance);
        
        return userSession;
    }
}
