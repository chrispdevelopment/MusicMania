<%-- 
    Document   : aticleProfile
    Created on : 19-Mar-2014, 03:51:57
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entities.news.Comment"%>
<%@page import="entities.news.CommentList"%>
<%@page import="entities.news.Article"%>
<%@page import="entities.users.UserList"%>
<%
    //if (session.getAttribute("session") == null) {
    //    response.sendRedirect(request.getContextPath());
    //}
    Article article = (Article) request.getAttribute("article");
    UserList userList = (UserList) request.getAttribute("users");
%>
<div class="col-md-8">
    <div class="content">
        <%
            String createdBy = null;
            for (int x = 0; x < userList.size(); x++) {
                if (article.getUserID() == userList.getUserAt(x).getUserID()) {
                    createdBy = userList.getUserAt(x).getUsername();
                }
            }

            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        %>
        <div class="panel panel-primary">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-5">
                        <h3 class="panel-title"><%= article.getTitle()%></h3>
                        created by <%= createdBy%> on <%= ft.format(article.getCreatedDate())%>
                    </div>
                    <div class="col-md-1 pull-right">
                        <button type="button" class="btn btn-default glyphicon glyphicon-pencil" data-toggle="modal" 
                                data-target="#editArticleModal"></button>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <%= article.getContent()%> <br/><br/>
                <div class="panel-group" id="accordion">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse<%= article.getArticleID()%>">
                                    <button type="button" class="btn btn-default glyphicon glyphicon-plus"></button>
                                </a>
                                Comments
                            </h4>
                        </div>
                        <div id="collapse<%= article.getArticleID()%>" class="panel-collapse collapse">
                            <div class="panel-body">
                                <%
                                    if (article.getComments() != null) {
                                        CommentList commentList = article.getComments();
                                        for (int x = 0; x < commentList.size(); x++) {
                                            Comment comment = commentList.getCommentAt(x);

                                            for (int y = 0; y < userList.size(); y++) {
                                                if (comment.getUserID() == userList.getUserAt(x).getUserID()) {
                                                    createdBy = userList.getUserAt(x).getUsername();
                                                }
                                            }
                                %>
                                <div class="well">
                                    Created by <%= createdBy%> on <%= ft.format(comment.getCreatedDate())%> <br/><br/>
                                    <%= comment.getContent()%>
                                    <% if (userSession != null && userSession.getUserID() == comment.getUserID() || userSession.getAccess().equals("admin")) {%>
                                    <div class="pull-right">
                                        <button type="button" class="btn btn-default glyphicon glyphicon-pencil" data-toggle="modal" 
                                                data-target="#editCommentModal" 
                                                onclick="setEditCommentData('<%= comment.getCommentID()%>',
                                                                '<%= comment.getContent()%>')"></button>
                                    </div>
                                    <div class="pull-right">
                                        <a href="<%=request.getContextPath()%>/adminProfiles/deleteComment/<%= comment.getCommentID()%>/<%= article.getArticleID() %> " id="deleteCommentSubmit" type="button" class="btn btn-default glyphicon glyphicon-remove"></a>
                                    </div>
                                    <% } %>
                                </div>
                                <% }
                                    }%>
                                <% if (userSession != null) {%>
                                <button type="button" class="btn btn-default" data-toggle="modal" 
                                        data-target="#addCommentModal" 
                                        onclick="setAddCommentData('<%= article.getArticleID()%>',
                                                        '<%= userSession.getUserID()%>')">
                                    Add Comment
                                </button>
                                <% }%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addCommentModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Add Comment</h4>
            </div>
            <div class="modal-body">
                <form id="addCommentForm" role="form" method="POST" action="<%=request.getContextPath()%>/adminProfiles/addComment">
                    <input type="hidden" name="articleID" id="articleID">
                    <input type="hidden" name="userID" id="userID">
                    <textarea class="form-control" name="comment" rows="3" placeholder="Type Comment Here"></textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="addCommentSubmit">Add Comment</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade" id="editCommentModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Edit Comment</h4>
            </div>
            <div class="modal-body">
                <form id="editCommentForm" role="form" method="POST" action="<%=request.getContextPath()%>/adminProfiles/editComment/<%= article.getArticleID() %>">
                    <input type="hidden" name="commentID" id="commentID">
                    <textarea class="form-control" name="comment" id="editComment" rows="3"></textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="editCommentSubmit">Add Comment</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade" id="editArticleModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Edit Article</h4>
            </div>
            <div class="modal-body">
                <form id="editArticleForm" role="form" method="POST" action="<%=request.getContextPath()%>/adminProfiles/editArticle">
                    <input type="hidden" name="articleID" id="articleID" value="<%= article.getArticleID()%>">
                    <div class="form-group">
                        <label for="title" class="col-sm-2 control-label">Title</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="title" name="title" placeholder="Enter title" value="<%= article.getTitle()%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="content" class="col-sm-2 control-label">Content</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" rows="4" id="content" name="content"><%= article.getContent()%></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="editArticleSubmit">Add Comment</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
    function setAddCommentData(articleID, userID) {
        $('#articleID').val(articleID);
        $('#userID').val(userID);
    }

    function setEditCommentData(commentID, comment) {
        $('#commentID').val(commentID);
        $('#editComment').val(comment);
    }
</script>