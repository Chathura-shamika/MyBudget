package com.admd.mybudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView aboutDetailsTextView = findViewById(R.id.helpDetailsTextView);
        String usermanual =
                "\n\n1. Registration and Login:\n\n" +
                        "     1.1 Sign Up (*Functionality is working*):\n"  +
                        "         Users can register for the system using the signup button on the login page.\n\n"+
                        "      1.2 Forget Password:\n"+
                        "         Users can change their password if forgotten through the forget password section.\n\n"+
                        "      1.3 Login (*Functionality is working*)\n"+
                        "          Users can log in using valid credentials on the login page.\n\n\n"+
                "2. Dashboard\n\n"+
                        "     2.1 2.1 Overview\n" +
                        "           Displays several cards to help users balance their budget.\n\n" +
                        "     2.1.1 First Card Section (Expenses)\n" +
                        "            Allows users to add amounts separately for Education, Finance, and Utility expenses.\n\n" +
                        "     2.1.2 Second Card Section (Income)\n" +
                        "             Displays main and extra income types.\n\n" +
                        "     2.1.3 Third Card Section (Profile, Settings, Report)\n" +
                        "             Allows users to change details and passwords in the profile section (*Functionality is working-Details display only*).\n" +
                        "             Setting and Report sections are currently non-functional.\n\n\n" +
                "3. Bottom Menu Bar (*Functionality is working*)\n\n" +
                        "       3.1 Sections\n" +
                        "               Dashboard, Income, and Expenses sections are functional.\n\n" +
                        "       3.1.1 Dashboard\n" +
                        "               Displays total income, total expenses, and total balance.\n\n" +
                        "       3.1.2 Income Section\n" +
                        "               Displays previous income details.\n" +
                        "               Users can add new income using the \"Add New Income\" button.\n\n" +
                        "       3.1.3 Expenses Section\n" +
                        "               Displays previous expenses details.\n" +
                        "               Users can add new expenses using the \"Add New Expenses\" button.\n\n\n" +
                "4. Add New Income/Expenses (*Functionality is working*)\n\n" +
                        "       4.1 Required Fields\n" +
                        "               Type, Date, and Amount for adding new income.\n" +
                        "               Type, Date, and Amount for adding new expenses.\n\n\n" +
                "5. Top of Dashboard, Income, and Expenses Pages (*Functionality is working*)\n\n" +
                        "       5.1 Total Display\n" +
                        "               Dashboard: Total Income, Total Expenses, Total Balance.\n" +
                        "               Income: Total Income.\n" +
                        "               Expenses: Total Expenses.\n\n\n" +
                "6. About Section (*Functionality is working*)\n\n" +
                        "       6.1 Overview\n" +
                        "               Student & System information about the project.\n\n\n" +
                "7. Help Section (*Functionality is working*)\n\n" +
                        "       7.1 User Manual\n" +
                        "               Supports users by displaying the user manual.\n\n\n" +
                "8. Logout (*Functionality is working*)\n\n" +
                        "       8.1 Logout Button\n" +
                        "               Allows users to log out of the system.\n\n"
                ;
        aboutDetailsTextView.setText(usermanual);
        findViewById(R.id.dashboardbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }


}