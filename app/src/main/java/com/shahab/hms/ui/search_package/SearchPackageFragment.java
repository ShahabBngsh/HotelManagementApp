package com.shahab.hms.ui.search_package;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.shahab.hms.MainActivity;
import com.shahab.hms.databinding.FragmentSearchPackageBinding;

public class SearchPackageFragment extends Fragment {

    private SearchPackageViewModel searchPackageViewModel;
    private FragmentSearchPackageBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchPackageViewModel =
                new ViewModelProvider(this).get(SearchPackageViewModel.class);

        binding = FragmentSearchPackageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sharedPref = getActivity().getSharedPreferences("app_values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("userid", "");
        editor.apply();

        Intent intent =  new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

//        final TextView textView = binding.textDashboard;
//        searchPackageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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