package com.example.quickmath;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    Handler h = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView title = findViewById(R.id.qm);
        Animation slide = AnimationUtils.loadAnimation(this,R.anim.slide_left);
        title.startAnimation(slide);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,starting_activity.class);
                startActivity(intent);
                finish();
            }
        },1200);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}