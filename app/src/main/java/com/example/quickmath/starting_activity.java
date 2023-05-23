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
    NumPadView npv;

    public void inits(){
        npv = findViewById(R.id.npv);
        mainD=findViewById(R.id.mainD);
    }

    public int generateNumber(int min,int max) {
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
        npv.setSubmitButtonClickListener(new NumPadView.SubmitButtonClickListener() {
            @Override
            public void onSubmitButtonClick(int input) {
                user_input=input;
                if(rez != user_input){

                    showPopup(findViewById(R.id.lejout),false);

                }
                else{
                    score++;
                    if(score==5){
                        showPopup(findViewById(R.id.lejout),true);
                    }
                    startGame();
                }
            }
        });

    }

    private String generateOp(){
        String op = "";
        int ran=(int)Math.floor(Math.random() * (4 - 1 + 1) + 1);
        switch(ran){
            case 1:op="+";break;
            case 2:op="-";break;
            case 3:op="*";break;
            case 4:op="/";break;
        }
        return op;
    }

    private void startGame(){
        int x=0;
        int y=0;
        String op = "+";
        if(score<2){
            x=generateNumber(5,30);
            y=generateNumber(10,50);
            rez=x+y;
        }
        else{
            op=generateOp();

            switch(op){
                case "+":
                    x=generateNumber(5,50);
                    y=generateNumber(10,60);
                    rez=x+y;break;
                case "-":
                    x=generateNumber(5,50);
                    y=generateNumber(10,60);
                    while(x-y<0){
                        x=generateNumber(5,50);
                        y=generateNumber(10,60);
                    }
                    rez=x-y;break;
                case "*":
                    x=generateNumber(5,15);
                    y=generateNumber(3,9);
                    rez=x*y;break;
                case "/":
                    x=generateNumber(10,80);
                    y=generateNumber(3,20);
                    while(x%y!=0){
                        x=generateNumber(10,80);
                        y=generateNumber(3,20);
                    }
                    rez=x/y;break;
            }

        }
        String eq = x + " " + op + " " + y;
        mainD.setText(eq);

    }

    public void showPopup(View v,boolean passed){
        EditText usernameEt;
        Button submit;
        TextView showScore,info;

        usernameDialog.setContentView(R.layout.username_popup);
        usernameEt = usernameDialog.findViewById(R.id.etUsername);
        submit = usernameDialog.findViewById(R.id.finalBtn);
        showScore = usernameDialog.findViewById(R.id.showScore);
        info = usernameDialog.findViewById(R.id.dynamic);
        usernameDialog.show();
        showScore.setText(String.valueOf(score));

        if(passed) info.setText("Good job, Now do it fast!");
        info.setText("Nice try, be quicker now!");


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
                    Toast.makeText(starting_activity.this,username + " is already taken!",Toast.LENGTH_LONG).show();
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