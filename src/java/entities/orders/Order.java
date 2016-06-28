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

package entities.orders;

import java.sql.Date;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
public class Order {
    private int orderID;
    private int userID;
    private double totalCost;
    private Date createdDate;
    private String completed;
    private Date completedDate;
    private OrderDetailList orderDetails;

    public Order() {
    }

    public Order(int orderID, int userID, double totalCost, Date createdDate, 
            String completed, Date completedDate) {
        this.orderID = orderID;
        this.userID = userID;
        this.totalCost = totalCost;
        this.createdDate = createdDate;
        this.completed = completed;
        this.completedDate = completedDate;
    }
    
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public OrderDetailList getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetailList orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }
}
