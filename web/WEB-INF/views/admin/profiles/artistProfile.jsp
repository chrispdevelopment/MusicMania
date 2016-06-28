<%-- 
    Document   : artistProfile
    Created on : 07-Mar-2014, 08:46:02
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>

<%@page import="entities.artists.GenreList"%>
<%@page import="entities.artists.BandList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entities.music.Song"%>
<%@page import="entities.music.SongList"%>
<%@page import="entities.users.SubscriptionList"%>
<%@page import="entities.artists.Band"%>
<%@page import="entities.artists.Artist"%>
<%
    Artist artist = null;
    Band band = null;
    SongList songs = (SongList) request.getAttribute("songs");
    BandList bands = (BandList) request.getAttribute("bands");
    GenreList genres = (GenreList) request.getAttribute("genres");
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    int artistID = 0;

    if (response.getHeader("type").equals("artist")) {
        artist = (Artist) request.getAttribute("artist");
        band = (Band) request.getAttribute("band");
        artistID = artist.getArtistID();
    } else {
        band = (Band) request.getAttribute("band");
        artistID = band.getBandID();
    }
%>
<input type="hidden" value="artistProfile" id="page">
<input type="hidden" value="<%=request.getContextPath()%>" id="context">
<div class="col-md-8">
    <div class="content">
        <div class="panel-group" id="accordion">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                            <% if (response.getHeader("type").equals("artist")) { %>
                            Artist Details
                            <% } else { %>
                            Band Details
                            <% } %>
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse in">
                    <div class="panel-body">
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
                                <div class="profileButton">
                                    <button type="button" class="btn btn-default center-block" data-toggle="modal" data-target="#editModal">Edit Details</button>
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
                                        <table class="profileTable">
                                            <tr>
                                                <td>Known As:</td>
                                                <td><%= artist.getKownAs()%></td>
                                            </tr>
                                            <% if (band != null) {%>
                                            <tr>
                                                <td>Member Of:</td>
                                                <td><%= band.getName()%></td>
                                            </tr>
                                            <% } %>
                                            <tr>
                                                <td>Started:</td>
                                                <% String startedDate = formatter.format(artist.getStart());%>
                                                <td><%= startedDate%></td>
                                            </tr>
                                            <% if (artist.getEnd() != null) { %>
                                            <tr>
                                                <td>Ended:</td>
                                                <% String endDate = formatter.format(artist.getEnd());%>
                                                <td><%= endDate%></td>
                                            </tr>
                                            <% }%>
                                            <tr>
                                                <td>Biography:</td>
                                                <td><%= artist.getBiograhpy()%></td>
                                            </tr>
                                        </table>
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
                                <div class="profileButton">
                                    <button type="button" class="btn btn-default center-block" data-toggle="modal" data-target="#editModal">Edit Details</button>
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
                                        <table class="profileTable">
                                            <tr>
                                                <td>Started:</td>
                                                <% String startedDate = formatter.format(band.getStart());%>
                                                <td><%= startedDate%></td>
                                            </tr>
                                            <% if (band.getEnd() != null) { %>
                                            <tr>
                                                <td>Ended:</td>
                                                <% String endDate = formatter.format(band.getEnd());%>
                                                <td><%= endDate%></td>
                                            </tr>
                                            <% }%>
                                            <tr>
                                                <td>Biography:</td>
                                                <td><%= band.getBiography()%></td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <% }%>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                            Releases
                        </a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-5">
                                <table class="table">                      
                                    <tr>
                                        <th></th>
                                        <th>Name</th>
                                        <th>Release Date</th>
                                    </tr>
                                    <%
                                        for (int x = 0; x < songs.size(); x++) {
                                            Song song = songs.getSongAt(x);
                                            if (artistID == song.getCreatedByID() && response.getHeader("type").equals(song.getCreatedByType())) {
                                    %>
                                    <tr>
                                        <td><input type="radio" name="selectSong" id="select<%= song.getSongID()%>" value="<%= song.getSongID()%>" ></td>
                                        <td><%= song.getName()%></td>
                                        <% String releaseDate = formatter.format(song.getReleaseDate());%>
                                        <td><%= releaseDate%></td>
                                    </tr>
                                    <% }
                                        } %>
                                </table>
                                <button class="btn btn-primary" data-toggle="modal" data-target="#addModal">Add Song</button>
                                <a href="#" class="btn btn-primary" role="button" id="delete">Delete Song</a>
                                <input type="hidden" id="artistID" value="<%= artistID %>">
                                <input type="hidden" id="deleteType" value="<%= response.getHeader("type") %>">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<% if (response.getHeader("type").equals("artist")) {%>
<!-- Modal -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Edit Artist</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="editForm" method="POST" action="<%=request.getContextPath()%>/adminProfiles/editArtistDetails/<%= response.getHeader("type")%>/<%= artistID%>">
                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="name" name="name" value="<%= artist.getName()%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="knowAs" class="col-sm-3 control-label">Know As</label>
                        <div class="col-sm-9">    
                            <input type="text" class="form-control" id="knowAs" name="knowAs" value="<%= artist.getKownAs()%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="start" class="col-sm-3 control-label">Started</label>
                        <div class="col-sm-9">
                            <% String startDate = formatter.format(artist.getStart());%>
                            <input type="date" class="form-control" id="start" name="start" value="<%= startDate%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="end" class="col-sm-3 control-label">End Date</label>
                        <div class="col-sm-9">
                            <% String endDate = "";
                                if (artist.getEnd() != null) {%>
                            <% endDate = formatter.format(artist.getStart());%>
                            <% }%>
                            <input type="date" class="form-control" id="end" name="end" value="<%= endDate%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="genre" class="col-sm-3 control-label">Genre</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="genre" name="genre">
                                <% for (int x = 0; x < genres.size(); x++) {
                                        if (artist.getGenreID() == genres.getGenreAt(x).getGenreID()) {
                                %>
                                <option selected value="<%= genres.getGenreAt(x).getGenreID()%>"><%= genres.getGenreAt(x).getName()%></option>
                                <% } else {%>
                                <option value="<%= genres.getGenreAt(x).getGenreID()%>"><%= genres.getGenreAt(x).getName()%></option>
                                <% }
                                    } %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="memberOf" class="col-sm-3 control-label">Member Of</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="genre" name="memberOf">
                                <option value="0">None</option>
                                <% for (int x = 0; x < bands.size(); x++) {
                                        if (artist.getMemberOfID() == bands.getBandAt(x).getBandID()) {
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
                        <label for="bio" class="col-sm-3 control-label">Biography</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" rows="3" id="bio" name="bio"><%= artist.getBiograhpy()%></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="picName" class="col-sm-3 control-label">Picture Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="picName" name="picName" value="<%= artist.getPicName()%>">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="editSubmit" class="btn btn-primary">Submit</button>
            </div>
        </div>
    </div>
</div>
<% } else {%>
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Edit Band</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="editForm" method="POST" action="<%=request.getContextPath()%>/adminProfiles/editArtistDetails/<%= response.getHeader("type")%>/<%= artistID%>">
                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="name" name="name" value="<%= band.getName()%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="start" class="col-sm-3 control-label">Started</label>
                        <div class="col-sm-9">
                            <% String startDate = formatter.format(band.getStart());%>
                            <input type="date" class="form-control" id="start" name="start" value="<%= startDate%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="end" class="col-sm-3 control-label">End Date</label>
                        <div class="col-sm-9">
                            <% String endDate = "";
                                if (band.getEnd() != null) {%>
                            <% endDate = formatter.format(band.getStart());%>
                            <input type="date" class="form-control" id="end" name="end" value="<%= endDate%>">
                            <% } %>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="genre" class="col-sm-3 control-label">Genre</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="genre" name="genre">
                                <% for (int x = 0; x < genres.size(); x++) {
                                        if (band.getGenreID() == genres.getGenreAt(x).getGenreID()) {
                                %>
                                <option selected value="<%= genres.getGenreAt(x).getGenreID()%>"><%= genres.getGenreAt(x).getName()%></option>
                                <% } else {%>
                                <option value="<%= genres.getGenreAt(x).getGenreID()%>"><%= genres.getGenreAt(x).getName()%></option>
                                <% }
                                    }%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bio" class="col-sm-3 control-label">Biography</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" rows="3" id="bio" name="bio"><%= band.getBiography()%></textarea>

                        </div>
                    </div>
                    <div class="form-group">
                        <label for="picName" class="col-sm-3 control-label">Picture Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="picName" name="picName" value="<%= band.getPicName()%>">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="editSubmit" class="btn btn-primary">Submit</button>
            </div>
        </div>
    </div>
</div>
<% }%>

<!-- Modal -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add Event</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="addForm" method="POST" action="<%=request.getContextPath()%>/adminProfiles/addSong/<%= artistID%>/<%= response.getHeader("type")%>">
                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="name" name="name" placeholder="Enter Event Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="date" class="col-sm-3 control-label">Release Date</label>
                        <div class="col-sm-9">
                            <input type="date" class="form-control" id="date" name="date" placeholder="19-11-1985">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="songLink" class="col-sm-3 control-label">Song Link</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="songLink" name="songLink" placeholder="I_Like_Big_Buts">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="addSubmit" class="btn btn-primary">Submit</button>
            </div>
        </div>
    </div>
</div>
