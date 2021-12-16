package com.shahab.hms;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SigninActivity extends AppCompatActivity {
    Button btn_signup;
    TextView txtView_login;

    TextView txtView_email;
    TextView txtView_password;
    TextView txtView_confirmpassword;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        txtView_email = findViewById(R.id.signin_emailfield);
        txtView_password = findViewById(R.id.signin_passwordfield);
        txtView_confirmpassword = findViewById(R.id.signin_confirmpasswordfield);

        btn_signup = findViewById(R.id.signin_signin);
        btn_signup.setOnClickListener(view -> initiateSignIn());
    }

    private void initiateSignIn() {
        String email = txtView_email.getText().toString();
        String password = txtView_password.getText().toString();
        String confirmpassword = txtView_confirmpassword.getText().toString();

        if (password.equals(confirmpassword)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(SigninActivity.this, password,
                                        Toast.LENGTH_SHORT).show();

                                Log.d("TAG", "createUserWithEmail:success");

                                FirebaseUser user = mAuth.getCurrentUser();
                                //----------------------------------- move this code to new page
                                String userid = user.getUid();

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("users/" + userid + "/Profile");
                                Profile userProfile = new Profile(userid, "Name", email, "03010000000", "A/0 street 00", "lorem ipsum", -1L); //name email phone address bio
                                myRef.setValue(userProfile);
                                //----------------------------------


                                FirebaseAuth.getInstance().signOut();

                                Intent loginIntent = new Intent(SigninActivity.this, MainActivity.class);
                                startActivity(loginIntent);


                            } else {
                                if (password.length()<8) {
                                    Toast.makeText(SigninActivity.this, "Password should be at least 8 characters long",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SigninActivity.this, "Email is already registered!",
                                            Toast.LENGTH_SHORT).show();
                                }

                                Log.w("TAG", "createUserWithEmail:failure", task.getException());

                            }
                        }
                    });

        } else {
            Toast.makeText(this, "Password are not matching", Toast.LENGTH_SHORT).show();
        }
    }

}
