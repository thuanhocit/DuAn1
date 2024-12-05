package com.example.duan1_personal_budgeting.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "QuanLyChiTieu", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng NguoiDung
        String tNguoiDung = "CREATE TABLE NguoiDung (" +
                "nguoiDungID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenNguoiDung TEXT NOT NULL UNIQUE," +
                "email TEXT NOT NULL," +
                "matKhau TEXT NOT NULL);";
        db.execSQL(tNguoiDung);

// Tạo bảng DanhMuc
        String tDanhMuc = "CREATE TABLE DanhMuc (" +
                "danhMucID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenDanhMuc TEXT NOT NULL," +
                "loaiDanhMuc INTEGER NOT NULL CHECK(loaiDanhMuc IN ('Chi tiêu', 'Thu nhập')));";
        db.execSQL(tDanhMuc);

// Tạo bảng TaiKhoan
        String tTaiKhoan = "CREATE TABLE TaiKhoan (" +
                "taiKhoanID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenTaiKhoan TEXT NOT NULL," +
                "soDu INTEGER NOT NULL);";
        db.execSQL(tTaiKhoan);

// Tạo bảng GiaoDich
        String tGiaoDich = "CREATE TABLE GiaoDich (" +
                "giaoDichID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "soTien INTEGER NOT NULL," +
                "ngayThang TEXT NOT NULL," +
                "moTa TEXT," +
                "danhMuc_id INTEGER NOT NULL," +
                "taiKhoan_id INTEGER NOT NULL," +
                "loai TEXT NOT NULL," +
                "FOREIGN KEY(danhMuc_id) REFERENCES DanhMuc(danhMucID)," +
                "FOREIGN KEY(taiKhoan_id) REFERENCES TaiKhoan(taiKhoanID));";
        db.execSQL(tGiaoDich);


        // Chèn dữ liệu vào bảng NguoiDung
        String iNguoiDung = "INSERT INTO NguoiDung (nguoiDungID, tenNguoiDung, email, matKhau) VALUES " +
                "(1, 'NguyenVanA', 'nguyenvana@example.com', '123456')," +
                "(2, 'TranThiB', 'tranthib@example.com', '123456')," +
                "(3, 'LeVanC', 'levanc@example.com', '123456');";
        db.execSQL(iNguoiDung);

        // Chèn dữ liệu vào bảng DanhMuc
        String iDanhMuc = "INSERT INTO DanhMuc (danhMucID, tenDanhMuc, loaiDanhMuc) VALUES " +
                "(1, 'Ăn uống', 'Chi tiêu')," +
                "(2, 'Giải trí', 'Chi tiêu')," +
                "(3, 'Tiết kiệm', 'Thu nhập')," +
                "(4, 'Học tập', 'Chi tiêu')," +
                "(5, 'Đầu tư', 'Thu nhập');";
        db.execSQL(iDanhMuc);

        // Chèn dữ liệu vào bảng TaiKhoan
        String iTaiKhoan = "INSERT INTO TaiKhoan (taiKhoanID, tenTaiKhoan, soDu) VALUES " +
                "(1, 'Tài khoản chính', 5000000)," +
                "(2, 'Tài khoản tiết kiệm', 3000000)," +
                "(3, 'Ví điện tử', 1000000);";
        db.execSQL(iTaiKhoan);

//        // Chèn dữ liệu vào bảng MucTieu
//        String iMucTieu = "INSERT INTO MucTieu (mucTieuID, tenMucTieu, soTienMucTieu, ngayKetThuc) VALUES " +
//                "(1, 'Mua xe máy', 30000000, '2024-12-31')," +
//                "(2, 'Đi du lịch', 10000000, '2024-06-30')," +
//                "(3, 'Mua laptop', 20000000, '2025-01-01');";
//        db.execSQL(iMucTieu);

        String iGiaoDich = "INSERT INTO GiaoDich (giaoDichID, soTien, ngayThang, moTa, danhMuc_id, taiKhoan_id, loai) VALUES " +
                "(1, 200000, '2024-01-01', 'Mua sách', 4, 2, 'Chi tiêu')," +
                "(2, 500000, '2024-02-15', 'Đi xem phim', 2, 2, 'Chi tiêu')," +
                "(3, 2000000, '2024-03-01', 'Chuyển vào tiết kiệm', 3, 1, 'Thu nhập')," +
                "(4, 3000000, '2024-04-10', 'Đóng học phí', 4, 3, 'Chi tiêu')," +
                "(5, 100000, '2024-05-05', 'Ăn tối ngoài', 1, 2, 'Chi tiêu')," +
                "(6, 5000000, '2024-06-01', 'Đầu tư cổ phiếu', 5, 3, 'Thu nhập');";
        db.execSQL(iGiaoDich);



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS NguoiDung");
            db.execSQL("DROP TABLE IF EXISTS DanhMuc");
            db.execSQL("DROP TABLE IF EXISTS TaiKhoan");
            db.execSQL("DROP TABLE IF EXISTS MucTieu");
            db.execSQL("DROP TABLE IF EXISTS GiaoDich");
            onCreate(db);
        }
    }

        // Thêm phương thức để lấy số dư tài khoản hiện có
        public int getAccountBalance(int accountId) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT soDu FROM TaiKhoan WHERE taiKhoanID = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(accountId)});
            int balance = 0;
            if (cursor.moveToFirst()) {
                balance = cursor.getInt(0);
            }
            cursor.close();
            return balance;
        }

        // Thêm phương thức để lấy tổng số dư
        public int getTotalBalance() {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT SUM(soDu) FROM TaiKhoan";
            Cursor cursor = db.rawQuery(query, null);
            int totalBalance = 0;
            if (cursor.moveToFirst()) {
                totalBalance = cursor.getInt(0);
            }
            cursor.close();
            return totalBalance;
        }

        // Thêm phương thức để lấy tổng thu nhập và chi tiêu
        public int getTotalIncome() {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT SUM(soTien) FROM GiaoDich WHERE loai = 'Thu nhập'";
            Cursor cursor = db.rawQuery(query, null);
            int totalIncome = 0;
            if (cursor.moveToFirst()) {
                totalIncome = cursor.getInt(0);
            }
            cursor.close();
            return totalIncome;
        }

        public int getTotalExpense() {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT SUM(soTien) FROM GiaoDich WHERE loai = 'Chi tiêu'";
            Cursor cursor = db.rawQuery(query, null);
            int totalExpense = 0;
            if (cursor.moveToFirst()) {
                totalExpense = cursor.getInt(0);
            }
            cursor.close();
            return totalExpense;
        }



//    public boolean addUser(String username, String email, String password) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("tenNguoiDung", username);
//        contentValues.put("email", email);
//        contentValues.put("matKhau", password);
//        long result = db.insert("NguoiDung", null, contentValues);
//        return result != -1;
//    }

//    public boolean checkUser(String username, String password) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {"nguoiDungID"};
//        String selection = "tenNguoiDung = ? AND matKhau = ?";
//        String[] selectionArgs = {username, password};
//        Cursor cursor = db.query("NguoiDung", columns, selection, selectionArgs, null, null, null);
//        int count = cursor.getCount();
//        cursor.close();
//        return count > 0;
//    }

//    public boolean updatePassword(String username, String newPassword) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("matKhau", newPassword);
//        String selection = "tenNguoiDung = ?";
//        String[] selectionArgs = {username};
//        int result = db.update("NguoiDung", contentValues, selection, selectionArgs);
//        return result > 0;
//    }

//    public boolean checkAccount(String username) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {"nguoiDungID"};
//        String selection = "tenNguoiDung = ?";
//        String[] selectionArgs = {username};
//        Cursor cursor = db.query("NguoiDung", columns, selection, selectionArgs, null, null, null);
//        int count = cursor.getCount();
//        cursor.close();
//        return count > 0;
//    }
}
