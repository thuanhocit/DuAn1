package com.example.duan1_personal_budgeting.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_personal_budgeting.database.DBHelper;
import com.example.duan1_personal_budgeting.model.TaiKhoan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaiKhoanDAO {
    DBHelper dbHelper;
    SQLiteDatabase database;
    public TaiKhoanDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<TaiKhoan> getTaiKhoan(){
        ArrayList<TaiKhoan> list = new ArrayList<>();
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM TaiKhoan", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new TaiKhoan(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    // Hàm lấy danh sách tài khoản từ cơ sở dữ liệu
    public List<HashMap<String, Object>> getTaiKhoanList() {
        List<HashMap<String, Object>> taiKhoanList = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        // Truy vấn lấy ID, tên tài khoản và số dư
        String query = "SELECT taiKhoanID, tenTaiKhoan, soDu FROM TaiKhoan";
        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()) {
            HashMap<String, Object> taiKhoan = new HashMap<>();
            taiKhoan.put("taiKhoanID", cursor.getInt(0));       // Lấy ID tài khoản
            taiKhoan.put("tenTaiKhoan", cursor.getString(1));   // Lấy tên tài khoản
            taiKhoan.put("soDu", cursor.getInt(2));            // Lấy số dư tài khoản
            taiKhoanList.add(taiKhoan);
        }
        cursor.close();

        return taiKhoanList;
    }


    public boolean addTK(String tenTK, int soDuTK){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenTaiKhoan", tenTK);
        values.put("soDu", soDuTK);

        long check = database.insert("TaiKhoan", null, values);
        if (check == -1)
            return false;
        return true;
    }

    public boolean updateTK(int tkID, String tenTK, int soDuTK){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenTaiKhoan", tenTK);
        values.put("soDu", soDuTK);

        long check = database.update("TaiKhoan",values, "taiKhoanID = ?", new String[]{String.valueOf(tkID)});
        if (check == -1)
            return false;
        return true;
    }

    public int xoaTaiKhoan(int taiKhoanID) {
        database = dbHelper.getWritableDatabase();

        // Kiểm tra giao dịch liên quan
        String checkQuery = "SELECT COUNT(*) FROM GiaoDich WHERE taiKhoan_id = ?";
        Cursor cursor = database.rawQuery(checkQuery, new String[]{String.valueOf(taiKhoanID)});
        cursor.moveToFirst();
        int giaoDichCount = cursor.getInt(0);
        cursor.close();

        if (giaoDichCount > 0) {
            // Có giao dịch liên quan, không thể xóa
            database.close();
            return 0;
        }

        // Xóa tài khoản
        int result = database.delete("TaiKhoan", "taiKhoanID = ?", new String[]{String.valueOf(taiKhoanID)});
        database.close();

        return result > 0 ? 1 : -1; // 1: Thành công, -1: Thất bại
    }

}
