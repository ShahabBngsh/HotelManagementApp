package com.shahab.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahab.hms.ui.search_room.Room_RV_Adapter;

import java.util.ArrayList;
import java.util.List;

public class SearchPackageActivity extends AppCompatActivity {

    RecyclerView search_package_rv;
    List<Package> ls;
    PackageRVAdapter adapter;
    String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_package);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roomId = extras.getString("roomId");
        }


        search_package_rv = findViewById(R.id.search_package_rv);
        ls=new ArrayList<>();
        getPackages();

        adapter = new PackageRVAdapter(ls, SearchPackageActivity.this, roomId);

        RecyclerView.LayoutManager lm=new LinearLayoutManager(SearchPackageActivity.this);
        search_package_rv.setLayoutManager(lm);
        search_package_rv.setAdapter(adapter);

    }

    private void getPackages() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference package_ref = database.getReference("/Package");

        package_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren()) {
                    String to_add_id = data.child("id").getValue().toString();
                    String to_add_price = data.child("price").getValue().toString();
                    String to_add_desc = data.child("desc").getValue().toString();
                    ls.add(new Package(to_add_id, to_add_desc, to_add_price));

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}