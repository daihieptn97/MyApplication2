package com.hieptran.quanlythuvien.QuanTriVien.NhapSach;

/**
 * Created by Hiep Tran on 4/23/2017.
 */

public class SachNhap {
    private String maSach;
    private String theLoai;
    private int soLuong;
    private String TenSach;

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        TenSach = tenSach;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoaiSach(String maLoaiSach) {
        this.theLoai = maLoaiSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }


    public SachNhap() {

    }
}
