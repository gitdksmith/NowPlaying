package com.example.derek.nowplaying;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Data Access Object containing methods for accessing database.
 */

@Dao
public interface SongDao {

    @Query("SELECT * FROM Song")
    List<Song> loadAll();

    @Insert
    void insert(Song... songs);

    @Delete
    void delete(Song song);
}

