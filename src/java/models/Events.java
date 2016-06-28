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
import entities.events.EventLineup;
import entities.events.EventLineupList;
import entities.events.EventList;
import entities.events.Location;
import entities.events.LocationList;
import helpers.QueryHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
public class Events {

    private PreparedStatement pstmt;
    private ResultSet rset;
    private final QueryHelper queryHelper = new QueryHelper();

    public EventList getAllEvents(Connection conn) throws SQLException, ClassNotFoundException {
        String queryString;
        EventList events = new EventList();

        queryString = "SELECT * FROM EVENTS ORDER BY eventDate ASC";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            Event event = new Event(rset.getInt(1), rset.getInt(2),
                    rset.getString(3), rset.getDate(4), rset.getTime(5),
                    rset.getTime(6), rset.getDouble(7), rset.getString(8),
                    rset.getString(9));

            events.addEvent(event);
        }

        return events;
    }

    public EventList getAllLatestEvents(Connection conn) throws SQLException, ClassNotFoundException {
        String queryString;
        EventList events = new EventList();

        queryString = "SELECT * FROM EVENTS WHERE eventDate > NOW() ORDER BY eventDate ASC";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            Event event = new Event(rset.getInt(1), rset.getInt(2),
                    rset.getString(3), rset.getDate(4), rset.getTime(5),
                    rset.getTime(6), rset.getDouble(7), rset.getString(8),
                    rset.getString(9));

            events.addEvent(event);
        }

        return events;
    }

    public EventList getArtistEvents(Connection conn, int actID) throws SQLException, ClassNotFoundException {
        String queryString;
        EventList events = new EventList();

        queryString = "SELECT * FROM EVENTLINEUP WHERE actID=" + actID;
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            Date todayDate = new Date();

            Event event = this.getEvent(conn, rset.getInt(1));

            if (event.getDate().after(todayDate)) {
                events.addEvent(event);
            }
        }

        return events;
    }

    public Event getEvent(Connection conn, int eventID) throws SQLException, ClassNotFoundException {
        String queryString;
        ResultSet rsetE;

        queryString = "SELECT * FROM EVENTS WHERE eventID=" + eventID;
        pstmt = conn.prepareStatement(queryString);
        rsetE = pstmt.executeQuery();

        while (rsetE.next()) {
            Event event = new Event(rsetE.getInt(1), rsetE.getInt(2),
                    rsetE.getString(3), rsetE.getDate(4), rsetE.getTime(5),
                    rsetE.getTime(6), rsetE.getDouble(7), rsetE.getString(8),
                    rsetE.getString(9));

            return event;
        }

        return null;
    }

    public Event getEventWithLineup(Connection conn, int eventID) throws SQLException, ClassNotFoundException {
        String queryString;
        ResultSet rsetE;
        ResultSet rsetL;
        EventLineupList eventLineupList = new EventLineupList();

        queryString = "SELECT * FROM EVENTS WHERE eventID =" + eventID;
        pstmt = conn.prepareStatement(queryString);
        rsetE = pstmt.executeQuery();

        queryString = "SELECT * FROM EVENTLINEUP WHERE eventID =" + eventID;
        pstmt = conn.prepareStatement(queryString);
        rsetL = pstmt.executeQuery();

        while (rsetE.next()) {
            Event event = new Event(rsetE.getInt(1), rsetE.getInt(2),
                    rsetE.getString(3), rsetE.getDate(4), rsetE.getTime(5),
                    rsetE.getTime(6), rsetE.getDouble(7), rsetE.getString(8),
                    rsetE.getString(9));

            while (rsetL.next()) {
                EventLineup eventLineup = new EventLineup(rsetL.getInt(1),
                        rsetL.getInt(2), rsetL.getString(3));

                eventLineupList.addEventLineup(eventLineup);

                event.setEventLineup(eventLineupList);
            }

            return event;
        }

        return null;
    }

    public EventList getLocationsEvents(Connection conn, int locationID) throws SQLException, ClassNotFoundException {
        String queryString;
        EventList events = new EventList();

        queryString = "SELECT * FROM EVENTS WHERE locationID=" + locationID;
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            Event event = new Event();
            
            event.setEventID(rset.getInt(1));
            
            events.addEvent(event);
        }

        return events;
    }

    public boolean createEvent(Connection conn, Event event) throws SQLException {
        String table = "EVENTS";
        HashMap data = new HashMap();

        data.put("locationID", event.getLocationID());
        data.put("name", event.getName());
        data.put("eventDate", event.getDate());
        data.put("start", event.getStart());
        data.put("end", event.getEnd());
        data.put("price", event.getPrice());
        data.put("description", event.getDescription());
        data.put("eventPic", event.getEventPic());

        return queryHelper.insert(conn, table, data);
    }

    public boolean updateEvent(Connection conn, Event event) throws SQLException {
        String table = "EVENTS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();

        data.put("locationID", event.getLocationID());
        data.put("name", event.getName());
        data.put("date", event.getDate());
        data.put("start", event.getStart());
        data.put("end", event.getEnd());
        data.put("price", event.getPrice());
        data.put("description", event.getDescription());
        data.put("eventPic", event.getEventPic());
        where.put("eventID", event.getEventID());

        return queryHelper.update(conn, table, data, where);
    }

    public boolean deleteEvent(Connection conn, int eventID) throws SQLException {
        String table = "EVENTS";
        HashMap where = new HashMap();

        where.put("eventID", eventID);

        this.deleteLineups(conn, eventID);

        return queryHelper.delete(conn, table, where);
    }

    private boolean deleteEventForLocation(Connection conn, int locationID) throws SQLException, ClassNotFoundException {
        String table = "EVENTS";
        HashMap where = new HashMap();

        EventList events = this.getLocationsEvents(conn, locationID);
        
        for(int x = 0; x < events.size(); x++) {
            this.deleteLineups(conn, events.getEventAt(x).getEventID());
        }
        
        where.put("locationID", locationID);

        return queryHelper.delete(conn, table, where);
    }

    public LocationList getAllLocations(Connection conn) throws SQLException, ClassNotFoundException {
        String queryString;
        LocationList locations = new LocationList();

        queryString = "SELECT * FROM LOCATIONS ORDER BY name";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        while (rset.next()) {
            Location location = new Location(rset.getInt(1), rset.getString(2),
                    rset.getString(3), rset.getString(4), rset.getString(5),
                    rset.getString(6), rset.getString(7), rset.getString(8));

            locations.addLocation(location);
        }

        return locations;
    }

    public Location getEventLocation(Connection conn, int locationID) throws SQLException, ClassNotFoundException {
        String queryString;

        queryString = "SELECT * FROM LOCATIONS WHERE locationID =" + locationID;
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        while (rset.next()) {
            Location location = new Location(rset.getInt(1), rset.getString(2),
                    rset.getString(3), rset.getString(4), rset.getString(5),
                    rset.getString(6), rset.getString(7), rset.getString(8));

            return location;
        }

        return null;
    }

    public boolean createLocation(Connection conn, Location location) throws SQLException {
        String table = "LOCATIONS";
        HashMap data = new HashMap();

        data.put("name", location.getName());
        data.put("address", location.getAddress());
        data.put("postCode", location.getPostCode());
        data.put("email", location.getEmail());
        data.put("phone", location.getPhone());
        data.put("url", location.getUrl());
        data.put("description", location.getDescription());

        return queryHelper.insert(conn, table, data);
    }

    public boolean updateLocation(Connection conn, Location location) throws SQLException {
        String table = "LOCATIONS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();

        data.put("name", location.getName());
        data.put("address", location.getAddress());
        data.put("postCode", location.getPostCode());
        data.put("email", location.getEmail());
        data.put("phone", location.getPhone());
        data.put("url", location.getUrl());
        data.put("description", location.getDescription());
        where.put("locationID", location.getLocationID());

        return queryHelper.update(conn, table, data, where);
    }

    public boolean deleteLocation(Connection conn, int locationID) throws SQLException, ClassNotFoundException {
        String table = "LOCATIONS";
        HashMap where = new HashMap();

        where.put("locationID", locationID);

        this.deleteEventForLocation(conn, locationID);

        return queryHelper.delete(conn, table, where);
    }

    public boolean createLineup(Connection conn, EventLineup lineup) throws SQLException {
        String table = "EVENTLINEUP";
        HashMap data = new HashMap();

        data.put("eventID", lineup.getEventID());
        data.put("actID", lineup.getActID());
        data.put("actType", lineup.getActType());

        return queryHelper.insert(conn, table, data);
    }

    public boolean updateLineup(Connection conn, EventLineup lineup) throws SQLException {
        String table = "EVENTLINEUP";
        HashMap data = new HashMap();
        HashMap where = new HashMap();

        data.put("actID", lineup.getActID());
        data.put("actType", lineup.getActType());
        where.put("eventID", lineup.getEventID());
        where.put("actID", lineup.getActID());

        return queryHelper.update(conn, table, data, where);
    }

    public boolean deleteLineup(Connection conn, int eventID, int actID) throws SQLException {
        String table = "EVENTLINEUP";
        HashMap where = new HashMap();

        where.put("eventID", eventID);
        where.put("actID", actID);

        return queryHelper.delete(conn, table, where);
    }

    private boolean deleteLineups(Connection conn, int eventID) throws SQLException {
        String table = "EVENTLINEUP";
        HashMap where = new HashMap();

        where.put("eventID", eventID);

        return queryHelper.delete(conn, table, where);
    }
}
