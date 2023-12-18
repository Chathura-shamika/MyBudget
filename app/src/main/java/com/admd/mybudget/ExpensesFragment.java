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

public class ExpensesFragment extends Fragment {

    private LinearLayout linearExpenses;
    private TextView totalexpensesTextView;
    private ProgressBar progressBar;
    private FirebaseFirestore firestore;
    private CollectionReference expensesCollection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        linearExpenses = view.findViewById(R.id.linearExpenses);
        progressBar = view.findViewById(R.id.progressBar1);
        totalexpensesTextView = view.findViewById(R.id.dispayamountofincome);
        firestore = FirebaseFirestore.getInstance();
        expensesCollection = firestore.collection("expenses");

        fetchExpensesData();

        Button addNewExpensesButton = view.findViewById(R.id.addnewexpensesButton);
        addNewExpensesButton.setOnClickListener(v -> openAddNewExpensesFragment());

        return view;
    }

    private void fetchExpensesData() {
        progressBar.setVisibility(View.VISIBLE);

        expensesCollection.get().addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);

            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                        String amount = documentSnapshot.getString("amount");
                        String dateTime = documentSnapshot.getString("date_time");
                        String description = documentSnapshot.getString("description");
                        String expenseType = documentSnapshot.getString("expense_type");

                        String expenseText = "Date: " + dateTime + "                              LKR: " + amount + "\n"
                                + "Expense Type: " + expenseType + "\n"
                                + "Description: " + description + "\n\n";

                        CardView cardView = createCardView(expenseText, documentSnapshot);
                        linearExpenses.addView(cardView);
                    }

                    calculateTotalExpense(querySnapshot);
                }
            } else {
                // Handle errors
            }
        });
    }

    private CardView createCardView(String expenseText, DocumentSnapshot documentSnapshot) {
        CardView cardView = new CardView(requireContext());
        cardView.setLayoutParams(new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        ));

        cardView.setCardElevation(5);

        TextView expenseTextView = createExpenseTextView(expenseText);

        cardView.setOnClickListener(v -> {
            // Display details popup when the card is clicked
            showDetailsPopup(documentSnapshot);
        });

        cardView.addView(expenseTextView);

        return cardView;
    }

    private TextView createExpenseTextView(String expenseText) {
        TextView expenseTextView = new TextView(requireContext());
        expenseTextView.setText(expenseText);
        expenseTextView.setTextSize(15);
        expenseTextView.setTextColor(getResources().getColor(R.color.black));
        return expenseTextView;
    }

    private void showDetailsPopup(DocumentSnapshot documentSnapshot) {
        ExpenseDetailsFragment detailsFragment = new ExpenseDetailsFragment(documentSnapshot);
        detailsFragment.show(getChildFragmentManager(), "ExpenseDetailsFragment");
    }

    private void calculateTotalExpense(QuerySnapshot snapshot) {
        double totalExpense = 0.00;

        for (DocumentSnapshot documentSnapshot : snapshot.getDocuments()) {
            if (documentSnapshot.contains("amount")) {
                String amountString = documentSnapshot.getString("amount");

                try {
                    double amount = Double.parseDouble(amountString);
                    totalExpense += amount;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        totalexpensesTextView.setText("LKR: " + totalExpense + "0");
    }

    private void openAddNewExpensesFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        addnewexpensesFragment fragment = new addnewexpensesFragment();

        fragmentTransaction.replace(R.id.framelayout01, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
