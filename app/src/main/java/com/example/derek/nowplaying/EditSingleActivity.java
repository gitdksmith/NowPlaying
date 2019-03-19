package com.example.derek.nowplaying;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Derek on 12/3/2016.
 */
public class EditSingleActivity  extends AppCompatActivity {
    private String id;
    private String song;
    private String artist;
    private String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single);
        Intent intent = getIntent();


        String key = DisplayLibraryActivity.LIBRARY_KEY;
        String[] messages = intent.getStringArrayExtra(key);
        id = messages[0];
        song = messages[1];
        artist = messages[2];
        tableName = getResources().getString(R.string.table_name);

        EditText editSong = (EditText) findViewById(R.id.edit_song);
        EditText editArtist = (EditText) findViewById(R.id.edit_artist);

        editSong.setText(song);
        editArtist.setText(artist);
    }

    public void update(View view){
        SQLiteDatabase db = openOrCreateDatabase("MeowPlaying.db", MODE_PRIVATE, null);
        try {
            Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
            if(cursor == null || cursor.getCount() < 1){
                Log.e(this.getClass().getSimpleName(), "No sql table exists");
                Toast.makeText(this,"No sql table found, creating table", Toast.LENGTH_LONG).show();
                db.execSQL("CREATE TABLE IF NOT exists songs(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " song VARCHAR, artist VARCHAR, CONSTRAINT song_artist_unique_constraint UNIQUE (song, artist));");
            }
        } catch (Exception e) {
            Log.e("sql error", e.toString());
            Toast.makeText(this,"Sql Error", Toast.LENGTH_LONG).show();
            return;
        }

        String newSong = ((EditText)findViewById(R.id.edit_song)).getText().toString();
        String newArtist = ((EditText)findViewById(R.id.edit_artist)).getText().toString();

        ContentValues cv = new ContentValues();
        cv.put("song", newSong);
        cv.put("artist", newArtist);

        String[] idArr={id};
        String message = null;

        int result = db.update(tableName, cv, "id=?", idArr);
        if(result > 0)
            message = "Updated "+result+" rows from library";
        else
            message = "Could not update library";

        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
        db.close();
    }

    public void delete(View view){
        SQLiteDatabase db = openOrCreateDatabase("MeowPlaying.db", MODE_PRIVATE, null);
        try {
            Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
            if(cursor == null || cursor.getCount() < 1){
                Log.e(this.getClass().getSimpleName(), "No sql table exists");
                Toast.makeText(this,"No sql table found", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (Exception e) {
            Log.e("sql error", e.toString());
            Toast.makeText(this,"Sql Error", Toast.LENGTH_LONG).show();
            return;
        }

        String[] idArr={id};
        String message = null;

        int result = db.delete(tableName, "id=?", idArr);
        if(result > 0)
            message = "Deleted "+result+" rows from library";
        else
            message = "Could not delete from library";

        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
        db.close();
    }
}
