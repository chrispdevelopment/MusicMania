<%-- 
    Document   : artists
    Created on : 07-Mar-2014, 08:36:23
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>
<%@page import="entities.artists.Artist"%>
<%@page import="entities.artists.ArtistList"%>
<%
    ArtistList artistList = (ArtistList) request.getAttribute("artists");
%>
<div class="col-md-8">
    <div class="content">
        <div class="row" id="portsMargin">
            <%
                for (int pos = 0; pos < artistList.size(); pos++) {
                    Artist artist = artistList.getArtistAt(pos);

            %>
            <a href="showProfile/artist/<%= artist.getArtistID() %>">
                <div class="col-md-3" id="borderControl">
                    <div>
                        <% if (artist.getPicName().equals("Null")) { %>
                        <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/default.png" class="img-responsive" alt="Responsive image">
                        <% } else { %>
                        <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/<%= artist.getPicName() %>.png" class="img-responsive" alt="Responsive image">
                        <% } %>
                    </div>
                    <div class="imageText">
                        <%= artist.getName() %>
                    </div>
                </div>
            </a>
            <% }%>
        </div>
    </div>
</div>
