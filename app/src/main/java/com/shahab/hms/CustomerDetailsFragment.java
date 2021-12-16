package com.shahab.hms;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahab.hms.databinding.FragmentCustomerDetailsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerDetailsFragment extends Fragment implements SearchView.OnQueryTextListener{
    RecyclerView rv;
    List<Profile> ls;
    ProfileRVAdapter adapter;
    SearchView searchView;
    ArrayList<Profile> arrayList = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;


    public CustomerDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment contactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerDetailsFragment newInstance(String param1, String param2, String param3, String param4, String param5) {
        CustomerDetailsFragment fragment = new CustomerDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_details, container, false);
        rv = view.findViewById(R.id.customerDetailRV);
//        newContact=view.findViewById(R.id.contacts_newContact);
//        newGroup=view.findViewById(R.id.contacts_newGroup);
        ls = new ArrayList<>();
        getUsersFromFirebase();
        adapter = new ProfileRVAdapter(ls, getContext());

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);


        searchView = view.findViewById(R.id.home_search);
        searchView.setOnQueryTextListener(this);
        arrayList.addAll(ls);
        return view;
    }

    private void getUsersFromFirebase() {

        CountDownLatch done = new CountDownLatch(10);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("app_values", Context.MODE_PRIVATE);
        String USER_ID = sharedPref.getString("userid", "none");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users/");



        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren()){
                    String user_id = data.getKey();

                    FirebaseDatabase ddataSnapshotatabase2 = FirebaseDatabase.getInstance();
                    DatabaseReference profile_name = ddataSnapshotatabase2.getReference("users/" + user_id + "/Profile");

                    profile_name.addListenerForSingleValueEvent(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String user_name = snapshot.child("name").getValue().toString();
                            String user_email = snapshot.child("email").getValue().toString();
                            String user_address = snapshot.child("address").getValue().toString();
                            String user_contactno = snapshot.child("contactno").getValue().toString();
                            String user_bio = snapshot.child("bio").getValue().toString();

                            Profile curr_profile = new Profile(user_id, user_name, user_email, user_address, user_contactno, user_bio, 0L);

                            DatabaseReference invoice_ref = database.getReference("Invoice/");
                            invoice_ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot invoice_snapshot) {
                                    for (DataSnapshot invoice_data:invoice_snapshot.getChildren()) {
                                        String invoice_id = invoice_data.getKey();

                                        FirebaseDatabase firebase_db_instance = FirebaseDatabase.getInstance();
                                        DatabaseReference invoice_id_ref = firebase_db_instance.getReference("Invoice/" + invoice_id);

                                        invoice_id_ref.addValueEventListener(new ValueEventListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.N)
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Long user_dues = (Long)invoice_data.child("duePayment").getValue();
                                                Long user_paid = (Long)invoice_data.child("paidAmount").getValue();
                                                if (curr_profile.id.equals(snapshot.child("customerId").getValue().toString())) {
                                                    if (user_dues > user_paid) {
//                                                        Toast.makeText(getContext(), String.valueOf(user_dues) + " " + user_name, Toast.LENGTH_SHORT).show();
                                                        curr_profile.setDues(curr_profile.getDues()+user_dues);
                                                        if (!ls.contains(curr_profile)) {
                                                            ls.add(curr_profile);
                                                            arrayList.add(curr_profile);
                                                            adapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }

                        @Override

                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        done.countDown();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        adapter.filter(newText, arrayList);
        return true;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }
//
}