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

package entities.events;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
public class Event {
    private int eventID;
    private int locationID;
    private String name;
    private Date date;
    private Time start;
    private Time end;
    private double price;
    private String description;
    private String eventPic;
    private EventLineupList eventLineup;

    public Event() {
    }

    public Event(int eventID, int locationID, String name, Date date, 
            Time start, Time end, double price, String description, 
            String eventPic) {
        this.eventID = eventID;
        this.locationID = locationID;
        this.name = name;
        this.date = date;
        this.start = start;
        this.end = end;
        this.price = price;
        this.description = description;
        this.eventPic = eventPic;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventPic() {
        return eventPic;
    }

    public void setEventPic(String eventPic) {
        this.eventPic = eventPic;
    }

    public EventLineupList getEventLineup() {
        return eventLineup;
    }

    public void setEventLineup(EventLineupList eventLineup) {
        this.eventLineup = eventLineup;
    }
}
