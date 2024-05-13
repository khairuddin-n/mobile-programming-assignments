package com.example.listview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ContactsDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PHONE_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addContact(String name, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<String> getAllContacts() {
        ArrayList<String> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String phoneNumber = cursor.getString(2);
                contactList.add(name + " - " + phoneNumber);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contactList;
    }

    public ArrayList<String> searchContacts(String searchText) {
        ArrayList<String> searchResults = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_NAME + " LIKE '%" + searchText + "%'" +
                " OR " + COLUMN_PHONE_NUMBER + " LIKE '%" + searchText + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String phoneNumber = cursor.getString(2);
                searchResults.add(name + " - " + phoneNumber);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return searchResults;
    }

    public void updateContact(String oldName, String oldPhoneNumber, String newName, String newPhoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_PHONE_NUMBER, newPhoneNumber);
        db.update(TABLE_NAME, values, COLUMN_NAME + " = ? AND " + COLUMN_PHONE_NUMBER + " = ?", new String[]{oldName, oldPhoneNumber});
        db.close();
    }

    public void deleteContact(String name, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_NAME + " = ? AND " + COLUMN_PHONE_NUMBER + " = ?", new String[]{name, phoneNumber});
        db.close();
    }
}


