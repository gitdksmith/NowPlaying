package com.example.derek.nowplaying;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Derek on 10/7/2016.
 */

class RetrieveSunHtml extends AsyncTask<String, Void, String[]> {

    @Override
    protected String[] doInBackground(String[] input){
        try {
            URL url = new URL(input[0]);
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = "trash";
            String[] message = new String[2];
            try {
                while ((line = br.readLine()) != null){
                    if(line.contains("Now playing:")){
                        line = br.readLine();
                        if(line != null){
                            String[] splits1 = line.split(">");
                            String[] splits2 = splits1[1].split("<");
                            message[0] = splits2[0];
                        }
                        line = br.readLine();
                        if(line != null){
                            String[] splits1 = line.split(">");
                            String[] splits2 = splits1[1].split("<");
                            message[1] = splits2[0];
                        }
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
