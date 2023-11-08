package com.example.recycleviewdemo.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "PRM.db";
    public DatabaseHelper(@Nullable Context context) {
        super(context, "PRM.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table allUsers(id INTEGER primary key AUTOINCREMENT, username TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists allUsers");
    }

    public boolean insertData(String username, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDatabase.insert("allUsers", null, contentValues);
        return result != -1;
    }
    public boolean checkUsername(String username){
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = myDatabase.rawQuery("Select * from allUsers where username = ?", new String[]{username});
        if(cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = myDatabase.rawQuery("Select * from allUsers where username = ? and password = ?", new String[]{username, password});
        if(cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

}
