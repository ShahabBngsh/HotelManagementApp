package com.shahab.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManagerNavigationActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_navigation);
        bottomNavView=findViewById(R.id.bottomNav);
        bottomNavView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new RoomUpdateFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment appFragment=null;
            switch (item.getItemId()){
                case R.id.addPackage:
                    appFragment=new addRoomFragment();
                    break;
                case R.id.addRoom:
                    appFragment=new addPackageFragment();
                    break;
                case R.id.roomUpdate:
                    appFragment=new RoomUpdateFragment();
                    break;
//                case R.id.packageUpdate:
//                    appFragment=new PackageUpdateFragment();
//                    break;
                case R.id.customerDetail:
                    appFragment=new CustomerDetailsFragment();
                    break;
//                case R.id.duePayment:
//                    appFragment=new DuePaymentFragment();
//                    break;
                case R.id.payments:
                    appFragment=new PaymentFragment();
                    break;
//                case R.id.checkout:
//                    appFragment=new CheckoutFragment();
//                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container,appFragment).commit();
            return true;
        }
    };
}