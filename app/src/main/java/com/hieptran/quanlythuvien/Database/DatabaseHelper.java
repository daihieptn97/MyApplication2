package com.hieptran.quanlythuvien.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hiep Tran on 4/22/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper databaseHelper;

    public static synchronized DatabaseHelper getDatabaseHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    public static final String Datbse_Name = "QuanLyThuVien";
    public static final int version = 1;

    public static final String Table_login_Name = "Account";
    public static final String Key_login_Email = "email";
    public static final String Key_login_Password = "password";


    public static final String Table_ThongTinDocGia_Name = "ThongTinDocGia";
    public static final String Key_ThongTinDocGia_tenDG = "tenDG";
    public static final String Key_ThongTinDocGia_maDG = "maDocGia";
    public static final String Key_ThongTinDocGia_diaChi = "diaChi";
    public static final String Key_ThongTinDocGia_lop = "lop";
    public static final String Key_ThongTinDocGia_sdt = "sdt";
    public static final String Key_ThongTinDocGia_ngaySinh = "ngaySinh";
    public static final String Key_ThongTinDocGia_avatar = "avatar";

    public static final String TableName_KiemTra = "KiemTra";
    public static final String Key_kiemTra_DocGia = "docGia";
    public static final String Key_kiemTra_NhoMatKhau = "nhoMatKhau";


    public DatabaseHelper(Context context) {
        super(context, Datbse_Name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE  TABLE \"main\".\"KiemTra\" (\"nhoMatKhau\" INTEGER PRIMARY KEY  NOT NULL , \"docGia\" INTEGER)");

        db.execSQL("CREATE  TABLE \"main\".\"Account\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
                " \"email\" TEXT NOT NULL , \"password\" TEXT NOT NULL )");

        db.execSQL("CREATE  TABLE \"main\".\"ThongTinDocGia\" (\"maDocGia\" TEXT PRIMARY KEY  NOT NULL , " +
                "\"tenDG\" TEXT, \"diaChi\" TEXT, \"lop\" TEXT, \"sdt\" TEXT, \"ngaySinh\" TEXT, \"avatar\" TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
