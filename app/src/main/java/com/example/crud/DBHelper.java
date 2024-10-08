package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDetails.db";
    private static final String TABLE_NAME = "UserDetails";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "FIRST_NAME";
    private static final String COL_3 = "LAST_NAME";
    private static final String COL_4 = "PHONE_NUMBER";
    private static final String COL_5 = "EMAIL";
    private static final String COL_6 = "ADDRESS";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, PHONE_NUMBER TEXT, EMAIL TEXT, ADDRESS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String firstName, String lastName, String phoneNumber, String email, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, lastName);
        contentValues.put(COL_4, phoneNumber);
        contentValues.put(COL_5, email);
        contentValues.put(COL_6, address);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // If result is -1, insertion failed
    }

    public Cursor retrieveData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateData(String id, String firstName, String lastName, String phoneNumber, String email, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, lastName);
        contentValues.put(COL_4, phoneNumber);
        contentValues.put(COL_5, email);
        contentValues.put(COL_6, address);

        int result = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return result > 0; // Returns true if at least one row was updated
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
