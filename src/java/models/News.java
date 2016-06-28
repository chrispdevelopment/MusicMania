/*
 * Copyright (C) 2014 Chris Pratt <www.chrispdevelopment.co.uk>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package models;

import entities.news.Article;
import entities.news.ArticleList;
import entities.news.Comment;
import entities.news.CommentList;
import helpers.QueryHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
public class News {

    private PreparedStatement pstmt;
    private ResultSet rset;
    private final QueryHelper queryHelper = new QueryHelper();

    public ArticleList getAllArticles(Connection conn) throws SQLException, ClassNotFoundException {
        String queryString;
        ArticleList articles = new ArticleList();
        CommentList comments;

        queryString = "SELECT * FROM ARTICLES ORDER BY createdDate DESC";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            Article article = new Article(rset.getInt(1), rset.getInt(2),
                    rset.getString(3), rset.getString(4), rset.getDate(5));

            comments = this.articleComments(conn, article.getArticleID());
            if (comments.size() > 0) {
                article.setComments(comments);
            }

            articles.addArticle(article);
        }
        return articles;
    }
    
    public ArticleList getTopArticles(Connection conn) throws SQLException, ClassNotFoundException {
        String queryString;
        ArticleList articles = new ArticleList();
        CommentList comments;

        queryString = "SELECT TOP 4 * FROM ARTICLES ORDER BY createdDate DESC";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            Article article = new Article(rset.getInt(1), rset.getInt(2),
                    rset.getString(3), rset.getString(4), rset.getDate(5));

            comments = this.articleComments(conn, article.getArticleID());
            if (comments.size() > 0) {
                article.setComments(comments);
            }

            articles.addArticle(article);
        }
        return articles;
    }
    
    public Article getArticle(Connection conn, int articleID) throws SQLException, ClassNotFoundException {
        String queryString;
        CommentList comments;

        queryString = "SELECT * FROM ARTICLES WHERE articleID=" + articleID;
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            Article article = new Article(rset.getInt(1), rset.getInt(2),
                    rset.getString(3), rset.getString(4), rset.getDate(5));

            comments = this.articleComments(conn, article.getArticleID());
            if (comments.size() > 0) {
                article.setComments(comments);
            }
            return article;
        }
        return null;
    }

    public boolean createArticle(Connection conn, Article article) throws SQLException {
        String table = "ARTICLES";
        HashMap data = new HashMap();
        
        data.put("userID", article.getUserID());
        data.put("title", article.getTitle());
        data.put("content", article.getContent());
        
        return queryHelper.insert(conn, table, data);
    }

    public boolean updateArticle(Connection conn, Article article) throws SQLException {
        String table = "ARTICLES";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("title", article.getTitle());
        data.put("content", article.getContent());
        where.put("articleID", article.getArticleID());
        
        return queryHelper.update(conn, table, data, where);
    }

    public boolean deleteArticle(Connection conn, int articleID) throws SQLException {
        String table = "ARTICLES";
        HashMap where = new HashMap();
        
        where.put("articleID", articleID);
        
        this.deleteArticlesComments(conn, articleID);
        
        return queryHelper.delete(conn, table, where);      
    }

    public CommentList articleComments(Connection conn, int articleID) throws SQLException, ClassNotFoundException {
        String queryString;
        CommentList comments;
        ResultSet results;

        queryString = "SELECT * FROM COMMENTS WHERE articleID = " + articleID + " ORDER BY createdDate ASC";
        pstmt = conn.prepareStatement(queryString);
        results = pstmt.executeQuery();

        if (results != null) {
            comments = new CommentList();
            while (results.next()) {
                comments.addComment(new Comment(results.getInt(1), results.getInt(2),
                        results.getInt(3), results.getString(4), results.getDate(5)));
            }
        } else {
            comments = null;
        }

        return comments;
    }

    public boolean createComment(Connection conn, Comment comment) throws SQLException {
        String table = "COMMENTS";
        HashMap data = new HashMap();
        
        data.put("articleID", comment.getArticleID());
        data.put("userID", comment.getUserID());
        data.put("content", comment.getContent());
        
        return queryHelper.insert(conn, table, data);
    }

    public boolean updateComment(Connection conn, Comment comment) throws SQLException {       
        String table = "COMMENTS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("content", comment.getContent());
        where.put("commentID", comment.getCommentID());
        
        return queryHelper.update(conn, table, data, where);
    }

    public boolean deleteComment(Connection conn, int commentID) throws SQLException {
        String table = "COMMENTS";
        HashMap where = new HashMap();
        
        where.put("commentID", commentID);
        
        return queryHelper.delete(conn, table, where);  
    }

    private boolean deleteArticlesComments(Connection conn, int articleID) throws SQLException {       
        String table = "COMMENTS";
        HashMap where = new HashMap();
        
        where.put("articleID", articleID);
        
        return queryHelper.delete(conn, table, where);  
    }
}
