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
import entities.mechandise.MerchandiseList;
import entities.orders.Order;
import entities.orders.OrderDetail;
import entities.orders.OrderDetailList;
import entities.users.Account;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.DbConnection;
import models.Merch;
import models.Orders;
import models.Users;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
@WebServlet(name = "OrdersServlet", urlPatterns = {"/OrdersServlet"})
public class OrdersServlet extends HttpServlet {

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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = "";
        String url;
        String goTo = "page";

        HttpSession session = request.getSession(false);
        DbConnection db = new DbConnection();
        Merch merchs = new Merch();
        Orders orders = new Orders();
        Users users = new Users();
        OrderDetailList orderDetails;

        MerchandiseList merch = new MerchandiseList();

        try {
            db.createConnection();
            merch = merchs.getAllMerch(db.conn);
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            response.setHeader("dbError", "Unable to connect to the database");
            userPath = "/index";
        }
        switch (this.action) {
            case "addItem": {
                int merchID = Integer.parseInt(request.getParameter("merchID"));
                if (request.getParameter("quantity").equals("")) {
                    response.setHeader("cartMessage", "Please enter a quantity");
                    userPath = "/itemDetails/" + merchID;
                    goTo = "servlet";
                } else {
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    String size = request.getParameter("size");
                    double totalCost = Double.parseDouble(request.getParameter("cost"));

                    OrderDetail item = new OrderDetail(merchID, quantity, size, totalCost);

                    if (session.getAttribute("cart") != null) {
                        orderDetails = (OrderDetailList) session.getAttribute("cart");
                        boolean exsists = false;

                        for (int x = 0; x < orderDetails.size(); x++) {
                            if (merchID == orderDetails.getOrderDetailAt(x).getMerchID()) {
                                response.setHeader("cartMessage", "You already have this item in you'r cart");
                                exsists = true;
                                userPath = "/itemDetails/" + merchID;
                                goTo = "servlet";
                                break;
                            }
                        }

                        if (exsists == false) {
                            orderDetails.addOrderDetail(item);

                            session.setAttribute("cart", orderDetails);
                            response.setHeader("cartMessage", "Item added to cart");
                            userPath = "/itemDetails/" + merchID;
                            goTo = "servlet";
                        }
                    } else {
                        orderDetails = new OrderDetailList();
                        orderDetails.addOrderDetail(item);

                        session.setAttribute("cart", orderDetails);
                        response.setHeader("cartMessage", "Item added to cart");
                        userPath = "/itemDetails/" + merchID;
                        goTo = "servlet";
                    }
                }
                break;
            }
            case "removeItem": {
                String[] urlRequest = request.getPathInfo().split("/");
                orderDetails = (OrderDetailList) session.getAttribute("cart");
                int merchID = Integer.parseInt(urlRequest[1]);
                for (int x = 0; x < orderDetails.size(); x++) {
                    if (merchID == orderDetails.getOrderDetailAt(x).getMerchID()) {
                        orderDetails.removeOrderDetailAt(x);

                        session.setAttribute("cart", orderDetails);
                        request.setAttribute("merch", merch);
                        response.setHeader("cartMessage", "Item removed");
                        userPath = "/cart";
                        break;
                    }
                }
                break;
            }
            case "cart": {
                request.setAttribute("merch", merch);
                userPath = "/cart";
                break;
            }
            case "order": {
                orderDetails = (OrderDetailList) session.getAttribute("cart");
                UserSessionBean userSession = (UserSessionBean) session.getAttribute("userSession");
                String[] urlRequest = request.getPathInfo().split("/");
                double totalCost = Double.parseDouble(urlRequest[1]);

                Order order = new Order();
                order.setUserID(userSession.getUserID());
                order.setTotalCost(totalCost);

                try {
                    db.createConnection();
                    Account account = users.getAccount(db.conn, userSession.getUserID());

                    if (order.getTotalCost() < account.getBalance()) {
                        int genKey = orders.createOrder(db.conn, order);
                        for (int x = 0; x < orderDetails.size(); x++) {
                            OrderDetail orderDetail = orderDetails.getOrderDetailAt(x);
                            orderDetail.setOrderID(genKey);
                            orders.createOrderDetail(db.conn, orderDetail);
                        }

                        account.setBalance(account.getBalance() - totalCost);
                        users.updateAccountBalance(db.conn, account.getBalance(), account.getUserID());

                        userSession.setBalance(account.getBalance());
                        session.setAttribute("userSession", userSession);
                        session.removeAttribute("cart");

                        response.setHeader("cartMessage", "Order Completed");
                        userPath = "/index";
                    } else {
                        request.setAttribute("merch", merch);
                        response.setHeader("cartMessage", "You don't have enough money for that order.");
                        userPath = "/cart";
                    }
                    db.closeConnection();
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
            url = userPath + ".jsp";
            response.setHeader("title", "Home");
        } else if (goTo.equals("servlet")) {
            url = userPath;
            response.setHeader("title", this.title);
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
}
