package com.shahab.hms.ui.search_room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahab.hms.R;
import com.shahab.hms.SearchPackageActivity;
import com.shahab.hms.room;

import java.util.List;

public class Room_RV_Adapter extends RecyclerView.Adapter<Room_RV_Adapter.myViewHolder> {
    List<room> ls;
    Context c;

    public Room_RV_Adapter(List<room> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.search_room_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.room_id.setText(ls.get(position).getRoomId());
        holder.room_price.setText(ls.get(position).getPrice());
        holder.room_desc.setText(ls.get(position).getDesc());
//        holder.room_pic.setImageResource(ls.get(position).getPic());

        rowClickListeners(holder, ls.get(position).getRoomId());

    }

    private void rowClickListeners(@NonNull myViewHolder holder, String roomId) {
        holder.room_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSearchPackageActicity(roomId);
            }
        });
        holder.room_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSearchPackageActicity(roomId);
            }
        });
    }

    private void launchSearchPackageActicity(String roomId) {

        Intent intent = new Intent(c, SearchPackageActivity.class);
        intent.putExtra("roomId", roomId);
        c.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView room_id, room_desc, room_price;
        ImageView room_pic;

        myViewHolder(@NonNull View itemView) {
            super(itemView);
            room_id = itemView.findViewById(R.id.search_room_id);
            room_desc = itemView.findViewById(R.id.search_room_desc);
            room_price = itemView.findViewById(R.id.search_room_price);
        }

    }



}
