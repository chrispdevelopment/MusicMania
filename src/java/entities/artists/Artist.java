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

package entities.artists;

import java.sql.Date;

/**
 *
 * @author Chris Pratt <www.chrispdevelopment.co.uk>
 */
public class Artist {
    private int artistID;
    private int genreID;
    private String genre;
    private int memberOfID;
    private String name;
    private Date start;
    private Date end;
    private String kownAs;
    private String biograhpy;
    private String picName;

    public Artist() {
    }

    public Artist(int artistID, int genreID, String genre, int memberOfID, 
            String name, Date start, Date end, String kownAs, String biograhpy, 
            String picName) {
        this.artistID = artistID;
        this.genreID = genreID;
        this.genre = genre;
        this.memberOfID = memberOfID;
        this.name = name;
        this.start = start;
        this.end = end;
        this.kownAs = kownAs;
        this.biograhpy = biograhpy;
        this.picName = picName;
    }

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getMemberOfID() {
        return memberOfID;
    }

    public void setMemberOfID(int memberOfID) {
        this.memberOfID = memberOfID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getKownAs() {
        return kownAs;
    }

    public void setKownAs(String kownAs) {
        this.kownAs = kownAs;
    }

    public String getBiograhpy() {
        return biograhpy;
    }

    public void setBiograhpy(String biograhpy) {
        this.biograhpy = biograhpy;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public int getGenreID() {
        return genreID;
    }

    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }
}
