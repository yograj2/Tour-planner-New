package com.example.yogra.tourplanner.Signup.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogra.tourplanner.Home.HomeActivity;
import com.example.yogra.tourplanner.Login.view.LoginActivity;
import com.example.yogra.tourplanner.R;
import com.example.yogra.tourplanner.Util.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.AlgorithmConstraints;

public class SignupActivity extends AppCompatActivity {

    public EditText username;
    public EditText emailaddress;
    public EditText password;
    public EditText conformpassword;
    public Button button;
    Intent intent;
    Context context;
    public TextView textView;
    public ProgressDialog progressDialog;
   // private FirebaseAuth firebaseAuth;
    public DatabaseReference databaseReference;
    private static final String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

       // firebaseAuth = FirebaseAuth.getInstance();

        //if user already registerd

       /* if (firebaseAuth.getCurrentUser()!= null){
            finish();
             intent = new Intent(SignupActivity.this,HomeActivity.class);
             startActivity(intent);
        }*/

       // Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.Username);
        emailaddress = findViewById(R.id.emailAddress);
        password = findViewById(R.id.Password);
        conformpassword = findViewById(R.id.ConformPassword);
        button = findViewById(R.id.signup);
        textView = findViewById(R.id.tv_signup_login);

        //progress Bar

        progressDialog = new ProgressDialog(this);

       /* public boolean onSupportNavigateUp(){
            finish();
            return true;
        }*/

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Username = username.getText().toString();
                    String Email = emailaddress.getText().toString();
                    String Password = password.getText().toString();
                    String Conformpassword = conformpassword.getText().toString();

                    if(Username.isEmpty()){
                        Toast.makeText(SignupActivity.this,"User Name should be not empty",Toast.LENGTH_SHORT).show();
                    }
                    else if (Email.isEmpty()){
                        Toast.makeText(SignupActivity.this, "Email Id should be not empty", Toast.LENGTH_SHORT).show();
                    }
                    else if (Password.isEmpty()){
                        Toast.makeText(SignupActivity.this,"Password should not be empty",Toast.LENGTH_SHORT).show();
                    }
                    else if (Conformpassword.isEmpty()){
                        Toast.makeText(SignupActivity.this,"Conform password should not be empty",Toast.LENGTH_SHORT).show();
                    }
                    else if (!Password.equals(Conformpassword)){
                        Toast.makeText(SignupActivity.this,"Password should  not same",Toast.LENGTH_SHORT).show();
                    }
                    else{

                        /*firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
                                            DatabaseReference currentUserDB = databaseReference.child(firebaseAuth.getCurrentUser().getUid());
                                            currentUserDB.child("name").setValue(username);
                                            finish();
                                            intent = new Intent(SignupActivity.this,HomeActivity.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(SignupActivity.this,"Not registerd",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });*/

                        progressDialog.setMessage("Registering Please wait");
                        progressDialog.show();

                        //Set the values in user...

                        User user = new User();
                        user.setName(username.getText().toString());
                        user.setEmail(emailaddress.getText().toString());
                        user.setPassword(password.getText().toString());
                        user.setConformpassword(conformpassword.getText().toString());

                        //create database

                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        //String email = databaseReference.push().getKey();
                        String email = emailaddress.getText().toString();
                       // User  user = new User(Username,Email,Password,Conformpassword);
                      //  assert email != null;
                        databaseReference.child("users").child(email.substring(0, email.indexOf("@"))).setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"onSuccess :");
                                        intent = new Intent(SignupActivity.this,HomeActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG,"onError" +e.toString());
                                    }
                                });

                    }
                }

            });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    // Add Back Button

    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

