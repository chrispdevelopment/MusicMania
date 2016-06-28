<%-- 
    Document   : news
    Created on : 11-Mar-2014, 05:23:53
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entities.users.User"%>
<%@page import="entities.news.Article"%>
<%@page import="entities.users.UserList"%>
<%@page import="entities.news.ArticleList"%>
<%
    ArticleList articles = (ArticleList) request.getAttribute("articles");
    UserList users = (UserList) request.getAttribute("users");
%>
<input type="hidden" value="News" id="page">
<input type="hidden" value="<%=request.getContextPath()%>" id="context">
<div class="col-md-8">
    <div class="content">

        <input type="hidden" value="News" id="page">
        <input type="hidden" value="<%=request.getContextPath()%>" id="context">
        <div class="col-md-8">
            <div class="content">
                <table class="table">
                    <tr>
                        <th></th>
                        <th>Title</th>
                        <th>Content</th>
                        <th>Created Date</th>
                        <th>Created By</th>
                    </tr>
                    <%
                        for (int x = 0; x < articles.size(); x++) {
                            Article article = articles.getArticleAt(x);
                            User user = null;
                            for (int y = 0; y < users.size(); y++) {
                                user = users.getUserAt(y);
                                if (user.getUserID() == article.getUserID()) {
                                    break;
                                }
                            }
                    %>
                    <tr>
                        <td><input type="radio" name="select" id="select<%= article.getArticleID()%>" value="<%= article.getArticleID()%>" ></td>
                        <td><%= article.getTitle()%></td>
                        <td><%= article.getContent()%></td>
                        <%
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

                            String createdDate = formatter.format(article.getCreatedDate());
                        %>
                        <td><%= createdDate%></td>
                        <td><%= user.getUsername()%></td>
                    </tr>
                    <% }%>
                </table>
                <button class="btn btn-primary" data-toggle="modal" data-target="#addModal">Add Article</button>
                <a href="#" class="btn btn-primary" role="button" id="edit">Edit Article</a>
                <a href="#" class="btn btn-primary" role="button" id="delete">Delete Article</a>
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
                        <form class="form-horizontal" role="form" id="addForm" method="POST" action="<%=request.getContextPath()%>/admin/addArticle/<%= userSession.getUserID()%>">
                            <div class="form-group">
                                <label for="title" class="col-sm-2 control-label">Title</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="title" name="title" placeholder="Enter title">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="content" class="col-sm-2 control-label">Content</label>
                                <div class="col-sm-10">
                                    <textarea class="form-control" rows="4" id="content" name="content"></textarea>
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
    </div>
</div>
