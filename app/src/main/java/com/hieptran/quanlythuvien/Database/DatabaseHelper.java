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

//    public static final String Tabble_DG_Name = "DocGia";
//    public static final String key_DG_MaDG = "madocgia";
//    public static final String key_DG_TenDangNhap = "tendangnhap";

    public static final String Table_login_Name = "Account";
    public static final String Key_login_Email = "email";
    public static final String Key_login_Password = "password";

    public DatabaseHelper(Context context) {
        super(context, Datbse_Name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE \"main\".\"Account\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
                " \"email\" TEXT NOT NULL , \"password\" TEXT NOT NULL )");

       // db.execSQL("PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"madocgia\" TEXT NOT NULL , \"tendangnhap\" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
