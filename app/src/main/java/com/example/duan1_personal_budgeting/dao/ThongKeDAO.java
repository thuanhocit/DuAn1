package com.example.duan1_personal_budgeting.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_personal_budgeting.database.DBHelper;

public class ThongKeDAO {
    DBHelper dbHelper;
    SQLiteDatabase database;

    public ThongKeDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int thongKeThuNhapTheoKhoangThoiGianTN(String ngayBatDau, String ngayKetThuc) {
        int tongTien = 0;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "SELECT SUM(soTien) FROM GiaoDich WHERE loai = 'Thu nhập' AND ngayThang BETWEEN ? AND ?",
                new String[]{ngayBatDau, ngayKetThuc});
        if (cursor.moveToFirst()) {
            tongTien = cursor.getInt(0);
        }
        cursor.close();
        return tongTien;
    }

    public int thongKeChiTieuTheoKhoangThoiGian(String ngayBatDau, String ngayKetThuc) {
        int tongTien = 0;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT SUM(soTien) FROM GiaoDich WHERE loai = 'Chi tiêu' AND ngayThang BETWEEN ? AND ?", new String[]{ngayBatDau, ngayKetThuc});
        if (cursor.moveToFirst()) {
            tongTien = cursor.getInt(0);
        }
        cursor.close();
        return tongTien;
    }



}
