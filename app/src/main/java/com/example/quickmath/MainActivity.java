package com.example.quickmath;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    TextView numtv;
    Button submit,leaderboard;
    EditText usernameET;
    String TempUser,TempScore;
    int x = 0;

    public void getData(){
        TempUser = usernameET.getText().toString();
        TempScore = numtv.getText().toString();
    }

    private void inits(){
        numtv = findViewById(R.id.numtv);
        submit = findViewById(R.id.btn2);
        usernameET = findViewById(R.id.usernameET);
        leaderboard = findViewById(R.id.btn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inits();

        numtv.setOnClickListener(view -> {
            x++;
            numtv.setText(Integer.toString(x));

        });

        leaderboard.setOnClickListener(view -> openLeaderboard());

        submit.setOnClickListener(view -> {


        });




    }

    class Task extends AsyncTask<Void, Void, Void>{
        String error="";
        String results="";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String URL = "jdbc:mysql://192.168.88.54:25566/players?useSSL=false";
                String user="korisnik";
                String pass="zavrsni";
                Connection conn = DriverManager.getConnection(URL,user,pass);
                String SQL = "INSERT INTO users(username,score) VALUES(?,?)";
                PreparedStatement pst = conn.prepareStatement(SQL);
                pst.setString(1, String.valueOf(usernameET.getText()));
                pst.setString(2, (String) numtv.getText());
                pst.execute();


            }catch(Exception e){
                error=e.toString();
                Log.d("SQL",error);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            super.onPostExecute(unused);
        }
    }



    public void openLeaderboard(){
        Intent intent = new Intent(MainActivity.this, leaderboard.class);
        startActivity(intent);
    }


}