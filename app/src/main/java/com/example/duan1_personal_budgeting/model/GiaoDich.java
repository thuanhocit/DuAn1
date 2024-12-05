package com.example.duan1_personal_budgeting.model;

public class GiaoDich {
    private int giaoDichID;
    private int soTien;
    private String ngayThang;
    private String moTa;

    private String loai;
    private int danhMuc_id;
    private int taiKhoan_id;

    public GiaoDich() {
    }

    public GiaoDich(int giaoDichID, int soTien, String ngayThang, String moTa, String loai, int danhMuc_id, int taiKhoan_id) {
        this.giaoDichID = giaoDichID;
        this.soTien = soTien;
        this.ngayThang = ngayThang;
        this.moTa = moTa;
        this.loai = loai;
        this.danhMuc_id = danhMuc_id;
        this.taiKhoan_id = taiKhoan_id;
    }


    public int getGiaoDichID() {
        return giaoDichID;
    }

    public void setGiaoDichID(int giaoDichID) {
        this.giaoDichID = giaoDichID;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getDanhMuc_id() {
        return danhMuc_id;
    }

    public void setDanhMuc_id(int danhMuc_id) {
        this.danhMuc_id = danhMuc_id;
    }

    public int getTaiKhoan_id() {
        return taiKhoan_id;
    }

    public void setTaiKhoan_id(int taiKhoan_id) {
        this.taiKhoan_id = taiKhoan_id;
    }

    public void setTenDanhMuc(String string) {
    }

    public void setTenTaiKhoan(String string) {
    }
}
