package com.example.quickmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class main_menu extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    Player igrac;
    Gson gson = new Gson();
    String json;

    Button btnLeaderboard,btnPlay;

    public void inits(){
        btnLeaderboard = findViewById(R.id.btnLeaderboard);
        btnPlay = findViewById(R.id.btnPlay);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm);

        inits();

        sharedPreferences = getSharedPreferences("BASE",Context.MODE_PRIVATE);
        Boolean state = sharedPreferences.getBoolean("tutPassed",false);

        json = sharedPreferences.getString("Player","");
        igrac = gson.fromJson(json,Player.class);

        TextView tv = findViewById(R.id.test);
        String finale = igrac.getUsername() + " " + igrac.getScore();
        tv.setText(finale);

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