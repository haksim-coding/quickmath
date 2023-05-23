package com.example.quickmath;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Player> players;
    SharedPreferences sharedPreferences;
    Gson gson = new Gson();
    String json;
    Player igrac;

    public MyAdapter(Context context, List<Player> players) {
        this.context = context;
        this.players = players;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.player_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            sharedPreferences = context.getSharedPreferences("BASE",Context.MODE_PRIVATE);
            json = sharedPreferences.getString("Player","");
            igrac = gson.fromJson(json,Player.class);

            holder.usernameV.setText(players.get(position).getUsername());
            holder.scoreV.setText(String.valueOf(players.get(position).getScore()));

            if(position==0){
                holder.rlMain.setBackgroundColor(
                        ContextCompat.getColor(holder.itemView.getContext(),R.color.gold)
                );
                igrac.printPlayer();
            }
            if(players.get(position).getUsername().equals(igrac.getUsername())){
                holder.rlMain.setBackgroundColor(
                        ContextCompat.getColor(holder.itemView.getContext(),R.color.mainBackground));
            }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
