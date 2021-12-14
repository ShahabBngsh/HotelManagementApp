package com.shahab.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmBookingActivity extends AppCompatActivity {

    String roomId;
    String packageId;
    TextView room_desc, room_id, package_desc, package_id, price_amount;
    Button confirm_booking;

    int roomPrice = 0;
    int packagePrice = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_booking);

        SharedPreferences prefs = getSharedPreferences("app_values", Context.MODE_PRIVATE);
        String userid = prefs.getString("userid", null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roomId = extras.getString("roomId");
            packageId = extras.getString("packageId");
            String packagePriceString = extras.getString("packagePrice");
            packagePrice = Integer.parseInt(packagePriceString);
        }

        room_desc = findViewById(R.id.confirm_room_desc);
        room_id = findViewById(R.id.confirm_room_id);
        package_desc = findViewById(R.id.confirm_package_desc);
        package_id = findViewById(R.id.confirm_package_id);
        price_amount = findViewById(R.id.confirm_price_amount);
        confirm_booking = findViewById(R.id.confirm_button);

        setValues();

        confirm_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference invoice_ref = database.getReference("/Invoice");
                invoice_ref.push().setValue(new Invoice(roomId, packageId, userid, roomPrice + packagePrice, 0, 0));
            }
        });

        Toast.makeText(ConfirmBookingActivity.this, roomId+" "+packageId, Toast.LENGTH_SHORT).show();



    }

    private void setValues() {
        room_id.setText(roomId);
        package_id.setText(packageId);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference room_ref = database.getReference("/Room");

        room_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren()) {
                    String to_add_id = data.child("roomId").getValue().toString();

                    if (to_add_id.equals(roomId)) {
                        room_desc.setText(data.child("desc").getValue().toString());
                        roomPrice = Integer.parseInt(data.child("price").getValue().toString());

                        price_amount.setText(Integer.toString(roomPrice + packagePrice));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}