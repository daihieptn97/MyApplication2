package com.hieptran.quanlythuvien.fragment.NhapSach;

/**
 * Created by Hiep Tran on 4/23/2017.
 */

public class SachNhap {
    private String maSach;
    private String maLoaiSach;
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

    public String getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setTheLoaiSach(String maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
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
