package com.example.derek.nowplaying;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Derek on 10/15/2016.
 */

public class DisplayLibraryActivity extends AppCompatActivity {

    public static final String LIBRARY_KEY="com.example.derek.myapplication.LIBRARY_KEY";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_library);

        SQLiteDatabase db = openOrCreateDatabase("MeowPlaying.db", MODE_PRIVATE, null);
        Cursor resultSet = db.rawQuery("select * from songs", null);

        boolean colorTracker = false;
        String green = "#A3FCCE";
        String blue = "#A3DDFC";

        try {
            while (resultSet.moveToNext()) {
                final String id = resultSet.getString(0);
                final String song = resultSet.getString(1);
                final String artist = resultSet.getString(2);

                TextView tv = (TextView) findViewById(R.id.table_song);

                TextView tid = new TextView(this);
                int padding = (int)getResources().getDimension(R.dimen.table_id_padding);
                tid.setPadding(0,0,padding,0);
                tid.setText(id);

//                Button bsong = new Button(this);
//                bsong.setLayoutParams(tv.getLayoutParams());
//                bsong.setText(song);
//                bsong.onclick

                TextView tsong = new TextView(this);
                tsong.setLayoutParams(tv.getLayoutParams());
                tsong.setText(song);

                TextView tartist = new TextView(this);
                tartist.setLayoutParams(tv.getLayoutParams());
                tartist.setText(artist);

                String color = colorTracker == true ? green : blue;
                colorTracker = !colorTracker;

//                <Button
//                android:layout_width="wrap_content"
//                android:layout_height="wrap_content"
//                android:layout_weight="1"
//                android:text="@string/save_button"
//                android:id="@+id/button"
//                android:layout_alignParentBottom="true"
//                android:layout_centerHorizontal="true"
//                android:layout_marginBottom="102dp"
//                android:onClick="save"/>

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

        } finally {
            resultSet.close();
        }
    }
}
