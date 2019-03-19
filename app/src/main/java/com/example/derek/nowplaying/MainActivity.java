package com.example.derek.nowplaying;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    public static final String KUTX_NOW_PLAYING="com.example.derek.myapplication.KUTX_NOW_PLAYING";
    public static final String SUN_NOW_PLAYING="com.example.derek.myapplication.SUN_NOW_PLAYING";
    public static boolean kutx_pressed = false;
    public static boolean sun_radio_pressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

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
                url = "http://radio.securenetsystems.net/songdata/v5/index.cfm?stationCallSign=SUNRADIO&amp";
                task = new RetrieveSunHtml().execute(url);
                sun_radio_pressed = true;
                break;
        }

        try {
            message = (String[]) task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        intent.putExtra(key, message);
        startActivity(intent);
    }

    public void viewLibrary(View view){
        Intent intent = new Intent(this, DisplayLibraryActivity.class);
        startActivity(intent);
    }
}
