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

import entities.mechandise.Merchandise;
import entities.mechandise.MerchandiseList;
import entities.mechandise.Size;
import entities.mechandise.SizeList;
import entities.mechandise.Type;
import entities.mechandise.TypeList;
import helpers.QueryHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Problems with result sets and pulling ints using getInt has caused me to have 
 * to use separate varibles to hold ints before passing them into the classes.
 * This only seems to be effecting the merchandise and types tables, some super 
 * wierd stuff going on.
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
public class Merch {

    private PreparedStatement pstmt;
    private ResultSet rset;
    private final QueryHelper queryHelper = new QueryHelper();

    public MerchandiseList getAllMerch(Connection conn) 
            throws SQLException, ClassNotFoundException {
        String queryString;
        MerchandiseList merchList = new MerchandiseList();

        queryString = "SELECT * FROM MERCHANDISE";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        while (rset.next()) {
            int int1 = rset.getInt(1);
            int int2 = rset.getInt(2);
            int int3 = rset.getInt(3);
            Merchandise merch = new Merchandise(int1, int2,
                    int3, rset.getString(4), rset.getString(5),
                    rset.getString(6), rset.getDouble(7), rset.getString(8), 
                    rset.getString(9));

            merchList.addMerchandise(merch);
        }

        return merchList;
    }
    
    public Merchandise getMerch(Connection conn, int merchID) 
            throws SQLException, ClassNotFoundException {
        String queryString;
        Merchandise merch;

        queryString = "SELECT * FROM MERCHANDISE WHERE merchID=" + merchID;
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        while (rset.next()) {
            int int1 = rset.getInt(1);
            int int2 = rset.getInt(2);
            int int3 = rset.getInt(3);
            merch = new Merchandise(int1, int2,
                    int3, rset.getString(4), rset.getString(5),
                    rset.getString(6), rset.getDouble(7), rset.getString(8), 
                    rset.getString(9));

            return merch;
        }

        return null;
    }
    
    public MerchandiseList getMerchByType(Connection conn, int typeID) 
            throws SQLException, ClassNotFoundException {
        String queryString;
        MerchandiseList merchList = new MerchandiseList();

        queryString = "SELECT * FROM MERCHANDISE WHERE typeID =" + typeID;
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        while (rset.next()) {
            int int1 = rset.getInt(1);
            int int2 = rset.getInt(2);
            int int3 = rset.getInt(3);
            Merchandise merch = new Merchandise(int1, int2,
                    int3, rset.getString(4), rset.getString(5),
                    rset.getString(6), rset.getDouble(7), rset.getString(8), 
                    rset.getString(9));

            merchList.addMerchandise(merch);
        }

        return merchList;
    }
    
    public MerchandiseList getArtistMerch(Connection conn, int typeID, 
            int artistID, String artistType) 
            throws SQLException, ClassNotFoundException {
        String queryString;
        MerchandiseList merchList = new MerchandiseList();

        queryString = "SELECT * FROM MERCHANDISE WHERE typeID =" + 
                typeID + " AND artistID=" + 
                artistID + " AND artistType='" + artistType + "'";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        while (rset.next()) {
            int int1 = rset.getInt(1);
            int int2 = rset.getInt(2);
            int int3 = rset.getInt(3);
            Merchandise merch = new Merchandise(int1, int2,
                    int3, rset.getString(4), rset.getString(5),
                    rset.getString(6), rset.getDouble(7), rset.getString(8), 
                    rset.getString(9));

            merchList.addMerchandise(merch);
        }

        return merchList;
    }

    public void createMerch(Connection conn, Merchandise merch) 
            throws SQLException, ClassNotFoundException {
        String table = "MERCHANDISE";
        HashMap data = new HashMap();
        
        data.put("typeID", merch.getTypeID());
        data.put("artistID", merch.getArtistID());
        data.put("artistType", merch.getArtistType());
        data.put("name", merch.getName());
        data.put("description", merch.getDescription());
        data.put("price", merch.getPrice());
        data.put("picName", merch.getPicName());
        data.put("sale", merch.getSale());
        
        queryHelper.insert(conn, table, data);
    }
    
    public boolean updateMerch(Connection conn, Merchandise merch) 
            throws SQLException, ClassNotFoundException {
        String table = "MERCHANDISE";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("typeID", merch.getTypeID());
        data.put("artistID", merch.getArtistID());
        data.put("artistType", merch.getArtistType());
        data.put("name", merch.getName());
        data.put("description", merch.getDescription());
        data.put("price", merch.getPrice());
        data.put("picName", merch.getPicName());
        data.put("sale", merch.getSale());
        where.put("merchID", merch.getMerchID());
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean deleteMerch(Connection conn, int merchID) 
            throws SQLException, ClassNotFoundException {
        String table = "MERCHANDISE";
        HashMap where = new HashMap();
        
        where.put("merchID", merchID);
        
        return queryHelper.delete(conn, table, where);      
    }
    
    public TypeList getTypes(Connection conn) 
            throws SQLException, ClassNotFoundException {
        String queryString;
        TypeList types = new TypeList();

        queryString = "SELECT * FROM TYPES";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        while (rset.next()) {
            int id = rset.getInt(1);
            Type type = new Type(id, rset.getString(2), rset.getString(3));

            types.addType(type);
        }

        return types;
    }
    
    public Type getType(Connection conn, int typeID) 
            throws SQLException, ClassNotFoundException {
        String queryString;

        queryString = "SELECT * FROM TYPES WHERE typeID=" + typeID;
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        while (rset.next()) {
            int id = rset.getInt(1);
            Type type = new Type(id, rset.getString(2), rset.getString(3));

            return type;
        }
        return null;
    }

    public TypeList getSaleTypes(Connection conn) 
            throws SQLException, ClassNotFoundException {
        String queryString;
        TypeList types;
        ResultSet rsetT;
        MerchandiseList merch = this.getAllMerch(conn);

        queryString = "SELECT * FROM TYPES";
        pstmt = conn.prepareStatement(queryString);
        rsetT = pstmt.executeQuery();

        types = new TypeList();
        while (rsetT.next()) {
            int id = rsetT.getInt(1);
            for (int x = 0; x < merch.size(); x++) {
                if (id == merch.getMerchAt(x).getTypeID() && 
                        merch.getMerchAt(x).getSale().equals("True")) {

                    Type type = new Type(id, rsetT.getString(2), rsetT.getString(3));

                    types.addType(type);
                    break;
                }
            }
        }

        return types;
    }
    
    public TypeList getArtistTypes(Connection conn, int artistID, String artistType) 
            throws SQLException, ClassNotFoundException {
        String queryString;
        TypeList types;
        ResultSet rsetT;
        MerchandiseList merch = this.getAllMerch(conn);

        queryString = "SELECT * FROM TYPES";
        pstmt = conn.prepareStatement(queryString);
        rsetT = pstmt.executeQuery();

        types = new TypeList();
        while (rsetT.next()) {
            int id = rsetT.getInt(1);
            for (int x = 0; x < merch.size(); x++) {
                if (id == merch.getMerchAt(x).getTypeID() && 
                        artistID == merch.getMerchAt(x).getArtistID() && 
                        merch.getMerchAt(x).getArtistType().equals(artistType)) {

                    Type type = new Type(id, rsetT.getString(2), rsetT.getString(3));

                    types.addType(type);
                    break;
                }
            }
        }

        return types;
    }
    
    public void createType(Connection conn, Type type) 
            throws SQLException, ClassNotFoundException {
        String table = "TYPES";
        HashMap data = new HashMap();
        
        data.put("name", type.getName());
        
        queryHelper.insert(conn, table, data);
    }
    
    public boolean updateType(Connection conn, Type type) 
            throws SQLException, ClassNotFoundException {
        String table = "TYPES";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("name", type.getName());
        where.put("typeID", type.getTypeID());
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean deleteType(Connection conn, int typeID) 
            throws SQLException, ClassNotFoundException {
        String table = "TYPES";
        HashMap where = new HashMap();
        
        where.put("typeID", typeID);
        
        return queryHelper.delete(conn, table, where);      
    }
    
    public SizeList getAllSizes(Connection conn) 
            throws SQLException, ClassNotFoundException {
        String queryString;
        SizeList sizeList = new SizeList();

        queryString = "SELECT * FROM SIZES";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        while (rset.next()) {
            Size size = new Size(rset.getInt(1), rset.getString(2));

            sizeList.addSize(size);
        }

        return sizeList;
    }
    
    public void createSize(Connection conn, Size size) 
            throws SQLException, ClassNotFoundException {
        String table = "SIZES";
        HashMap data = new HashMap();
        
        data.put("size", size.getSize());
        
        queryHelper.insert(conn, table, data);
    }
    
    public boolean updateSize(Connection conn, Size size) 
            throws SQLException, ClassNotFoundException {
        String table = "SIZES";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("size", size.getSize());
        where.put("sizeID", size.getSizeID());
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean deleteSize(Connection conn, int sizeID) 
            throws SQLException, ClassNotFoundException {
        String table = "SIZES";
        HashMap where = new HashMap();
        
        where.put("sizeID", sizeID);
        
        return queryHelper.delete(conn, table, where);      
    }
}
