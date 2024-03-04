//package com.example.loginsignupsqlite;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DBHelper  extends SQLiteOpenHelper {
//
//    public static final String databaseName = "Userdata.db";
//    public DBHelper(Context context) {
//        super(context, "Userdata.db", null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase DB) {
//        DB.execSQL("create table Userdetails(email TEXT primary key, password TEXT)");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
//        DB.execSQL("drop table if exists Userdetails");
//    }
//
//    public Boolean insertData(String email, String password){
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("email", email);
//        contentValues.put("password", password);
//        long result = DB.insert("Userdetails", null, contentValues);
//        if (result == -1){
//            return false;
//        } else {
//            return true;
//        }
//
//
//    }
//
//    public Boolean checkUserEmail(String email){
//        SQLiteDatabase DB = this.getWritableDatabase();
//        Cursor cursor = DB.rawQuery("Select * from Userdetails where email=?", new String[] {email});
//        if (cursor.getCount() > 0) {
//            return true;
//        } else{
//            return false;
//        }
//    }
//
//    public Boolean checkUserPassword(String email, String password){
//        SQLiteDatabase DB = this.getWritableDatabase();
//        Cursor cursor = DB.rawQuery("Select * from Userdetails where email=? and password=?", new String[] {email, password});
//        if (cursor.getCount() > 0){
//            return true;
//        } else {
//            return false;
//        }
//    }
//}

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
    private static final int DATABASE_VERSION = 2;

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
                COLUMN_BOOK_ISBN + " TEXT)";
        db.execSQL(createTableUserDetails);
        db.execSQL(createTableBookLibrary);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK_LIBRARY);
        onCreate(db);
    }

    // User-related operations
    public Boolean insertUserData(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_EMAIL, email);
        contentValues.put(COLUMN_USER_PASSWORD, password);
        long result = db.insert(TABLE_USER_DETAILS, null, contentValues);
        return result != -1;
    }

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

    // Book-related operations
    public void addBook(String title, String author, String isbn){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BOOK_TITLE, title);
        cv.put(COLUMN_BOOK_AUTHOR, author);
        cv.put(COLUMN_BOOK_ISBN, isbn);
        long result = db.insert(TABLE_BOOK_LIBRARY, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllBooks(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_BOOK_LIBRARY, null);
    }

    void updateData(String row_id, String title, String author, String isbn){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BOOK_TITLE, title);
        cv.put(COLUMN_BOOK_AUTHOR, author);
        cv.put(COLUMN_BOOK_ISBN, isbn);

        long result = db.update(TABLE_BOOK_LIBRARY, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_BOOK_LIBRARY, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_BOOK_LIBRARY);
    }
}
