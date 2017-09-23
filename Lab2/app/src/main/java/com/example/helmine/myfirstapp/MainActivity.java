package com.example.helmine.myfirstapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "MyFirstAppLogging";
    private MediaPlayer mp;  //declare a member variable for the MediaPlayer object

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(DEBUG_TAG, "in the onCreate() method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playMusicFromWeb();
    }

    public void playMusicFromWeb()
    {
        try {
            Uri file = Uri.parse("http://www.darkmatterproductions.com/mp3/Sumi%20Jo%20-%20the%20Rhapsody.mp3");
            mp = MediaPlayer.create(this, file);
            mp.start();
        }
        catch (Exception e)
        {
            Log.e(DEBUG_TAG, "Player failed", e);
        }
    }

    protected void onStop()
    {
        if (mp!=null)
        {
            mp.stop();
            mp.release();
        }
        super.onStop();
    }


}
