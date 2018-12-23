package com.example.yogra.tourplanner.Login.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yogra.tourplanner.R;
import com.example.yogra.tourplanner.Signup.view.SignupActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    public Button signup;
    public Button login;
    public EditText loginemailid;
    public EditText loginpassword;
    Intent intent;
    public DatabaseReference mdatabaseReference;
    public ProgressDialog progressDialog;

    //Firebase Authentication
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*firebaseAuth = FirebaseAuth.getInstance();

       if (firebaseAuth.getCurrentUser()!= null){
            finish();
            intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            Log.d("LoginActivity","successfully done");
        }*/

        loginemailid = findViewById(R.id.login_email_id);
        loginpassword = findViewById(R.id.login_password);
        login = findViewById(R.id.login_button);
        signup = findViewById(R.id.signup_button);

        //progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = loginemailid.getText().toString();
                String Password = loginpassword.getText().toString();
                if (Email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Email Id should not be empty", Toast.LENGTH_SHORT).show();
                    Log.d("Login", "Successfully done");
                } else if (Password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Password should not be empty", Toast.LENGTH_SHORT).show();
                }
                /*else{
                    firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        finish();
                                        intent = new Intent(LoginActivity.this,HomeActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }*/

                else {

                    progressDialog.setMessage("Registering please wait");
                    progressDialog.show();
                    mdatabaseReference = FirebaseDatabase.getInstance().getReference();
                    final String email = loginemailid.getText().toString();
                    final String password = loginpassword.getText().toString();
                    DatabaseReference users = mdatabaseReference.child("users");
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        boolean isExit = false;

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                if (ds.child("email").getValue().toString().equals(email) && ds.child("password").getValue().toString().equals(password)) {
                                    isExit = true;
                                    break;
                                }

                            }
                            if (isExit) {
                                intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Please First Registered ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Log.d("LoginActivity", "onCancelled : ");
                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
