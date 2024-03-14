package com.example.loginsignupsqlite;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;


public class MessageFragment extends Fragment {

    TextInputEditText name, message, phone, email;
    RadioButton agreePrivacyPolicy;
    SwitchCompat cookieSwitch;
    Button submit;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        name = view.findViewById(R.id.book_name);
        message = view.findViewById(R.id.message);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        agreePrivacyPolicy = view.findViewById(R.id.agreePrivacyPolicy);
        cookieSwitch = view.findViewById(R.id.cookieSwitch);
        submit = view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString().trim();
                String messageText = message.getText().toString().trim();
                String phoneText = phone.getText().toString().trim();
                String emailText = email.getText().toString().trim();

                // check if any field is empty
                if (nameText.isEmpty() || messageText.isEmpty() || phoneText.isEmpty() || emailText.isEmpty()) {
                    Toast.makeText(getContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }

                // check if the phone field contains only digits
                if (!phoneText.matches("[0-9]+")) {
                    Toast.makeText(getContext(), "Phone must contain only numbers.", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }

                // check if the email address contains "@"
                if (!emailText.contains("@")) {
                    Toast.makeText(getContext(), "Email address must include '@'.", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }

                // check if the user agreed to the privacy policy
                if (!agreePrivacyPolicy.isChecked()) {
                    Toast.makeText(getContext(), "You must agree to the Privacy and Policy to proceed", Toast.LENGTH_LONG).show();
                    return; // Stop further execution
                }

                // if all validations pass, proceed with form submission
                Toast.makeText(getContext(), "Message has been sent", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}