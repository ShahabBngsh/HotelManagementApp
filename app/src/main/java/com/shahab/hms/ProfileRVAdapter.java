package com.shahab.hms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileRVAdapter  extends RecyclerView.Adapter<ProfileRVAdapter.profileViewHolder> implements Filterable {

    Context c;
    List<Profile> ls;
    ArrayList<Profile> lsCopy;

    public ProfileRVAdapter(Context c, List<Profile> ls) {
        this.c = c;
        this.ls = ls;
        this.lsCopy = new ArrayList<Profile>();
        this.lsCopy.addAll(ls);
    }

    @NonNull
    @Override
    public profileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(c).inflate(R.layout.profile_row, parent,false);
        return new profileViewHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull profileViewHolder holder, int position) {
        Toast.makeText(c, "RVVVVVVVVVVVVVVV", Toast.LENGTH_SHORT).show();
        holder.profile_row_name.setText(ls.get(position).getName());
        holder.profile_row_email.setText(ls.get(position).getEmail());
        holder.profile_row_contactno.setText(ls.get(position).getContactno());
        holder.profile_row_address.setText(ls.get(position).getAddress());
        holder.profile_row_bio.setText(ls.get(position).getBio());
        rowClickListeners(holder);
    }
    private void rowClickListeners(@NonNull profileViewHolder holder) {
        holder.profile_row_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSpecificPersonActicity();
            }
        });
        holder.profile_row_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSpecificPersonActicity();
            }
        });
        holder.profile_row_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSpecificPersonActicity();
            }
        });
        holder.profile_row_contactno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSpecificPersonActicity();
            }
        });
        holder.profile_row_bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSpecificPersonActicity();
            }
        });
    }

    private void launchSpecificPersonActicity() {
        Toast.makeText(c, "Clicked ...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public void filter(String text) {
        ls.clear();
        if(text.isEmpty()){
            ls.addAll(lsCopy);
        } else{
            text = text.toLowerCase();
            for(Profile item: lsCopy){
                if(item.name .toLowerCase().contains(text) || item.address.toLowerCase().contains(text) || item.contactno.toLowerCase().contains(text)){
                    ls.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class profileViewHolder extends RecyclerView.ViewHolder {
        TextView profile_row_name, profile_row_email, profile_row_contactno, profile_row_address, profile_row_bio;
        public profileViewHolder(@NonNull View itemView) {
            super(itemView);
            Toast.makeText(c, "Clicked ...", Toast.LENGTH_SHORT).show();

            profile_row_name = itemView.findViewById(R.id.profile_row_name);
            profile_row_email = itemView.findViewById(R.id.profile_row_email);
            profile_row_contactno = itemView.findViewById(R.id.profile_row_phone);
            profile_row_address = itemView.findViewById(R.id.profile_row_address);
            profile_row_bio = itemView.findViewById(R.id.profile_row_bio);
        }
    }
}
