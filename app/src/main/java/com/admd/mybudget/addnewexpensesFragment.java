package com.admd.mybudget;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addnewexpensesFragment extends Fragment {

    private EditText expenseTypeEditText;
    private EditText dateTimeEditText;
    private EditText amountEditText;
    private EditText descriptionEditText;
    private Button submitExpensesButton;
    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnewexpenses, container, false);

        initializeViews(view);

        mAuth= FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        submitExpensesButton.setOnClickListener(v -> {
            disableInputFields();
            if (validateInput()) {
                saveDataToFirestore();
            } else {
                enableInputFields();
            }
        });

        Button cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> {
            clearInputFields();
            enableInputFields();
        });

        // Initialize and set up the date picker for the date_time_edit_text
        setDateTimePicker(dateTimeEditText);

        return view;
    }

    private void initializeViews(View view) {
        expenseTypeEditText = view.findViewById(R.id.expense_type_edit_text);
        dateTimeEditText = view.findViewById(R.id.date_time_edit_text);
        amountEditText = view.findViewById(R.id.amount_edit_text);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        submitExpensesButton = view.findViewById(R.id.submit_expenses);
    }

    private void disableInputFields() {
        expenseTypeEditText.setEnabled(false);
        dateTimeEditText.setEnabled(false);
        amountEditText.setEnabled(false);
        descriptionEditText.setEnabled(false);
        submitExpensesButton.setEnabled(false);
    }

    private void enableInputFields() {
        expenseTypeEditText.setEnabled(true);
        dateTimeEditText.setEnabled(true);
        amountEditText.setEnabled(true);
        descriptionEditText.setEnabled(true);
        submitExpensesButton.setEnabled(true);
    }

    private boolean validateInput() {
        boolean isValid = true;

        String expenseType = expenseTypeEditText.getText().toString().trim();
        String dateTime = dateTimeEditText.getText().toString().trim();
        String amount = amountEditText.getText().toString().trim();

        if (TextUtils.isEmpty(expenseType)) {
            expenseTypeEditText.setError("Required");
            isValid = false;
        } else {
            expenseTypeEditText.setError(null);
        }

        if (TextUtils.isEmpty(dateTime)) {
            dateTimeEditText.setError("Required");
            isValid = false;
        } else {
            dateTimeEditText.setError(null);
        }

        if (TextUtils.isEmpty(amount)) {
            amountEditText.setError("Required");
            isValid = false;
        } else {
            amountEditText.setError(null);
        }

        return isValid;
    }

    private void saveDataToFirestore() {
        String expenseType = expenseTypeEditText.getText().toString();
        String dateTime = dateTimeEditText.getText().toString();
        String amount = amountEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        userID = mAuth.getCurrentUser().getUid();

        Map<String, Object> expenses = new HashMap<>();
        expenses.put("user_ID",userID);
        expenses.put("expense_type", expenseType);
        expenses.put("date_time", dateTime);
        expenses.put("amount", amount);
        expenses.put("description", description);

        firestore.collection("expenses")
                .add(expenses)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(requireContext(), "Expenses Added Successfully..!", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                    enableInputFields();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error adding expenses", e);
                    Toast.makeText(requireContext(), "Error adding expenses. Please try again.", Toast.LENGTH_SHORT).show();
                    enableInputFields();
                });
    }

    private void clearInputFields() {
        expenseTypeEditText.setText("");
        dateTimeEditText.setText("");
        amountEditText.setText("");
        descriptionEditText.setText("");
    }

    private void setDateTimePicker(EditText dateTimeEditText) {
        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateTimeEditText(dateTimeEditText);
        };

        dateTimeEditText.setOnClickListener(v -> new DatePickerDialog(
                requireContext(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show());
    }

    private void updateDateTimeEditText(EditText dateTimeEditText) {
        // Format the date as needed, e.g., "dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateTimeEditText.setText(dateFormat.format(calendar.getTime()));
    }
}
