package com.admd.mybudget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        mAuth = FirebaseAuth.getInstance();
        incomeCollection = firestore.collection("income");
        expensesCollection = firestore.collection("expenses");

        fetchData();
        CardView openProfile = view.findViewById(R.id.Profile);
        openProfile.setOnClickListener(v ->  openProfileFragment());


        CardView utilityCard = view.findViewById(R.id.Utilitycard);
        utilityCard.setOnClickListener(v -> showMessage("This Utility section has not been developed.."));

        CardView financeCard = view.findViewById(R.id.Finance);
        financeCard.setOnClickListener(v -> showMessage("This Finance section has not been developed.."));

        CardView academicCard = view.findViewById(R.id.Academic);
        academicCard.setOnClickListener(v -> showMessage("This Academic section has not been developed.."));

        CardView mainIncomeCard = view.findViewById(R.id.Mainincome);
        mainIncomeCard.setOnClickListener(v -> showMessage("This Main Income section has not been developed.."));

        CardView extraIncomeCard = view.findViewById(R.id.Extraincomecard);
        extraIncomeCard.setOnClickListener(v -> showMessage("This Extra Income section has not been developed.."));

        CardView settingCard = view.findViewById(R.id.Setting);
        settingCard.setOnClickListener(v -> showMessage("This Setting section has not been developed.."));

        CardView reportCard = view.findViewById(R.id.Report);
        reportCard.setOnClickListener(v -> showMessage("This Report section has not been developed.."));



        return view;
    }

    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);


        incomeCollection.get().addOnCompleteListener(incomeTask -> {
            if (incomeTask.isSuccessful()) {
                QuerySnapshot incomeSnapshot = incomeTask.getResult();
                if (incomeSnapshot != null) {
                    double totalIncome = calculateTotalAmount(incomeSnapshot);
                    float totalIncomeFloat = (float) totalIncome;
                    incomeTotalDashboard.setText("LKR " + String.format("%.2f", totalIncomeFloat));

                    // Fetch expenses data
                    expensesCollection.get().addOnCompleteListener(expensesTask -> {
                        if (expensesTask.isSuccessful()) {
                            QuerySnapshot expensesSnapshot = expensesTask.getResult();
                            if (expensesSnapshot != null) {
                                double totalExpenses = calculateTotalAmount(expensesSnapshot);
                                float totalExpensesFloat = (float) totalExpenses;
                                totalExpensesTextView.setText("LKR " + String.format("%.2f", totalExpensesFloat));

                                // Calculate and display balance
                                float balance = totalIncomeFloat - totalExpensesFloat;
                                balanceTextView.setText("LKR " + String.format("%.2f", balance));

                                // Hide the progress bar when data fetching and calculations are complete
                                progressBar.setVisibility(View.GONE);
                            } else {
                                // Handle null expensesSnapshot
                            }
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
                String userIDD = documentSnapshot.getString("user_ID");

                if (userIDD != null && userIDD.equals(userID)) {
                    try {
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

    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void openProfileFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ProfileFragment fragment = new ProfileFragment();

        fragmentTransaction.replace(R.id.framelayout01, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
