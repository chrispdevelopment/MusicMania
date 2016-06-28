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
import entities.artists.Genre;
import entities.artists.GenreList;
import entities.events.Event;
import entities.events.EventList;
import entities.events.Location;
import entities.events.LocationList;
import entities.mechandise.Merchandise;
import entities.mechandise.MerchandiseList;
import entities.mechandise.Size;
import entities.mechandise.SizeList;
import entities.mechandise.Type;
import entities.mechandise.TypeList;
import entities.news.Article;
import entities.news.ArticleList;
import entities.orders.OrderList;
import entities.users.User;
import entities.users.UserList;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Artists;
import models.DbConnection;
import models.Events;
import models.Merch;
import models.News;
import models.Orders;
import models.Users;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
@WebServlet(name = "AdminServlet", urlPatterns = {"/AdminServlet"})
public class AdminServlet extends HttpServlet {

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
        String[] urlRequest = null;

        if (request.getPathInfo() != null) {
            urlRequest = request.getPathInfo().split("/");
            this.action = urlRequest[1];
        }

        DbConnection db = new DbConnection();
        Users users = new Users();
        News news = new News();
        Merch merch = new Merch();
        Artists artists = new Artists();
        Events events = new Events();
        Orders orders = new Orders();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

        switch (this.action) {
            case "adminHome": {
                userPath = "/adminHome";
                break;
            }
            case "userManagment": {
                try {
                    db.createConnection();
                    UserList userList = users.getAllUsers(db.conn);
                    db.closeConnection();

                    request.setAttribute("users", userList);
                    userPath = "/users";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "addUser": {
                boolean duplicate = false;
                User user = new User();
                user.setUsername(request.getParameter("username"));
                user.setPassword(request.getParameter("password"));
                user.setFirstname(request.getParameter("firstname"));
                user.setSurname(request.getParameter("surname"));
                user.setPostCode(request.getParameter("postCode"));
                user.setPhone(request.getParameter("phone"));
                user.setEmail(request.getParameter("email"));
                user.setAccessLevel(request.getParameter("accessLevel"));

                String address = request.getParameter("address1");
                if (!request.getParameter("address2").isEmpty()) {
                    address = address.concat("," + request.getParameter("address2"));
                }
                if (!request.getParameter("address3").isEmpty()) {
                    address = address.concat("," + request.getParameter("address3"));
                }
                if (!request.getParameter("address4").isEmpty()) {
                    address = address.concat("," + request.getParameter("address4"));
                }
                user.setAddress(address);

                
                java.util.Date date = sdf1.parse(request.getParameter("dob"));
                java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
                user.setdOB(sqlStartDate);
                try {
                    db.createConnection();
                    UserList userList = users.getAllUsers(db.conn);
                    for (int x = 0; x < userList.size(); x++) {
                        User currentUser = userList.getUserAt(x);
                        if (currentUser.getUsername().equals(user.getUsername())) {
                            response.setHeader("adminMessage", "Username already exsists");
                            duplicate = true;
                            break;
                        }
                    }
                    if (!duplicate) {
                        users.createUser(db.conn, user);
                        response.setHeader("adminMessage", "User Added");
                    }
                    db.closeConnection();

                    userPath = "/admin/userManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/userManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "deleteUser": {
                try {
                    db.createConnection();
                    users.deleteUser(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "User Deleted");
                    userPath = "/admin/userManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/userManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "newsManagment": {
                try {
                    db.createConnection();
                    UserList userList = users.getAllUsers(db.conn);
                    ArticleList articleList = news.getAllArticles(db.conn);
                    db.closeConnection();

                    request.setAttribute("articles", articleList);
                    request.setAttribute("users", userList);
                    userPath = "/news";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "addArticle": {
                Article article = new Article();
                article.setUserID(Integer.parseInt(urlRequest[2]));
                article.setTitle(request.getParameter("title"));
                article.setContent(request.getParameter("content"));

                try {
                    db.createConnection();
                    news.createArticle(db.conn, article);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Article created");
                    userPath = "/admin/newsManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/newsManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "deleteNews": {
                try {
                    db.createConnection();
                    news.deleteArticle(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "Article Deleted");
                    userPath = "/admin/newsManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/newsManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "merchManagment": {
                int typeID = 0;
                boolean typeIDSet = false;
                if (urlRequest.length == 3) {
                    typeIDSet = true;
                }
                try {
                    db.createConnection();
                    SizeList sizeList = merch.getAllSizes(db.conn);
                    TypeList typeList = merch.getTypes(db.conn);
                    if (!typeIDSet) {
                        for (int x = 0; x < typeList.size(); x++) {
                            typeID = typeList.getTypeAt(x).getTypeID();
                            break;
                        }
                    } else {
                        typeID = Integer.parseInt(urlRequest[2]);
                    }
                    MerchandiseList merchList = merch.getMerchByType(db.conn, typeID);
                    ArtistList artistList = artists.getAllArtists(db.conn);
                    BandList bandList = artists.getAllBands(db.conn);
                    db.closeConnection();

                    request.setAttribute("sizes", sizeList);
                    request.setAttribute("types", typeList);
                    request.setAttribute("merch", merchList);
                    request.setAttribute("artists", artistList);
                    request.setAttribute("bands", bandList);
                    userPath = "/merch";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "addMerch": {
                Merchandise item = new Merchandise();

                item.setTypeID(Integer.parseInt(request.getParameter("typeID")));
                item.setArtistType(request.getParameter("artistSelect").toLowerCase());
                if (item.getArtistType().equals("artist")) {
                    item.setArtistID(Integer.parseInt(request.getParameter("artistID")));
                } else {
                    item.setArtistID(Integer.parseInt(request.getParameter("bandID")));
                }
                item.setName(request.getParameter("name"));
                item.setDescription(request.getParameter("description"));
                item.setPrice(Double.parseDouble(request.getParameter("price")));
                if (!request.getParameter("picName").isEmpty()) {
                    item.setPicName(request.getParameter("picName"));
                }
                item.setSale(request.getParameter("sale"));
                try {
                    db.createConnection();
                    merch.createMerch(db.conn, item);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Merchandise Added");
                    userPath = "/admin/merchManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/merchManagment";
                }
                break;
            }
            case "deleteMerch": {
                try {
                    db.createConnection();
                    merch.deleteMerch(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "Merchandise Deleted");
                    userPath = "/admin/merchManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/merchManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "addType": {
                Type type = new Type();
                
                type.setName(request.getParameter("name"));
                try {
                    db.createConnection();
                    merch.createType(db.conn, type);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Type Added");
                    userPath = "/admin/merchManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/merchManagment";
                }
                break;
            }
            case "editType": {
                Type type = new Type();
                
                type.setTypeID(Integer.parseInt(request.getParameter("typeID")));
                type.setName(request.getParameter("name"));
                try {
                    db.createConnection();
                    merch.updateType(db.conn, type);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Edited Type");
                    userPath = "/admin/merchManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/merchManagment";
                }
                break;
            }
            case "deleteType": {
                try {
                    db.createConnection();
                    merch.deleteType(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "Type Deleted");
                    userPath = "/admin/merchManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/merchManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "addSize": {
                Size size = new Size();
                
                size.setSize(request.getParameter("size"));
                try {
                    db.createConnection();
                    merch.createSize(db.conn, size);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Size Added");
                    userPath = "/admin/merchManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/merchManagment";
                }
                break;
            }
            case "editSize": {
                Size size = new Size();
                
                size.setSize(request.getParameter("size"));
                
                size.setSizeID(Integer.parseInt(request.getParameter("sizeID")));
                try {
                    db.createConnection();
                    merch.updateSize(db.conn, size);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Edited Size");
                    userPath = "/admin/merchManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/merchManagment";
                }
                break;
            }
            case "deleteSize": {
                try {
                    db.createConnection();
                    merch.deleteSize(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "Size Deleted");
                    userPath = "/admin/merchManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/merchManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "artistManagment": {
                String type = "artists";
                boolean typeSet = false;
                if (urlRequest.length == 3) {
                    typeSet = true;
                }
                try {
                    db.createConnection();
                    GenreList genreList = artists.getAllGenres(db.conn);
                    ArtistList artistList = null;
                    if (typeSet) {
                        type = urlRequest[2];                      
                    }
                    if(type.equals("artists")) {
                        artistList = artists.getAllArtists(db.conn);
                    }
                    BandList bandList = artists.getAllBands(db.conn);
                    db.closeConnection();

                    request.setAttribute("genres", genreList);
                    request.setAttribute("artists", artistList);
                    request.setAttribute("bands", bandList);
                    userPath = "/artists";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "addArtist": {
                Artist artist = new Artist();
                
                artist.setName(request.getParameter("name"));
                artist.setKownAs(request.getParameter("knowAs"));
                java.util.Date date = sdf1.parse(request.getParameter("start"));
                java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
                artist.setStart(sqlStartDate);
                java.sql.Date sqlEndDate = null;
                if(!request.getParameter("end").isEmpty()) {
                    java.util.Date date2 = sdf1.parse(request.getParameter("end"));
                    sqlEndDate = new java.sql.Date(date.getTime());
                }
                artist.setEnd(sqlEndDate);
                artist.setGenreID(Integer.parseInt(request.getParameter("genre")));
                artist.setMemberOfID(Integer.parseInt(request.getParameter("memberOf")));
                artist.setBiograhpy(request.getParameter("bio"));
                artist.setPicName(request.getParameter("picName"));
                
                try {
                    db.createConnection();
                    artists.createArtist(db.conn, artist);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Artist Added");
                    userPath = "/admin/artistManagment/artists";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/artistManagment";
                }
                break;
            }
            case "deleteArtist": {
                try {
                    db.createConnection();
                    artists.deleteArtist(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "Artist Deleted");
                    userPath = "/admin/artistManagment/artists";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/artistManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "addBand": {
                Band band = new Band();
                
                band.setName(request.getParameter("name"));
                java.util.Date date = sdf1.parse(request.getParameter("start"));
                java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
                band.setStart(sqlStartDate);
                java.sql.Date sqlEndDate = null;
                if(!request.getParameter("end").isEmpty()) {
                    java.util.Date date2 = sdf1.parse(request.getParameter("end"));
                    sqlEndDate = new java.sql.Date(date.getTime());
                }
                band.setEnd(sqlEndDate);
                band.setGenreID(Integer.parseInt(request.getParameter("genre")));
                band.setBiography(request.getParameter("bio"));
                band.setPicName(request.getParameter("picName"));
                try {
                    db.createConnection();
                    artists.createBand(db.conn, band);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Band Added");
                    userPath = "/admin/artistManagment/bands";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/artistManagment";
                }
                break;
            }
            case "deleteBand": {
                try {
                    db.createConnection();
                    artists.deleteBand(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "Band Deleted");
                    userPath = "/admin/artistManagment/bands";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/artistManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "addGenre": {
                Genre genre = new Genre();
                
                genre.setName(request.getParameter("name"));
                try {
                    db.createConnection();
                    artists.createGenre(db.conn, genre);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Genre Added");
                    userPath = "/admin/artistManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/artistManagment";
                }
                break;
            }
            case "editGenre": {
                Genre genre = new Genre();
                
                genre.setName(request.getParameter("name"));              
                genre.setGenreID(Integer.parseInt(request.getParameter("genreID")));

                try {
                    db.createConnection();
                    artists.updateGenre(db.conn, genre);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Edited Genre");
                    userPath = "/admin/artistManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/artistManagment";
                }
                break;
            }
            case "deleteGenre": {
                try {
                    db.createConnection();
                    artists.deleteGenre(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "Genre Deleted");
                    userPath = "/admin/artistManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/artistManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "eventsManagment": {
                try {
                    db.createConnection();
                    EventList eventList = events.getAllEvents(db.conn);
                    LocationList locationList = events.getAllLocations(db.conn);
                    db.closeConnection();

                    request.setAttribute("events", eventList);
                    request.setAttribute("locations", locationList);
                    userPath = "/events";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "addEvent": {
                Event event = new Event();
                
                event.setName(request.getParameter("name"));
                event.setLocationID(Integer.parseInt(request.getParameter("location")));
                java.util.Date date = sdf1.parse(request.getParameter("date"));
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                event.setDate(sqlDate);           
                event.setStart(java.sql.Time.valueOf(request.getParameter("start")));
                event.setEnd(java.sql.Time.valueOf(request.getParameter("end")));
                event.setPrice(Double.parseDouble(request.getParameter("price")));
                event.setDescription(request.getParameter("desc"));
                event.setEventPic(request.getParameter("picName"));
                
                try {
                    db.createConnection();
                    events.createEvent(db.conn, event);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Event Added");
                    userPath = "/admin/eventsManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/eventsManagment";
                }
                break;
            }
            case "deleteEvent": {
                try {
                    db.createConnection();
                    events.deleteEvent(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "Event Deleted");
                    userPath = "/admin/eventsManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/eventsManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "addLocation": {
                Location location = new Location();
                
                location.setName(request.getParameter("name"));              
                String address = request.getParameter("address1");
                if (!request.getParameter("address2").isEmpty()) {
                    address = address.concat("," + request.getParameter("address2"));
                }
                if (!request.getParameter("address3").isEmpty()) {
                    address = address.concat("," + request.getParameter("address3"));
                }
                if (!request.getParameter("address4").isEmpty()) {
                    address = address.concat("," + request.getParameter("address4"));
                }
                location.setAddress(address);
                location.setPostCode(request.getParameter("postCode"));
                location.setEmail(request.getParameter("email"));
                location.setPhone(request.getParameter("phone"));
                location.setUrl(request.getParameter("url"));
                location.setDescription(request.getParameter("desc"));
                
                try {
                    db.createConnection();
                    events.createLocation(db.conn, location);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Location Added");
                    userPath = "/admin/eventsManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/eventsManagment";
                }
                break;
            }
            case "deleteLocation": {
                try {
                    db.createConnection();
                    events.deleteLocation(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "Location Deleted");
                    userPath = "/admin/eventsManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/eventsManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "orderManagment": {
                String completed = "No";
                boolean completedSet = false;
                if (urlRequest.length == 3) {
                    completedSet = true;
                }
                try {
                    db.createConnection();     
                    if (completedSet) {
                        completed = urlRequest[2];                      
                    }
                    OrderList orderList = orders.getOrderByStatus(db.conn, completed);
                    MerchandiseList merchList = merch.getAllMerch(db.conn);
                    db.closeConnection();

                    request.setAttribute("orders", orderList);
                    request.setAttribute("merch", merchList);
                    userPath = "/orders";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "completeOrder": {
                try {
                    db.createConnection();
                    orders.updateOrder(db.conn, Integer.parseInt(urlRequest[2]), "Yes");
                    db.closeConnection();

                    response.setHeader("adminMessage", "Order Completed");
                    userPath = "/admin/orderManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/orderManagment";
                    goTo = "servlet";
                }
                break;
            }
            case "deleteOrder": {
                try {
                    db.createConnection();
                    orders.deleteEvent(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "Order Deleted");
                    userPath = "/admin/orderManagment";
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/admin/orderManagment";
                    goTo = "servlet";
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
            url = "/WEB-INF/views/admin" + userPath + ".jsp";
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

}
