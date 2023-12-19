package com.admd.mybudget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class dashboardFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView incomeTotalDashboard;
    private TextView totalExpensesTextView;
    private TextView balanceTextView;
    private FirebaseFirestore firestore;
    private CollectionReference incomeCollection;
    private CollectionReference expensesCollection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        progressBar = view.findViewById(R.id.progressBar3);
        incomeTotalDashboard = view.findViewById(R.id.incometoataldashboard);
        totalExpensesTextView = view.findViewById(R.id.totalExpensesTextView1);
        balanceTextView = view.findViewById(R.id.totalBalance);

        firestore = FirebaseFirestore.getInstance();
        incomeCollection = firestore.collection("income");
        expensesCollection = firestore.collection("expenses");

        fetchData();

        return view;
    }

    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);

        // Fetch income data
        incomeCollection.get().addOnCompleteListener(incomeTask -> {


            if (incomeTask.isSuccessful()) {
                QuerySnapshot incomeSnapshot = incomeTask.getResult();
                if (incomeSnapshot != null) {
                    double totalIncome = calculateTotalAmount(incomeSnapshot);
                    incomeTotalDashboard.setText("LKR " + totalIncome + "0");

                    // Fetch expenses data
                    expensesCollection.get().addOnCompleteListener(expensesTask -> {
                        if (expensesTask.isSuccessful()) {
                            QuerySnapshot expensesSnapshot = expensesTask.getResult();
                            if (expensesSnapshot != null) {
                                double totalExpenses = calculateTotalAmount(expensesSnapshot);
                                totalExpensesTextView.setText("LKR " + totalExpenses + "0");

                                // Calculate and display balance
                                double balance = totalIncome - totalExpenses;
                                balanceTextView.setText("LKR " + balance + "0");
                            }
                            progressBar.setVisibility(View.GONE);
                        } else {
                            // Handle errors for expenses
                        }
                    });
                }
            } else {
                // Handle errors for income
            }
        });
    }

    private double calculateTotalAmount(QuerySnapshot snapshot) {
        double totalAmount = 0.00;

        for (DocumentSnapshot documentSnapshot : snapshot.getDocuments()) {
            // Assuming 'amount' is the field in Firestore for income/expense amount
            if (documentSnapshot.contains("amount")) {
                String amountString = documentSnapshot.getString("amount");

                // Convert the string amount to double
                try {
                    double amount = Double.parseDouble(amountString);
                    totalAmount += amount;
                } catch (NumberFormatException e) {
                    // Handle the case where the amount cannot be parsed
                    e.printStackTrace();
                }
            }
        }
        return totalAmount;
    }
}
