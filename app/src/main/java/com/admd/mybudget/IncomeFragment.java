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

        // Fetch data from Firestore
        fetchData();
        Button addNewIncomeButton = view.findViewById(R.id.addnewincomebutton);
        addNewIncomeButton.setOnClickListener(v -> openAddNewIncomeFragment());
        return view;
    }

    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);

        // Fetch income data
        incomeCollection.get().addOnCompleteListener(incomeTask -> {
            progressBar.setVisibility(View.GONE);

            if (incomeTask.isSuccessful()) {
                QuerySnapshot incomeSnapshot = incomeTask.getResult();
                if (incomeSnapshot != null) {
                    processSnapshot(incomeSnapshot);
                    calculateTotalIncome(incomeSnapshot);
                }
            } else {
                // Handle errors for income
            }
        });
    }

    private void processSnapshot(QuerySnapshot snapshot) {
        linearContainer.removeAllViews(); // Clear existing views
        for (DocumentSnapshot documentSnapshot : snapshot.getDocuments()) {
            // Get data from the document
            String amount = documentSnapshot.getString("amount");
            String dateTime = documentSnapshot.getString("date_time");
            String description = documentSnapshot.getString("description");
            String type = documentSnapshot.getString("income_type");

            // Create a string with improved UI design
            String itemText = "Date: " + dateTime + "                              LKR: " + amount + "\n"
                    + "Income Type: " + type + "\n"
                    + "Description: " + description + "\n\n";

            // Add the item to the LinearLayout with improved UI design
            CardView cardView = createCardView(createItemTextView(itemText), documentSnapshot);
            linearContainer.addView(cardView);
        }
    }

    private TextView createItemTextView(String itemText) {
        TextView itemTextView = new TextView(requireContext());
        itemTextView.setText(itemText);
        itemTextView.setTextSize(15); // Set text size
        itemTextView.setTextColor(getResources().getColor(R.color.black)); // Set text color
        // You can further customize the TextView properties here
        return itemTextView;
    }

    private CardView createCardView(View content, DocumentSnapshot documentSnapshot) {
        CardView cardView = new CardView(requireContext());
        cardView.setLayoutParams(new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        ));
        cardView.setRadius(5); // Set corner radius
        cardView.addView(content);

        cardView.setOnClickListener(v -> {
            // Display details popup when the card is clicked
            showDetailsPopup(documentSnapshot);
        });

        return cardView;
    }

    private void calculateTotalIncome(QuerySnapshot snapshot) {
        double totalIncome = 0.00;

        for (DocumentSnapshot documentSnapshot : snapshot.getDocuments()) {
            // Assuming 'amount' is the field in Firestore for income amount
            if (documentSnapshot.contains("amount")) {
                String amountString = documentSnapshot.getString("amount");

                // Convert the string amount to double
                try {
                    double amount = Double.parseDouble(amountString);
                    totalIncome += amount;
                } catch (NumberFormatException e) {
                    // Handle the case where the amount cannot be parsed
                    e.printStackTrace();
                }
            }
        }
        // Display the total income in the TextView
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
