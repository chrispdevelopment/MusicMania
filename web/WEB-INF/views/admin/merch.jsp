<%-- 
    Document   : merch
    Created on : 11-Mar-2014, 05:24:36
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>
<%@page import="entities.artists.Band"%>
<%@page import="entities.artists.Artist"%>
<%@page import="entities.mechandise.Size"%>
<%@page import="entities.mechandise.Type"%>
<%@page import="entities.mechandise.Merchandise"%>
<%@page import="entities.artists.BandList"%>
<%@page import="entities.artists.ArtistList"%>
<%@page import="entities.mechandise.MerchandiseList"%>
<%@page import="entities.mechandise.TypeList"%>
<%@page import="entities.mechandise.SizeList"%>
<%
    SizeList sizes = (SizeList) request.getAttribute("sizes");
    TypeList types = (TypeList) request.getAttribute("types");
    MerchandiseList merch = (MerchandiseList) request.getAttribute("merch");
    ArtistList artists = (ArtistList) request.getAttribute("artists");
    BandList bands = (BandList) request.getAttribute("bands");
%>
<input type="hidden" value="Merch" id="page">
<input type="hidden" value="<%=request.getContextPath()%>" id="context">
<div class="col-md-8">
    <div class="content">
        <div class="dropdown">
            <button class="btn btn-primary" type="button" id="dropdownMenu1" data-toggle="dropdown">
                Merchandise Type
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                <% for (int x = 0; x < types.size(); x++) { %>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="<%=request.getContextPath()%>/admin/merchManagment/<%= types.getTypeAt(x).getTypeID() %>"><%= types.getTypeAt(x).getName() %></a></li>
                <% } %>
            </ul>
        </div> <br/>
        <div class="panel-group" id="accordion">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                            Merchandise
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse in">
                    <div class="panel-body">


                        <table class="table">
                            <tr>
                                <th></th>
                                <th>Name</th>
                                <th>Artist/Band</th>
                                <th>Type</th>
                                <th>Price</th>
                                <th>On Sale</th>
                            </tr>
                            <%
                                for (int x = 0; x < merch.size(); x++) {
                                    Merchandise item = merch.getMerchAt(x);
                                    Type type = null;
                                    Artist artist = null;
                                    Band band = null;
                                    for (int y = 0; y < types.size(); y++) {
                                        if (item.getTypeID() == types.getTypeAt(y).getTypeID()) {
                                            type = types.getTypeAt(y);
                                            break;
                                        }
                                    }
                                    if (item.getArtistType().equals("artist")) {
                                        for (int y = 0; y < artists.size(); y++) {
                                            if (item.getArtistID() == artists.getArtistAt(y).getArtistID()) {
                                                artist = artists.getArtistAt(y);
                                                break;
                                            }
                                        }
                                    } else {
                                        for (int y = 0; y < bands.size(); y++) {
                                            if (item.getArtistID() == bands.getBandAt(y).getBandID()) {
                                                band = bands.getBandAt(y);
                                                break;
                                            }
                                        }
                                    }
                            %>
                            <tr>
                                <td><input type="radio" name="select" id="select<%= item.getMerchID()%>" value="<%= item.getMerchID()%>" ></td>
                                <td><%= item.getName()%></td>
                                <% if (artist != null) {%>
                                <td><%= artist.getName()%></td>
                                <% } else {%>
                                <td><%= band.getName()%></td>
                                <% }%>
                                <td><%= type.getName()%></td>
                                <td><%= item.getPrice()%></td>
                                <td><%= item.getSale()%></td>
                            </tr>
                            <% }%>
                        </table>
                        <button class="btn btn-primary" data-toggle="modal" data-target="#addModal">Add Merchandise</button>
                        <a href="#" class="btn btn-primary" role="button" id="edit">Edit Merchandise</a>
                        <a href="#" class="btn btn-primary" role="button" id="delete">Delete Merchandise</a>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                            Types & Sizes
                        </a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Types</h3>
                                    </div>
                                    <div class="panel-body">
                                        <table class="table">
                                            <tr>
                                                <th></th>
                                                <th>Name</th>
                                            </tr>
                                            <%
                                                for (int x = 0; x < types.size(); x++) {
                                                    Type type = types.getTypeAt(x);
                                            %>
                                            <tr>
                                                <td><input type="radio" name="selectType" id="select<%= type.getTypeID()%>" value="<%= type.getTypeID()%>" ></td>
                                                <td><%= type.getName()%></td>
                                            </tr>
                                            <input type="hidden" value="<%= type.getName()%>" id="typeName<%= type.getTypeID()%>">
                                            <% }%>
                                        </table>
                                        <button class="btn btn-primary" data-toggle="modal" data-target="#addTypeModal">Add Type</button>
                                        <button class="btn btn-primary" data-toggle="modal" data-target="#editTypeModal">Edit Type</button>
                                        <a href="#" class="btn btn-primary" role="button" id="deleteType">Delete Type</a>
                                    </div>
                                </div>

                            </div>
                            <div class="col-md-6">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Sizes</h3>
                                    </div>
                                    <div class="panel-body">
                                        <table class="table">
                                            <tr>
                                                <th></th>
                                                <th>Name</th>
                                            </tr>
                                            <%
                                                for (int x = 0; x < sizes.size(); x++) {
                                                    Size size = sizes.getSizeAt(x);
                                            %>
                                            <tr>
                                                <td><input type="radio" name="selectSize" id="select<%= size.getSizeID()%>" value="<%= size.getSizeID()%>" ></td>
                                                <td><%= size.getSize()%></td>
                                            <input type="hidden" value="<%= size.getSize()%>" id="sizeName<%= size.getSizeID()%>">
                                            </tr>
                                            <% }%>
                                        </table>
                                        <button class="btn btn-primary" data-toggle="modal" data-target="#addSizeModal">Add Size</button>
                                        <button class="btn btn-primary" data-toggle="modal" data-target="#editSizeModal">Edit Size</button>
                                        <a href="#" class="btn btn-primary" role="button" id="deleteSize">Delete Size</a>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Edit Details</h4>
            </div>
            <div class="modal-body">
                <form id="addForm" method="POST" action="<%=request.getContextPath()%>/admin/addMerch" class="form-horizontal">
                    <div class="form-group">
                        <label for="Name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="name" name="name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="Name" class="col-sm-3 control-label">Artist/Band</label>
                        <div class="col-sm-9">
                            <select class="form-control" id="artistSelect" name="artistSelect">
                                <option selected>Artist</option>
                                <option>Band</option>
                            </select>
                        </div>                 
                    </div>
                    <div class="form-group" id="artist">
                        <label for="artistID" class="col-sm-3 control-label">Artist Name</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="artistID">
                                <% for (int x = 0; x < artists.size(); x++) {%>       
                                <option value="<%= artists.getArtistAt(x).getArtistID()%>"><%= artists.getArtistAt(x).getName()%></option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" id="band">
                        <label for ="bandID" class="col-sm-3 control-label">Band Name</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="bandID">
                                <% for (int x = 0; x < bands.size(); x++) {%>
                                <option value="<%= bands.getBandAt(x).getBandID()%>"><%= bands.getBandAt(x).getName()%></option>
                                <% }%>
                            </select>
                        </div>                 
                    </div>
                    <div class="form-group">
                        <label for="price" class="col-sm-3 control-label">Price</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="price" name="price">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="type" class="col-sm-3 control-label">Type</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="typeID">
                                <% for (int x = 0; x < types.size(); x++) {%>
                                <option value="<%= types.getTypeAt(x).getTypeID()%>"><%= types.getTypeAt(x).getName()%></option>
                                <% }%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sale" class="col-sm-3 control-label">Sale</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="sale">
                                <option selected>True</option>
                                <option>False</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description" class="col-sm-3 control-label">Description</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" rows="4" name="description"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="picName" class="col-sm-3 control-label">Picture Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="picName" name="picName" >
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="addSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addTypeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add Type</h4>
            </div>
            <div class="modal-body">
                <form id="addTypeForm" method="POST" action="<%=request.getContextPath()%>/admin/addType" class="form-horizontal">
                    <div class="form-group">
                        <label for="Name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="addTypeName" name="name">
                        </div>
                    </div>                   
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="addTypeSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editTypeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Edit Type</h4>
            </div>
            <div class="modal-body">
                <form id="editTypeForm" method="POST" action="<%=request.getContextPath()%>/admin/editType" class="form-horizontal">
                    <div class="form-group">
                        <label for="Name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="hidden" id="typeID" name="typeID">
                            <input type="text" class="form-control" id="editTypeName" name="name">
                        </div>
                    </div>                   
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="editTypeSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addSizeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add Size</h4>
            </div>
            <div class="modal-body">
                <form id="addSizeForm" method="POST" action="<%=request.getContextPath()%>/admin/addSize" class="form-horizontal">
                    <div class="form-group">
                        <label for="size" class="col-sm-3 control-label">Size</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="addSizeName" name="size">
                        </div>
                    </div>                   
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="addSizeSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editSizeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Edit Size</h4>
            </div>
            <div class="modal-body">
                <form id="editSizeForm" method="POST" action="<%=request.getContextPath()%>/admin/editSize" class="form-horizontal">
                    <div class="form-group">
                        <label for="Name" class="col-sm-3 control-label">Size</label>
                        <div class="col-sm-9">
                            <input type="hidden" id="sizeID" name="sizeID">
                            <input type="text" class="form-control" id="editSizeName" name="size">
                        </div>
                    </div>                   
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="editSizeSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>
