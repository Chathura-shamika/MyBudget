package com.admd.mybudget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private EditText editName;
    private EditText editMnumber;
    private EditText editEmail;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        editName = view.findViewById(R.id.editName);
        editMnumber = view.findViewById(R.id.editMnumber);
        editEmail = view.findViewById(R.id.editEmail);

        fetchData();

        return view;
    }

    private void fetchData() {
        if (mAuth.getCurrentUser() != null) {
            String userID = mAuth.getCurrentUser().getUid();

            firestore.collection("users").document(userID).get().addOnCompleteListener(profileTask -> {
                if (profileTask.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = profileTask.getResult();
                    if (documentSnapshot != null && documentSnapshot.exists()) {

                        String userName = documentSnapshot.getString("mName");
                        String mobileNumber = documentSnapshot.getString("mMobilenumber");
                        String email = documentSnapshot.getString("mEmail");

                        // Check for null values before setting to EditText
                        if (userName != null) {
                            editName.setText(userName);
                        }

                        if (mobileNumber != null) {
                            editMnumber.setText(mobileNumber);
                        }

                        if (email != null) {
                            editEmail.setText(email);
                        }
                    }
                }
            });
        }
    }
}
