package com.example.duan1_personal_budgeting.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DemoDB {
    SQLiteDatabase database;
    public DemoDB(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
}
