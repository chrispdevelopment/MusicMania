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

import entities.artists.Artist;
import entities.artists.ArtistList;
import entities.artists.Band;
import entities.artists.BandList;
import entities.events.Event;
import entities.events.EventLineup;
import entities.events.EventLineupList;
import entities.events.EventList;
import entities.events.Location;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Artists;
import models.DbConnection;
import models.Events;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
@WebServlet(name = "EventsServlet", urlPatterns = {"/EventsServlet"})
public class EventsServlet extends HttpServlet {

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
        DbConnection db = new DbConnection();
        Events events = new Events();
        Artists artists = new Artists();
        EventList eventList;
        switch (this.action) {
            case "allEvents":
                try {
                    db.createConnection();
                    eventList = events.getAllLatestEvents(db.conn);
                    db.closeConnection();

                    request.setAttribute("events", eventList);
                    userPath = "/events";

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            case "artistEvents":
                {
                    String[] urlRequest = request.getPathInfo().split("/");
                    try {
                        db.createConnection();
                        eventList = events.getArtistEvents(db.conn, Integer.parseInt(urlRequest[2]));
                        if(urlRequest[1].equals("artist")) {
                            Artist artist = artists.getArtist(db.conn, Integer.parseInt(urlRequest[2]));
                            response.setHeader("artistName", artist.getName());
                        } else {
                            Band band = artists.getBand(db.conn, Integer.parseInt(urlRequest[2]));
                            response.setHeader("artistName", band.getName());
                        }
                        db.closeConnection();

                        request.setAttribute("events", eventList);            
                        userPath = "/artistEvents";

                    } catch (Exception e) {
                        e.printStackTrace();
                        response.setHeader("dbError", "Unable to connect to the database");
                        userPath = "/index";
                    }
                    break;
                }
            case "eventProfile":
                {
                    String[] urlRequest = request.getPathInfo().split("/");
                    ArtistList artistList = new ArtistList();
                    BandList bandList = new BandList();
                    Event event;
                    Location location;
                    try {
                        db.createConnection();
                        event = events.getEventWithLineup(db.conn, Integer.parseInt(urlRequest[1]));
                        EventLineupList eventLineupList = event.getEventLineup();
                        for(int x = 0; x < eventLineupList.size(); x++) {
                            EventLineup eventLineup = eventLineupList.getEventLineupAt(x);
                            
                            if(eventLineup.getActType().equals("artist")) {
                                artistList.addArtist(artists.getArtist(db.conn, eventLineup.getActID()));
                            }
                            else {
                                bandList.addBand(artists.getBand(db.conn, eventLineup.getActID()));
                            }
                        }
                        location = events.getEventLocation(db.conn, event.getLocationID());
                        db.closeConnection();
                        
                        request.setAttribute("event", event);
                        request.setAttribute("location", location);
                        request.setAttribute("artists", artistList);
                        request.setAttribute("bands", bandList);
                        userPath = "/eventProfile";
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
