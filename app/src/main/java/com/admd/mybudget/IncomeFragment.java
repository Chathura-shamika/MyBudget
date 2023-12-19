package com.admd.mybudget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class IncomeFragment extends Fragment {

    private LinearLayout linearContainer;
    private ProgressBar progressBar;
    private TextView totalIncomeTextView;
    private FirebaseFirestore firestore;
    private CollectionReference incomeCollection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        linearContainer = view.findViewById(R.id.linearIncome);
        progressBar = view.findViewById(R.id.progressBar2);
        totalIncomeTextView = view.findViewById(R.id.dispayamountofincome);
        firestore = FirebaseFirestore.getInstance();
        incomeCollection = firestore.collection("income");

        fetchIncomeData();

        Button addNewIncomeButton = view.findViewById(R.id.addnewincomebutton);
        addNewIncomeButton.setOnClickListener(v -> openAddNewIncomeFragment());

        return view;
    }

    private void fetchIncomeData() {
        progressBar.setVisibility(View.VISIBLE);

        incomeCollection.get().addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);

            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                        String amount = documentSnapshot.getString("amount");
                        String dateTime = documentSnapshot.getString("date_time");
                        String description = documentSnapshot.getString("description");
                        String incomeType = documentSnapshot.getString("income_type");

                        String content = "Date: " + dateTime + "                    LKR: " + amount + "\n"
                                + "Income Type: " + incomeType + "\n"
                                + "Description: " + description + "\n\n";

                        CardView cardView = createCardView(content, documentSnapshot);
                        linearContainer.addView(cardView);
                    }

                    calculateTotalIncome(querySnapshot);
                }
            } else {
                // Handle errors
            }
        });
    }

    private CardView createCardView(String content, DocumentSnapshot documentSnapshot) {
        CardView cardView = new CardView(requireContext());
        cardView.setLayoutParams(new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        ));
        cardView.setRadius(5); // Set corner radius
        TextView incomeTextView = createIncomeTextView(content);
        cardView.addView(incomeTextView);

        cardView.setOnClickListener(v -> showDetailsPopup(documentSnapshot));

        return cardView;
    }

    private TextView createIncomeTextView(String incomeText) {
        TextView incomeTextView = new TextView(requireContext());
        incomeTextView.setText(incomeText);
        incomeTextView.setTextSize(15);
        incomeTextView.setTextColor(getResources().getColor(R.color.black));
        return incomeTextView;
    }

    private void calculateTotalIncome(QuerySnapshot snapshot) {
        double totalIncome = 0.00;

        for (DocumentSnapshot documentSnapshot : snapshot.getDocuments()) {
            if (documentSnapshot.contains("amount")) {
                String amountString = documentSnapshot.getString("amount");

                try {
                    double amount = Double.parseDouble(amountString);
                    totalIncome += amount;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        totalIncomeTextView.setText("LKR: " + String.format("%.2f", totalIncome));
    }

    private void showDetailsPopup(DocumentSnapshot documentSnapshot) {
        IncomeDetailsFragment detailsFragment = new IncomeDetailsFragment(documentSnapshot);
        detailsFragment.show(getChildFragmentManager(), "IncomeDetailsFragment");
    }

    private void openAddNewIncomeFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        addnewincomeFragment fragment = new addnewincomeFragment();

        fragmentTransaction.replace(R.id.framelayout01, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
