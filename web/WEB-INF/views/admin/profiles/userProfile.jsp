<%--
    Document   : account
    Created on : 11-Mar-2014, 05:25:37
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>
<%@page import="entities.mechandise.Merchandise"%>
<%@page import="entities.mechandise.MerchandiseList"%>
<%@page import="entities.orders.OrderDetailList"%>
<%@page import="entities.orders.Order"%>
<%@page import="entities.orders.OrderDetail"%>
<%@page import="entities.artists.Band"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entities.orders.OrderList"%>
<%@page import="entities.artists.BandList"%>
<%@page import="entities.users.Account"%>
<%@page import="entities.users.User"%>
<%@page import="entities.artists.ArtistList"%>
<%@page import="entities.artists.Artist"%>
<%
    User user = (User) request.getAttribute("user");
    Account account = (Account) request.getAttribute("account");
    ArtistList artistList = (ArtistList) request.getAttribute("artists");
    BandList bandList = (BandList) request.getAttribute("bands");
    OrderList orders = (OrderList) request.getAttribute("orders");
    MerchandiseList merchList = (MerchandiseList) request.getAttribute("merch");
%>
<div class="col-md-8">
    <div class="content">
        <div class="panel-group" id="accordion">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                            Profile Information
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse in">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-5">
                                <table class="profileTable">
                                    <tr>
                                        <td>Username:</td>
                                        <td><%= user.getUsername()%></td>
                                    </tr>
                                    <tr>
                                        <td>Access Level:</td>
                                        <td><%= user.getAccessLevel()%></td>
                                    </tr>
                                    <tr>
                                        <td>Email:</td>
                                        <td><%= user.getEmail()%></td>
                                    </tr>
                                </table>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#emailModal">Change Email</button>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#accessModal">Change Access Level</button>
                            </div>
                            <div class="col-md-5">
                                <table class="profileTable">
                                    <tr>
                                        <td>First Name:</td>
                                        <td><%= user.getFirstname()%></td>
                                    </tr>
                                    <tr>
                                        <td>Surname:</td>
                                        <td><%= user.getSurname()%></td>
                                    </tr>
                                    <tr>
                                        <td>Date of Birth:</td>
                                        <%
                                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

                                            String dob = formatter.format(user.getdOB());
                                        %>
                                        <td><%= dob%></td>
                                    </tr>
                                </table>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#infoModal">Change Details</button>
                            </div>
                        </div>
                        <div class="panel panel-default" id="infoPanel">
                            <div class="panel-heading">
                                <h3 class="panel-title">Additional Info</h3>
                            </div>
                            <div class="panel-body">
                                <table class="profileTable">
                                    <tr>
                                        <td class="addressTitle">Address:</td>
                                        <%
                                            String[] address;
                                            address = user.getAddress().split(",");
                                        %>
                                        <td>
                                            <% for (String s : address) {%>
                                            <%= s%> <br/>
                                            <% }%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Post Code:</td>
                                        <td><%= user.getPostCode()%></td>
                                    </tr>
                                    <tr>
                                        <td>Phone Number:</td>
                                        <td><%= user.getPhone()%></td>
                                    </tr>
                                </table>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addInfoModal">Change Details</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                            Account Information
                        </a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="row rowSpacer">
                            <div class="col-md-5">
                                <table class="profileTable">
                                    <tr>
                                        <td>Balance:</td>
                                        <td>£<%= account.getBalance()%></td>
                                    </tr>
                                </table>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addMoneyModal">Add Money</button>
                            </div>
                            <div class="col-md-5">
                                <table class="profileTable">
                                    <tr>
                                        <td>Card Number:</td>
                                        <%
                                            String cardNumberString = "";
                                            if (account.getCardNumber() != null) {
                                                String[] cardNumber = account.getCardNumber().split(" ");

                                                for (int x = 0; x < 4; x++) {
                                                    if (x == 3) {
                                                        cardNumberString = cardNumberString.concat(cardNumber[x]);
                                                    } else {
                                                        cardNumberString = cardNumberString.concat("xxxx-");
                                                    }
                                                }
                                            }
                                        %>
                                        <td><%= cardNumberString%></td>
                                    </tr>
                                    <tr>
                                        <td>Date of Expiry:</td>
                                        <%
                                            if (account.getCardDOE() != null) {
                                                String dOE = formatter.format(account.getCardDOE());
                                        %>
                                        <td><%= dOE%></td>
                                        <% } %>
                                    </tr>
                                    <tr>
                                        <td>Name on Card:</td>
                                        <% if (account.getNameOnCard() != null) {%>
                                        <td><%= account.getNameOnCard()%></td>
                                        <% } %>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="row rowSpacer">
                            <div class="col-md-5">
                                <table class="profileTable">
                                    <tr>
                                        <td class="addressTitle">Delivery Address:</td>
                                        <%
                                            String[] delAddress = null;
                                            if (account.getDeliveryAddress() != null) {
                                                delAddress = account.getDeliveryAddress().split(",");
                                        %>
                                        <td>
                                            <% for (String s : delAddress) {%>
                                            <%= s%> <br/>
                                            <% } %>
                                        </td>
                                        <% } %>
                                    </tr>
                                </table>
                                <% if (account.getDeliveryAddress() != null) { %>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#delAddressModal">Change Address</button>
                                <% } else { %>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#delAddressModal">Add Address</button>
                                <% } %>
                            </div>
                            <div class="col-md-5">
                                <table class="profileTable">
                                    <tr>
                                        <td class="addressTitle">Billing Address:</td>
                                        <%
                                            String[] bilAddress = null;
                                            if (account.getBillingAddress() != null) {
                                                bilAddress = account.getBillingAddress().split(",");
                                        %>
                                        <td>
                                            <% for (String s : bilAddress) {%>
                                            <%= s%> <br/>
                                            <% } %>
                                        </td>
                                        <% } %>
                                    </tr>
                                </table>
                                <% if (account.getBillingAddress() != null) { %>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#bilAddressModal">Change Address</button>
                                <% } else { %>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#bilAddressModal">Add Address</button>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                            Subscriptions
                        </a>
                    </h4>
                </div>
                <div id="collapseThree" class="panel-collapse collapse">
                    <div class="panel-body">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Artists</h3>
                            </div>
                            <div class="panel-body panelArtists">
                                <% if (artistList.size() > 0) { %>
                                <div class="row" id="portsMargin">
                                    <%
                                        for (int pos = 0; pos < artistList.size(); pos++) {
                                            Artist artist = artistList.getArtistAt(pos);

                                    %>
                                    <div class="col-md-3" id="borderControl">
                                        <div>
                                            <% if (artist.getPicName() == null) {%>
                                            <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/default.png" class="img-responsive" alt="Responsive image">
                                            <% } else {%>
                                            <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/<%= artist.getPicName()%>.png" class="img-responsive" alt="Responsive image">
                                            <% }%>
                                        </div>
                                        <div class="imageText">
                                            <%= artist.getName()%>
                                        </div>
                                    </div>
                                    <% }%>
                                </div>
                                <% } else { %>
                                No Subscriptions
                                <% } %>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Bands</h3>
                            </div>
                            <div class="panel-body panelArtists">
                                <% if (bandList.size() > 0) { %>
                                <div class="row" id="portsMargin">
                                    <%
                                        for (int pos = 0; pos < bandList.size(); pos++) {
                                            Band band = bandList.getBandAt(pos);

                                    %>
                                    <div class="col-md-3" id="borderControl">
                                        <div>
                                            <% if (band.getPicName() == null) {%>
                                            <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/default.png" class="img-responsive" alt="Responsive image">
                                            <% } else {%>
                                            <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/<%= band.getPicName()%>.png" class="img-responsive" alt="Responsive image">
                                            <% }%>
                                        </div>
                                        <div class="imageText">
                                            <%= band.getName()%>
                                        </div>
                                    </div>
                                    <% }%>
                                </div>
                                <% } else { %>
                                No Subscriptions
                                <% }%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                            Orders
                        </a>
                    </h4>
                </div>
                <div id="collapseFour" class="panel-collapse collapse">
                    <div class="panel-body">

                        <%
                            if (orders.size() > 0) {
                                for (int x = 0; x < orders.size(); x++) {
                                    Order order = orders.getOrderAt(x);
                                    OrderDetailList items = order.getOrderDetails();
                        %>
                        <div class="well">
                            <div class="row" >
                                <div class="col-md-4">
                                    <table class="profileTable">
                                        <tr>
                                            <td>Order Number:</td>
                                            <td><%= order.getOrderID()%></td>
                                        </tr>
                                        <tr>
                                            <td>Total Cost:</td>
                                            <td><%= order.getTotalCost()%></td>
                                        </tr>
                                        <tr>
                                            <td>Order Created:</td>
                                            <% String createdDate = formatter.format(order.getCreatedDate());%>
                                            <td><%= createdDate%></td>
                                        </tr>
                                        <tr>
                                            <td>Completed:</td>
                                            <td><%= order.getCompleted()%></td>
                                        </tr>
                                        <tr>
                                            <td>Completed Date:</td>
                                            <%
                                                String completedDate = "";
                                                if (order.getCompletedDate() != null) {
                                                    completedDate = formatter.format(order.getCompletedDate());
                                                }
                                            %>
                                            <td><%= completedDate%></td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="col-md-8">

                                    <%
                                        for (int y = 0; y < items.size(); y++) {
                                            OrderDetail item = items.getOrderDetailAt(y);
                                            Merchandise merchItem = null;
                                            for (int z = 0; z < merchList.size(); z++) {
                                                if (merchList.getMerchAt(z).getMerchID() == item.getMerchID()) {
                                                    merchItem = merchList.getMerchAt(z);
                                                    break;
                                                }
                                            }
                                    %>
                                    <table class="table profileTable">
                                        <tr>
                                            <td>Name</td>
                                            <td>Quantity</td>
                                            <td>Size</td>
                                            <td>Cost</td>
                                        </tr>
                                        <tr>
                                            <td><%= merchItem.getName()%></td>
                                            <td><%= item.getQuantity()%></td>
                                            <td><%= item.getSize()%></td>
                                            <td><%= item.getTotalCost()%></td>

                                    </table> <br/>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                        <% }
                        } else { %>
                        No Orders

                        <% }%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="emailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Change Email</h4>
            </div>
            <div class="modal-body">
                <form id="emailForm" method="POST" action="<%=request.getContextPath()%>/adminProfiles/changeEmail/<%= user.getUserID()%>" class="form-horizontal">
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" id="email" name="email" value="<%= user.getEmail()%>">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="emailSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="accessModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Change Access Level</h4>
            </div>
            <div class="modal-body">
                <form id="accessForm" method="POST" action="<%=request.getContextPath()%>/adminProfiles/changeAccess/<%= user.getUserID()%>" class="form-horizontal">
                    <div class="form-group">
                        <label for=accessLevel class="col-sm-3 control-label">Access Level</label>
                        <div class="col-sm-9">
                            <select name="accessLevel" id="accessLevel" class="form-control">
                                <% if (user.getAccessLevel().equals("user")) { %>
                                <option selected>User</option>
                                <option>Admin</option>
                                <% } else { %>
                                <option>User</option>
                                <option selected>Admin</option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="accessSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Change Basic Information</h4>
            </div>
            <div class="modal-body">
                <form id="infoForm" method="POST" action="<%=request.getContextPath()%>/adminProfiles/changeInfo/<%= user.getUserID()%>" class="form-horizontal">
                    <div class="form-group">
                        <label for="firstname" class="col-sm-3 control-label">First Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="firstname" name="firstname" value="<%= user.getFirstname()%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="surname" class="col-sm-3 control-label">Surname</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="surname" name="surname" value="<%= user.getSurname()%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dob" class="col-sm-3 control-label">Date of Birth</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="dob" name="dob" value="<%= dob%>">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="infoSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addInfoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Change Additional Information</h4>
            </div>
            <div class="modal-body">
                <form id="addInfoForm" method="POST" action="<%=request.getContextPath()%>/adminProfiles/changeAddInfo/<%= user.getUserID()%>" class="form-horizontal">
                    <div class="form-group">
                        <label for="Address" class="col-sm-3 control-label">Address</label>
                        <div class="col-sm-9">
                            <%
                                String address1 = "";
                                String address2 = "";
                                String address3 = "";
                                String address4 = "";
                                if (address.length == 1) {
                                    address1 = address[0];
                                } else if (address.length == 2) {
                                    address1 = address[0];
                                    address2 = address[1];
                                } else if (address.length == 3) {
                                    address1 = address[0];
                                    address2 = address[1];
                                    address3 = address[2];
                                } else if (address.length == 4) {
                                    address1 = address[0];
                                    address2 = address[1];
                                    address3 = address[2];
                                    address4 = address[3];
                                }
                            %>
                            <input type="text" class="form-control" id="address" name="address1" value="<%= address1%>">
                            <input type="text" class="form-control" id="address" name="address2" value="<%= address2%>">
                            <input type="text" class="form-control" id="address" name="address3" value="<%= address3%>">
                            <input type="text" class="form-control" id="address" name="address4" value="<%= address4%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="postCode" class="col-sm-3 control-label">Post Code</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="postCode" name="postCode" value="<%= user.getPostCode()%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="phone" class="col-sm-3 control-label">Phone Number</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="phone" name="phone" value="<%= user.getPhone()%>">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="addInfoSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addMoneyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add Money</h4>
            </div>
            <div class="modal-body">
                <form id="addMoneyForm" method="POST" action="<%=request.getContextPath()%>/adminProfiles/addMoney/<%= user.getUserID()%>" class="form-horizontal">
                    <div class="form-group">
                        <label for="amount" class="col-sm-3 control-label">Amount</label>
                        <div class="col-sm-9 input-group">
                            <span class="input-group-addon">£</span>
                            <input type="text" class="form-control" id="amount" name="amount">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="addMoneySubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="delAddressModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Change Delivery Address</h4>
            </div>
            <div class="modal-body">
                <form id="delAddressForm" method="POST" action="<%=request.getContextPath()%>/adminProfiles/changeDelAddress/<%= user.getUserID()%>" class="form-horizontal">
                    <div class="form-group">
                        <label for="Address" class="col-sm-4 control-label">Delivery Address</label>
                        <div class="col-sm-8">
                            <%
                                if (delAddress != null) {
                                    if (delAddress.length == 1) {
                                        address1 = delAddress[0];
                                        address2 = "";
                                        address3 = "";
                                        address4 = "";
                                    } else if (delAddress.length == 2) {
                                        address1 = delAddress[0];
                                        address2 = delAddress[1];
                                        address3 = "";
                                        address4 = "";
                                    } else if (delAddress.length == 3) {
                                        address1 = delAddress[0];
                                        address2 = delAddress[1];
                                        address3 = delAddress[2];
                                        address4 = "";
                                    } else if (delAddress.length == 4) {
                                        address1 = delAddress[0];
                                        address2 = delAddress[1];
                                        address3 = delAddress[2];
                                        address4 = delAddress[3];
                                    }
                                } else {
                                    address1 = "";
                                    address2 = "";
                                    address3 = "";
                                    address4 = "";
                                }
                            %>
                            <input type="text" class="form-control" id="address" name="address1" value="<%= address1%>">
                            <input type="text" class="form-control" id="address" name="address2" value="<%= address2%>">
                            <input type="text" class="form-control" id="address" name="address3" value="<%= address3%>">
                            <input type="text" class="form-control" id="address" name="address4" value="<%= address4%>">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="delAddressSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="bilAddressModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Change Billing Address</h4>
            </div>
            <div class="modal-body">
                <form id="bilAddressForm" method="POST" action="<%=request.getContextPath()%>/adminProfiles/changeBilAddress/<%= user.getUserID()%>" class="form-horizontal">
                    <div class="form-group">
                        <label for="Address" class="col-sm-3 control-label">Billing Address</label>
                        <div class="col-sm-9">
                            <%
                                if (delAddress != null) {
                                    if (bilAddress.length == 1) {
                                        address1 = bilAddress[0];
                                        address2 = "";
                                        address3 = "";
                                        address4 = "";
                                    } else if (bilAddress.length == 2) {
                                        address1 = bilAddress[0];
                                        address2 = bilAddress[1];
                                        address3 = "";
                                        address4 = "";
                                    } else if (bilAddress.length == 3) {
                                        address1 = bilAddress[0];
                                        address2 = bilAddress[1];
                                        address3 = bilAddress[2];
                                        address4 = "";
                                    } else if (bilAddress.length == 4) {
                                        address1 = bilAddress[0];
                                        address2 = bilAddress[1];
                                        address3 = bilAddress[2];
                                        address4 = bilAddress[3];
                                    }
                                } else {
                                    address1 = "";
                                    address2 = "";
                                    address3 = "";
                                    address4 = "";
                                }
                            %>
                            <input type="text" class="form-control" id="address" name="address1" value="<%= address1%>">
                            <input type="text" class="form-control" id="address" name="address2" value="<%= address2%>">
                            <input type="text" class="form-control" id="address" name="address3" value="<%= address3%>">
                            <input type="text" class="form-control" id="address" name="address4" value="<%= address4%>">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="bilAddressSubmit">Save changes</button>
            </div>
        </div>
    </div>
</div>