package com.example.quickmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class leaderboard extends AppCompatActivity {

    RecyclerView rv;
    List<Player> playerList = new ArrayList<Player>();

    SharedPreferences sharedPreferences;

    String json;
    Player igrac;
    Gson gson = new Gson();

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        sharedPreferences = getSharedPreferences("BASE", Context.MODE_PRIVATE);

        try {
            fillPlayerList();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        json = sharedPreferences.getString("Player", "");
        igrac = gson.fromJson(json, Player.class);


         rv = findViewById(R.id.rv_players);
         rv.setLayoutManager(new LinearLayoutManager(this));
         rv.setAdapter(new MyAdapter(getApplicationContext(),playerList));



    }

    private void fillPlayerList() throws ExecutionException, InterruptedException {
        new LoadList().execute().get();

    }

    class LoadList extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                conn=JavaConnect.connectDb();
                String sql = "SELECT * FROM users ORDER BY score DESC";
                assert conn != null;
                pst = conn.prepareStatement(sql);
                rs=pst.executeQuery();

                while(rs.next()){
                    playerList.add(new Player(rs.getString(2),rs.getInt(3)));
                }

                conn.close();
                pst.close();
                rs.close();


            }catch(Exception e){
                Log.d("ERROR",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            super.onPostExecute(unused);
        }
    }





}