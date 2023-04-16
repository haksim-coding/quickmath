package com.example.quickmath;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.concurrent.ExecutionException;

public class starting_activity extends AppCompatActivity {

    Button submit;
    TextView mainD;
    EditText input;

    int user_input = 0;
    int score = 0;
    String username="";
    int rez=0;

    boolean error;

    Dialog usernameDialog;

    SharedPreferences sharedPreferences;

    public void inits(){
        submit=findViewById(R.id.gumb);
        mainD=findViewById(R.id.mainD);
        input=findViewById(R.id.input);
    }

    public int generateNumber() {
        int min = 1;
        int max = 100;
        return (int)Math.floor(Math.random() * (max - min + 1) + min);
    }

    public void endTutF(){
        sharedPreferences = getSharedPreferences("BASE",Context.MODE_PRIVATE);
        Intent intent = new Intent(this,main_menu.class);
        sharedPreferences.edit().putBoolean("tutPassed",true).apply();
        startActivity(intent);
        finish();
    }

    public void endTut(){
        Intent intent = new Intent(this,main_menu.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        usernameDialog = new Dialog(starting_activity.this);
        usernameDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences("BASE",Context.MODE_PRIVATE);
        boolean state = sharedPreferences.getBoolean("tutPassed",false);

        if(state){
            endTut();
        }
        inits();
        startGame();
       submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user_input=Integer.parseInt(String.valueOf(input.getText()));
                    if(rez != user_input){

                            showPopup(view);

                    }
                    else{
                        input.setText("");
                        score++;
                        startGame();
                    }
                }
            });




    }

    public void startGame(){
        int x = generateNumber();
        int y = generateNumber();
        String eq = x + " + " + y;
        mainD.setText(eq);
        rez=x+y;
    }

    public void showPopup(View v){
        EditText usernameEt;
        Button submit;
        TextView showScore;

        usernameDialog.setContentView(R.layout.username_popup);
        usernameEt = usernameDialog.findViewById(R.id.etUsername);
        submit = usernameDialog.findViewById(R.id.finalBtn);
        showScore = usernameDialog.findViewById(R.id.showScore);
        usernameDialog.show();
        showScore.setText(String.valueOf(score));



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error=false;
                username = String.valueOf(usernameEt.getText());
                Player igrac = new Player(username,score);
                Gson gson = new Gson();
                String json = gson.toJson(igrac);
                sharedPreferences.edit().putString("Player",json).apply();
                try {
                    new Task().execute().get();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if(!error){
                    endTutF();
                }
                else{
                    Toast.makeText(starting_activity.this,username + " is alredy taken!",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
    class Task extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {

                Connection conn = JavaConnect.connectDb();
                String SQL = "INSERT INTO users(username,score) VALUES(?,?)";
                assert conn != null;
                PreparedStatement pst = conn.prepareStatement(SQL);
                pst.setString(1,username);
                pst.setString(2, String.valueOf(score));
                pst.execute();
                conn.close();


            }catch(SQLIntegrityConstraintViolationException e){ //duplicate entry exception check
                error=true;
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



}