<%-- 
    Document   : artists
    Created on : 18-Mar-2014, 22:12:49
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>
<%@page import="entities.artists.Band"%>
<%@page import="entities.artists.Artist"%>
<%@page import="entities.artists.Genre"%>
<%@page import="entities.artists.GenreList"%>
<%@page import="entities.artists.BandList"%>
<%@page import="entities.artists.ArtistList"%>
<%
    ArtistList artists = (ArtistList) request.getAttribute("artists");
    BandList bands = (BandList) request.getAttribute("bands");
    GenreList genres = (GenreList) request.getAttribute("genres");
%>

<% if (artists != null) { %>
<input type="hidden" value="artist" id="type">
<input type="hidden" value="Artist" id="page">
<% } else { %>
<input type="hidden" value="band" id="type">
<input type="hidden" value="Band" id="page">
<% }%>
<input type="hidden" value="<%=request.getContextPath()%>" id="context">
<div class="col-md-8">
    <div class="content">
        <div class="dropdown">
            <button class="btn btn-primary" type="button" id="dropdownMenu1" data-toggle="dropdown">
                Merchandise Type
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                <li role="presentation"><a role="menuitem" tabindex="-1" href="<%=request.getContextPath()%>/admin/artistManagment/artists">Artists</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="<%=request.getContextPath()%>/admin/artistManagment/bands">Bands</a></li>
            </ul>
        </div> <br/>
        <div class="panel-group" id="accordion">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                            Artists/Bands
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse in">
                    <div class="panel-body">
                        <% if (artists != null) { %>
                        <table class="table">                      
                            <tr>
                                <th></th>
                                <th>Name</th>
                                <th>Known As</th>
                                <th>Member Of</th>
                                <th>Genre</th>
                            </tr>
                            <%
                                for (int x = 0; x < artists.size(); x++) {
                                    Artist artist = artists.getArtistAt(x);
                                    Band band = new Band();
                                    if (artist.getMemberOfID() > 0) {
                                        for (int y = 0; y < bands.size(); y++) {
                                            if (artist.getMemberOfID() == bands.getBandAt(y).getBandID()) {
                                                band = bands.getBandAt(y);
                                                break;
                                            }
                                        }
                                    }
                            %>
                            <tr>
                                <td><input type="radio" name="selectArtist" id="select<%= artist.getArtistID()%>" value="<%= artist.getArtistID()%>" ></td>
                                <td><%= artist.getName()%></td>
                                <td><%= artist.getKownAs()%></td>
                                <td><%= band.getName()%></td>
                                <td><%= artist.getGenre()%></td>
                            </tr>
                            <% }%>
                        </table>
                        <button class="btn btn-primary" data-toggle="modal" data-target="#addModal">Add Artist</button>
                        <a href="#" class="btn btn-primary" role="button" id="edit">Edit Artist</a>
                        <a href="#" class="btn btn-primary" role="button" id="delete">Delete Artist</a>
                        <% } else { %>
                        <table class="table">                      
                            <tr>
                                <th></th>
                                <th>Name</th>
                                <th>Genre</th>
                            </tr>
                            <%
                                for (int x = 0; x < bands.size(); x++) {
                                    Band band = bands.getBandAt(x);
                            %>
                            <tr>
                                <td><input type="radio" name="selectArtist" id="select<%= band.getBandID()%>" value="<%= band.getBandID()%>" ></td>
                                <td><%= band.getName()%></td>
                                <td><%= band.getGenre()%></td>
                            </tr>
                            <% }%>
                        </table>
                        <button class="btn btn-primary" data-toggle="modal" data-target="#addModal">Add Band</button>
                        <a href="#" class="btn btn-primary" role="button" id="edit">Edit Band</a>
                        <a href="#" class="btn btn-primary" role="button" id="delete">Delete Band</a>
                        <% } %>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                            Genres
                        </a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-6">
                                <table class="table">
                                    <tr>
                                        <th></th>
                                        <th>Name</th>
                                    </tr>
                                    <%
                                        for (int x = 0; x < genres.size(); x++) {
                                            Genre genre = genres.getGenreAt(x);
                                    %>
                                    <tr>
                                        <td><input type="radio" name="selectGenre" id="select<%= genre.getGenreID()%>" value="<%= genre.getGenreID()%>" ></td>
                                        <td><%= genre.getName()%></td>
                                    <input type="hidden" value="<%= genre.getName()%>" id="genreName<%= genre.getGenreID()%>">
                                    </tr>
                                    <% }%>
                                </table>
                                <button class="btn btn-primary" data-toggle="modal" data-target="#addGenreModal">Add Genre</button>
                                <button class="btn btn-primary" data-toggle="modal" data-target="#editGenreModal">Edit Genre</button>
                                <a href="#" class="btn btn-primary" role="button" id="deleteGenre">Delete Genre</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<% if (artists != null) {%>
<!-- Modal -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add Artist</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="addForm" method="POST" action="<%=request.getContextPath()%>/admin/addArtist">
                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="name" name="name" placeholder="Enter Artist Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="knowAs" class="col-sm-3 control-label">Know As</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="knowAs" name="knowAs" placeholder="Enter Artist Alt Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="start" class="col-sm-3 control-label">Started</label>
                        <div class="col-sm-9">
                            <input type="date" class="form-control" id="start" name="start" placeholder="19-11-1985">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="end" class="col-sm-3 control-label">End Date</label>
                        <div class="col-sm-9">
                            <input type="date" class="form-control" id="end" name="end" placeholder="19-11-2004">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="genre" class="col-sm-3 control-label">Genre</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="genre" name="genre">
                                <% for (int x = 0; x < genres.size(); x++) {%>
                                <option value="<%= genres.getGenreAt(x).getGenreID()%>"><%= genres.getGenreAt(x).getName()%></option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="memberOf" class="col-sm-3 control-label">Member Of</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="genre" name="memberOf">
                                <option value="0">None</option>
                                <% for (int x = 0; x < bands.size(); x++) {%>
                                <option value="<%= bands.getBandAt(x).getBandID()%>"><%= bands.getBandAt(x).getName()%></option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bio" class="col-sm-3 control-label">Biography</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" rows="3" id="bio" name="bio"></textarea>

                        </div>
                    </div>
                    <div class="form-group">
                        <label for="picName" class="col-sm-3 control-label">Picture Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="picName" name="picName" placeholder="Enter Picture Name">
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
<% } else {%>
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add Band</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="addForm" method="POST" action="<%=request.getContextPath()%>/admin/addBand">
                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="name" name="name" placeholder="Enter Artist Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="start" class="col-sm-3 control-label">Started</label>
                        <div class="col-sm-9">
                            <input type="date" class="form-control" id="start" name="start" placeholder="19-11-1985">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="end" class="col-sm-3 control-label">End Date</label>
                        <div class="col-sm-9">
                            <input type="date" class="form-control" id="end" name="end" placeholder="19-11-2004">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="genre" class="col-sm-3 control-label">Genre</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="genre" name="genre">
                                <% for (int x = 0; x < genres.size(); x++) {%>
                                <option value="<%= genres.getGenreAt(x).getGenreID()%>"><%= genres.getGenreAt(x).getName()%></option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bio" class="col-sm-3 control-label">Biography</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" rows="3" id="bio" name="bio"></textarea>

                        </div>
                    </div>
                    <div class="form-group">
                        <label for="picName" class="col-sm-3 control-label">Picture Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="picName" name="picName" placeholder="Enter Picture Name">
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
<% }%>

<div class="modal fade" id="addGenreModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add Genre</h4>
            </div>
            <div class="modal-body">
                <form id="addGenreForm" method="POST" action="<%=request.getContextPath()%>/admin/addGenre" class="form-horizontal">
                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="addGenreName" name="name">
                        </div>
                    </div>                   
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="addGenreSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editGenreModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Edit Genre</h4>
            </div>
            <div class="modal-body">
                <form id="editGenreForm" method="POST" action="<%=request.getContextPath()%>/admin/editGenre" class="form-horizontal">
                    <div class="form-group">
                        <label for="Name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="hidden" id="genreID" name="genreID">
                            <input type="text" class="form-control" id="editGenreName" name="name">
                        </div>
                    </div>                   
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="editGenreSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>