package com.hieptran.quanlythuvien.DocGia;

import java.util.Date;

/**
 * Created by Hiep Tran on 4/22/2017.
 */

public class DocGia {
    private String hoTen;
    private Date NgaySinh;
    private String lop;
    private String diachi;
    private int anhDaiDien;

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(int anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public DocGia(String hoTen, Date ngaySinh, String lop, String diachi, int anhDaiDien) {
        this.hoTen = hoTen;
        NgaySinh = ngaySinh;
        this.lop = lop;
        this.diachi = diachi;
        this.anhDaiDien = anhDaiDien;
    }

    public DocGia() {

    }
}
