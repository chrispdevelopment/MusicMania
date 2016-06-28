<%-- 
    Document   : basket
    Created on : 09-Mar-2014, 16:05:56
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>

<%@page import="entities.mechandise.Merchandise"%>
<%@page import="entities.mechandise.MerchandiseList"%>
<%@page import="entities.orders.OrderDetail"%>
<%@page import="entities.orders.OrderDetailList"%>
<%
    OrderDetailList orderDetails = new OrderDetailList();

    if(session.getAttribute("cart") != null) {
       orderDetails = (OrderDetailList) session.getAttribute("cart"); 
    }
    
    MerchandiseList merchList = (MerchandiseList) request.getAttribute("merch");

    String merchName = null;
    String merchDesc = null;
    String picName = null;
    double total = 0;
%>

<div class="col-md-8">
    <div class="content">
        <% if (orderDetails.size() > 0) {
                for (int x = 0; x < orderDetails.size(); x++) {
                    OrderDetail item = orderDetails.getOrderDetailAt(x);
                    total = total + item.getTotalCost();
                    for (int y = 0; y < merchList.size(); y++) {
                        Merchandise merch = merchList.getMerchAt(y);
                        if (item.getMerchID() == merch.getMerchID()) {
                            merchName = merch.getName();
                            merchDesc = merch.getDescription();
                            picName = merch.getPicName();
                        }
                    }
        %>
        <div class="row" id="borderPad">
            <div class="col-md-4">
                <% if (picName.equals("Null")) {%>
                <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/default.png" class="img-responsive" alt="Responsive image">
                <% } else {%>
                <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/<%= picName%>.png" class="img-responsive" alt="Responsive image">
                <% }%>
            </div>
            <div class="col-md-8" id="item">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-md-4">
                                <h3 class="panel-title"><%= merchName%></h3> 
                            </div>
                            <div class="col-md-4 pull-right">
                                Quantity: <%= item.getQuantity()%> <br />
                                Cost: £<%= item.getTotalCost()%>
                            </div>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-4">
                                <%= merchDesc%>
                            </div>
                            <div class="col-md-4 pull-right">
                                <a href="<%=request.getContextPath()%>/deleteItem/<%= item.getMerchID()%>" class="btn btn-default" role="button">Delete Item</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <% }%>

        <div class="pull-right" id="borderPad">
            <div>
                Total Cost = £<%= total%>
            </div>
            <div>
                <a href="<%=request.getContextPath()%>/order/<%= total %>" class="btn btn-default" role="button">Submit Order</a>    
            </div>
        </div>
        <% } else { %>
        <h2>No Items Currently In Cart</h2>
        <% } %>
    </div>
</div>
