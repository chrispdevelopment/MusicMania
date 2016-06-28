<%-- 
    Document   : eventProfile
    Created on : 09-Mar-2014, 15:59:19
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>
<%@page import="entities.artists.Band"%>
<%@page import="entities.artists.Artist"%>
<%@page import="entities.events.Location"%>
<%@page import="entities.artists.BandList"%>
<%@page import="entities.artists.ArtistList"%>
<%@page import="entities.events.Event"%>
<%
    Event event = (Event) request.getAttribute("event");
    ArtistList artists = (ArtistList) request.getAttribute("artists");
    BandList bands = (BandList) request.getAttribute("bands");
    Location location = (Location) request.getAttribute("location");
%>
<script>
    var geocoder;
    var map;

    function initialize() {
        geocoder = new google.maps.Geocoder();
        var latlng = new google.maps.LatLng(-34.397, 150.644);
        var mapOptions = {
            zoom: 8,
            center: latlng
        }
        map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
        codeAddress();
    }

    function codeAddress() {
        var address = '<%= location.getPostCode()%>';
        geocoder.geocode({'address': address}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                map.setCenter(results[0].geometry.location);
                var marker = new google.maps.Marker({
                    map: map,
                    position: results[0].geometry.location,
                    title: '<%= location.getName()%>'
                });
            } else {
                alert("Geocode was not successful for the following reason: " + status);
            }
        });
    }

    google.maps.event.addDomListener(window, 'load', initialize);

</script>

<div class="col-md-8">
    <div class="content">
        <div class="row" onload="codeAddress()">
            <div class="col-md-6" id="map-canvas"></div>
            <div class="col-md-5">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><%= event.getName()%></h3>
                    </div>
                    <div class="panel-body">
                        <table >
                            <tr>
                                <td>Location:</td>
                                <td><%= location.getName()%></td>     
                            </tr>
                            <tr>
                                <td>Date:</td>
                                <td><%= event.getDate()%></td>     
                            </tr>
                            <tr>
                                <td>Start:</td>
                                <td><%= event.getStart()%></td>     
                            </tr>
                            <tr>
                                <td>End:</td>
                                <td><%= event.getEnd()%></td>     
                            </tr>
                            <tr>
                                <td>Description:</td>
                                <td><%= event.getDescription()%></td>     
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="row" id="lineup">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Lineup</h3>
                    </div>
                    <div class="panel-body">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Artists</h3>
                            </div>
                            <div class="panel-body">
                                <%
                                    for (int pos = 0; pos < artists.size(); pos++) {
                                        Artist artist = artists.getArtistAt(pos);

                                %>
                                <a href="showProfile/artist/<%= artist.getArtistID()%>">
                                    <div class="col-md-3" id="borderControl">
                                        <div>
                                            <% if (artist.getPicName().equals("Null")) {%>
                                            <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/default.png" class="img-responsive" alt="Responsive image">
                                            <% } else {%>
                                            <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/<%= artist.getPicName()%>.png" class="img-responsive" alt="Responsive image">
                                            <% }%>
                                        </div>
                                        <div class="imageText">
                                            <%= artist.getName()%>
                                        </div>
                                    </div>
                                </a>
                                <% }%>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Bands</h3>
                                </div>
                                <div class="panel-body">
                                    <%
                                        for (int pos = 0; pos < bands.size(); pos++) {
                                            Band band = bands.getBandAt(pos);

                                    %>
                                    <a href="showProfile/band/<%= band.getBandID()%>">
                                        <div class="col-md-3" id="borderControl">
                                            <div>
                                                <% if (band.getPicName().equals("Null")) {%>
                                                <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/default.png" class="img-responsive" alt="Responsive image">
                                                <% } else {%>
                                                <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/<%= band.getPicName()%>.png" class="img-responsive" alt="Responsive image">
                                                <% }%>
                                            </div>
                                            <div class="imageText">
                                                <%= band.getName()%>
                                            </div>
                                        </div>
                                    </a>
                                    <% }%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>