package com.example.quickmath;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.concurrent.ExecutionException;

public class GameActivity extends AppCompatActivity {

    Button submit;
    TextView mainD;
    EditText input;

    int user_input = 0;
    int score = 0;

    String json;
    Player igrac;
    String username="";
    int rez=0;

    boolean error;

    Dialog usernameDialog;

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    ObjectAnimator animator;

    NumPadView npv;

    int timeInMilis = 0;
    int dbScore=0;

    SharedPreferences sharedPreferences;
    Gson gson = new Gson();


    public int generateNumber() {
        int min = 1;
        int max = 30;
        return (int)Math.floor(Math.random() * (max - min + 1) + min);
    }

    public void inits(){

        mainD=findViewById(R.id.mainD);
        npv = findViewById(R.id.npv);
    }

    public void countdown(){

        animator = ObjectAnimator.ofInt(mProgressBar, "progress", 0, 1000);
        animator.setDuration(timeInMilis);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();

        mCountDownTimer=new CountDownTimer(timeInMilis,1000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                //showPopup(findViewById(R.id.lejout));
            }
        };
        mCountDownTimer.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("BASE",Context.MODE_PRIVATE);

        usernameDialog = new Dialog(GameActivity.this);
        usernameDialog.setCancelable(false);

        inits();

        json = sharedPreferences.getString("Player", "");
        igrac = gson.fromJson(json, Player.class);

        timeInMilis = 5000;

        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
        mProgressBar.setProgress(0);

        countdown();


        startGame();
        npv.setCityButtonClickListener(new NumPadView.CityButtonClickListener() {
            @Override
            public void onCityButtonClick(int userInput) {
                user_input=userInput;
                if(rez != user_input){

                    showPopup(findViewById(R.id.lejout));
                    mCountDownTimer.cancel();
                    animator.cancel();
                }
                else{
                    score++;
                    mCountDownTimer.cancel();
                    mProgressBar.setProgress(0);
                    startGame();
                    countdown();
                }
            }
        });


        /*submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_input=Integer.parseInt(String.valueOf(input.getText()));
                if(rez != user_input){

                    showPopup(view);
                    mCountDownTimer.cancel();
                    animator.cancel();
                }
                else{
                    input.setText("");
                    score++;
                    mCountDownTimer.cancel();
                    mProgressBar.setProgress(0);
                    startGame();
                    countdown();
                }
            }
        });*/




    }

    public void startGame(){
        int x = generateNumber();
        int y = generateNumber();
        String eq = x + " + " + y;
        mainD.setText(eq);
        rez=x+y;
    }


    public void showPopup(View v){
        Button submit;
        TextView showScore,alert;

        usernameDialog.setContentView(R.layout.endgame_popup);
        submit = usernameDialog.findViewById(R.id.finalBtn);
        showScore = usernameDialog.findViewById(R.id.showScore);
        alert = usernameDialog.findViewById(R.id.alert);
        usernameDialog.show();
        showScore.setText(String.valueOf(score));

        try {
            new Task().execute().get();
            if(dbScore<score){
                alert.setText("NEW HIGH SCORE!");
                igrac.setScore(score);
                new UpdateDbTask().execute();
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this,main_menu.class);
                startActivity(intent);
            }
        });


    }
    class Task extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {

                Connection conn = JavaConnect.connectDb();

                String SQL = "SELECT score FROM users WHERE username='" + igrac.getUsername() + "'";
                assert conn != null;
                PreparedStatement pst = conn.prepareStatement(SQL);
                ResultSet rs = pst.executeQuery();
                rs.next();
                dbScore = rs.getInt(1);
                Log.d("TEST", String.valueOf(dbScore));
                conn.close();
            }
            catch(Exception ex){
                Log.d("SQL", ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            super.onPostExecute(unused);
        }
    }

    class UpdateDbTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {

                Connection conn = JavaConnect.connectDb();

                String SQL = "UPDATE users SET score=? WHERE username='"+igrac.getUsername()+"'";
                assert conn != null;
                PreparedStatement pst = conn.prepareStatement(SQL);
                pst.setString(1, String.valueOf(score));
                pst.execute();
                conn.close();
                igrac.setScore(score);
                String json = gson.toJson(igrac);
                sharedPreferences.edit().putString("Player",json).apply();

                conn.close();
            }
            catch(Exception ex){
                Log.d("SQL", ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            super.onPostExecute(unused);
        }
    }

    @Override
    public void onBackPressed() {
        // nothing
    }



}