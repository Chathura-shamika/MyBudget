package com.admd.mybudget;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addnewincomeFragment extends Fragment {

    private EditText incomeTypeEditText;
    private EditText dateTimeEditText1;
    private EditText amountEditText;
    private EditText descriptionEditText;
    private Button submitIncomeButton;

    private FirebaseFirestore firestore;
    private Calendar calendar1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnewincome, container, false);

        initializeViews(view);

        firestore = FirebaseFirestore.getInstance();

        submitIncomeButton.setOnClickListener(v -> {
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

        // Initialize date picker button
        setDateTimePickerincome(dateTimeEditText1);


        return view;
    }

    private void initializeViews(View view) {
        incomeTypeEditText = view.findViewById(R.id.incomeTypeEditText);
        dateTimeEditText1 = view.findViewById(R.id.dateTimeEditText1);
        amountEditText = view.findViewById(R.id.amount_edit_text);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        submitIncomeButton = view.findViewById(R.id.submitIncomeButton);
    }

    private void disableInputFields() {
        incomeTypeEditText.setEnabled(false);
        dateTimeEditText1.setEnabled(false);
        amountEditText.setEnabled(false);
        descriptionEditText.setEnabled(false);
        submitIncomeButton.setEnabled(false);
    }

    private void enableInputFields() {
        incomeTypeEditText.setEnabled(true);
        dateTimeEditText1.setEnabled(true);
        amountEditText.setEnabled(true);
        descriptionEditText.setEnabled(true);
        submitIncomeButton.setEnabled(true);
    }

    private boolean validateInput() {
        boolean isValid = true;

        String incomeType = incomeTypeEditText.getText().toString().trim();
        String dateTime = dateTimeEditText1.getText().toString().trim();
        String amount = amountEditText.getText().toString().trim();


        if (TextUtils.isEmpty(incomeType)) {
            incomeTypeEditText.setError("Required");
            isValid = false;
        } else {
            incomeTypeEditText.setError(null);
        }

        if (TextUtils.isEmpty(dateTime)) {
            dateTimeEditText1.setError("Required");
            isValid = false;
        } else {
            dateTimeEditText1.setError(null);
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
        String incomeType = incomeTypeEditText.getText().toString();
        String dateTime = dateTimeEditText1.getText().toString();
        String amount = amountEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        Map<String, Object> income = new HashMap<>();
        income.put("income_type", incomeType);
        income.put("date_time", dateTime);
        income.put("amount", amount);
        income.put("description", description);

        firestore.collection("income")
                .add(income)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(requireContext(), "Income Added Successfully..!", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                    enableInputFields();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "something went to wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    enableInputFields();
                });
    }

    private void clearInputFields() {
        incomeTypeEditText.setText("");
        dateTimeEditText1.setText("");
        amountEditText.setText("");
        descriptionEditText.setText("");
    }

    private void setDateTimePickerincome(EditText dateTimeEditText) {
        calendar1 = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar1.set(Calendar.YEAR, year);
            calendar1.set(Calendar.MONTH, month);
            calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateTimeEditText(dateTimeEditText);
        };

        dateTimeEditText.setOnClickListener(v -> new DatePickerDialog(
                requireContext(), dateSetListener,
                calendar1.get(Calendar.YEAR),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH)
        ).show());
    }

    private void updateDateTimeEditText(EditText dateTimeEditText) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateTimeEditText.setText(dateFormat.format(calendar1.getTime()));
    }
}
