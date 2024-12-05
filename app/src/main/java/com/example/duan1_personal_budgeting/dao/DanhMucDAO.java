package com.example.duan1_personal_budgeting.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.duan1_personal_budgeting.database.DBHelper;
import com.example.duan1_personal_budgeting.model.DanhMuc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DanhMucDAO {
    DBHelper dbHelper;
    SQLiteDatabase database;
    public DanhMucDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<DanhMuc> getDanhMuc(){
        ArrayList<DanhMuc> list = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM DanhMuc", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new DanhMuc(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    // Hàm lấy danh sách danh mục từ cơ sở dữ liệu
    public List<HashMap<String, Object>> getDanhMucList() {
        List<HashMap<String, Object>> danhMucList = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        // Truy vấn lấy danh mục
        String query = "SELECT * FROM DanhMuc WHERE loaiDanhMuc = 'Chi tiêu'";
        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()) {
            HashMap<String, Object> danhMuc = new HashMap<>();
            danhMuc.put("danhMucID", cursor.getInt(0)); // Lấy ID
            danhMuc.put("tenDanhMuc", cursor.getString(1)); // Lấy tên
            danhMuc.put("loaiDanhMuc",cursor.getString(2));
            danhMucList.add(danhMuc);
        }
        cursor.close();

        return danhMucList;
    }

    public List<HashMap<String, Object>> getDanhMucListTN() {
        List<HashMap<String, Object>> danhMucListTN = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        // Truy vấn lấy danh mục
        String query = "SELECT * FROM DanhMuc WHERE loaiDanhMuc = 'Thu nhập'";
        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()) {
            HashMap<String, Object> danhMuc = new HashMap<>();
            danhMuc.put("danhMucID", cursor.getInt(0)); // Lấy ID
            danhMuc.put("tenDanhMuc", cursor.getString(1)); // Lấy tên
            danhMuc.put("loaiDanhMuc",cursor.getString(2));
            danhMucListTN.add(danhMuc);
        }
        cursor.close();

        return danhMucListTN;
    }



    public boolean addDM(String tenDM, String loaiDM){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenDanhMuc", tenDM);
        values.put("loaiDanhMuc", loaiDM);

        long check = database.insert("DanhMuc", null, values);
        if (check == -1)
            return false;
        return true;
    }

    public boolean updateDM(int dmID, String tenDM, String loaiDM){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenDanhMuc", tenDM);
        values.put("loaiDanhMuc", loaiDM);

        long check = database.update("DanhMuc",values, "danhMucID = ?", new String[]{String.valueOf(dmID)} );
        if (check == -1)
            return false;
        return true;
    }

//    public  int deleteDM(int danhMucID){
//        Cursor cursor;
//        try {
//            // Mở kết nối cơ sở dữ liệu
//            database = dbHelper.getWritableDatabase();
//
//
//            // Kiểm tra ràng buộc với bảng GiaoDich
//            String checkQuery = "SELECT COUNT(*) FROM GiaoDich WHERE danhMuc_id = ?";
//            cursor = database.rawQuery(checkQuery, new String[]{String.valueOf(danhMucID)});
//            cursor.moveToFirst();
//            int giaoDichCount = cursor.getInt(0);
//
//            if (giaoDichCount > 0) {
//                // Nếu có giao dịch liên quan, không thể xóa danh mục
//                return false;
//            }
//
//            // Xóa danh mục
//            int result = db.delete("DanhMuc", "danhMucID = ?", new String[]{String.valueOf(danhMucId)});
//            return result > 0; // Trả về true nếu xóa thành công
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public int deleteDM(int danhMucID) {
        database = dbHelper.getWritableDatabase();

        // Kiểm tra ràng buộc với bảng GiaoDich
        String checkQuery = "SELECT COUNT(*) FROM GiaoDich WHERE danhMuc_id = ?";
        Cursor cursor = database.rawQuery(checkQuery, new String[]{String.valueOf(danhMucID)});
        cursor.moveToFirst();
        int giaoDichCount = cursor.getInt(0);
        cursor.close();

        if (giaoDichCount > 0) {
            // Có giao dịch liên quan, không thể xóa
            database.close();
            return 0;
        }

        // Thực hiện xóa danh mục
        int result = database.delete("DanhMuc", "danhMucID = ?", new String[]{String.valueOf(danhMucID)});
        database.close();

        // Trả về 1 nếu xóa thành công, -1 nếu thất bại
        return result > 0 ? 1 : -1;
    }



}
