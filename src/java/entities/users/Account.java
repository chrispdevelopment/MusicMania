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

package entities.users;

import java.sql.Date;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
public class Account {
    private int userID;
    private String cardNumber;
    private Date cardDOE;
    private String nameOnCard;
    private double balance;
    private String deliveryAddress;
    private String billingAddress;

    public Account(int userID, String cardNumber, Date cardDOE, String nameOnCard, 
            double balance, String deliveryAddress, String billingAddress) {
        this.userID = userID;
        this.cardNumber = cardNumber;
        this.cardDOE = cardDOE;
        this.nameOnCard = nameOnCard;
        this.balance = balance;
        this.deliveryAddress = deliveryAddress;
        this.billingAddress = billingAddress;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getCardDOE() {
        return cardDOE;
    }

    public void setCardDOE(Date cardDOE) {
        this.cardDOE = cardDOE;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    
}
