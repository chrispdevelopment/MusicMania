<%-- 
    Document   : sales
    Created on : 09-Mar-2014, 16:05:09
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>

<%@page import="entities.mechandise.SizeList"%>
<%@page import="entities.mechandise.Merchandise"%>
<%
    Merchandise item = (Merchandise) request.getAttribute("item");
    SizeList sizes = (SizeList) request.getAttribute("sizes");
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
            <% if (userSession != null) { %>
            <div class="col-md-3">
                <form id="orderForm" method="POST" action="<%=request.getContextPath()%>/addToCart" role="form">
                    <input type="hidden" name="merchID" value="<%= item.getMerchID() %>">
                    <input type="hidden" name="merchPic" value="<%= item.getPicName() %>">
                    <div class="form-group">
                        <label for="quantity">Quantity</label>
                        <input type="number" class="form-control" id="quantity" name="quantity" placeholder="0" onkeyup="updateCost($('#quantity').val())">
                    </div>
                    <% if (item.getTypeID() == 1 || item.getTypeID() == 8) { %>
                    <div class="form-group">
                        <label for="sizes">Sizes</label>
                        <select class="form-control" name="size">
                            <%for (int x = 0; x < sizes.size(); x++) {%>
                            <option><%= sizes.getSizeAt(x).getSize()%></option>
                            <% } %>
                        </select>
                    </div>
                    <% }%>
                    <div class="form-group">
                        <label for="cost">Cost</label>
                        <div class="input-group">
                            <span class="input-group-addon">£</span>
                            <input type="number" class="form-control" id="cost" name="cost" value="<%= item.getPrice()%>" readonly>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-default">Add To Cart</button>
                </form>
            </div>
            <% }%>
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
                        <%= item.getDescription()%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function updateCost(quantity) {
        var totalCost = quantity * <%= item.getPrice()%>;
        $('#cost').val(totalCost);
    }
</script>