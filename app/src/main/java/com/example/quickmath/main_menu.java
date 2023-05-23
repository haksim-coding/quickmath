package com.example.quickmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.Gson;

public class main_menu extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    Player igrac;
    Gson gson = new Gson();
    String json;

    Button btnLeaderboard,btnPlay;

    VideoView vw;



    public void inits(){
        btnLeaderboard = findViewById(R.id.btnLeaderboard);
        btnPlay = findViewById(R.id.btnPlay);
        vw = findViewById(R.id.vidBack);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.background_vid);
        vw.setVideoURI(uri);
        vw.start();

        vw.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

    }

    @Override
    protected void onPause() {
        vw.suspend();
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        vw.resume();
        super.onPostResume();
    }

    @Override
    protected void onRestart() {
        vw.start();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        vw.stopPlayback();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm);

        inits();

        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_menu.this,leaderboard.class);
                startActivity(intent);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_menu.this,GameActivity.class);
                startActivity(intent);
            }
        });

    }



}