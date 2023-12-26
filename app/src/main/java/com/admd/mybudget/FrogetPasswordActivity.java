package com.admd.mybudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FrogetPasswordActivity extends AppCompatActivity {

    private Button forgetPasswordButton;
    private EditText emailAddressEditText;
    private Button cancelButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_froget_password_activity);

        forgetPasswordButton = findViewById(R.id.Sendverifycation);
        emailAddressEditText = findViewById(R.id.editTextEmailAddress);
        cancelButton = findViewById(R.id.navigatetologinpage);
        progressBar = findViewById(R.id.progressBar);

        forgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = emailAddressEditText.getText().toString();

                if (!emailAddress.isEmpty()) {
                    Toast.makeText(FrogetPasswordActivity.this, "Check your Email: " + emailAddress, Toast.LENGTH_SHORT).show();
                    emailAddressEditText.setText("");
                } else {
                    emailAddressEditText.setError("Email field is required");
                }

            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(FrogetPasswordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
