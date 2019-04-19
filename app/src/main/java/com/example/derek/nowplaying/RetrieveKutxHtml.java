package com.example.derek.nowplaying;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Retrieve song information from Kutx website.
 * Main Activity passes in url. As of 04/2019 the url returns json with song data.
 */

public class RetrieveKutxHtml extends AsyncTask<String, Void, String[]> {

    @Override
    protected String[] doInBackground(String[] input){
        try {
            URL url = new URL(input[0]);
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = "trash";
            String[] message = new String[2];

            // parse out song and artist from returned json.
            try {
                if((line = br.readLine()) != null){
                    JSONObject obj = new JSONObject(line);
                    if( obj.getJSONObject("onNow").has("song")) {
                        message[0] = obj.getJSONObject("onNow").getJSONObject("song").getString("trackName");
                        message[1] = obj.getJSONObject("onNow").getJSONObject("song").getString("artistName");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return message;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e2){
            e2.printStackTrace();
        }

        return null;
    }
}
