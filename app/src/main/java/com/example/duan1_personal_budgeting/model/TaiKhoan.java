package com.example.duan1_personal_budgeting.model;

public class TaiKhoan {

    private int taiKhoanID;
    private String tenTaiKhoan;

    private int soDu;

    public TaiKhoan(int taiKhoanID, String tenTaiKhoan, int soDu) {
        this.taiKhoanID = taiKhoanID;
        this.tenTaiKhoan = tenTaiKhoan;
        this.soDu = soDu;
    }

    public int getTaiKhoanID() {
        return taiKhoanID;
    }

    public void setTaiKhoanID(int taiKhoanID) {
        this.taiKhoanID = taiKhoanID;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public int getSoDu() {
        return soDu;
    }

    public void setSoDu(int soDu) {
        this.soDu = soDu;
    }
}
