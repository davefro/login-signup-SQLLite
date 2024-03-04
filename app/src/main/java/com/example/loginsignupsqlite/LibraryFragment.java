package com.example.loginsignupsqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class LibraryFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton addButton;
    DBHelper DB;
    ArrayList<String> book_id, book_author, book_title, book_isbn;
    CustomAdapter customAdapter;

    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        addButton = view.findViewById(R.id.addButton);

        // Initialize DBHelper and ArrayLists
        DB = new DBHelper(getActivity()); // Use getActivity() for the context
        book_id = new ArrayList<>();
        book_author = new ArrayList<>();
        book_title = new ArrayList<>();
        book_isbn = new ArrayList<>();

        storeDataInArrays();

        // Setup RecyclerView with CustomAdapter
        customAdapter = new CustomAdapter(getActivity(), book_id, book_title, book_author, book_isbn);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddItems.class);
                startActivity(intent);
            }
        });

        // Return the view after all setup is complete
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume(){
        super.onResume();
        // Clear the current data
        book_id.clear();
        book_title.clear();
        book_author.clear();
        book_isbn.clear();

        // Re-fetch data
        storeDataInArrays();

        // Notify adapter about data change
        customAdapter.notifyDataSetChanged();
    }

    private void storeDataInArrays() {
        // Implement this method to fetch data from the database and fill the ArrayLists
        Cursor cursor = DB.readAllBooks();
        if(cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_isbn.add(cursor.getString(3));
            }
        }
    }
}
