package com.example.derek.nowplaying;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

/**
 * Display library alternating table color rows.
 */

public class DisplayLibraryActivity extends AppCompatActivity {

    public static final String LIBRARY_KEY="com.example.derek.myapplication.LIBRARY_KEY";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_library);

        AppDatabase db = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "songDB")
                .allowMainThreadQueries().build();
        SongDao dao = db.getSongDao();

        boolean colorTracker = false;
        String green = "#A3FCCE";
        String blue = "#A3DDFC";

        try {
            List<Song> songList = dao.loadAll();
            Iterator<Song> songListIterator =((List) dao.loadAll()).iterator();
            while (songListIterator.hasNext()) {
                Song currentSong = songListIterator.next();
                final String id = Integer.toString(currentSong.getId());
                final String song = currentSong.getSongName();
                final String artist = currentSong.getArtistName();

                TextView tv = (TextView) findViewById(R.id.table_song);

                TextView tid = new TextView(this);
                int padding = (int)getResources().getDimension(R.dimen.table_id_padding);
                tid.setPadding(0,0,padding,0);
                tid.setText(id);

                TextView tsong = new TextView(this);
                tsong.setLayoutParams(tv.getLayoutParams());
                tsong.setText(song);

                TextView tartist = new TextView(this);
                tartist.setLayoutParams(tv.getLayoutParams());
                tartist.setText(artist);

                String color = colorTracker == true ? green : blue;
                colorTracker = !colorTracker;

                TableRow row = new TableRow(this);
                row.addView(tid);
                row.addView(tsong);
                row.addView(tartist);
                row.setBackgroundColor(Color.parseColor(color));
                row.setPadding(0,15,15,0);
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), DisplaySingleActivity.class);
                        String[] message = {id, song, artist};
                        intent.putExtra(LIBRARY_KEY, message);
                        startActivity(intent);
                    }
                });

                TableLayout table = (TableLayout) findViewById(R.id.TableLayout1);
                table.addView(row);
            }

        }
        finally {
        }
    }
}
