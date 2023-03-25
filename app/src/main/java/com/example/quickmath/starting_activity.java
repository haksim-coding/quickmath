package com.example.quickmath;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class starting_activity extends AppCompatActivity {

    Button submit;
    TextView mainD,rezTV,scoreTV;
    EditText input;

    int user_input = 0;
    int score = 0;
    int rez=0;

    private String username = "";
    public void inits(){
        submit=findViewById(R.id.gumb);
        mainD=findViewById(R.id.mainD);
        input=findViewById(R.id.input);
        rezTV=findViewById(R.id.rez);
        scoreTV=findViewById(R.id.score);
    }

    public int generateNumber() {
        int min = 1;
        int max = 100;
        return (int)Math.floor(Math.random() * (max - min + 1) + min);
    }

    public void endTutF(){
        SharedPreferences sharedPreferences = getSharedPreferences("BASE",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
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

        inits();

        SharedPreferences sharedPreferences = getSharedPreferences("BASE",Context.MODE_PRIVATE);
        boolean state = sharedPreferences.getBoolean("tutPassed",false);
        if(state){
            endTut();
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        }
        startGame();
        //rezTV.setText(String.valueOf(rez));
        //scoreTV.setText(String.valueOf(user_input));
       submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user_input=Integer.parseInt(String.valueOf(input.getText()));
                    //rezTV.setText(String.valueOf(rez));
                    //scoreTV.setText(String.valueOf(user_input));
                    if(rez != user_input){

                        AlertDialog.Builder builder = new AlertDialog.Builder(starting_activity.this);
                        builder.setTitle("Username");

                        final EditText input = new EditText(starting_activity.this);

                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);


                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                username = input.getText().toString();
                                sharedPreferences.edit().putString("username",username).apply();
                                sharedPreferences.edit().putInt("score",score).apply();
                                endTutF();

                            }
                        });
                        builder.show();

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

}