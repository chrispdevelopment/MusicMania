<%-- 
    Document   : events
    Created on : 11-Mar-2014, 05:24:52
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>

<%@page import="entities.events.Location"%>
<%@page import="entities.events.Event"%>
<%@page import="entities.events.LocationList"%>
<%@page import="entities.events.EventList"%>

<%
    EventList events = (EventList) request.getAttribute("events");
    LocationList locations = (LocationList) request.getAttribute("locations");
%>

<input type="hidden" value="Event" id="page">
<input type="hidden" value="<%=request.getContextPath()%>" id="context">
<div class="col-md-8">
    <div class="content">
        <div class="panel-group" id="accordion">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                            Events
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse in">
                    <div class="panel-body">
                        <table class="table">                      
                            <tr>
                                <th></th>
                                <th>Name</th>
                                <th>Location</th>
                                <th>Date</th>
                                <th>Start</th>
                                <th>End</th>
                                <th>Price</th>
                            </tr>
                            <%
                                for (int x = 0; x < events.size(); x++) {
                                    Event event = events.getEventAt(x);
                                    Location location = null;
                                    for (int y = 0; y < locations.size(); y++) {
                                        if (event.getLocationID() == locations.getLocationAt(y).getLocationID()) {
                                            location = locations.getLocationAt(y);
                                            break;
                                        }
                                    }
                            %>
                            <tr>
                                <td><input type="radio" name="select" id="select<%= event.getEventID()%>" value="<%= event.getEventID()%>" ></td>
                                <td><%= event.getName()%></td>
                                <td><%= location.getName()%></td>
                                <td><%= event.getDate()%></td>
                                <td><%= event.getStart()%></td>
                                <td><%= event.getEnd()%></td>
                                <td><%= event.getPrice()%></td>
                            </tr>
                            <% }%>
                        </table>
                        <button class="btn btn-primary" data-toggle="modal" data-target="#addModal">Add Event</button>
                        <a href="#" class="btn btn-primary" role="button" id="edit">Edit Event</a>
                        <a href="#" class="btn btn-primary" role="button" id="delete">Delete Event</a>

                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                            Locations
                        </a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse">
                    <div class="panel-body">
                        <table class="table">                      
                            <tr>
                                <th></th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Web Link</th>
                            </tr>
                            <%
                                for (int x = 0; x < locations.size(); x++) {
                                    Location location = locations.getLocationAt(x);
                            %>
                            <tr>
                                <td><input type="radio" name="selectLocation" id="select<%= location.getLocationID()%>" value="<%= location.getLocationID()%>" ></td>
                                <td><%= location.getName()%></td>
                                <td><%= location.getEmail()%></td>
                                <td><%= location.getPhone()%></td>
                                <td><%= location.getUrl()%></td>
                            </tr>
                            <% }%>
                        </table>
                        <button class="btn btn-primary" data-toggle="modal" data-target="#addLocationModal">Add Location</button>
                        <a href="#" class="btn btn-primary" role="button" id="editLocation">Edit Location</a>
                        <a href="#" class="btn btn-primary" role="button" id="deleteLocation">Delete Location</a>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add Event</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="addForm" method="POST" action="<%=request.getContextPath()%>/admin/addEvent">
                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="name" name="name" placeholder="Enter Event Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="location" class="col-sm-3 control-label">Location</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="location">
                                <% for (int x = 0; x < locations.size(); x++) {%>
                                <option value="<%= locations.getLocationAt(x).getLocationID()%>"><%= locations.getLocationAt(x).getName()%></option>
                                <% }%>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="date" class="col-sm-3 control-label">Event Date</label>
                        <div class="col-sm-9">
                            <input type="date" class="form-control" id="date" name="date" placeholder="19-11-1985">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="start" class="col-sm-3 control-label">Event Start</label>
                        <div class="col-sm-9">
                            <input type="time" class="form-control" id="start" name="start" placeholder="16:20:20">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="end" class="col-sm-3 control-label">Event End</label>
                        <div class="col-sm-9">
                            <input type="time" class="form-control" id="end" name="end" placeholder="20:21:20">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="price" class="col-sm-3 control-label">Price</label>
                        <div class="col-sm-9 input-group">
                            <span class="input-group-addon">£</span>
                            <input type="text" class="form-control" id="price" name="price" placeholder="0">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="desc" class="col-sm-3 control-label">Description</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" rows="3" id="desc" name="desc"></textarea>
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

<div class="modal fade" id="addLocationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add Location</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="addLocationForm" method="POST" action="<%=request.getContextPath()%>/admin/addLocation">
                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="name" name="name" placeholder="Enter Location Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="address" class="col-sm-3 control-label">Address</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="address1" name="address1" placeholder="Address One">
                            <input type="text" class="form-control" id="address2" name="address2" placeholder="Address One">
                            <input type="text" class="form-control" id="address3" name="address3" placeholder="Address One">
                            <input type="text" class="form-control" id="address4" name="address4" placeholder="Address One">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="postCode" class="col-sm-3 control-label">Post Code</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="postCode" name="postCode" placeholder="Enter Post Code">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-sm-3 control-label">Email</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="email" name="email" placeholder="email@email.com">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="phone" class="col-sm-3 control-label">Phone Number</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="phone" name="phone" placeholder="Enter Phonenumber">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="url" class="col-sm-3 control-label">Web Site</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="url" name="url" placeholder="Enter Website Link">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="desc" class="col-sm-3 control-label">Description</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" rows="3" id="desc" name="desc"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="addLocationSubmit" class="btn btn-primary">Submit</button>
            </div>
        </div>
    </div>
</div>
