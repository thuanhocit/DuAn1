package com.example.duan1_personal_budgeting.model;

public class ChiTieu extends GiaoDich{
    public ChiTieu() {

    }

    public ChiTieu(int giaoDichID, int soTien, String ngayThang, String moTa, String loai, int danhMuc_id, int taiKhoan_id) {
        super(giaoDichID, soTien, ngayThang, moTa, loai, danhMuc_id, taiKhoan_id);
    }
}
