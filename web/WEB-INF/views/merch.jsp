<%-- 
    Document   : merch
    Created on : 09-Mar-2014, 16:04:39
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>
<%@page import="entities.mechandise.Merchandise"%>
<%@page import="entities.mechandise.MerchandiseList"%>
<%@page import="entities.mechandise.Type"%>
<%@page import="entities.mechandise.TypeList"%>
<%
    TypeList types = (TypeList) request.getAttribute("types");
    MerchandiseList merchList = (MerchandiseList) request.getAttribute("merch");
%>
<div class="col-md-8">
    <div class="content">
        <div class="row" id="portsMargin">
            <%
                if (response.getHeader("action").equals("saleTypes")) {
                    for (int pos = 0; pos < types.size(); pos++) {
                        Type type = types.getTypeAt(pos);
            %>
            <a href="saleItems/<%= type.getTypeID()%>">
                <div class="col-md-3" id="borderControl">
                    <div>
                        <% if (type.getTypePic().equals("Null")) {%>
                        <img class="image" src="<%=request.getContextPath()%>/assets/images/merch/default.png" class="img-responsive" alt="Responsive image">
                        <% } else {%>
                        <img class="image" src="<%=request.getContextPath()%>/assets/images/merch/<%= type.getTypePic()%>.png" class="img-responsive" alt="Responsive image">
                        <% }%>
                    </div>
                    <div class="imageText">
                        <%= type.getName()%>
                    </div>
                </div>
            </a>
            <% }
            } else if (response.getHeader("action").equals("artistTypes")) {
                for (int pos = 0; pos < types.size(); pos++) {
                    Type type = types.getTypeAt(pos);
            %>
            <a href="<%=request.getContextPath()%>/artistMerchItems/<%= type.getTypeID()%>/<%= response.getHeader("artistID")%>/<%= response.getHeader("artistType")%>">
                <div class="col-md-3" id="borderControl">
                    <div>
                        <% if (type.getTypePic().equals("Null")) {%>
                        <img class="image" src="<%=request.getContextPath()%>/assets/images/merch/default.png" class="img-responsive" alt="Responsive image">
                        <% } else {%>
                        <img class="image" src="<%=request.getContextPath()%>/assets/images/merch/<%= type.getTypePic()%>.png" class="img-responsive" alt="Responsive image">
                        <% }%>
                    </div>
                    <div class="imageText">
                        <%= type.getName()%>
                    </div>
                </div>
            </a>
            <% }
            } else if (response.getHeader("action").equals("saleItems") || response.getHeader("action").equals("artistItems")) {
                for (int pos = 0; pos < merchList.size(); pos++) {
                    Merchandise merch = merchList.getMerchAt(pos);
            %>
            <a href="<%=request.getContextPath()%>/itemDetails/<%= merch.getMerchID()%>">
                <div class="col-md-3" id="borderControl">
                    <div>
                        <% if (merch.getPicName().equals("Null")) {%>
                        <img class="image" src="<%=request.getContextPath()%>/assets/images/merch/default.png" class="img-responsive" alt="Responsive image">
                        <% } else {%>
                        <img class="image" src="<%=request.getContextPath()%>/assets/images/merch/<%= merch.getPicName()%>.png" class="img-responsive" alt="Responsive image">
                        <% }%>
                    </div>
                    <div class="imageText">
                        <%= merch.getName()%>
                    </div>
                </div>
            </a>
            <% }
                }%>
        </div>
    </div>
</div>
