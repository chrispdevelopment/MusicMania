<%-- 
    Document   : userOrders
    Created on : 18-Mar-2014, 23:28:57
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>
<%@page import="entities.mechandise.MerchandiseList"%>
<%@page import="entities.mechandise.Merchandise"%>
<%@page import="entities.orders.OrderDetail"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entities.orders.OrderDetailList"%>
<%@page import="entities.orders.Order"%>
<%@page import="entities.orders.OrderList"%>
<%
    OrderList orders = (OrderList) request.getAttribute("orders");
    MerchandiseList merchList = (MerchandiseList) request.getAttribute("merch");
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
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
                <li role="presentation"><a role="menuitem" tabindex="-1" href="<%=request.getContextPath()%>/admin/orderManagment/No">Uncompleted</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="<%=request.getContextPath()%>/admin/orderManagment/Yes">Completed</a></li>
            </ul>
        </div> <br/>
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
                    <% if (order.getCompleted().equals("No")) {%>
                    <a href="<%=request.getContextPath()%>/admin/completeOrder/<%= order.getOrderID()%>" class="btn btn-primary" role="button" id="completeOrder">Complete Order</a><br/><br/>
                    <a href="<%=request.getContextPath()%>/admin/deleteOrder/<%= order.getOrderID()%>" class="btn btn-primary" role="button" id="deleteOrder">Delete Order</a>
                    <% } else {%>
                    <a href="<%=request.getContextPath()%>/admin/deleteOrder/<%= order.getOrderID()%>" class="btn btn-primary" role="button" id="deleteOrder">Delete Order</a>
                    <% } %>
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
                    <% }%>
                </div>
            </div>
        </div>
        <% } } else { %>
        <h2>No Orders</h2>
        <% } %>
    </div>
</div>
