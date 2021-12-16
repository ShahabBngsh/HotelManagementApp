package com.shahab.hms;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addPackageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addPackageFragment extends Fragment {
    EditText price, desc, packageId;
    Button addPackage;
    DatabaseReference storePackageReference;
    FirebaseDatabase database;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addPackageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addPackageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addPackageFragment newInstance(String param1, String param2) {
        addPackageFragment fragment = new addPackageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_package, container, false);
        packageId=view.findViewById(R.id.addPackage_Id);
        price=view.findViewById(R.id.addPackage_Price);
        desc=view.findViewById(R.id.addPackage_Description);
        addPackage=view.findViewById(R.id.addPackage_addPackage);
        addPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (packageId.getText().toString().matches("") || price.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Fill the input fields", Toast.LENGTH_SHORT).show();
                } else {
                    database = FirebaseDatabase.getInstance();
                    storePackageReference = database.getReference("Package");
                    storePackageReference.push().setValue(
                            new Package(
                                    packageId.getText().toString(),
                                    desc.getText().toString(),
                                    price.getText().toString())
                    );
                    Toast.makeText(getActivity(), "New Package Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ManagerNavigationActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}