<%-- 
    Document   : sales
    Created on : 09-Mar-2014, 16:05:09
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>

<%@page import="entities.mechandise.TypeList"%>
<%@page import="entities.artists.BandList"%>
<%@page import="entities.artists.ArtistList"%>
<%@page import="entities.mechandise.Type"%>
<%@page import="entities.artists.Band"%>
<%@page import="entities.artists.Artist"%>
<%@page import="entities.mechandise.SizeList"%>
<%@page import="entities.mechandise.Merchandise"%>
<%
    Merchandise item = (Merchandise) request.getAttribute("item");
    Artist artist = null;
    Band band = null;
    if (item.getArtistType().equals("artist")) {
        artist = (Artist) request.getAttribute("artist");
    } else {
        band = (Band) request.getAttribute("band");
    }
    Type type = (Type) request.getAttribute("type");
    ArtistList artists = (ArtistList) request.getAttribute("artists");
    BandList bands = (BandList) request.getAttribute("bands");
    TypeList types = (TypeList) request.getAttribute("types");
%>

<div class="col-md-8">
    <div class="content">
        <div class="row">
            <div class="col-md-6">
                <% if (item.getPicName().equals("Null")) {%>
                <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/default.png" class="img-responsive" alt="Responsive image">
                <% } else {%>
                <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/<%= item.getPicName()%>.png" class="img-responsive" alt="Responsive image">
                <% }%>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 text-center">
                <h3><%= item.getName()%></h3>
            </div>
        </div>
        <div class="row">
            <div class="col-md-9">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Item Info</h3>
                    </div>
                    <div class="panel-body">
                        <table class="profileTable">
                            <tr>
                                <td>Description:</td>
                                <td><%= item.getDescription()%></td>
                            </tr>
                            <tr>
                                <% if (artist != null) {%>
                                <td>Artist Name</td>
                                <td><%= artist.getName()%></td>
                                <% } else {%>
                                <td>Band Name</td>
                                <td><%= band.getName()%></td>
                                <% }%>
                            </tr>
                            <tr>
                                <td>Price</td>
                                <td><%= item.getPrice()%></td>
                            </tr>
                            <tr>
                                <td>Type</td>
                                <td><%= type.getName()%></td>
                            </tr>
                            <tr>
                                <td>On Sale</td>
                                <td><%= item.getSale()%></td>
                            </tr>
                        </table>
                        <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editModal">Change Details</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Edit Details</h4>
            </div>
            <div class="modal-body">
                <form id="editForm" method="POST" action="<%=request.getContextPath()%>/adminProfiles/editMerchDetails/<%= item.getMerchID()%>" class="form-horizontal">
                    <div class="form-group">
                        <label for="Name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="name" name="name" value="<%= item.getName()%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="Name" class="col-sm-3 control-label">Artist/Band</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="artistSelect" name="artistSelect">
                                <% if (item.getArtistType().equals("artist")) { %>
                                <option selected>Artist</option>
                                <option>Band</option>
                                <% } else { %>
                                <option>Artist</option>
                                <option selected>Band</option>
                                <% }%>
                            </select>
                            <input type="hidden" value="<%= item.getArtistType()%>" id="artistType">
                        </div>                 
                    </div>
                    <div class="form-group" id="artist">
                        <label for="artistID" class="col-sm-3 control-label">Artist Name</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="artistID">
                                <% for (int x = 0; x < artists.size(); x++) {
                                        if (item.getArtistID() == artists.getArtistAt(x).getArtistID()) {
                                %>       
                                <option selected value="<%= artists.getArtistAt(x).getArtistID()%>"><%= artists.getArtistAt(x).getName()%></option>
                                <% } else {%>
                                <option value="<%= artists.getArtistAt(x).getArtistID()%>"><%= artists.getArtistAt(x).getName()%></option>
                                <% }
                                    } %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" id="band">
                        <label for ="bandID" class="col-sm-3 control-label">Band Name</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="bandID">
                                <% for (int x = 0; x < bands.size(); x++) {
                                        if (item.getArtistID() == bands.getBandAt(x).getBandID()) {
                                %>
                                <option selected value="<%= bands.getBandAt(x).getBandID()%>"><%= bands.getBandAt(x).getName()%></option>
                                <% } else {%>
                                <option value="<%= bands.getBandAt(x).getBandID()%>"><%= bands.getBandAt(x).getName()%></option>
                                <% }
                                    }%>
                            </select>
                        </div>                 
                    </div>
                    <div class="form-group">
                        <label for="price" class="col-sm-3 control-label">Price</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="price" name="price" value="<%= item.getPrice() %>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="type" class="col-sm-3 control-label">Type</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="typeID">
                                <% for (int x = 0; x < types.size(); x++) { 
                                    if (item.getTypeID() == types.getTypeAt(x).getTypeID()) {
                                %>
                                <option selected value="<%= types.getTypeAt(x).getTypeID() %>"><%= types.getTypeAt(x).getName() %></option>
                                <% } else { %>
                                <option value="<%= types.getTypeAt(x).getTypeID() %>"><%= types.getTypeAt(x).getName() %></option>
                                <% } } %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sale" class="col-sm-3 control-label">Sale</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="sale">
                                <% if (item.getSale().equals("True")) { %>
                                <option selected>True</option>
                                <option>False</option>
                                <% } else { %>
                                <option>True</option>
                                <option selected>False</option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description" class="col-sm-3 control-label">Description</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" rows="4" name="description"><%= item.getDescription()%></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="picName" class="col-sm-3 control-label">Picture Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="picName" name="picName" value="<%= item.getPicName()%>">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="editSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>

<script>
    function showArtist() {
        $('#artist').show();
    }

    function showBand() {
        $("#band").show();
    }
</script>