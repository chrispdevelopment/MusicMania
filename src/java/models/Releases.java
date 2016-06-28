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

import entities.artists.Artist;
import entities.artists.Band;
import entities.music.Song;
import entities.music.SongList;
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
public class Releases {
    private PreparedStatement pstmt;
    private ResultSet rset;
    private final QueryHelper queryHelper = new QueryHelper();
    
    public SongList getSongs(Connection conn) throws SQLException, ClassNotFoundException {
        String queryString;
        Artists artists = new Artists();
        SongList songs;
        Artist artist;
        Band band;

        queryString = "SELECT TOP 5 * FROM RELEASES ORDER BY releaseDate";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();

        songs = new SongList();
        while (rset.next()) {
            Song song = new Song(rset.getInt(1), rset.getInt(2),
                    rset.getString(3), rset.getString(4), rset.getDate(5),
                    rset.getString(6));
            
            if (song.getCreatedByType().equals("artist")) {
                artist = artists.getArtist(conn, song.getCreatedByID());
                song.setCreatedByName(artist.getName());
            } else {
                band = artists.getBand(conn, song.getCreatedByID());
                song.setCreatedByName(band.getName());
            }
            
            songs.addSong(song);
        }

        return songs;
    }
    
    public void createSong(Connection conn, Song song) 
            throws SQLException, ClassNotFoundException {
        String table = "RELEASES";
        HashMap data = new HashMap();
        
        data.put("songID", song.getSongID());
        data.put("createdByID", song.getCreatedByID());
        data.put("createdByType", song.getCreatedByType());
        data.put("name", song.getName());
        data.put("releaseDate", song.getReleaseDate());
        data.put("songLink", song.getSongLink());
        
        queryHelper.insert(conn, table, data);
    }
    
    public boolean deleteSong(Connection conn, int songID) 
            throws SQLException, ClassNotFoundException {
        String table = "RELEASES";
        HashMap where = new HashMap();
        
        where.put("songID", songID);
        
        return queryHelper.delete(conn, table, where);      
    }
    
    public boolean deleteSongByArtist(Connection conn, int artistID, String artistType) 
            throws SQLException, ClassNotFoundException {
        String table = "RELEASES";
        HashMap where = new HashMap();
        
        where.put("createdByID", artistID);
        where.put("createdByType", artistType);
        
        return queryHelper.delete(conn, table, where);      
    }
}
