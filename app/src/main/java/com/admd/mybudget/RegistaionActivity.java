package com.admd.mybudget;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistaionActivity extends AppCompatActivity {
    private EditText mEmail;
    private EditText mPass;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registaion);

        mAuth=FirebaseAuth.getInstance();

        registration();
    }

    private void registration() {

        mEmail = findViewById(R.id.reg_email_login);
        mPass = findViewById(R.id.reg_password_login);
        Button btnReg = findViewById(R.id.reg_form_submit);
        TextView mSignin = findViewById(R.id.reg_Signup_login);


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View views) {

                String email = mEmail.getText().toString().trim();
                String password = mPass.getText().toString().trim();




                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email Required..");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPass.setError("Password Required..");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(getApplicationContext(), "Registration Success..!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(views.getContext(), MainActivity.class);
                                    views.getContext().startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Something Went Wrong..!", Toast.LENGTH_SHORT).show();
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