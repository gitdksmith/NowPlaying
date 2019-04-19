package com.example.derek.nowplaying;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Class to edit oor delete row in database.
 */
public class EditSingleActivity  extends AppCompatActivity {
    private String id;
    private String song;
    private String artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Populate the song and artists fields.
        Buttons below will allow for editing or searching.
         */
        setContentView(R.layout.activity_edit_single);
        Intent intent = getIntent();

        String key = DisplayLibraryActivity.LIBRARY_KEY;
        String[] messages = intent.getStringArrayExtra(key);
        id = messages[0];
        song = messages[1];
        artist = messages[2];

        EditText editSong = (EditText) findViewById(R.id.edit_song);
        EditText editArtist = (EditText) findViewById(R.id.edit_artist);

        editSong.setText(song);
        editArtist.setText(artist);
    }

    /**
     * Get the new song and artist from text fields, apply to new Song object and update
     * using dao convenience method.
     * @param view
     */
    public void update(View view){
        AppDatabase db = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "songDB")
                .allowMainThreadQueries().build();
        SongDao dao = db.getSongDao();

        String newSongName = ((EditText)findViewById(R.id.edit_song)).getText().toString();
        String newArtist = ((EditText)findViewById(R.id.edit_artist)).getText().toString();

        String message = "Could not update library";
        Song newSong = new Song(Integer.parseInt(id), newSongName, newArtist);

        if(!newSongName.isEmpty() && !newArtist.isEmpty() && dao.update(newSong) > 0){
            message =  "Updated library";
        }

        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    /**
     * Create song object from and delete using dao convenience method.
     * @param view
     */
    public void delete(View view){
        AppDatabase db = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "songDB")
                .allowMainThreadQueries().build();
        SongDao dao = db.getSongDao();

        String message = "Could not delete from library";
        Song s = new Song(Integer.parseInt(id), song, artist);

        if(dao.delete(s) > 0){
            message = "Deleted song from library";
        }

        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
}
