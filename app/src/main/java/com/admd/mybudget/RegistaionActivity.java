package com.admd.mybudget;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class RegistaionActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private EditText mEmail;
    private EditText mPass;
    private EditText mName;
    private EditText mMobilenumber;
    private String userID;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registaion);

        mAuth=FirebaseAuth.getInstance();
        fStore =FirebaseFirestore.getInstance();
        registration();
    }

    private void registration() {

        mEmail = findViewById(R.id.reg_email_login);
        mPass = findViewById(R.id.reg_password_login);
        mName =findViewById(R.id.reg_username);
        mMobilenumber = findViewById(R.id.mobilenumber);
        progressBar = findViewById(R.id.progressBarloin);
        Button btnReg = findViewById(R.id.reg_form_submit);
        TextView mSignin = findViewById(R.id.reg_Signup_login);



    btnReg.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View views) {

            String email = mEmail.getText().toString().trim();
            String password = mPass.getText().toString().trim();
            String User_Name = mName.getText().toString().trim();
            String Mobil_Number = mMobilenumber.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                progressBar.setVisibility(View.GONE);
                mEmail.setError("Email Required..");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                progressBar.setVisibility(View.GONE);
                mPass.setError("Password Required..");
                return;
            }

            if (mAuth.getCurrentUser() != null) {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(views.getContext(), HomeActivity.class);

            }
            progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "Registration Success..!", Toast.LENGTH_SHORT).show();

                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference =fStore.collection("users").document(userID);
                            Map<String,Object> user =new HashMap<>();
                            user.put("user_ID",userID);
                            user.put("mName",User_Name);
                            user.put("mMobilenumber",Mobil_Number);
                            user.put("mEmail",email);
                            user.put("mPass",password);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"onSuccess: user profile created for"+userID);
                                }
                            });

                            Intent intent = new Intent(views.getContext(), HomeActivity.class);
                            views.getContext().startActivity(intent);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Error..." + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        }
    });

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);}

        });
    }

}