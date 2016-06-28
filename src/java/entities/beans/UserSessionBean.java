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

package entities.beans;

import entities.users.SubscriptionList;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
public class UserSessionBean {

    private int userID;
    private String username;
    private String access;
    private boolean loggedIn;
    private SubscriptionList subscriptions;
    private double balance;

    public UserSessionBean(int userID, String username, String access, boolean loggedIn, SubscriptionList subscriptions, double balance) {
        this.userID = userID;
        this.username = username;
        this.access = access;
        this.loggedIn = loggedIn;
        this.subscriptions = subscriptions;
        this.balance = balance;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SubscriptionList getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(SubscriptionList subscriptions) {
        this.subscriptions = null;
        this.subscriptions = subscriptions;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
