package com.example.derek.nowplaying;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Database class that returns the Data Access Object
 */

@Database(entities = {Song.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SongDao getSongDao();
}
