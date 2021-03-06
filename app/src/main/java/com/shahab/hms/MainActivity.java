package com.shahab.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText login_email;
    EditText login_password;
    Button btn_login;
    TextView txtView_signup;

    private FirebaseAuth mAuth;

    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        txtView_signup = findViewById(R.id.login_signinbtn);
        txtView_signup.setOnClickListener(view -> launchSignupActivity());

        btn_login = findViewById(R.id.login_loginbtn);
        btn_login.setOnClickListener(view -> launchNavigationActivity());

        login_email = findViewById(R.id.login_email_edittext);
        login_password = findViewById(R.id.login_password_edittext);

        try {
            SharedPreferences sharedPref = getSharedPreferences("app_values", Context.MODE_PRIVATE);
            String user_session = sharedPref.getString("userid", null);

            if (user_session != null && !user_session.isEmpty()) {
                if(user_session.equals("cIhWgHHzTzPSIwhKPFDQLWmzNzy1")){
                    Intent navigationIntent = new Intent(MainActivity.this, ManagerNavigationActivity.class);
                    startActivity(navigationIntent);
                }

                else {
                    Intent navigationIntent = new Intent(MainActivity.this, CustomerNavigationActivity.class);
                    startActivity(navigationIntent);
                }
            }



        }

        catch (Exception e) {

        }

    }

    private void launchNavigationActivity() {
        String login_email_check = login_email.getText().toString();
        String login_password_check = login_password.getText().toString();
        if (login_email_check.equals("") || login_password_check.equals("")) {
            Toast.makeText(MainActivity.this, "Input cannot be empty.",
                    Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(login_email_check, login_password_check)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                SharedPreferences sharedPref = getSharedPreferences("app_values", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("userid", user.getUid());
                                editor.apply();

                                if(user != null && user.getUid().equals("cIhWgHHzTzPSIwhKPFDQLWmzNzy1")){
//                                    Toast.makeText(MainActivity.this, "i'm da MANAGA", Toast.LENGTH_SHORT).show();
                                    Intent navigationIntent = new Intent(MainActivity.this, ManagerNavigationActivity.class);
                                    startActivity(navigationIntent);
                                } else if( user != null && !user.getUid().equals("cIhWgHHzTzPSIwhKPFDQLWmzNzy1")) {
//                                    Toast.makeText(MainActivity.this, "the customa is always right", Toast.LENGTH_SHORT).show();
                                    Intent navigationIntent = new Intent(MainActivity.this, CustomerNavigationActivity.class);
                                    startActivity(navigationIntent);
                                }


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }

    }

    private void launchSignupActivity() {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        Toast.makeText(this, "the customa is always right", Toast.LENGTH_SHORT).show();
        if(currentUser != null && currentUser.getUid().equals("cIhWgHHzTzPSIwhKPFDQLWmzNzy1")){
//            Toast.makeText(this, "i'm da MANAGA", Toast.LENGTH_SHORT).show();
            Intent navigationIntent = new Intent(this, ManagerNavigationActivity.class);
            startActivity(navigationIntent);
        } else if( currentUser != null && !currentUser.getUid().equals("cIhWgHHzTzPSIwhKPFDQLWmzNzy1")) {
            Intent navigationIntent = new Intent(this, CustomerNavigationActivity.class);
            startActivity(navigationIntent);
        }
//        Intent intent = new Intent(this, CustomerNavigationActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().signOut();
    }
}