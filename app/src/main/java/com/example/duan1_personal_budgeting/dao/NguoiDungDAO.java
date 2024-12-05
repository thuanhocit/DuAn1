package com.example.duan1_personal_budgeting.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_personal_budgeting.database.DBHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NguoiDungDAO {

    DBHelper dbHelper;
    SQLiteDatabase database;

    public NguoiDungDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public boolean addUser(String username, String email, String password) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenNguoiDung", username);
        contentValues.put("email", email);
        contentValues.put("matKhau", password);
        long result = database.insert("NguoiDung", null, contentValues);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        database = dbHelper.getReadableDatabase();
        String[] columns = {"nguoiDungID"};
        String selection = "tenNguoiDung = ? AND matKhau = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = database.query("NguoiDung", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean updatePassword(String username, String newPassword) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matKhau", newPassword);
        String selection = "tenNguoiDung = ?";
        String[] selectionArgs = {username};
        int result = database.update("NguoiDung", contentValues, selection, selectionArgs);
        return result > 0;
    }

    public boolean checkAccount(String username) {
        database = dbHelper.getReadableDatabase();
        String[] columns = {"nguoiDungID"};
        String selection = "tenNguoiDung = ?";
        String[] selectionArgs = {username};
        Cursor cursor = database.query("NguoiDung", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean regexEmail(String email){
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkEmailExists(String email) {
        database = dbHelper.getReadableDatabase();
        String[] columns = {"nguoiDungID"}; // Cột nào cũng được, chỉ cần xác nhận có dòng nào không
        String selection = "email = ?";
        String[] selectionArgs = {email};
        Cursor cursor = database.query("NguoiDung", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount(); // Lấy số lượng dòng trả về
        cursor.close();
        return count > 0; // Trả về true nếu email đã tồn tại
    }

}
