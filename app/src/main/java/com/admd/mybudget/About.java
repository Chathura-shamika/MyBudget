package com.admd.mybudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        TextView aboutDetailsTextView = findViewById(R.id.aboutDetailsTextView);
        String aboutText = "Language, Version, and Framework:\n" +"\n"+
                "*   Language: The primary language I used in my project, for typical Android development, is likely Java or Kotlin.\n" +
                "*   Version: While the specific version of the language isn't explicitly mentioned, I've tailored it based on compatibility with the Android SDK and libraries, considering the Android version supported by the development environment.\n" +
                "*   Framework: I built my project on the Android framework, standard for mobile applications on Android devices. This includes utilizing the Android SDK, which provides essential tools and libraries for app development.\n" +"\n"+

                "Android Version and Firebase Connection:\n" +
                "*  Android Version: The project is designed to run on a specific Android version, ensuring compatibility and optimal performance. This version is determined by the targeted devices and the features utilized in the app.\n" +
                "*  Firebase Connection: The project integrates with Firebase, specifically Firebase Firestore, for efficient data storage and retrieval. This connection allows seamless management of income and expense data.\n" +
                "\n"+

                "Main Purpose of the Project:\n" +
                "*  The primary goal of my project is to create a comprehensive budget management mobile application.\n" +
                "\n"+"\n" +

                "Functionality:\n" +
                "   Login and Register: Allows me to log in or register for an account to access personalized features.\n" +
                "Income and Expense Addition:\n" +
                "   I can add details of my income transactions.\n" +
                "   I can add details of my expense transactions.\n" +
                "Dashboard Display:\n" +
                "   The dashboard displays a summary of income, expenses, and the balance.\n" +
                "   It provides an overview of my financial status.\n" +
                "About Page:\n" +
                "   Displays information about the application, possibly including details about the developer or team behind the app.\n" +
                "Home Page (Customer Support):\n" +
                "   Provides support to me, possibly containing FAQs, contact information, or links to customer support services.\n" +
                "Profile Page:\n" +
                "   Allows me to view and edit my profile details.\n" +
                "Report Page:\n" +
                "   Supports the generation of reports, providing insights into expenses and income patterns.\n" +"\n"+
                "Conclusion:\n" +
                "*  My project is tailored for a specific Android version, ensuring compatibility, and leverages Firebase for efficient data storage and retrieval. The focus is on providing me with a comprehensive budget management solution, covering user authentication, transaction recording, dashboard visualization, support features, and profile management, with the added ability to generate insightful reports."
                +"\n" +"\n"
                ;
        aboutDetailsTextView.setText(aboutText);
        progressBar = findViewById(R.id.progressBarabout);

        findViewById(R.id.dashboardbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}
