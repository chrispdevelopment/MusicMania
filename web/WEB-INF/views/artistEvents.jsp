<%-- 
    Document   : artistEvents
    Created on : 09-Mar-2014, 16:52:20
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>

<%@page import="entities.events.Event"%>
<%@page import="entities.events.EventList"%>
<%
    EventList eventList = (EventList) request.getAttribute("events");
%>
<div class="col-md-8">
    <div class="content">
        <h2>Events for <%= response.getHeader("artistName") %></h2>
        <div class="row" id="portsMargin">
            <%
                for (int pos = 0; pos < eventList.size(); pos++) {
                    Event event = eventList.getEventAt(pos);

            %>
            <a href="<%=request.getContextPath()%>/eventProfile/<%= event.getEventID()%>">
                <div class="col-md-3" id="borderControl">
                    <div>
                        <% if (event.getEventPic().equals("Null")) {%>
                        <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/default.png" class="img-responsive" alt="Responsive image">
                        <% } else { %>
                        <img class="image" src="<%=request.getContextPath()%>/assets/images/portraits/<%= event.getEventPic() %>.png" class="img-responsive" alt="Responsive image">
                        <% } %>
                    </div>
                    <div class="imageText">
                        <%= event.getName()%>
                    </div>
                </div>
            </a>
            <% }%>
        </div>
    </div>
</div>
