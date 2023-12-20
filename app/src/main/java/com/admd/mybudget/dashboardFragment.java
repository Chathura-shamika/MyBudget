package com.admd.mybudget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
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
    private String userID;
    private FirebaseAuth mAuth;
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
        mAuth= FirebaseAuth.getInstance();
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
                    float totalIncomeFloat = (float) totalIncome;
                    incomeTotalDashboard.setText("LKR " +  String.format("%.2f", totalIncomeFloat));


        expensesCollection.get().addOnCompleteListener(expensesTask -> {
            if (expensesTask.isSuccessful()) {
                 QuerySnapshot expensesSnapshot = expensesTask.getResult();
                   if (expensesSnapshot != null) {
                       double totalExpenses = calculateTotalAmount(expensesSnapshot);
                       float totalexpensesFloat = (float) totalExpenses;
                       totalExpensesTextView.setText("LKR " + String.format("%.2f", totalexpensesFloat));


                       // Calculate and display balance
                         float balance = (float) (totalIncome - totalExpenses);
                       balanceTextView.setText("LKR " + String.format("%.2f", balance));

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
        float totalAmount = 0;
        userID = mAuth.getCurrentUser().getUid();
        for (DocumentSnapshot documentSnapshot : snapshot.getDocuments()) {

            if (documentSnapshot.contains("amount")) {
                String amountString = documentSnapshot.getString("amount");
                String userIDD =  documentSnapshot.getString("user_ID");

                if (userIDD != null && userIDD.equals(userID)) {
                    try {
                       // double amount = Double.parseDouble(amountString);
                        float amount = Float.parseFloat(amountString);
                         totalAmount += amount;
                    } catch (NumberFormatException e) {

                        e.printStackTrace();
                    }
                }
            }
        }
        return totalAmount;
    }

}
