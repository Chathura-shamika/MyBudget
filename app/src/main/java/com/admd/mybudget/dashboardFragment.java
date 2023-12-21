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


        CardView utilityCard = view.findViewById(R.id.Utilitycard);
        utilityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUtilityCardClicked(v);

            }

        });
        CardView finance = view.findViewById(R.id.Finance);
        finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUtilityCardClicked1(v);

            }

        });
        CardView academic = view.findViewById(R.id.Academic);
        academic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUtilityCardClicked(v);

            }

        });
        CardView Mainincome = view.findViewById(R.id.Mainincome);
        Mainincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUtilityCardClicked(v);

            }

        });
        CardView Extraincomecard = view.findViewById(R.id.Extraincomecard);
        Extraincomecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUtilityCardClicked(v);

            }

        });
        CardView Profile = view.findViewById(R.id.Profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUtilityCardClicked(v);

            }

        });
        CardView Setting = view.findViewById(R.id.Setting);
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUtilityCardClicked(v);

            }

        });
        CardView Report = view.findViewById(R.id.Report);
        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUtilityCardClicked(v);

            }

        });


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
    //onUtilityCardClicked
    public void onUtilityCardClicked(View view) {
        showMessage("This Utility section has not been developed..");
    }
    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    //onUtilityCardClicked1
    public void onUtilityCardClicked1(View view) {
        showMessage1("This Finance section has not been developed..");
    }

    private void showMessage1(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    ////onUtilityCardClicked2
    public void onUtilityCardClicked2(View view) {
        showMessage2("This Academic section has not been developed..");
    }
    private void showMessage2(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    ////onUtilityCardClicked3
    public void onUtilityCardClicked3(View view) {
        showMessage3("This Main Income section has not been developed..");
    }
    private void showMessage3(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    ////onUtilityCardClicked4
    public void onUtilityCardClicked4(View view) {
        showMessage4("This Extra Income section has not been developed..");
    }
    private void showMessage4(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    ////onUtilityCardClicked5
    public void onUtilityCardClicked5(View view) {
        showMessage5("This Profile section has not been developed..");
    }
    private void showMessage5(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    ////onUtilityCardClicked6
    public void onUtilityCardClicked6(View view) {
        showMessage6("This Setting section has not been developed..");
    }
    private void showMessage6(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    ////onUtilityCardClicked7
    public void onUtilityCardClicked7(View view) {
        showMessage7("This Report section has not been developed..");
    }
    private void showMessage7(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }



}
