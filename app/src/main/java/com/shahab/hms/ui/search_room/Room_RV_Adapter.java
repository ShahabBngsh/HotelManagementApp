package com.shahab.hms.ui.search_room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahab.hms.Profile;
import com.shahab.hms.R;
import com.shahab.hms.SearchPackageActivity;
import com.shahab.hms.room;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Room_RV_Adapter extends RecyclerView.Adapter<Room_RV_Adapter.myViewHolder> implements Filterable{
    List<room> ls;
    ArrayList<room> lsCopy;
    Context c;

    public Room_RV_Adapter(List<room> ls, Context c) {
        this.ls = ls;
        this.c = c;
        this.lsCopy = new ArrayList<room>();
        this.lsCopy.addAll(ls);
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
        Picasso.get().load(ls.get(position).getPic()).resize(100,100).into(holder.room_pic);

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
            room_pic = itemView.findViewById(R.id.search_room_pic);
        }

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public void filter(String text, List<room> oldls) {
        lsCopy.clear();
//        Toast.makeText(c, String.valueOf(lsCopy.size()), Toast.LENGTH_SHORT).show();
        lsCopy.addAll(oldls);
        ls.clear();
        if(text.isEmpty()){
            ls.addAll(lsCopy);
        } else{
            text = text.toLowerCase();
            for(room item: lsCopy){
                if(String.valueOf(item.getPrice()).contains(text)){
                    ls.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }



}
