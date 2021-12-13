package com.shahab.hms;

import android.os.Build;
import android.os.Bundle;

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

import com.shahab.hms.databinding.FragmentCustomerDetailsBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerDetailsFragment extends Fragment {

//    private CustomerDetailViewModel customerDetailViewModel;
//    private FragmentCustomerDetailsBinding binding;

    List<Profile> ls;
    RecyclerView rv;
    ProfileRVAdapter adapter;

    SearchView searchView;
    ArrayList<Profile> arrayList = new ArrayList<Profile>();

    public CustomerDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerDetailsFragment newInstance(String param1, String param2) {
        CustomerDetailsFragment fragment = new CustomerDetailsFragment();
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        CustomerDetailViewModel =
//                new ViewModelProvider(this).get(CustomerDetailViewModel.class);

        View view = inflater.inflate(R.layout.fragment_customer_details, container, false);
//        View root = view.getRootView();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

//        arrayList.addAll(ls);
        rv = view.findViewById(R.id.customerDetailRV);
        ls=new ArrayList<>();

//        ls = new ArrayList<>();
        ls.add(new Profile("Shahab", "b@a.com", "03011234567", "F11/2 street 69", ""));
        ls.add(new Profile("Usama", "c@a.com", "03017654321", "F10/2 street 13", "my bio"));
        ls.add(new Profile("Piyush", "d@a.com", "03012143657", "I11/2 street 10", "None"));
        ls.add(new Profile("Zain", "e@a.com", "03013216541", "G7/3 street 19", "htis is a bio"));
        ls.add(new Profile("Zohair", "f@a.com", "03014321765", "F11/2 street 69", "another random bio"));
        ls.add(new Profile("Saad", "g@a.com", "03011243563", "F6/1 street 5", ""));
//
        adapter = new ProfileRVAdapter(getContext(), ls);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_customer_details, container, false);
        return view;
    }
}