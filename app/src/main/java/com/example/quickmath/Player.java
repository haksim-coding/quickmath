package com.example.quickmath;

import android.util.Log;

import java.io.Serializable;

public class Player implements Serializable {

     private String username;
     private int score;

    public Player(String username, int score) {
        this.username = username;
        this.score = score;
    }


    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

     public void printPlayer(){
         Log.d("PLAYER",getUsername() + " " + getScore());
     }
}
