<%-- 
    Document   : bands
    Created on : 07-Mar-2014, 08:45:16
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>

<%@page import="entities.artists.Band"%>
<%@page import="entities.artists.BandList"%>
<%
    BandList bandList = (BandList) request.getAttribute("bands");
%>
<div class="col-md-8">
    <div class="content">
        <div class="row" id="portsMargin">
            <%
                for (int pos = 0; pos < bandList.size(); pos++) {
                    Band band = bandList.getBandAt(pos);

            %>
            <a href="showProfile/band/<%= band.getBandID()%>">
                <div class="col-md-3" id="borderControl">
                    <div>
                        <% if (band.getPicName().equals("Null")) {%>
                        <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/default.png" class="img-responsive" alt="Responsive image">
                        <% } else { %>
                        <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/<%= band.getPicName() %>.png" class="img-responsive" alt="Responsive image">
                        <% } %>
                    </div>
                    <div class="imageText">
                        <%= band.getName()%>
                    </div>
                </div>
            </a>
            <% }%>
        </div>
    </div>
</div>
