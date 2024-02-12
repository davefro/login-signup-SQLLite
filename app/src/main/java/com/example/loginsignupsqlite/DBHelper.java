package com.example.loginsignupsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {

    public static final String databaseName = "Userdata.db";
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table Userdetails(email TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop table if exists Userdetails");
    }

    public Boolean insertData(String email, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = DB.insert("Userdetails", null, contentValues);
        if (result == -1){
            return false;
        } else {
            return true;
        }


    }

    public Boolean checkUserEmail(String email){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where email=?", new String[] {email});
        if (cursor.getCount() > 0) {
            return true;
        } else{
            return false;
        }
    }

    public Boolean checkUserPassword(String email, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where email=? and password=?", new String[] {email, password});
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }
}
