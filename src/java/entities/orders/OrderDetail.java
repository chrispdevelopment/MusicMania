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

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
public class OrderDetail {

    private int itemID;
    private int merchID;
    private int orderID;
    private int quantity;
    private String size;
    private double totalCost;

    public OrderDetail(int merchID, int quantity, String size, double totalCost) {
        this.merchID = merchID;
        this.quantity = quantity;
        this.size = size;
        this.totalCost = totalCost;
    }

    public OrderDetail(int itemID, int merchID, int orderID, int quantity, String size, double totalCost) {
        this.itemID = itemID;
        this.merchID = merchID;
        this.orderID = orderID;
        this.quantity = quantity;
        this.size = size;
        this.totalCost = totalCost;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getMerchID() {
        return merchID;
    }

    public void setMerchID(int merchID) {
        this.merchID = merchID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
