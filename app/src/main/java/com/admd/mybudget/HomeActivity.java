package com.admd.mybudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.admd.mybudget.databinding.ContentMainBinding;

public class HomeActivity extends AppCompatActivity {


   ContentMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       binding = ContentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new dashboardFragment());

        binding.bottomNavigationview1.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.dashboard) {
                replaceFragment(new dashboardFragment());
            }
            else if (itemId == R.id.income) {
                replaceFragment(new IncomeFragment());
            } else if (itemId == R.id.expenses) {
                replaceFragment(new ExpensesFragment());
            } else {
                replaceFragment(new dashboardFragment());
            }

            return true;
        });

        navigateFunction();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout01, fragment);
        fragmentTransaction.commit();
    }

    private TextView topbarhelp;
    private TextView topbarabout;
    private TextView topbarLogout;

    private void navigateFunction() {
        topbarhelp = findViewById(R.id.help_top_bar);
        topbarabout = findViewById(R.id.about_top_bar);
        topbarLogout = findViewById(R.id.logout_top_bar);

        topbarhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        topbarabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        topbarLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}
