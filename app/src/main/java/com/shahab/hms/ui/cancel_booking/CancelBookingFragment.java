package com.shahab.hms.ui.cancel_booking;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahab.hms.databinding.FragmentCancelBookingBinding;

public class CancelBookingFragment extends Fragment {

    private CancelBookingViewModel cancelBookingViewModel;
    private FragmentCancelBookingBinding binding;

    EditText room_id, package_id;
    Button cancel_button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cancelBookingViewModel =
                new ViewModelProvider(this).get(CancelBookingViewModel.class);

        binding = FragmentCancelBookingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences prefs = getActivity().getSharedPreferences("app_values", Context.MODE_PRIVATE);
        String userid = prefs.getString("userid", null);


        room_id = binding.cancelRoomId;
        package_id = binding.cancelPackageId;
        cancel_button = binding.cancelButton;

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference invoice_ref = database.getReference("/Invoice");

                invoice_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data:snapshot.getChildren()) {
                            String packageId = data.child("packageId").getValue().toString();
                            String customerId = data.child("customerId").getValue().toString();
                            String roomId = data.child("roomId").getValue().toString();


                            if (packageId.equals(package_id.getText().toString()) && roomId.equals(room_id.getText().toString()) && customerId.equals(userid)) {
                                DatabaseReference checkout_ref = database.getReference("/Invoice/" + data.getKey() + "/checkoutStatus");
                                checkout_ref.setValue("1");

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });


//        final TextView textView = binding.textNotifications;
//        cancelBookingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}