package com.example.quickmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class main_menu extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm);

        sharedPreferences = getSharedPreferences("BASE",Context.MODE_PRIVATE);
        Boolean state = sharedPreferences.getBoolean("tutPassed",false);

        TextView tv = findViewById(R.id.test);
        String finale = sharedPreferences.getString("username",null) + sharedPreferences.getInt("score",0);
        tv.setText(finale);


    }

}