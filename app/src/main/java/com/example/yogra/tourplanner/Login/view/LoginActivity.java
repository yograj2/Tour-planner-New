package com.example.yogra.tourplanner.Login.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yogra.tourplanner.BaseActivity;
import com.example.yogra.tourplanner.Home.HomeActivity;
import com.example.yogra.tourplanner.R;
import com.example.yogra.tourplanner.Signup.view.SignupActivity;
import com.example.yogra.tourplanner.TourPlannerConstant;
import com.example.yogra.tourplanner.Util.PreferenceHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends BaseActivity {

    public Button signup;
    public Button login;
    public EditText loginemailid;
    public EditText loginpassword;

    public DatabaseReference mdatabaseReference;
    public ProgressDialog progressDialog;
    //public FloatingActionButton floatingActionButton;
    private static final String TAG = "LoginActivity";

    //Firebase Authentication
    private FirebaseAuth firebaseAuth;

    Context context;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginemailid = findViewById(R.id.login_email_id);
        loginpassword = findViewById(R.id.login_password);
        login = findViewById(R.id.login_button);
        signup = findViewById(R.id.signup_button);
        //floatingActionButton=findViewById(R.id.Home_button_admin);
        context=this;

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
                    /*final String adminEmail = "admin@gmail.com";
                    final String adminPassword = "admin@1234";*/

                    DatabaseReference users = mdatabaseReference.child("users");
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        boolean isExist = false;

                       // @SuppressLint("RestrictedApi")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                if ((ds.child("email").getValue().toString().equals(email) && ds.child("password").getValue().toString().equals(password))
                                        )
                                {

                                    Log.d(TAG,"Added succesfullyy!!!!!!!!!!!!!!");
                                    isExist = true;
                                    break;
                                }
                            }
                            if (isExist) {
                                intent = new Intent(LoginActivity.this, HomeActivity.class);
                               /* CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams();
                                params.setBehavior(null);
                                floatingActionButton.requestLayout();
                                floatingActionButton.setVisibility(View.GONE);*/
                                //floatingActionButton.setEnabled(false);
                                String userType;
                                if  (email.equalsIgnoreCase("admin@gmail.com")) {
                                    userType = TourPlannerConstant.ADMIN_USER;
                                } else {
                                    userType = TourPlannerConstant.NORMAL_USER;
                                }
                                //intent.putExtra(TourPlannerConstant.USER_TYPE,userType);
                                //mPreferenceHelper.putBoolean("check",true);
                                mPreferenceHelper.putExtra(TourPlannerConstant.USER_TYPE,userType);

                                startActivity(intent);
                                mPreferenceHelper.putBoolean(PreferenceHelper.IS_LOGIN,true);
                                Log.d(TAG,"preference sucessfully");
                                finish();


                              // floatingActionButton.show();

                            }
                            else {
                                progressDialog.dismiss();
                               //floatingActionButton.hide();

                                Toast.makeText(LoginActivity.this, "Please First Registered ", Toast.LENGTH_SHORT).show();
                            }
                           }



                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Log.d(TAG, "onCancelled : ");
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

    @Override
    protected void init() {

    }
}
