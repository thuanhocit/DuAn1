package com.example.duan1_personal_budgeting.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1_personal_budgeting.database.DBHelper;

public class ChiTieuDAO {
    DBHelper dbHelper;
    SQLiteDatabase database;

    public ChiTieuDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public boolean addGDCT(int soTien, String ngayThang, String moTa, int danhMuc_id, int taiKhoan_id, String loai){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soTien", soTien);
        values.put("ngayThang", ngayThang);
        values.put("moTa", moTa);
        values.put("danhMuc_id", danhMuc_id);
        values.put("taiKhoan_id", taiKhoan_id);
        values.put("loai", loai);

        long check = database.insert("GiaoDich", null, values);
        if (check == -1)
            return false;
        return true;
    }


    public int getSoDuTaiKhoan(int taiKhoanId) {
        database = dbHelper.getReadableDatabase();
        int soDu = 0;
        Cursor cursor = database.rawQuery("SELECT soDu FROM TaiKhoan WHERE taiKhoanID = ?", new String[]{String.valueOf(taiKhoanId)});
        if (cursor.moveToFirst()){
            soDu = cursor.getInt(0);
        }
        cursor.close();
        return soDu;
    }

    public boolean updateSoDu(int taiKhoanId, int soMoi){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soDu", soMoi);
        long check = database.update("TaiKhoan", values, "taiKhoanID = ?", new String[]{String.valueOf(taiKhoanId)});
        return check > 0;
    }


}
