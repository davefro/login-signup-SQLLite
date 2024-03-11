package com.example.loginsignupsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper  extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ApplicationDB.db";
    private static final int DATABASE_VERSION = 1;

    // User Table
    private static final String TABLE_USER_DETAILS = "Userdetails";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    // Book Table
    private static final String TABLE_BOOK_LIBRARY = "my_library";
    private static final String COLUMN_BOOK_ID = "_id";
    private static final String COLUMN_BOOK_TITLE = "book_title";
    private static final String COLUMN_BOOK_AUTHOR = "book_author";
    private static final String COLUMN_BOOK_ISBN = "book_isbn";
    private static final String COLUMN_BOOK_RATING = "book_rating";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User Details Table
        String createTableUserDetails = "CREATE TABLE " + TABLE_USER_DETAILS +
                " (" + COLUMN_USER_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_USER_PASSWORD + " TEXT)";
        // Create Book Library Table
        String createTableBookLibrary = "CREATE TABLE " + TABLE_BOOK_LIBRARY +
                " (" + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOK_TITLE + " TEXT, " +
                COLUMN_BOOK_AUTHOR + " TEXT, " +
                COLUMN_BOOK_ISBN + " TEXT, " +
                COLUMN_BOOK_RATING + " REAL)";
        db.execSQL(createTableUserDetails);
        db.execSQL(createTableBookLibrary);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK_LIBRARY);
        onCreate(db);
    }

    // insert user data
    public Boolean insertUserData(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_EMAIL, email);
        contentValues.put(COLUMN_USER_PASSWORD, password);
        long result = db.insert(TABLE_USER_DETAILS, null, contentValues);
        return result != -1;
    }

    // check if user exist
    public Boolean checkUserEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_USER_DETAILS + " where email=?", new String[] {email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkUserPassword(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_USER_DETAILS + " where email=? and password=?", new String[] {email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Add books method
    public void addBook(String title, String author, String isbn, float rating){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BOOK_TITLE, title);
        cv.put(COLUMN_BOOK_AUTHOR, author);
        cv.put(COLUMN_BOOK_ISBN, isbn);
        cv.put(COLUMN_BOOK_RATING, rating);

        long result = db.insert(TABLE_BOOK_LIBRARY, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // read all books from DB
    public Cursor readAllBooks(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_BOOK_LIBRARY, null);
    }

    // update books data
    void updateData(String row_id, String title, String author, String isbn, float rating){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BOOK_TITLE, title);
        cv.put(COLUMN_BOOK_AUTHOR, author);
        cv.put(COLUMN_BOOK_ISBN, isbn);
        cv.put(COLUMN_BOOK_RATING, rating);

        long result = db.update(TABLE_BOOK_LIBRARY, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    // delete books
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_BOOK_LIBRARY, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    // reset password
    public boolean resetUserPassword(String email, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_PASSWORD, newPassword);

        // update password where email matches
        int result = db.update(TABLE_USER_DETAILS, contentValues, COLUMN_USER_EMAIL + "=?", new String[]{email});

        return result > 0;
    }

    // display user email
    public String getCurrentUserEmail() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT email FROM Userdetails";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            String email = cursor.getString(0);
            cursor.close();
            return email;
        } else {
            return "No Email Found";
        }
    }

    // update user details
    public boolean updateUserDetails(String oldEmail, String newEmail, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_EMAIL, newEmail);
        contentValues.put(COLUMN_USER_PASSWORD, newPassword);
        int result = db.update(TABLE_USER_DETAILS, contentValues, COLUMN_USER_EMAIL + "=?", new String[]{oldEmail});
        return result > 0;
    }

    // delete user account
    public boolean deleteUserByEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_USER_DETAILS, COLUMN_USER_EMAIL + "=?", new String[]{email});
        return result > 0;
    }

}
