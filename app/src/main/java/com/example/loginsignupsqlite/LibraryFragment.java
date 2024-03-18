package com.example.loginsignupsqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
    ArrayList<Book> books;
    CustomAdapter customAdapter;

    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("LibraryFragment", "onCreateView: Starting to create view.");
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        recyclerView = view.findViewById(R.id.search_book_recycler_view);
        addButton = view.findViewById(R.id.addButton);

        // initialize DBHelper and ArrayLists
        DB = new DBHelper(getActivity());
        books = new ArrayList<>();

        storeDataInArrays();

        // setup RecyclerView with CustomAdapter
        customAdapter = new CustomAdapter(getActivity(), books);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LibraryFragment", "addButton: Clicked.");
                Intent intent = new Intent(getActivity(), AddItems.class);
                startActivity(intent);
            }
        });

        // return the view after all setup is complete
        Log.d("LibraryFragment", "onCreateView: View created.");
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume(){
        Log.d("LibraryFragment", "onResume: Fragment resumed.");
        super.onResume();
        // clear the current data
        books.clear();

        // re-fetch data
        storeDataInArrays();

        // notify adapter about data change
        customAdapter.notifyDataSetChanged();
        Log.d("LibraryFragment", "onResume: Data refreshed.");
    }


    // fetch data from db and store it in the books arraylist
    private void storeDataInArrays() {
        Cursor cursor = DB.readAllBooks();
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No data.", Toast.LENGTH_SHORT).show();
            Log.d("LibraryFragment", "storeDataInArrays: No data found.");
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_BOOK_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_BOOK_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_BOOK_AUTHOR));
                String isbn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_BOOK_ISBN));
                float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_BOOK_RATING));
                // create and add objects to the list
                books.add(new Book(id, title, author, isbn, rating));
            }
            Log.d("LibraryFragment", "storeDataInArrays: Data fetched successfully.");
            cursor.close();
        }
    }
}
