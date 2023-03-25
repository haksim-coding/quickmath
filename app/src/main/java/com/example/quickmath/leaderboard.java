package com.example.quickmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class leaderboard extends AppCompatActivity {

    RecyclerView rv;
    List<Player> playerList = new ArrayList<Player>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

         rv = findViewById(R.id.rv_users);

         fillUsers();
    }

    private void fillUsers() {
        for(int i=0;i<10;i++){
            int x=67;
            playerList.add(new Player("haksim",x+i));
        }

        playerList.get(6).printPlayer();
    }


}