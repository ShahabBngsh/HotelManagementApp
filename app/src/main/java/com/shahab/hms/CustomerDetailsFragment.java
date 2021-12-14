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
import android.widget.Toast;

import com.shahab.hms.databinding.FragmentCustomerDetailsBinding;

import java.util.ArrayList;
import java.util.List;

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
        ls.add(new Profile("Usama", "c@a.com", "03017654321", "F10/2 street 13", "my bio"));
        ls.add(new Profile("Piyush", "d@a.com", "03012143657", "I11/2 street 10", "None"));
        ls.add(new Profile("Zain", "e@a.com", "03013216541", "G7/3 street 19", "htis is a bio"));
        ls.add(new Profile("Zohair", "f@a.com", "03014321765", "F11/2 street 69", "another random bio"));
        ls.add(new Profile("Saad", "g@a.com", "03011243563", "F6/1 street 5", ""));
        arrayList.addAll(ls);
//        contactsContactsList.add(new contactsSingleContact("Friend1","11111111111"));
//        contactsContactsList.add(new contactsSingleContact("Friend1","11111111111"));
//        contactsContactsList.add(new contactsSingleContact("Friend1","11111111111"));
//        contactsContactsList.add(new contactsSingleContact("Friend1","11111111111"));
//        contactsContactsList.add(new contactsSingleContact("Friend1","11111111111"));
//        contactsContactsList.add(new contactsSingleContact("Friend1","11111111111"));
//        contactsContactsList.add(new contactsSingleContact("Friend1","11111111111"));
//        contactsContactsList.add(new contactsSingleContact("Friend1","11111111111"));
//        contactsContactsList.add(new contactsSingleContact("Friend1","11111111111"));
        adapter = new ProfileRVAdapter(arrayList, getContext());
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        searchView = view.findViewById(R.id.home_search);
        searchView.setOnQueryTextListener(this);

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(getContext(), "*" + newText, Toast.LENGTH_SHORT).show();
        adapter.filter(newText);
        return true;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }
//
}