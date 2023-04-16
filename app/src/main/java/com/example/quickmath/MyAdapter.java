package com.example.quickmath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Player> players;

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
            holder.usernameV.setText(players.get(position).getUsername());
            holder.scoreV.setText(String.valueOf(players.get(position).getScore()));

            if(position==0){
                holder.rlMain.setBackgroundColor(
                        ContextCompat.getColor(holder.itemView.getContext(),R.color.gold)
                );
            }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
