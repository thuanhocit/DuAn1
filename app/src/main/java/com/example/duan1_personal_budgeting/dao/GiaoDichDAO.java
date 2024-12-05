package com.example.duan1_personal_budgeting.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1_personal_budgeting.database.DBHelper;
import com.example.duan1_personal_budgeting.model.GiaoDich;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiaoDichDAO {

    DBHelper dbHelper;
    SQLiteDatabase database;



    public GiaoDichDAO(Context context) {
        dbHelper = new DBHelper(context);
    }


    public List<Map<String, String>> getAllGiaoDich() {
        List<Map<String, String>> danhSachGiaoDichGD = new ArrayList<>();
        database = dbHelper.getReadableDatabase();

        // Truy vấn lấy tất cả giao dịch
        String truyVan = "SELECT GiaoDich.giaoDichID, GiaoDich.ngayThang, GiaoDich.soTien, " +
                "GiaoDich.moTa, GiaoDich.loai, DanhMuc.tenDanhMuc, TaiKhoan.tenTaiKhoan " +
                "FROM GiaoDich " +
                "INNER JOIN DanhMuc ON GiaoDich.danhMuc_id = DanhMuc.danhMucID " +
                "INNER JOIN TaiKhoan ON GiaoDich.taiKhoan_id = TaiKhoan.taiKhoanID " +
                "ORDER BY GiaoDich.ngayThang DESC";

        Cursor cursor = database.rawQuery(truyVan, null);

        if (cursor.moveToFirst()) {
            do {
                Map<String, String> giaoDich = new HashMap<>();
                giaoDich.put("giaoDichID", String.valueOf(cursor.getInt(0)));
                giaoDich.put("ngayThang", cursor.getString(1));
                giaoDich.put("soTien", String.valueOf(cursor.getInt(2)));
                giaoDich.put("moTa", cursor.getString(3));
                giaoDich.put("loai", cursor.getString(4));
                giaoDich.put("tenDanhMuc", cursor.getString(5));
                giaoDich.put("tenTaiKhoan", cursor.getString(6));

                danhSachGiaoDichGD.add(giaoDich);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return danhSachGiaoDichGD;
    }





    public List<Map<String, String>> getChiTieu() {
        List<Map<String, String>> danhSachGiaoDich = new ArrayList<>();
        database = dbHelper.getReadableDatabase();

        // Cập nhật câu truy vấn SQL để lấy thêm taiKhoan_id và danhMuc_id
        String truyVan = "SELECT GiaoDich.giaoDichID, GiaoDich.loai, GiaoDich.ngayThang, " +
                "DanhMuc.tenDanhMuc, TaiKhoan.tenTaiKhoan, GiaoDich.soTien, GiaoDich.moTa, " +
                "GiaoDich.danhMuc_id, GiaoDich.taiKhoan_id " + // Lấy thêm danhMuc_id và taiKhoan_id
                "FROM GiaoDich " +
                "INNER JOIN DanhMuc ON GiaoDich.danhMuc_id = DanhMuc.danhMucID " +
                "INNER JOIN TaiKhoan ON GiaoDich.taiKhoan_id = TaiKhoan.taiKhoanID " +
                "WHERE GiaoDich.loai = 'Chi tiêu'";

        // Thực hiện truy vấn và lấy dữ liệu
        Cursor cursor = database.rawQuery(truyVan, null);

        // Duyệt qua kết quả truy vấn và lấy thông tin
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> giaoDich = new HashMap<>();
                giaoDich.put("giaoDichID", String.valueOf(cursor.getInt(0)));  // Cột giaoDichID
                giaoDich.put("loai", cursor.getString(1));  // Cột loai
                giaoDich.put("ngayThang", cursor.getString(2));  // Cột ngayThang
                giaoDich.put("tenDanhMuc", cursor.getString(3));  // Cột tenDanhMuc
                giaoDich.put("tenTaiKhoan", cursor.getString(4));  // Cột tenTaiKhoan
                giaoDich.put("soTien", String.valueOf(cursor.getInt(5)));  // Cột soTien
                giaoDich.put("moTa", cursor.getString(6));  // Cột moTa
                giaoDich.put("danhMuc_id", String.valueOf(cursor.getInt(7)));  // Cột danhMuc_id
                giaoDich.put("taiKhoan_id", String.valueOf(cursor.getInt(8)));  // Cột taiKhoan_id

                // Thêm vào danh sách
                danhSachGiaoDich.add(giaoDich);
            } while (cursor.moveToNext());
        }

        // Đóng cursor và database
        cursor.close();
        database.close();

        return danhSachGiaoDich;
    }


    public boolean updateGiaoDich(int giaoDich_id, int soTien, String ngayThang, String moTa, int danhMuc_id, int taiKhoan_id, String loai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soTien", soTien);
        values.put("ngayThang", ngayThang);
        values.put("moTa", moTa);
        values.put("danhMuc_id", danhMuc_id);
        values.put("taiKhoan_id", taiKhoan_id);

        int rows = db.update("GiaoDich", values, "giaoDichID = ?", new String[]{String.valueOf(giaoDich_id)});
        return rows > 0;
    }



    public boolean deleteGDCT(int giaoDichID, int danhMucID, int taiKhoanID) {
        database = dbHelper.getWritableDatabase();
        try {
//            // Kiểm tra xem danh mục còn tồn tại không
//            Cursor cursorDanhMuc = database.rawQuery("SELECT * FROM DanhMuc WHERE danhMucID = ?", new String[]{String.valueOf(danhMucID)});
//            if (cursorDanhMuc.getCount() > 0) {
//                cursorDanhMuc.close();
//                Log.e("ChiTieuDAO", "Danh mục vẫn tồn tại, không thể xóa!");
//                return false; // Không được phép xóa nếu danh mục còn tồn tại
//            }
//            cursorDanhMuc.close();
//
//            // Kiểm tra xem tài khoản còn tồn tại không
//            Cursor cursorTaiKhoan = database.rawQuery("SELECT * FROM TaiKhoan WHERE taiKhoanID = ?", new String[]{String.valueOf(taiKhoanID)});
//            if (cursorTaiKhoan.getCount() > 0) {
//                cursorTaiKhoan.close();
//                Log.e("ChiTieuDAO", "Tài khoản vẫn tồn tại, không thể xóa!");
//                return false; // Không được phép xóa nếu tài khoản còn tồn tại
//            }
//            cursorTaiKhoan.close();

            // Nếu không có ràng buộc, tiến hành xóa giao dịch
            int result = database.delete("GiaoDich", "giaoDichID = ?", new String[]{String.valueOf(giaoDichID)});
            return result > 0; // Trả về true nếu xóa thành công
        } catch (Exception e) {
            Log.e("ChiTieuDAO", "Lỗi khi xóa giao dịch có ràng buộc", e);
            return false; // Trả về false nếu có lỗi xảy ra
        } finally {
            database.close(); // Đóng kết nối cơ sở dữ liệu
        }
    }



    public List<Map<String, String>> getThuNhap() {
        List<Map<String, String>> danhSachGiaoDichTN = new ArrayList<>();
        database = dbHelper.getReadableDatabase();

        // Truy vấn dữ liệu với các cột mong muốn, thêm danhMuc_id và taiKhoan_id
        String truyVan = "SELECT GiaoDich.giaoDichID, GiaoDich.loai, GiaoDich.ngayThang, " +
                "DanhMuc.tenDanhMuc, TaiKhoan.tenTaiKhoan, GiaoDich.soTien, GiaoDich.moTa, " +
                "GiaoDich.danhMuc_id, GiaoDich.taiKhoan_id " + // Lấy thêm danhMuc_id và taiKhoan_id
                "FROM GiaoDich " +
                "INNER JOIN DanhMuc ON GiaoDich.danhMuc_id = DanhMuc.danhMucID " +
                "INNER JOIN TaiKhoan ON GiaoDich.taiKhoan_id = TaiKhoan.taiKhoanID " +
                "WHERE GiaoDich.loai = 'Thu nhập'";

        // Thực hiện truy vấn và lấy dữ liệu
        Cursor cursor = database.rawQuery(truyVan, null);

        // Duyệt qua kết quả truy vấn và lấy thông tin
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> giaoDich = new HashMap<>();
                giaoDich.put("giaoDichID", String.valueOf(cursor.getInt(0)));  // Cột giaoDichID
                giaoDich.put("loai", cursor.getString(1));  // Cột loai
                giaoDich.put("ngayThang", cursor.getString(2));  // Cột ngayThang
                giaoDich.put("tenDanhMuc", cursor.getString(3));  // Cột tenDanhMuc
                giaoDich.put("tenTaiKhoan", cursor.getString(4));  // Cột tenTaiKhoan
                giaoDich.put("soTien", String.valueOf(cursor.getInt(5)));  // Cột soTien
                giaoDich.put("moTa", cursor.getString(6));  // Cột moTa
                giaoDich.put("danhMuc_id", String.valueOf(cursor.getInt(7)));  // Cột danhMuc_id
                giaoDich.put("taiKhoan_id", String.valueOf(cursor.getInt(8)));  // Cột taiKhoan_id

                // Thêm vào danh sách
                danhSachGiaoDichTN.add(giaoDich);
            } while (cursor.moveToNext());
        }

        // Đóng cursor và database
        cursor.close();
        database.close();

        return danhSachGiaoDichTN;
    }


}
