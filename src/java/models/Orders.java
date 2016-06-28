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

import entities.events.Event;
import entities.orders.Order;
import entities.orders.OrderDetail;
import entities.orders.OrderDetailList;
import entities.orders.OrderList;
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
public class Orders {

    private PreparedStatement pstmt;
    private ResultSet rset;
    private final QueryHelper queryHelper = new QueryHelper();

    public OrderList getAllOrder(Connection conn) throws SQLException, ClassNotFoundException {
        String queryString;
        OrderList orders = new OrderList();
        OrderDetailList orderDetails;

        queryString = "SELECT * FROM ORDERS ORDER BY createdDate DESC";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            Order order = new Order(rset.getInt(1), rset.getInt(2),
                    rset.getDouble(3), rset.getDate(4), rset.getString(5), 
                    rset.getDate(6));

            orderDetails = this.getOrderDetails(conn, order.getOrderID());

            order.setOrderDetails(orderDetails);

            orders.addOrder(order);
        }

        return orders;
    }

    public OrderList getUserOrders(Connection conn, int userID) throws SQLException, ClassNotFoundException {
        String queryString;
        OrderList orders = new OrderList();
        OrderDetailList orderDetails;

        queryString = "SELECT * FROM ORDERS WHERE userID=" + userID + " ORDER BY createdDate DESC";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            Order order = new Order(rset.getInt(1), rset.getInt(2),
                    rset.getDouble(3), rset.getDate(4), rset.getString(5), 
                    rset.getDate(6));

            orderDetails = this.getOrderDetails(conn, order.getOrderID());

            order.setOrderDetails(orderDetails);

            orders.addOrder(order);
        }

        return orders;
    }
    
    public OrderList getOrderByStatus(Connection conn, String completed) throws SQLException, ClassNotFoundException {
        String queryString;
        OrderList orders = new OrderList();
        OrderDetailList orderDetails;

        queryString = "SELECT * FROM ORDERS WHERE completed='" + completed + "' ORDER BY createdDate DESC";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            Order order = new Order(rset.getInt(1), rset.getInt(2),
                    rset.getDouble(3), rset.getDate(4), rset.getString(5), 
                    rset.getDate(6));

            orderDetails = this.getOrderDetails(conn, order.getOrderID());

            order.setOrderDetails(orderDetails);

            orders.addOrder(order);
        }

        return orders;
    }

    public int createOrder(Connection conn, Order order) throws SQLException, ClassNotFoundException {
        String table = "ORDERS";
        HashMap data = new HashMap();

        data.put("userID", order.getUserID());
        data.put("totalCost", order.getTotalCost());
        data.put("completed", "No");

        return queryHelper.insertReturnID(conn, table, data);
    }
    
    public boolean updateOrder(Connection conn, int orderID, String completed) throws SQLException {
        String table = "ORDERS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();

        data.put("completed", completed);
        where.put("orderID", orderID);

        return queryHelper.update(conn, table, data, where);
    }

    public boolean deleteEvent(Connection conn, int orderID) throws SQLException {
        String table = "ORDERS";
        HashMap where = new HashMap();

        where.put("orderID", orderID);

        this.deleteOrderDetails(conn, orderID);

        return queryHelper.delete(conn, table, where);
    }

    public OrderDetailList getOrderDetails(Connection conn, int orderID) throws SQLException, ClassNotFoundException {
        String queryString;
        ResultSet rset2;
        OrderDetailList orderDetails = new OrderDetailList();

        queryString = "SELECT * FROM ORDERDETAILS WHERE orderID=" + orderID + " ORDER BY totalCost DESC";
        pstmt = conn.prepareStatement(queryString);
        rset2 = pstmt.executeQuery();
        while (rset2.next()) {
            OrderDetail item = new OrderDetail(rset2.getInt(1), rset2.getInt(2),
                    rset2.getInt(3), rset2.getInt(4), rset2.getString(5),
                    rset2.getDouble(6));

            orderDetails.addOrderDetail(item);
        }

        return orderDetails;
    }

    public boolean createOrderDetail(Connection conn, OrderDetail item) throws SQLException, ClassNotFoundException {
        String table = "ORDERDETAILS";
        HashMap data = new HashMap();

        data.put("merchID", item.getMerchID());
        data.put("orderID", item.getOrderID());
        data.put("quantity", item.getQuantity());
        data.put("size", item.getSize());
        data.put("totalCost", item.getTotalCost());

        return queryHelper.insert(conn, table, data);
    }
    
    private boolean deleteOrderDetails(Connection conn, int orderID) throws SQLException {
        String table = "ORDERDETAILS";
        HashMap where = new HashMap();

        where.put("orderID", orderID);

        return queryHelper.delete(conn, table, where);
    }
}
