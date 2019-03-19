package com.example.derek.nowplaying;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        String key = MainActivity.SUN_NOW_PLAYING;
        if (MainActivity.kutx_pressed) key = MainActivity.KUTX_NOW_PLAYING;
        MainActivity.sun_radio_pressed = false;
        MainActivity.kutx_pressed = false;

        Intent intent = getIntent();
        String[] messages = intent.getStringArrayExtra(key);
        if (messages != null) {

            EditText editSong = (EditText) findViewById(R.id.edit_song);
            EditText editArtist = (EditText) findViewById(R.id.edit_artist);

            String songMessage = null;
            String artistMessage = null;
            if(messages[0] == null && messages[1] == null){
                songMessage = artistMessage = null;
            }
            else if (messages[0] == null){
                artistMessage = messages[1];
            }
            else if(messages[1] == null){
                songMessage = messages[0];
            }
            else {
                songMessage = messages[0];
                artistMessage = messages[1];
            }

            editSong.setText(songMessage);
            editArtist.setText(artistMessage);
        }
    }

    public void save(View view){
        SQLiteDatabase db = openOrCreateDatabase("MeowPlaying.db", MODE_PRIVATE, null);
        try {
//            db.execSQL("drop table if exists songs");
            db.execSQL("CREATE TABLE IF NOT exists songs(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " song VARCHAR, artist VARCHAR, CONSTRAINT song_artist_unique_constraint UNIQUE (song, artist));");
        } catch (Exception e) {
            Log.e("sql error", e.toString());
        }

        String[] names = new String[] {((EditText)findViewById(R.id.edit_song)).getText().toString(),
                ((EditText)findViewById(R.id.edit_artist)).getText().toString()};
        String message = null;

        Cursor resultSet = db.rawQuery("select * from songs where song=? and artist=?", names);
        if(resultSet.getCount() < 1) {

            db.execSQL("INSERT INTO songs VALUES (null, \"" + names[0] + "\",\"" + names[1] + "\")");
            Cursor verifySet = db.rawQuery("select * from songs where song=? and artist=?", names);
            if (verifySet.getCount() > 0) {
                message = "saved to library";
            } else {
                message = "unable to save to library";
            }
        }else {
            message = "already exists in library";
        }
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();

        db.close();
        resultSet.close();
    }
}
