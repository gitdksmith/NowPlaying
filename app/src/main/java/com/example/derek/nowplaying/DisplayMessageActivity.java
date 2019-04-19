package com.example.derek.nowplaying;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Class to display retrieved song information in editable fields with option to save
 * song info to library.
 */

public class DisplayMessageActivity extends AppCompatActivity {

    /**
     * Display song and artist names passed by intent extra
     * set in MainActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        //Determine if sun or kutx button was pressed
        String key = MainActivity.SUN_NOW_PLAYING;
        if (MainActivity.kutx_pressed) key = MainActivity.KUTX_NOW_PLAYING;
        MainActivity.sun_radio_pressed = false;
        MainActivity.kutx_pressed = false;

        //get the extra info containing song and artist data
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

    /**
     * Creates Song object to save to songDB database
     * @param view
     */
    public void save(View view){
        AppDatabase db = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "songDB")
                .allowMainThreadQueries().build();
        SongDao dao = db.getSongDao();

        String[] names = new String[] {((EditText)findViewById(R.id.edit_song)).getText().toString(),
                ((EditText)findViewById(R.id.edit_artist)).getText().toString()};
        String message = null;

        List<Song> songList = dao.loadAll();

        Song nowPlaying = new Song();
        nowPlaying.setSongName(names[0]);
        nowPlaying.setArtistName(names[1]);
        if(songList.contains(nowPlaying))
            message = "already exists in library";
        else{
            dao.insert(nowPlaying);
            message = "saved to library";
        }

        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
}
