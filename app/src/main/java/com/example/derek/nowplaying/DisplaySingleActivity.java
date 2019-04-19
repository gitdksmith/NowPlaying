package com.example.derek.nowplaying;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * Class to view song info from library. Can update or search song data on YouTube.
 */

public class DisplaySingleActivity extends AppCompatActivity {
    private ProgressBar spinner;
    private String id;
    private String song;
    private String artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_single);
        Intent intent = getIntent();

        spinner=(ProgressBar)findViewById(R.id.progressBar);

        String key = DisplayLibraryActivity.LIBRARY_KEY;
        String[] messages = intent.getStringArrayExtra(key);
        id = messages[0];
        song = messages[1];
        artist = messages[2];

        TextView editSong = (TextView) findViewById(R.id.text_song_display_single);
        TextView editArtist = (TextView) findViewById(R.id.text_artist_display_single);

        editSong.setText(song);
        editArtist.setText(artist);
    }

    public void searchYoutube(View view){
        Intent intent = new Intent(Intent.ACTION_SEARCH);
//        Intent intent = getIntent();
        intent.setPackage("com.google.android.youtube");

        spinner.setVisibility(View.VISIBLE);

        String key = DisplayLibraryActivity.LIBRARY_KEY;
//        String[] messages = intent.getStringArrayExtra(key);
//        String query = messages[1]+" "+messages[2];
        String query = song + " " + artist;
//        String query = "hello world";
        intent.putExtra("query", query);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        spinner.setVisibility(View.GONE);

    }

    public void edit(View view){
        Intent intent = new Intent(this, EditSingleActivity.class);
        intent.putExtra(DisplayLibraryActivity.LIBRARY_KEY, new String[] {id, song, artist});

        startActivity(intent);

    }
}
