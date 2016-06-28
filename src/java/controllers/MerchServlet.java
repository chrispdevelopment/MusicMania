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

import entities.mechandise.Merchandise;
import entities.mechandise.MerchandiseList;
import entities.mechandise.SizeList;
import entities.mechandise.TypeList;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.DbConnection;
import models.Merch;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
@WebServlet(name = "MerchServlet", urlPatterns = {"/MerchServlet"})
public class MerchServlet extends HttpServlet {

    protected String title = null;
    protected String action = null;

    public void init(ServletConfig servletConfig) throws ServletException {
        this.title = servletConfig.getInitParameter("title");
        this.action = servletConfig.getInitParameter("action");
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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

        DbConnection db = new DbConnection();
        Merch merchs = new Merch();
        switch (this.action) {
            case "saleTypes": {
                TypeList types;
                try {
                    db.createConnection();
                    types = merchs.getSaleTypes(db.conn);
                    db.closeConnection();

                    request.setAttribute("types", types);
                    userPath = "/merch";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "artistTypes": {
                TypeList types;
                String[] urlRequest = request.getPathInfo().split("/");
                try {
                    db.createConnection();
                    types = merchs.getArtistTypes(db.conn, Integer.parseInt(urlRequest[1]), urlRequest[2]);
                    db.closeConnection();

                    request.setAttribute("types", types);
                    response.setHeader("artistID", urlRequest[1]);
                    response.setHeader("artistType", urlRequest[2]);
                    userPath = "/merch";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "saleItems": {
                MerchandiseList merch;
                String[] urlRequest = request.getPathInfo().split("/");
                try {
                    db.createConnection();
                    merch = merchs.getMerchByType(db.conn, Integer.parseInt(urlRequest[1]));
                    db.closeConnection();

                    request.setAttribute("merch", merch);
                    userPath = "/merch";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "artistItems": {
                MerchandiseList merch;
                String[] urlRequest = request.getPathInfo().split("/");
                try {
                    db.createConnection();
                    merch = merchs.getArtistMerch(db.conn, Integer.parseInt(urlRequest[1]), Integer.parseInt(urlRequest[2]), urlRequest[3]);
                    db.closeConnection();

                    request.setAttribute("merch", merch);
                    userPath = "/merch";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "itemDetails": {
                Merchandise merch;
                SizeList sizes;
                String[] urlRequest = request.getPathInfo().split("/");
                try {
                    db.createConnection();
                    merch = merchs.getMerch(db.conn, Integer.parseInt(urlRequest[1]));
                    sizes = merchs.getAllSizes(db.conn);
                    db.closeConnection();

                    request.setAttribute("item", merch);
                    request.setAttribute("sizes", sizes);
                    userPath = "/merchDetails";
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
        } else {
            url = "/WEB-INF/views" + userPath + ".jsp";
            response.setHeader("title", this.title);
            response.setHeader("action", this.action);
        }

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
