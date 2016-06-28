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
import entities.artists.ArtistList;
import entities.artists.Band;
import entities.artists.BandList;
import entities.artists.Genre;
import entities.artists.GenreList;
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
public class Artists {
    private PreparedStatement pstmt;
    private ResultSet rset;
    private final QueryHelper queryHelper = new QueryHelper();
    Releases songs = new Releases();
    
    public ArtistList getAllArtists(Connection conn) throws SQLException, ClassNotFoundException {
        String queryString;
        ArtistList artists = new ArtistList();
        Genre genre;
        
        queryString = "SELECT * FROM ARTISTS ORDER BY name ASC";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            genre = this.getGenre(conn, rset.getInt(2));
            
            Artist artist = new Artist(rset.getInt(1), genre.getGenreID(), genre.getName(),
                    rset.getInt(3), rset.getString(4), rset.getDate(5), 
                    rset.getDate(6), rset.getString(7), rset.getString(8), 
                    rset.getString(9));
            
            artists.addArtist(artist);
        }
        return artists;
    }
    
    public Artist getArtist(Connection conn, int artistID) throws SQLException, ClassNotFoundException {
        String queryString;
        Genre genre;
        
        queryString = "SELECT * FROM ARTISTS WHERE artistID=" + artistID;
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            genre = this.getGenre(conn, rset.getInt(2));
            
            Artist artist = new Artist(rset.getInt(1), genre.getGenreID(), genre.getName(),
                    rset.getInt(3), rset.getString(4), rset.getDate(5), 
                    rset.getDate(6), rset.getString(7), rset.getString(8), 
                    rset.getString(9));
            
            return artist;
        }
        return null;
    }
    
    public void createArtist(Connection conn, Artist artist) throws SQLException, ClassNotFoundException {
        String table = "ARTISTS";
        HashMap data = new HashMap();
        
        data.put("name", artist.getName());
        data.put("kownAs", artist.getKownAs());
        data.put("start", artist.getStart());
        if(artist.getEnd() != null) {
            data.put("end", artist.getEnd());
        }
        data.put("genreID", artist.getGenreID());
        data.put("memberOfID", artist.getMemberOfID());
        data.put("biography", artist.getBiograhpy());
        data.put("picName", artist.getPicName());
        
        queryHelper.insert(conn, table, data);
    }
    
    public boolean updateArtist(Connection conn, Artist artist) throws SQLException, ClassNotFoundException {
        String table = "ARTISTS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("name", artist.getName());
        data.put("kownAs", artist.getKownAs());
        data.put("start", artist.getStart());
        if(artist.getEnd() != null) {
            data.put("end", artist.getEnd());
        }
        data.put("genreID", artist.getGenreID());
        data.put("memberOfID", artist.getMemberOfID());
        data.put("biography", artist.getBiograhpy());
        data.put("picName", artist.getPicName());
        where.put("artistID", artist.getArtistID());
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean deleteArtist(Connection conn, int artistID) throws SQLException, ClassNotFoundException {
        String table = "ARTISTS";
        HashMap where = new HashMap();
        
        where.put("artistID", artistID);
        
        songs.deleteSongByArtist(conn, artistID, "artist");
        
        return queryHelper.delete(conn, table, where);      
    }
    
    public BandList getAllBands(Connection conn) throws SQLException, ClassNotFoundException {
        String queryString;
        BandList bands = new BandList();
        Genre genre;
        
        queryString = "SELECT * FROM BANDS ORDER BY name ASC";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            genre = this.getGenre(conn, rset.getInt(2));
            
            Band band = new Band(rset.getInt(1), genre.getGenreID(), genre.getName(),
                    rset.getString(3), rset.getDate(4), rset.getDate(5), 
                    rset.getString(6), rset.getString(7));
            
            bands.addBand(band);
        }
        return bands;
    }
    
    public Band getBand(Connection conn, int bandID) throws SQLException, ClassNotFoundException {
        String queryString;
        Genre genre;
        
        queryString = "SELECT * FROM BANDS WHERE bandID=" + bandID;
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {
            genre = this.getGenre(conn, rset.getInt(2));
            
            Band band = new Band(rset.getInt(1), genre.getGenreID(), genre.getName(),
                    rset.getString(3), rset.getDate(4), rset.getDate(5), 
                    rset.getString(6), rset.getString(7));
            
            return band;
        }
        return null;
    }
    
    public void createBand(Connection conn, Band band) throws SQLException, ClassNotFoundException {
        String table = "BANDS";
        HashMap data = new HashMap();
        
        data.put("name", band.getName());
        data.put("start", band.getStart());
        if(band.getEnd() != null) {
            data.put("end", band.getEnd());
        }
        data.put("genreID", band.getGenreID());
        data.put("biography", band.getBiography());
        data.put("picName", band.getPicName());
        
        queryHelper.insert(conn, table, data);
    }
    
    public boolean updateBand(Connection conn, Band band) throws SQLException, ClassNotFoundException {
        String table = "BANDS";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("name", band.getName());
        data.put("start", band.getStart());
        if(band.getEnd() != null) {
            data.put("end", band.getEnd());
        }
        data.put("genreID", band.getGenreID());
        data.put("biography", band.getBiography());
        data.put("picName", band.getPicName());
        where.put("bandID", band.getBandID());
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean deleteBand(Connection conn, int bandID) throws SQLException, ClassNotFoundException {
        String table = "BANDS";
        HashMap where = new HashMap();
        
        where.put("bandID", bandID);
        
        songs.deleteSongByArtist(conn, bandID, "band");
        
        return queryHelper.delete(conn, table, where);      
    }
    
    public GenreList getAllGenres(Connection conn) throws SQLException, ClassNotFoundException {
        String queryString;
        GenreList genres = new GenreList();
        
        queryString = "SELECT * FROM GENRES ORDER BY name ASC";
        pstmt = conn.prepareStatement(queryString);
        rset = pstmt.executeQuery();
        while (rset.next()) {            
            Genre genre = new Genre(rset.getInt(1), rset.getString(2));
            
            genres.addGenre(genre);
        }
        return genres;
    }
    
    private Genre getGenre(Connection conn, int genreID) throws SQLException, ClassNotFoundException {
        String queryString;
        ResultSet rsetGenre;
        
        queryString = "SELECT * FROM GENRES WHERE genreID=" + genreID;
        pstmt = conn.prepareStatement(queryString);
        rsetGenre = pstmt.executeQuery();
        while (rsetGenre.next()) {
            Genre genre = new Genre(rsetGenre.getInt(1), rsetGenre.getString(2));
            return genre;
        }
        return null;
    }
    
    public void createGenre(Connection conn, Genre genre) throws SQLException, ClassNotFoundException {
        String table = "GENRES";
        HashMap data = new HashMap();
        
        data.put("name", genre.getName());
        
        queryHelper.insert(conn, table, data);
    }
    
    public boolean updateGenre(Connection conn, Genre genre) throws SQLException, ClassNotFoundException {
        String table = "GENRES";
        HashMap data = new HashMap();
        HashMap where = new HashMap();
        
        data.put("name", genre.getName());
        where.put("genreID", genre.getGenreID());
        
        return queryHelper.update(conn, table, data, where);
    }
    
    public boolean deleteGenre(Connection conn, int genreID) throws SQLException, ClassNotFoundException {
        String table = "GENRES";
        HashMap where = new HashMap();
        
        where.put("genreID", genreID);
        
        return queryHelper.delete(conn, table, where);      
    }
}
