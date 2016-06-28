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

import entities.artists.ArtistList;
import entities.artists.BandList;
import entities.beans.UserSessionBean;
import entities.mechandise.MerchandiseList;
import entities.orders.OrderList;
import entities.users.Account;
import entities.users.Subscription;
import entities.users.SubscriptionList;
import entities.users.User;
import entities.users.UserList;
import models.DbConnection;
import models.Users;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Artists;
import models.Merch;
import models.Orders;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

    protected String title = null;
    protected String action = null;

    public void init(ServletConfig servletConfig) throws ServletException {
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
     * @throws java.text.ParseException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        String userPath = "";
        String url;
        String goTo = "form";
        HttpSession session;
        Users users = new Users();
        Artists artists = new Artists();
        Orders orders = new Orders();
        Merch merch = new Merch();
        UserSessionBean userSession = (UserSessionBean) request.getSession().getAttribute("userSession");

        DbConnection db = new DbConnection();
        switch (this.action) {
            case "sub": {
                String[] urlRequest = request.getPathInfo().split("/");
                Subscription sub = new Subscription(Integer.parseInt(urlRequest[3]),
                        Integer.parseInt(urlRequest[2]), urlRequest[1]);
                try {
                    db.createConnection();
                    users.createSubscription(db.conn, sub);
                    SubscriptionList subs = users.getSubscriptions(db.conn, Integer.parseInt(urlRequest[3]));
                    db.closeConnection();

                    session = request.getSession(false);
                    this.updateSession(session, subs);

                    if (urlRequest[1].equals("artist")) {
                        userPath = "/showProfile/artist/" + urlRequest[2];
                    } else {
                        userPath = "/showProfile/band/" + urlRequest[2];
                    }
                    goTo = "servlet";

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "unSub": {
                String[] urlRequest = request.getPathInfo().split("/");
                try {
                    db.createConnection();
                    users.deleteSubscription(db.conn,
                            Integer.parseInt(urlRequest[3]),
                            Integer.parseInt(urlRequest[2]));
                    SubscriptionList subs = users.getSubscriptions(db.conn, Integer.parseInt(urlRequest[3]));
                    db.closeConnection();

                    session = request.getSession(false);
                    this.updateSession(session, subs);

                    if (urlRequest[1].equals("artist")) {
                        userPath = "/showProfile/artist/" + urlRequest[2];
                    } else {
                        userPath = "/showProfile/band/" + urlRequest[2];
                    }
                    goTo = "servlet";

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "reg": {
                boolean duplicate = false;
                User user = new User();
                user.setUsername(request.getParameter("username"));
                user.setPassword(request.getParameter("password"));
                user.setFirstname(request.getParameter("firstname"));
                user.setSurname(request.getParameter("surname"));
                user.setPostCode(request.getParameter("postCode"));
                user.setPhone(request.getParameter("phone"));
                user.setEmail(request.getParameter("email"));
                
                String address = request.getParameter("address1");
                if(!request.getParameter("address2").isEmpty()) {
                    address = address.concat("," + request.getParameter("address2"));
                }
                if(!request.getParameter("address3").isEmpty()) {
                    address = address.concat("," + request.getParameter("address3"));
                }
                if(!request.getParameter("address4").isEmpty()) {
                    address = address.concat("," + request.getParameter("address4"));
                }
                user.setAddress(address);

                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date date = sdf1.parse(request.getParameter("dob"));
                java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
                user.setdOB(sqlStartDate);
                try {
                    db.createConnection();
                    UserList userList = users.getAllUsers(db.conn);
                    for (int x = 0; x < userList.size(); x++) {
                        User currentUser = userList.getUserAt(x);
                        if (currentUser.getUsername().equals(user.getUsername())) {
                            response.setHeader("regMessage", "Username already exsists");
                            duplicate = true;
                            break;
                        }
                    }
                    if (!duplicate) {
                        users.createUser(db.conn, user);
                        response.setHeader("regMessage", "Successfully Registered");
                    }
                    db.closeConnection();

                    userPath = "/index";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "profile": {
                ArtistList artistList = new ArtistList();
                BandList bandList = new BandList();

                try {
                    db.createConnection();
                    User user = users.getUser(db.conn, userSession.getUserID());
                    Account account = users.getAccount(db.conn, userSession.getUserID());
                    SubscriptionList subs = userSession.getSubscriptions();

                    for (int x = 0; x < subs.size(); x++) {
                        Subscription sub = subs.getSubscriptionAt(x);
                        if (sub.getType().equals("artist")) {
                            artistList.addArtist(artists.getArtist(db.conn, sub.getSubToID()));
                        } else {
                            bandList.addBand(artists.getBand(db.conn, sub.getSubToID()));
                        }
                    }

                    OrderList orderList = orders.getUserOrders(db.conn, userSession.getUserID());
                    MerchandiseList merchList = merch.getAllMerch(db.conn);
                    db.closeConnection();

                    request.setAttribute("user", user);
                    request.setAttribute("account", account);
                    request.setAttribute("artists", artistList);
                    request.setAttribute("bands", bandList);
                    request.setAttribute("orders", orderList);
                    request.setAttribute("merch", merchList);
                    userPath = "/userProfile";

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }

                break;
            }
            case "changePassword": {
                String newPassword = request.getParameter("password");

                try {
                    db.createConnection();
                    users.updateUserPassword(db.conn, newPassword, userSession.getUserID());
                    db.closeConnection();

                    response.setHeader("profileMessage", "Password Changed");
                    userPath = "/logout";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }

                break;
            }
            case "changeEmail": {
                String newEmail = request.getParameter("email");

                try {
                    db.createConnection();
                    users.updateUserEmail(db.conn, newEmail, userSession.getUserID());
                    db.closeConnection();

                    response.setHeader("profileMessage", "Email Changed");
                    userPath = "/profile";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "changeInfo": {
                String firstname = request.getParameter("firstname");
                String surname = request.getParameter("surname");

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date = formatter.parse(request.getParameter("dob"));
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                
                try {
                    db.createConnection();
                    users.updateUserInfo(db.conn, firstname, surname, sqlDate, userSession.getUserID());
                    db.closeConnection();

                    response.setHeader("profileMessage", "Basic information changed");
                    userPath = "/profile";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "changeAddInfo": {
                String address = request.getParameter("address1");
                if(!request.getParameter("address2").isEmpty()) {
                    address = address.concat("," + request.getParameter("address2"));
                }
                if(!request.getParameter("address3").isEmpty()) {
                    address = address.concat("," + request.getParameter("address3"));
                }
                if(!request.getParameter("address4").isEmpty()) {
                    address = address.concat("," + request.getParameter("address4"));
                }
                
                String postCode = request.getParameter("postCode");
                String phone = request.getParameter("phone");
                
                try {
                    db.createConnection();
                    users.updateUserAddInfo(db.conn, address, postCode, phone, userSession.getUserID());
                    db.closeConnection();

                    response.setHeader("profileMessage", "Additional information changed");
                    userPath = "/profile";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            //This code would go off to credit card auth if running live and would 
            //use the stored card details and the CVC to charge the card before adding amount.
            case "addMoney": { 
                double amount = Double.parseDouble(request.getParameter("amount"));
                double totalBalance = userSession.getBalance();
                
                totalBalance = totalBalance + amount;
                
                userSession.setBalance(totalBalance);
                
                try {
                    db.createConnection();
                    users.updateAccountBalance(db.conn, totalBalance, userSession.getUserID());
                    db.closeConnection();

                    response.setHeader("profileMessage", "Money added to you're account");
                    userPath = "/profile";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                
                break;
            }
            case "cardDetails": {
                String cardNumber = request.getParameter("cardNum1") + " " + 
                        request.getParameter("cardNum2") + " " + 
                        request.getParameter("cardNum3") + " " + 
                        request.getParameter("cardNum4");
                
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date = formatter.parse(request.getParameter("doe"));
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                
                String nameOnCard = request.getParameter("nameOnCard");
                
                try {
                    db.createConnection();
                    users.updateAccountCardDetails(db.conn, cardNumber, sqlDate, nameOnCard, userSession.getUserID());
                    db.closeConnection();

                    response.setHeader("profileMessage", "Card details changed");
                    userPath = "/profile";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                
                break;
            }
            case "changeDelAddress": {
                String delAddress = request.getParameter("address1");
                if(!request.getParameter("address2").isEmpty()) {
                    delAddress = delAddress.concat("," + request.getParameter("address2"));
                }
                if(!request.getParameter("address3").isEmpty()) {
                    delAddress = delAddress.concat("," + request.getParameter("address3"));
                }
                if(!request.getParameter("address4").isEmpty()) {
                    delAddress = delAddress.concat("," + request.getParameter("address4"));
                }
                
                try {
                    db.createConnection();
                    users.updateAccountDelAddress(db.conn, delAddress, userSession.getUserID());
                    db.closeConnection();

                    response.setHeader("profileMessage", "Delivery Address changed");
                    userPath = "/profile";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                
                break;
            }
            case "changeBilAddress": {
                String bilAddress = request.getParameter("address1");
                if(!request.getParameter("address2").isEmpty()) {
                    bilAddress = bilAddress.concat("," + request.getParameter("address2"));
                }
                if(!request.getParameter("address3").isEmpty()) {
                    bilAddress = bilAddress.concat("," + request.getParameter("address3"));
                }
                if(!request.getParameter("address4").isEmpty()) {
                    bilAddress = bilAddress.concat("," + request.getParameter("address4"));
                }
                
                try {
                    db.createConnection();
                    users.updateAccountBilAddress(db.conn, bilAddress, userSession.getUserID());
                    db.closeConnection();

                    response.setHeader("profileMessage", "Billing Address changed");
                    userPath = "/profile";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            default:
                userPath = "/index";
                break;
        }

        if (userPath.equals("/index")) {
            url = "/" + userPath + ".jsp";
            response.setHeader("title", "Home");
        } else if (goTo.equals("servlet")) {
            url = "/" + userPath;
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void updateSession(HttpSession session, SubscriptionList subs) {
        UserSessionBean userSession = (UserSessionBean) session.getAttribute("userSession");
        userSession.setSubscriptions(subs);
        session.setAttribute("userSession", userSession);
    }
}
