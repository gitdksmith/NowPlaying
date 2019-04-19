package com.example.derek.nowplaying;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Data Access Object containing methods for accessing database.
 */

@Dao
public interface SongDao {

    @Query("SELECT * FROM Song")
    public List<Song> loadAll();

    @Insert
    public void insert(Song... songs);

    @Delete
    public int delete(Song song);

    @Update
    public int update(Song song);
}

