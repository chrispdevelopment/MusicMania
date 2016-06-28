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
public class User {
    private int userID;
    private String username;
    private String password;
    private String firstname;
    private String surname;
    private String address;
    private String postCode;
    private String phone;
    private String email;
    private String accessLevel;
    private Date dOB;
    private SubscriptionList subscriptions;
    private Account account;

    public User() {}
    
    public User(int userID, String username, String password, String firstname, 
            String surname, String address, String postCode, String phone, 
            String email, String accessLevel, Date dOB) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.postCode = postCode;
        this.phone = phone;
        this.email = email;
        this.accessLevel = accessLevel;
        this.dOB = dOB;
    }
   
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Date getdOB() {
        return dOB;
    }

    public void setdOB(Date dOB) {
        this.dOB = dOB;
    }

    public SubscriptionList getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(SubscriptionList subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
