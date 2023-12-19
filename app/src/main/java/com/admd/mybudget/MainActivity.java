package com.admd.mybudget;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPass;
    private Button btnLogin;
    private TextView mForgetPassword;
    private TextView mSignupHere; // Move the initialization here
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        mSignupHere = findViewById(R.id.Signup_login);

        loginDetails();
    }

    private void loginDetails() {
        mEmail = findViewById(R.id.email_login);
        mPass = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.button_login);
        mForgetPassword = findViewById(R.id.forget_password_login);
        progressBar = findViewById(R.id.progressBarloin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vis) {
                String email = mEmail.getText().toString().trim();
                String password = mPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email Required..");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPass.setError("Password Required..");
                    return;
                } else {
                    progressBar.setVisibility(View.VISIBLE); // Show progress bar

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE); // Hide progress bar

                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Welcome to MyBudget", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(vis.getContext(), HomeActivity.class);
                                        vis.getContext().startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please check your Login details and try again..", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        mSignupHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegistaionActivity.class);
                view.getContext().startActivity(intent);
            }

        });

        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FrogetPasswordActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
}
