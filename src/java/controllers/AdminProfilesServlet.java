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
import entities.artists.GenreList;
import entities.beans.UserSessionBean;
import entities.mechandise.Merchandise;
import entities.mechandise.MerchandiseList;
import entities.mechandise.Type;
import entities.mechandise.TypeList;
import entities.music.Song;
import entities.music.SongList;
import entities.news.Article;
import entities.news.Comment;
import entities.orders.OrderList;
import entities.users.Account;
import entities.users.Subscription;
import entities.users.SubscriptionList;
import entities.users.User;
import entities.users.UserList;
import java.io.IOException;
import java.sql.SQLException;
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
import models.Artists;
import models.DbConnection;
import models.Events;
import models.Merch;
import models.News;
import models.Orders;
import models.Releases;
import models.Users;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
@WebServlet(name = "AdminProfilesServlet", urlPatterns = {"/AdminProfilesServlet"})
public class AdminProfilesServlet extends HttpServlet {

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
            throws ServletException, IOException, ParseException, SQLException, ClassNotFoundException {
        String userPath = "";
        String url;
        String goTo = "form";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        String[] urlRequest = request.getPathInfo().split("/");
        this.action = urlRequest[1];

        DbConnection db = new DbConnection();
        Users users = new Users();
        Artists artists = new Artists();
        Orders orders = new Orders();
        Merch merch = new Merch();
        News news = new News();
        Events events = new Events();
        Releases releases = new Releases();

        UserSessionBean userSession = (UserSessionBean) request.getSession().getAttribute("userSession");

        switch (this.action) {
            case "editUser": {
                ArtistList artistList = new ArtistList();
                BandList bandList = new BandList();

                try {
                    db.createConnection();
                    User user = users.getUser(db.conn, Integer.parseInt(urlRequest[2]));
                    Account account = users.getAccount(db.conn, Integer.parseInt(urlRequest[2]));
                    SubscriptionList subs = users.getSubscriptions(db.conn, Integer.parseInt(urlRequest[2]));

                    for (int x = 0; x < subs.size(); x++) {
                        Subscription sub = subs.getSubscriptionAt(x);
                        if (sub.getType().equals("artist")) {
                            artistList.addArtist(artists.getArtist(db.conn, sub.getSubToID()));
                        } else {
                            bandList.addBand(artists.getBand(db.conn, sub.getSubToID()));
                        }
                    }

                    OrderList orderList = orders.getUserOrders(db.conn, Integer.parseInt(urlRequest[2]));
                    MerchandiseList merchList = merch.getAllMerch(db.conn);
                    db.closeConnection();

                    request.setAttribute("user", user);
                    request.setAttribute("account", account);
                    request.setAttribute("artists", artistList);
                    request.setAttribute("bands", bandList);
                    request.setAttribute("orders", orderList);
                    request.setAttribute("merch", merchList);
                    userPath = "/userProfile";
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
            }
            case "changeEmail": {
                String newEmail = request.getParameter("email");

                try {
                    db.createConnection();
                    users.updateUserEmail(db.conn, newEmail, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("profileMessage", "Email Changed");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                }
                break;
            }
            case "changeAccess": {
                String accessLevel = request.getParameter("accessLevel");

                try {
                    db.createConnection();
                    users.updateUserAccess(db.conn, accessLevel, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("profileMessage", "Access Level Changed");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                }
                break;
            }
            case "changeInfo": {
                String firstname = request.getParameter("firstname");
                String surname = request.getParameter("surname");

                Date date = formatter.parse(request.getParameter("dob"));
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                try {
                    db.createConnection();
                    users.updateUserInfo(db.conn, firstname, surname, sqlDate, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("profileMessage", "Basic information changed");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                }
                break;
            }
            case "changeAddInfo": {
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

                String postCode = request.getParameter("postCode");
                String phone = request.getParameter("phone");

                try {
                    db.createConnection();
                    users.updateUserAddInfo(db.conn, address, postCode, phone, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("profileMessage", "Additional information changed");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                }
                break;
            }
            //This code would go off to credit card auth if running live and would 
            //use the stored card details and the CVC to charge the card before adding amount.
            case "addMoney": {
                double amount = Double.parseDouble(request.getParameter("amount"));

                try {
                    db.createConnection();
                    User user = users.getUser(db.conn, Integer.parseInt(urlRequest[2]));
                    double totalBalance = user.getAccount().getBalance() + amount;
                    users.updateAccountBalance(db.conn, totalBalance, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("profileMessage", "Money added to you're account");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                }

                break;
            }
            case "changeDelAddress": {
                String delAddress = request.getParameter("address1");
                if (!request.getParameter("address2").isEmpty()) {
                    delAddress = delAddress.concat("," + request.getParameter("address2"));
                }
                if (!request.getParameter("address3").isEmpty()) {
                    delAddress = delAddress.concat("," + request.getParameter("address3"));
                }
                if (!request.getParameter("address4").isEmpty()) {
                    delAddress = delAddress.concat("," + request.getParameter("address4"));
                }

                try {
                    db.createConnection();
                    users.updateAccountDelAddress(db.conn, delAddress, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("profileMessage", "Delivery Address changed");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                }

                break;
            }
            case "changeBilAddress": {
                String bilAddress = request.getParameter("address1");
                if (!request.getParameter("address2").isEmpty()) {
                    bilAddress = bilAddress.concat("," + request.getParameter("address2"));
                }
                if (!request.getParameter("address3").isEmpty()) {
                    bilAddress = bilAddress.concat("," + request.getParameter("address3"));
                }
                if (!request.getParameter("address4").isEmpty()) {
                    bilAddress = bilAddress.concat("," + request.getParameter("address4"));
                }

                try {
                    db.createConnection();
                    users.updateAccountBilAddress(db.conn, bilAddress, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("profileMessage", "Billing Address changed");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/adminProfiles/editUser/" + urlRequest[2];
                    goTo = "servlet";
                }
                break;
            }
            case "editNews": {
                Article article;
                UserList userList;

                try {
                    db.createConnection();
                    article = news.getArticle(db.conn, Integer.parseInt(urlRequest[2]));
                    userList = users.getAllUsers(db.conn);
                    db.closeConnection();

                    request.setAttribute("article", article);
                    request.setAttribute("users", userList);
                    userPath = "/articleProfile";
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
            }
            case "editArticle": {
                int articleID = Integer.parseInt(request.getParameter("articleID"));
                String articleTitle = request.getParameter("title");
                String content = request.getParameter("content");

                Article article = new Article();
                article.setArticleID(articleID);
                article.setTitle(articleTitle);
                article.setContent(content);

                try {
                    db.createConnection();
                    news.updateArticle(db.conn, article);
                    db.closeConnection();

                    response.setHeader("adminMessage", "News Article changed");
                    userPath = "/adminProfiles/editNews/" + articleID;
                    goTo = "servlet";
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
            }
            case "addComment": {
                Comment newComment = new Comment();

                newComment.setArticleID(Integer.parseInt(request.getParameter("articleID")));
                newComment.setUserID(Integer.parseInt(request.getParameter("userID")));
                newComment.setContent(request.getParameter("comment"));
                try {
                    db.createConnection();
                    news.createComment(db.conn, newComment);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Comment added");
                    userPath = "/adminProfiles/editNews/" + request.getParameter("articleID");
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "editComment": {
                Comment updatedComment = new Comment();

                updatedComment.setCommentID(Integer.parseInt(request.getParameter("commentID")));
                updatedComment.setContent(request.getParameter("comment"));
                try {
                    db.createConnection();
                    news.updateComment(db.conn, updatedComment);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Comment Edited");
                    userPath = "/adminProfiles/editNews/" + urlRequest[2];
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "deleteComment": {
                try {
                    db.createConnection();
                    news.deleteComment(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "Comment added");
                    userPath = "/adminProfiles/editNews/" + urlRequest[3];
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "editMerch": {
                Merchandise item;
                Artist artist = null;
                Band band = null;
                Type type;

                try {
                    db.createConnection();
                    item = merch.getMerch(db.conn, Integer.parseInt(urlRequest[2]));
                    if (item.getArtistType().equals("artist")) {
                        artist = artists.getArtist(db.conn, item.getArtistID());
                    } else {
                        band = artists.getBand(db.conn, item.getArtistID());
                    }
                    type = merch.getType(db.conn, item.getTypeID());
                    ArtistList artistList = artists.getAllArtists(db.conn);
                    BandList bandList = artists.getAllBands(db.conn);
                    TypeList typeList = merch.getTypes(db.conn);
                    db.closeConnection();

                    request.setAttribute("item", item);
                    if (item.getArtistType().equals("artist")) {
                        request.setAttribute("artist", artist);
                    } else {
                        request.setAttribute("band", band);
                    }
                    request.setAttribute("type", type);
                    request.setAttribute("artists", artistList);
                    request.setAttribute("bands", bandList);
                    request.setAttribute("types", typeList);
                    userPath = "/merchProfile";
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
            }
            case "editMerchDetails": {
                Merchandise item = new Merchandise();

                item.setMerchID(Integer.parseInt(urlRequest[2]));
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
                    merch.updateMerch(db.conn, item);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Merchandise Edited");
                    userPath = "/adminProfiles/editMerch/" + urlRequest[2];
                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "editArtist": {
                Band band;
                Artist artist;
                try {
                    db.createConnection();
                    SongList songList = releases.getSongs(db.conn);
                    BandList bandList = artists.getAllBands(db.conn);
                    GenreList genreList = artists.getAllGenres(db.conn);

                    request.setAttribute("songs", songList);
                    request.setAttribute("bands", bandList);
                    request.setAttribute("genres", genreList);

                    if (urlRequest[3].equals("artist")) {
                        artist = artists.getArtist(db.conn, Integer.parseInt(urlRequest[2]));
                        band = artists.getBand(db.conn, artist.getMemberOfID());
                        request.setAttribute("artist", artist);
                        request.setAttribute("band", band);
                        response.setHeader("type", "artist");
                    } else {
                        band = artists.getBand(db.conn, Integer.parseInt(urlRequest[2]));
                        request.setAttribute("band", band);
                        response.setHeader("type", "band");
                    }
                    db.closeConnection();

                    userPath = "/artistProfile";
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
            }
            case "editArtistDetails": {
                Artist artist = new Artist();
                Band band = new Band();
                if (urlRequest[2].equals("artist")) {
                    artist.setArtistID(Integer.parseInt(urlRequest[3]));
                    artist.setName(request.getParameter("name"));
                    artist.setKownAs(request.getParameter("knowAs"));
                    Date startDate = formatter.parse(request.getParameter("start"));
                    java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
                    artist.setStart(sqlStartDate);
                    if (!request.getParameter("end").isEmpty()) {
                        Date endDate = formatter.parse(request.getParameter("end"));
                        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
                        artist.setEnd(sqlEndDate);
                    }
                    artist.setGenreID(Integer.parseInt(request.getParameter("genre")));
                    artist.setMemberOfID(Integer.parseInt(request.getParameter("memberOf")));
                    artist.setBiograhpy(request.getParameter("bio"));
                    artist.setPicName(request.getParameter("picName"));
                } else {
                    band.setBandID(Integer.parseInt(urlRequest[3]));
                    band.setName(request.getParameter("name"));
                    Date startDate = formatter.parse(request.getParameter("start"));
                    java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
                    band.setStart(sqlStartDate);
                    if (request.getParameter("end") != null) {
                        Date endDate = formatter.parse(request.getParameter("end"));
                        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
                        band.setEnd(sqlEndDate);
                    }
                    band.setGenreID(Integer.parseInt(request.getParameter("genre")));
                    band.setBiography(request.getParameter("bio"));
                    band.setPicName(request.getParameter("picName"));
                }
                try {
                    db.createConnection();
                    if (urlRequest[2].equals("artist")) {
                        artists.updateArtist(db.conn, artist);
                        userPath = "/adminProfiles/editArtist/" + urlRequest[3] + "/" + urlRequest[2];
                    } else {
                        artists.updateBand(db.conn, band);
                        userPath = "/adminProfiles/editArtist/" + urlRequest[3] + "/" + urlRequest[2];
                    }
                    db.closeConnection();

                    response.setHeader("adminMessage", "Artist Edited");

                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "addSong": {
                Song song = new Song();

                song.setName(request.getParameter("name"));
                Date date = formatter.parse(request.getParameter("date"));
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                song.setReleaseDate(sqlDate);
                song.setSongLink(request.getParameter("songLink"));

                try {
                    db.createConnection();
                    if (urlRequest[3].equals("artist")) {
                        Artist artist = artists.getArtist(db.conn, Integer.parseInt(urlRequest[2]));
                        song.setCreatedByID(artist.getArtistID());
                        song.setCreatedByType("artist");
                        userPath = "/adminProfiles/editArtist/" + urlRequest[2] + "/artist";
                    } else {
                        Band band = artists.getBand(db.conn, Integer.parseInt(urlRequest[2]));
                        song.setCreatedByID(band.getBandID());
                        song.setCreatedByType("band");
                        userPath = "/adminProfiles/editArtist/" + urlRequest[2] + "/band";
                    }
                    releases.createSong(db.conn, song);
                    db.closeConnection();

                    response.setHeader("adminMessage", "Song Created");

                    goTo = "servlet";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setHeader("dbError", "Unable to connect to the database");
                    userPath = "/index";
                }
                break;
            }
            case "deleteSong": {
                try {
                    db.createConnection();
                    releases.deleteSong(db.conn, Integer.parseInt(urlRequest[2]));
                    db.closeConnection();

                    response.setHeader("adminMessage", "Song Deleted");
                    userPath = "/adminProfiles/editArtist/" + urlRequest[3] + "/" + urlRequest[4];
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
            url = "/WEB-INF/views/admin/profiles" + userPath + ".jsp";
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
        } catch (SQLException ex) {
            Logger.getLogger(AdminProfilesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminProfilesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(AdminProfilesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminProfilesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
