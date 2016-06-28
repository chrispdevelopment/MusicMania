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

import entities.users.Account;
import entities.users.AccountList;
import entities.users.Subscription;
import entities.users.SubscriptionList;
import entities.users.User;
import entities.users.UserList;
import helpers.QueryHelper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
public class Users {

    private PreparedStatement pstmt;
    private ResultSet rset;
    private final QueryHelper queryHelper = new QueryHelper();

    public UserList getAllUsers(Connection conn) throws SQLException, ClassNotFoundException {
        String queryString;
        UserList users;
        SubscriptionList subs;
        Account account;

        queryString = "SELECT * FROM USERS ORDER BY username ASC";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        users = new UserList();
        while (rset.next()) {
            User user = new User(rset.getInt(1), rset.getString(2),
                    rset.getString(3), rset.getString(4), rset.getString(5),
                    rset.getString(6), rset.getString(7), rset.getString(8),
                    rset.getString(9), rset.getString(10), rset.getDate(11));

            subs = this.getSubscriptions(conn, user.getUserID());
            account = this.getAccount(conn, user.getUserID());

            if (subs.size() > 0) {
                user.setSubscriptions(subs);
            }
            user.setAccount(account);

            users.addUser(user);
        }

        return users;
    }
    
    public User getUser(Connection conn, int userID) throws SQLException, ClassNotFoundException {
        String queryString;
        User user;

        queryString = "SELECT * FROM USERS WHERE userID=" + userID;
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        rset.next();
        user = new User(rset.getInt(1), rset.getString(2),
                rset.getString(3), rset.getString(4), rset.getString(5),
                rset.getString(6), rset.getString(7), rset.getString(8),
                rset.getString(9), rset.getString(10), rset.getDate(11));
        
        Account account = this.getAccount(conn, user.getUserID());
        user.setAccount(account);

        return user;
    }

    public User getUserEmail(Connection conn, String email) throws SQLException, ClassNotFoundException {
        String queryString;
        User user;

        queryString = "SELECT * FROM USERS WHERE email='" + email + "'";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        rset.next();
        user = new User(rset.getInt(1), rset.getString(2),
                rset.getString(3), rset.getString(4), rset.getString(5),
                rset.getString(6), rset.getString(7), rset.getString(8),
                rset.getString(9), rset.getString(10), rset.getDate(11));

        return user;
    }

    public void createUser(Connection conn, User user) throws SQLException, ClassNotFoundException {
        String table = "USERS";
        HashMap data = new HashMap();
        
        data.put("username", user.getUsername());
        data.put("password", user.getPassword());
        data.put("firstname", user.getFirstname());
        data.put("surname", user.getSurname());
        data.put("address", user.getAddress());
        data.put("postCode", user.getPostCode());
        data.put("phone", user.getPhone());
        data.put("email", user.getEmail());
        data.put("dOB", user.getdOB());
        
        int lastKey = queryHelper.insertReturnID(conn, table, data);
        
        this.createAccount(conn, lastKey);
    }
    
    public boolean updateUserPassword(Connection conn, String password, int userID) throws SQLException, ClassNotFoundException {
        String table = "USERS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("password", password);
        where.put("userID", userID);
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean updateUserEmail(Connection conn, String email, int userID) throws SQLException, ClassNotFoundException {
        String table = "USERS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("email", email);
        where.put("userID", userID);
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean updateUserAccess(Connection conn, String accessLevel, int userID) throws SQLException, ClassNotFoundException {
        String table = "USERS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("accessLevel", accessLevel);
        where.put("userID", userID);
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean updateUserInfo(Connection conn, String firstname, String surname, Date dob, int userID) throws SQLException, ClassNotFoundException {
        String table = "USERS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("firstname", firstname);
        data.put("surname", surname);
        data.put("dOB", dob);
        where.put("userID", userID);
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean updateUserAddInfo(Connection conn, String address, String postCode, String phone, int userID) throws SQLException, ClassNotFoundException {
        String table = "USERS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("address", address);
        data.put("postCode", postCode);
        data.put("phone", phone);
        where.put("userID", userID);
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean deleteUser(Connection conn, int userID) throws SQLException, ClassNotFoundException {
        String table = "USERS";
        HashMap where = new HashMap();
        
        where.put("userID", userID);
        
        this.deleteAccount(conn, userID);
        this.deleteUsersSubscriptions(conn, userID);
        
        return queryHelper.delete(conn, table, where);      
    }

    public SubscriptionList getSubscriptions(Connection conn, int userID) throws SQLException, ClassNotFoundException {
        ResultSet rsetP;
        String queryString;
        SubscriptionList subs;

        queryString = "SELECT * FROM SUBSCRIPTIONS WHERE userID =" + userID;
        pstmt = conn.prepareStatement(queryString);
        rsetP = pstmt.executeQuery();

        if (rsetP != null) {
            subs = new SubscriptionList();
            while (rsetP.next()) {
                subs.addSubscription(new Subscription(rsetP.getInt(1), rsetP.getInt(2), rsetP.getString(3)));
            }
        } else {
            subs = null;
        }

        return subs;
    }

    public boolean createSubscription(Connection conn, Subscription sub) throws SQLException, ClassNotFoundException {
        String table = "SUBSCRIPTIONS";
        HashMap data = new HashMap();
        
        data.put("userID", sub.getUserID());
        data.put("subToID", sub.getSubToID());
        data.put("type", sub.getType());
        
        return queryHelper.insert(conn, table, data);
    }

    public boolean deleteSubscription(Connection conn, int userID, int subToID) throws SQLException, ClassNotFoundException {        
        String table = "SUBSCRIPTIONS";
        HashMap where = new HashMap();
        
        where.put("userID", userID);
        where.put("subToID", subToID);
        
        return queryHelper.delete(conn, table, where); 
    }

    public boolean deleteUsersSubscriptions(Connection conn, int userID) throws SQLException, ClassNotFoundException {        
        String table = "SUBSCRIPTIONS";
        HashMap where = new HashMap();
        
        where.put("userID", userID);
        
        return queryHelper.delete(conn, table, where); 
    }

    public AccountList getAllAccounts(Connection conn) throws SQLException, ClassNotFoundException {
        AccountList accountList = new AccountList();
        String queryString;

        queryString = "SELECT * FROM ACCOUNTS";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        while (rset.next()) {
            Account account = new Account(rset.getInt(1), rset.getString(2),
                    rset.getDate(3), rset.getString(4), rset.getDouble(5),
                    rset.getString(6), rset.getString(7));

            accountList.addAccount(account);
        }
        
        return accountList;
    }
    
    public Account getAccount(Connection conn, int userID) throws SQLException, ClassNotFoundException {
        String queryString;
        ResultSet rset2;

        queryString = "SELECT * FROM ACCOUNTS WHERE userID=" + userID;
        pstmt = conn.prepareStatement(queryString);
        rset2 = pstmt.executeQuery();

        while (rset2.next()) {
            Account account = new Account(rset2.getInt(1), rset2.getString(2),
                    rset2.getDate(3), rset2.getString(4), rset2.getDouble(5),
                    rset2.getString(6), rset2.getString(7));
            
            return account;
        }
        return null;
    }

    public boolean createAccount(Connection conn, int userID) throws SQLException, ClassNotFoundException { 
        String table = "ACCOUNTS";
        HashMap data = new HashMap();
        
        data.put("userID", userID);
        
        return queryHelper.insert(conn, table, data);
    }
    
    public boolean updateAccountBalance(Connection conn, double amount, int userID) throws SQLException, ClassNotFoundException {        
        String table = "ACCOUNTS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("balance", amount);
        where.put("userID", userID);
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean updateAccountCardDetails(Connection conn, String cardNum, Date doe, String nameOnCard, int userID) throws SQLException, ClassNotFoundException {        
        String table = "ACCOUNTS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("cardNumber", cardNum);
        data.put("cardDOE", doe);
        data.put("nameOnCard", nameOnCard);
        where.put("userID", userID);
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean updateAccountDelAddress(Connection conn, String delAddress, int userID) throws SQLException, ClassNotFoundException {        
        String table = "ACCOUNTS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("deliveryAddress", delAddress);
        where.put("userID", userID);
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean updateAccountBilAddress(Connection conn, String bilAddress, int userID) throws SQLException, ClassNotFoundException {        
        String table = "ACCOUNTS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("billingAddress", bilAddress);
        where.put("userID", userID);
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean deleteAccount(Connection conn, int userID) throws SQLException, ClassNotFoundException {        
        String table = "ACCOUNTS";
        HashMap where = new HashMap();
        
        where.put("userID", userID);
        
        return queryHelper.delete(conn, table, where); 
    }
}
