package com.admd.mybudget;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.DocumentSnapshot;

public class ExpenseDetailsFragment extends DialogFragment {

    private DocumentSnapshot documentSnapshot;

    public ExpenseDetailsFragment(DocumentSnapshot documentSnapshot) {
        this.documentSnapshot = documentSnapshot;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_details, container, false);

        TextView detailsTextView = view.findViewById(R.id.detailsTextView);
        Button editButton = view.findViewById(R.id.editButton);
        Button deleteButton = view.findViewById(R.id.deleteButton);

        // Extract details from the DocumentSnapshot
        String amount = documentSnapshot.getString("amount");
        String dateTime = documentSnapshot.getString("date_time");
        String description = documentSnapshot.getString("description");
        String expenseType = documentSnapshot.getString("expense_type");

        // Display details in the TextView
        String expenseDetails = "Date: " + dateTime + "\n"
                + "LKR: " + amount + "\n"
                + "Expense Type: " + expenseType + "\n"
                + "Description: " + description;

        detailsTextView.setText(expenseDetails);

        // Set click listeners for buttons
        editButton.setOnClickListener(v -> {
            // Handle edit button click
            // Implement your edit logic here
            // You can pass expense details to the edit screen (documentSnapshot)
            dismiss(); // Close the dialog after clicking edit
        });

        deleteButton.setOnClickListener(v -> {
            // Handle delete button click
            // Implement your delete logic here
            // You can use documentSnapshot.getId() to get the document ID for deletion
            dismiss(); // Close the dialog after clicking delete
        });

        return view;
    }
}
