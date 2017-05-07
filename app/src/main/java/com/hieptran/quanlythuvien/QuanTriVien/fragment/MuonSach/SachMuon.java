package com.hieptran.quanlythuvien.QuanTriVien.fragment.MuonSach;

/**
 * Created by daihieptn97 on 26/04/2017.
 */

public class SachMuon {
    private String maSach;
    private String maDocGia;
    private String ngayMuon;
    private String ngayTra;



    private boolean hienTrangMuon;

    public boolean isHienTrangMuon() {
        return hienTrangMuon;
    }

    public void setHienTrangMuon(boolean hienTrangMuon) {
        this.hienTrangMuon = hienTrangMuon;
    }

    public String getMaMuonSach() {
        return maMuonSach;
    }

    public void setMaMuonSach(String maMuonSach) {
        this.maMuonSach = maMuonSach;
    }

    private String maMuonSach;

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaDocGia() {
        return maDocGia;
    }

    public void setMaDocGia(String maDocGia) {
        this.maDocGia = maDocGia;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public SachMuon() {

    }
}
