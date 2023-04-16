package com.example.quickmath;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout rlMain;
    TextView usernameV,scoreV;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        usernameV = itemView.findViewById(R.id.usernameView);
        scoreV = itemView.findViewById(R.id.scoreView);
        rlMain = itemView.findViewById(R.id.rlMain);
    }
}
