package com.shahab.hms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahab.hms.R;
import com.shahab.hms.room;

import java.util.List;

public class PackageRVAdapter extends RecyclerView.Adapter<PackageRVAdapter.myViewHolder> {
    List<Package> ls;
    Context c;
    String roomId;

    public PackageRVAdapter(List<Package> ls, Context c, String roomId) {
        this.ls = ls;
        this.c = c;
        this.roomId = roomId;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.search_package_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.room_id.setText(ls.get(position).getId());
        holder.room_price.setText(ls.get(position).getPrice());
        holder.room_desc.setText(ls.get(position).getDesc());

        rowClickListeners(holder, ls.get(position).getId(), ls.get(position).getPrice());

    }

    private void rowClickListeners(myViewHolder holder, String id, String price) {
        holder.room_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchConfirmBookingActicity(id, price);
            }
        });
        holder.room_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchConfirmBookingActicity(id, price);
            }
        });
    }

    private void launchConfirmBookingActicity(String id, String price) {
        Intent intent = new Intent(c, ConfirmBookingActivity.class);
        Bundle extras = new Bundle();
        extras.putString("packageId", id);
        extras.putString("roomId", roomId);
        extras.putString("packagePrice", price);
        intent.putExtras(extras);
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
            room_id = itemView.findViewById(R.id.search_package_id);
            room_desc = itemView.findViewById(R.id.search_package_desc);
            room_price = itemView.findViewById(R.id.search_package_price);
        }

    }



}
