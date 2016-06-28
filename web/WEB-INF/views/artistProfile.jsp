<%-- 
    Document   : artistProfile
    Created on : 07-Mar-2014, 08:46:02
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>

<%@page import="entities.users.SubscriptionList"%>
<%@page import="entities.artists.Band"%>
<%@page import="entities.artists.Artist"%>
<%
    Artist artist = null;
    Band band = null;
    SubscriptionList subs = null;
    boolean subbed = false;

    if (response.getHeader("type").equals("artist")) {
        artist = (Artist) request.getAttribute("profile");
    } else {
        band = (Band) request.getAttribute("profile");
    }
%>
<div class="col-md-8">
    <div class="content">
        <% if (response.getHeader("type").equals("artist")) { %>
        <div class="row">
            <div class="col-md-6">
                <% if (artist.getPicName().equals("Null")) {%>
                <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/default.png" class="img-responsive" alt="Responsive image">
                <% } else {%>
                <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/<%= artist.getPicName()%>.png" class="img-responsive" alt="Responsive image">
                <% }%>
            </div>
            <div class="col-md-3">
                <%  if (userSession != null) {
                        if (userSession.getSubscriptions() != null) {
                            subs = userSession.getSubscriptions();
                            for (int pos = 0; pos < subs.size(); pos++) {
                                if (subs.getSubscriptionAt(pos).getSubToID()
                                        == artist.getArtistID() && subs.getSubscriptionAt(pos).getType().equals("artist")) {
                                    subbed = true;
                                    break;
                                }
                            }
                        }
                        if (subbed == true) {%>
                <div class="profileButton">
                    <a href="<%=request.getContextPath()%>/unSub/artist/<%= artist.getArtistID()%>/<%= userSession.getUserID()%>" type="button" class="btn btn-default center-block">Un-Subscribe</a>
                </div>
                <% } else {%>
                <div class="profileButton">
                    <a href="<%=request.getContextPath()%>/sub/artist/<%= artist.getArtistID()%>/<%= userSession.getUserID()%>" type="button" class="btn btn-default center-block">Subscribe</a>
                </div>
                <% } }%>
                <div class="profileButton">
                    <a href="<%=request.getContextPath()%>/artistEvents/artist/<%= artist.getArtistID()%>" type="button" class="btn btn-default center-block">Upcoming Events</a>
                </div>
                <div class="profileButton">
                    <a href="<%=request.getContextPath()%>/artistMerchTypes/<%= artist.getArtistID()%>/artist" type="button" class="btn btn-default center-block">Artist Merchandise</a>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 text-center">
                <h3><%= artist.getName()%></h3>
            </div>
        </div>
        <div class="row">
            <div class="col-md-9">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Artist Info</h3>
                    </div>
                    <div class="panel-body">
                        <%= artist.getBiograhpy()%>
                    </div>
                </div>
            </div>
        </div>

        <% } else {%>
        <div class="row">
            <div class="col-md-6">
                <% if (band.getPicName().equals("Null")) {%>
                <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/default.png" class="img-responsive" alt="Responsive image">
                <% } else {%>
                <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/<%= band.getPicName()%>.png" class="img-responsive" alt="Responsive image">
                <% }%>
            </div>
            <div class="col-md-3">
                <%  if (userSession != null) {
                        if (userSession.getSubscriptions() != null) {
                            subs = userSession.getSubscriptions();
                            for (int pos = 0; pos < subs.size(); pos++) {
                                if (subs.getSubscriptionAt(pos).getSubToID()
                                        == band.getBandID() && subs.getSubscriptionAt(pos).getType().equals("band")) {
                                    subbed = true;
                                    break;
                                }
                            }
                        }
                        if (subbed == true) {%>
                <div class="profileButton">
                    <a href="<%=request.getContextPath()%>/unSub/band/<%= band.getBandID()%>/<%= userSession.getUserID()%>" type="button" class="btn btn-default center-block">Un-Subscribe</a>
                </div>
                <% } else {%>
                <div class="profileButton">
                    <a href="<%=request.getContextPath()%>/sub/band/<%= band.getBandID()%>/<%= userSession.getUserID()%>" type="button" class="btn btn-default center-block">Subscribe</a>
                </div>
                <% } }%>
                <div class="profileButton">
                    <a href="<%=request.getContextPath()%>/artistEvents/band/<%= band.getBandID()%>" type="button" class="btn btn-default center-block">Upcoming Events</a>
                </div>
                <div class="profileButton">
                    <a href="<%=request.getContextPath()%>/artistMerchTypes/<%= band.getBandID()%>/band" type="button" class="btn btn-default center-block">Artist Merchandise</a>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 text-center">
                <h3><%= band.getName()%></h3>
            </div>
        </div>
        <div class="row">
            <div class="col-md-9">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Band Info</h3>
                    </div>
                    <div class="panel-body">
                        <%= band.getBiography()%>
                    </div>
                </div>
            </div>
        </div>
        <% }%>
    </div>
</div>
