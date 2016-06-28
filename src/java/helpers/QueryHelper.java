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

package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
public class QueryHelper {
    private PreparedStatement pstmt;
    
    public boolean insert(Connection conn, String table, HashMap data) throws SQLException{
        String queryString = "INSERT INTO " + table + " (";
        
        Iterator i = data.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            if(i.hasNext()) {
                queryString = queryString.concat(me.getKey().toString() + ", ");
            } else {
                queryString = queryString.concat(me.getKey().toString() + ") ");
            }         
        }
        
        queryString = queryString.concat("VALUES (");
        
        Iterator i2 = data.entrySet().iterator();
        while(i2.hasNext()) {
            Map.Entry me = (Map.Entry)i2.next();
            if(i2.hasNext()) {
                queryString = queryString.concat("'" + me.getValue() + "', ");
            } else {
                queryString = queryString.concat("'" + me.getValue() + "')");
            }         
        }
        
        pstmt = conn.prepareStatement(queryString);
        pstmt.execute();
        
        return true;
    }
    
    public int insertReturnID(Connection conn, String table, HashMap data) throws SQLException{
        String queryString = "INSERT INTO " + table + " (";
        
        Iterator i = data.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            if(i.hasNext()) {
                queryString = queryString.concat(me.getKey().toString() + ", ");
            } else {
                queryString = queryString.concat(me.getKey().toString() + ") ");
            }         
        }
        
        queryString = queryString.concat("VALUES (");
        
        Iterator i2 = data.entrySet().iterator();
        while(i2.hasNext()) {
            Map.Entry me = (Map.Entry)i2.next();
            if(i2.hasNext()) {
                queryString = queryString.concat("'" + me.getValue() + "', ");
            } else {
                queryString = queryString.concat("'" + me.getValue() + "')");
            }         
        }
        
        pstmt = conn.prepareStatement(queryString);
        pstmt.execute();
        
        int generatedkey = -1;
        String queryString2 = "SELECT @@IDENTITY";
        pstmt = conn.prepareStatement(queryString2);
        ResultSet rSet = pstmt.executeQuery();

        if (rSet.next()) {
            generatedkey = rSet.getInt(1);
        }
        
        return generatedkey;
    }
    
    public boolean update(Connection conn, String table, HashMap data, HashMap where) throws SQLException{
        String queryString = "UPDATE " + table + " SET ";
        
        Iterator i = data.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            if(i.hasNext()) {
                queryString = queryString.concat(me.getKey().toString() + "='" 
                        + me.getValue() + "', ");
            } else {
                queryString = queryString.concat(me.getKey().toString() + "='" 
                        + me.getValue() + "' ");
            }         
        }
        
        queryString = queryString.concat("WHERE ");
        
        Iterator i2 = where.entrySet().iterator();
        while(i2.hasNext()) {
            Map.Entry me = (Map.Entry)i2.next();
            if(i2.hasNext()) {
                queryString = queryString.concat(me.getKey().toString() + "=" 
                        + me.getValue() + " AND ");
            } else {
                queryString = queryString.concat(me.getKey().toString() + "=" 
                        + me.getValue());
            }         
        }
        
        pstmt = conn.prepareStatement(queryString);
        pstmt.execute();
        
        return true;
    }
    
    public boolean delete(Connection conn, String table, HashMap where) throws SQLException{
        String queryString = "DELETE FROM " + table;
               
        queryString = queryString.concat(" WHERE ");
        
        Iterator i = where.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            if(i.hasNext()) {
                queryString = queryString.concat(me.getKey().toString() + "=" 
                        + me.getValue() + " AND ");
            } else {
                queryString = queryString.concat(me.getKey().toString() + "=" 
                        + me.getValue() + "");
            }         
        }
        
        pstmt = conn.prepareStatement(queryString);
        pstmt.execute();
        
        return true;
    }
}
