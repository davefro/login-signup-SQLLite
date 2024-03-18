package com.example.loginsignupsqlite;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class MessageFragment extends Fragment {

    EditText searchInput;
    ImageButton searchButton;
    TextInputEditText message;
    RadioButton agreePrivacyPolicy;
    SwitchCompat cookieSwitch;
    Button submit;
    RecyclerView recyclerView;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("MessageFragment", "onCreateView: Starting to create view.");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        searchInput = view.findViewById(R.id.search_bookName_input);
        searchButton = view.findViewById(R.id.search_book_btn);
        message = view.findViewById(R.id.message);
        agreePrivacyPolicy = view.findViewById(R.id.agreePrivacyPolicy);
        cookieSwitch = view.findViewById(R.id.cookieSwitch);
        submit = view.findViewById(R.id.submit);
        recyclerView = view.findViewById(R.id.search_book_recycler_view);

        Log.d("MessageFragment", "onCreateView: Views initialized.");
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchInput.getText().toString().trim();
                Log.d("MessageFragment", "onClick: Search initiated with term - " + searchTerm);
                if (!searchTerm.isEmpty() && searchTerm.length() >= 3) {
                    setupSearchRecyclerView(searchTerm);
                } else {
                    searchInput.setError("Please enter at least 3 characters to search");
                    clearSearchResults();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = message.getText().toString().trim();
                Log.d("MessageFragment", "onClick: Attempting to submit message.");
                // set error
                if (messageText.isEmpty()){
                    message.setError("Message cannot be empty");
                    return;
                } else if (messageText.length() < 10) {
                    message.setError("Message must be at least 10 character");
                    return;
                }

                // check if the user agreed to the privacy policy
                if (!agreePrivacyPolicy.isChecked()) {
                    Toast.makeText(getContext(), "You must agree to the Privacy and Policy to proceed", Toast.LENGTH_LONG).show();
                    return;
                }

                // if all validations pass, proceed with form submission
                Toast.makeText(getContext(), "Message has been sent", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MessageConfirmation.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void setupSearchRecyclerView(String searchTerm) {
        DBHelper dbHelper = new DBHelper(getActivity());
        ArrayList<Book> books = dbHelper.searchBooks(searchTerm);
        Log.d("MessageFragment", "setupSearchRecyclerView: Searching for books with term - " + searchTerm);

        // if no books found, show a message and clear previous results
        if (books.isEmpty()) {
            Toast.makeText(getContext(), "No books found", Toast.LENGTH_SHORT).show();
            clearSearchResults();
        } else {
            // display book in recyclerview
            CustomAdapter customAdapter = new CustomAdapter(getActivity(), books);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(customAdapter);
        }
    }

    private void clearSearchResults(){
        Log.d("MessageFragment", "clearSearchResults: Clearing search results.");
        CustomAdapter customAdapter = new CustomAdapter(getActivity(), new ArrayList<>());
        recyclerView.setAdapter(customAdapter);
    }
}