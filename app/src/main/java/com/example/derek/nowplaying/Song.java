package com.example.derek.nowplaying;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class representing a table in AppDatabase accessed via methods in SongDao.
 */

@Entity
public class Song {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String songName;
    private String artistName;

    public int getId(){
        return id;
    }
    public String getSongName() {
        return songName;
    }
    public String getArtistName() {
        return artistName;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setSongName(String songName) {
        this.songName = songName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
