package com.example.duan1_personal_budgeting.model;

public class DanhMuc {
    private int danhMucID;
    private String tenDanhMuc;
    private String loaiDanhMuc;

    private int nguoiDungID;

//    public DanhMuc(int danhMucID, String tenDanhMuc) {
//        this.danhMucID = danhMucID;
//        this.tenDanhMuc = tenDanhMuc;
//    }

    public DanhMuc(int danhMucID, String tenDanhMuc, String loaiDanhMuc) {
        this.danhMucID = danhMucID;
        this.tenDanhMuc = tenDanhMuc;
        this.loaiDanhMuc = loaiDanhMuc;
    }

    public int getDanhMucID() {
        return danhMucID;
    }

    public void setDanhMucID(int danhMucID) {
        this.danhMucID = danhMucID;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getLoaiDanhMuc() {
        return loaiDanhMuc;
    }

    public void setLoaiDanhMuc(String loaiDanhMuc) {
        this.loaiDanhMuc = loaiDanhMuc;
    }

    public int getNguoiDungID() {
        return nguoiDungID;
    }

    public void setNguoiDungID(int nguoiDungID) {
        this.nguoiDungID = nguoiDungID;
    }


}
