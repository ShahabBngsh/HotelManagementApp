package com.shahab.hms.ui.search_room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahab.hms.R;
import com.shahab.hms.databinding.FragmentSearchRoomBinding;
import com.shahab.hms.room;

import java.util.ArrayList;
import java.util.List;

public class SearchRoomFragment extends Fragment {

    private SearchRoomViewModel searchRoomViewModel;
    private FragmentSearchRoomBinding binding;


    RecyclerView search_view_rv;
    List<room> ls;
    Room_RV_Adapter adapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchRoomViewModel =
                new ViewModelProvider(this).get(SearchRoomViewModel.class);

        binding = FragmentSearchRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        search_view_rv = binding.searchRoomRv;
        ls=new ArrayList<>();
        getRooms();




        adapter = new Room_RV_Adapter(ls, getContext());

        RecyclerView.LayoutManager lm=new LinearLayoutManager(getActivity());
        search_view_rv.setLayoutManager(lm);
        search_view_rv.setAdapter(adapter);






//        final TextView textView = binding.textHome;
//        searchRoomViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private void getRooms() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference room_ref = database.getReference("/Room");

        room_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren()) {
                    String to_add_pic = data.child("pic").getValue().toString();
                    String to_add_id = data.child("roomId").getValue().toString();
                    String to_add_price = data.child("price").getValue().toString();
                    String to_add_desc = data.child("desc").getValue().toString();
                    ls.add(new room(to_add_id, to_add_desc, to_add_pic, to_add_price));

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}