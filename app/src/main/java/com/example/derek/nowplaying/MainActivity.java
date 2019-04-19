package com.example.derek.nowplaying;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

// TODO: Can save empty fields to library
// TODO: non centered fields in DisplaySingleActivity view
// TODO: If "now playing" is not available, try for "last" or "current program".
// TODO: Backup library online
// TODO: What if we made the song id a hash of song and artist names? Would we update the hash each
//       time we update part of the data? Is it ok to update a primary key?

public class MainActivity extends AppCompatActivity {
    public static final String KUTX_NOW_PLAYING="com.example.derek.myapplication.KUTX_NOW_PLAYING";
    public static final String SUN_NOW_PLAYING="com.example.derek.myapplication.SUN_NOW_PLAYING";
    public static boolean kutx_pressed = false;
    public static boolean sun_radio_pressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize database using Room
        AppDatabase db = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "songDB")
                .build();
    }

    /**
     * Activity_main.xml creates buttons that when clicked call this method. Depending on the button
     * we either retrieve sun radio or kutx information.
     */
    public void getNowPlaying(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String[] message = null;
        String key = null;
        String url = null;
        AsyncTask task = null;

        switch(view.getId()){
            case R.id.kutx_button :
                key = KUTX_NOW_PLAYING;
                url = "https://api.composer.nprstations.org/v1/widget/50ef24ebe1c8a1369593d032/now?format=json";
                task = new RetrieveKutxHtml().execute(url);
                kutx_pressed = true;
                break;
            case R.id.sun_radio_button :
                key = SUN_NOW_PLAYING;
                url = "https://radio.securenetsystems.net/songdata/v5/index.cfm?stationCallSign=SUNRADIO&amp";
                task = new RetrieveSunHtml().execute(url);
                sun_radio_pressed = true;
                break;
        }

        try {
            message = (String[]) task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // pass song data to DisplayMessageActivity via extra and run it
        intent.putExtra(key, message);
        startActivity(intent);
    }

    /**
     * Button action for viewing library from main screen
     */
    public void viewLibrary(View view){
        Intent intent = new Intent(this, DisplayLibraryActivity.class);
        startActivity(intent);
    }
}
