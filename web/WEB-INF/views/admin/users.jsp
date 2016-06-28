<%-- 
    Document   : users
    Created on : 11-Mar-2014, 05:24:22
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>
<%@page import="entities.users.User"%>
<%@page import="entities.users.UserList"%>
<%
    UserList users = (UserList) request.getAttribute("users");
%>
<input type="hidden" value="User" id="page">
<input type="hidden" value="<%=request.getContextPath()%>" id="context">
<div class="col-md-8">
    <div class="content">
        <table class="table">
            <tr>
                <th></th>
                <th>Username</th>
                <th>Name</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Access Level</th>
            </tr>
            <%
                for (int x = 0; x < users.size(); x++) {
                    User user = users.getUserAt(x);
            %>
            <tr>
                <td><input type="radio" name="select" id="select<%= user.getUserID()%>" value="<%= user.getUserID()%>" ></td>
                <td><%= user.getUsername()%></td>
                <td><%= user.getFirstname() + " " + user.getSurname()%></td>
                <td><%= user.getPhone()%></td>
                <td><%= user.getEmail()%></td>
                <td><%= user.getAccessLevel()%></td>
            </tr>
            <% }%>
        </table>
        <button class="btn btn-primary" data-toggle="modal" data-target="#addModal">Add User</button>
        <a href="#" class="btn btn-primary" role="button" id="edit">Edit User</a>
        <a href="#" class="btn btn-primary" role="button" id="delete">Delete User</a>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add User</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" id="addForm" method="POST" action="<%=request.getContextPath()%>/admin/addUser">
                    <div class="form-group">
                        <label for="username" class="col-sm-3 control-label">Username</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="username" name="username" placeholder="Enter username">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-3 control-label">Password</label>
                        <div class="col-sm-9">
                            <input type="password" class="form-control" id="password" name="password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="firstName" class="col-sm-3 control-label">First Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="firstName" name="firstname" placeholder="Enter first name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="surname" class="col-sm-3 control-label">Surname</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="surname" name="surname" placeholder="Enter surname">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="address" class="col-sm-3 control-label">Address</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="address" name="address1" placeholder="Address Line One">
                            <input type="text" class="form-control" id="address" name="address2" placeholder="Address Line Two">
                            <input type="text" class="form-control" id="address" name="address3" placeholder="Address Line Three">
                            <input type="text" class="form-control" id="address" name="address4" placeholder="Address Line Four">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="postCode" class="col-sm-3 control-label">Post Code</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="postCode" name="postCode" placeholder="Enter post code">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="phone" class="col-sm-3 control-label">Phone Number</label>
                        <div class="col-sm-9">
                            <input type="tel" class="form-control" id="phone" name="phone" placeholder="Enter phone number">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-sm-3 control-label">Email</label>
                        <div class="col-sm-9">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Enter email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dob" class="col-sm-3 control-label">Date of Birth</label>
                        <div class="col-sm-9">
                            <input type="date" class="form-control" id="dob" name="dob" placeholder="19-11-1985">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="accessLevel" class="col-sm-3 control-label">Access Level</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="accessLevel" id="accessLevel">
                                <option>User</option>
                                <option>Admin</option>
                            </select>
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
