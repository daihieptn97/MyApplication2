package com.hieptran.quanlythuvien.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieptran.quanlythuvien.QuanTriVien.fragment.TaoDocGia.DocGia;

/**
 * Created by hieptran on 5/8/2017.
 */

public class Database_thongtindocgia {

    SQLiteDatabase database;
    DatabaseHelper databaseHelper;
    private static Database_thongtindocgia database_thongtindocgia;

    public Database_thongtindocgia(Context context) {
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public static Database_thongtindocgia getDatbase_account(Context context) {
        if (database_thongtindocgia == null) {
            database_thongtindocgia = new Database_thongtindocgia(context);
        }
        return database_thongtindocgia;
    }

    public void Open_Database_thongtin() {
        databaseHelper.getWritableDatabase();
    }

    public void Insert_Datbase_thongtin(DocGia account) {
        database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(databaseHelper.Key_ThongTinDocGia_diaChi, account.getDiaChi());
        contentValues.put(databaseHelper.Key_ThongTinDocGia_lop, account.getLop());
        contentValues.put(databaseHelper.Key_ThongTinDocGia_maDG, account.getMaDocGia());
        contentValues.put(databaseHelper.Key_ThongTinDocGia_ngaySinh, account.getNgaySinh());
        contentValues.put(databaseHelper.Key_ThongTinDocGia_sdt, account.getSdt());
        contentValues.put(databaseHelper.Key_ThongTinDocGia_avatar, account.getAvatar());
        contentValues.put(databaseHelper.Key_ThongTinDocGia_tenDG, account.getTenDG());
        database.insert(databaseHelper.Table_ThongTinDocGia_Name, null, contentValues);
    }

    public boolean isEmpty() {
        int n;
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("select * from " + databaseHelper.Table_ThongTinDocGia_Name + "", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return false;
        } else return true;
    }

    public boolean isExits(String temp) {
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("select email from " + databaseHelper.Table_ThongTinDocGia_Name + "where email =  '" + temp + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void Delete_TTDG() {
        Cursor cursor = databaseHelper.getWritableDatabase().rawQuery("delete from " + databaseHelper.Table_ThongTinDocGia_Name + " ", null);
        cursor.moveToFirst();
    }

    public DocGia loadThongTinDocGia() {
        DocGia account = new DocGia();
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("select * from " + databaseHelper.Table_ThongTinDocGia_Name + "", null);
        cursor.moveToFirst();
        account.setMaDocGia(cursor.getString(cursor.getColumnIndex(databaseHelper.Key_ThongTinDocGia_maDG)));
        account.setTenDG(cursor.getString(cursor.getColumnIndex(databaseHelper.Key_ThongTinDocGia_tenDG)));
        account.setAvatar(cursor.getString(cursor.getColumnIndex(databaseHelper.Key_ThongTinDocGia_avatar)));
        account.setDiaChi(cursor.getString(cursor.getColumnIndex(databaseHelper.Key_ThongTinDocGia_diaChi)));
        account.setLop(cursor.getString(cursor.getColumnIndex(databaseHelper.Key_ThongTinDocGia_lop)));
        account.setNgaySinh(cursor.getString(cursor.getColumnIndex(databaseHelper.Key_ThongTinDocGia_ngaySinh)));
        account.setSdt(cursor.getString(cursor.getColumnIndex(databaseHelper.Key_ThongTinDocGia_sdt)));
        return account;
    }
}
