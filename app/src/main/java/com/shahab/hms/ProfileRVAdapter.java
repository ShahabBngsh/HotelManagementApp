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

public class ProfileRVAdapter extends RecyclerView.Adapter<ProfileRVAdapter.profileViewHolder> implements Filterable{
    List<Profile> ls;
    ArrayList<Profile> lsCopy;
    Context c;
    public ProfileRVAdapter(List<Profile> ls, Context c){
        this.c=c;
        this.ls=ls;
        this.lsCopy = new ArrayList<Profile>();
        this.lsCopy.addAll(ls);
    }
    @NonNull
    @Override
    public ProfileRVAdapter.profileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView= LayoutInflater.from(c).inflate(R.layout.profile_row, viewGroup,false);
        return new profileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull profileViewHolder Holder, int i) {
        Holder.name.setText(ls.get(i).getName());
        Holder.email.setText(ls.get(i).getEmail());
        Holder.contactno.setText(ls.get(i).getContactno());
        Holder.address.setText(ls.get(i).getAddress());
        Holder.bio.setText(ls.get(i).getBio());
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public void filter(String text, List<Profile> oldls) {
        lsCopy.clear();
//        Toast.makeText(c, String.valueOf(lsCopy.size()), Toast.LENGTH_SHORT).show();
        lsCopy.addAll(oldls);
        ls.clear();
        if(text.isEmpty()){
            ls.addAll(lsCopy);
        } else{
            text = text.toLowerCase();
            for(Profile item: lsCopy){
                if(item.name.toLowerCase().contains(text) || item.address.toLowerCase().contains(text)){
                    ls.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class profileViewHolder extends RecyclerView.ViewHolder {
        TextView name,email, contactno, address, bio;
        public profileViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.profile_row_name);
            email=itemView.findViewById(R.id.profile_row_email);
            contactno=itemView.findViewById(R.id.profile_row_phone);
            address=itemView.findViewById(R.id.profile_row_address);
            bio=itemView.findViewById(R.id.profile_row_bio);
        }
    }
}
