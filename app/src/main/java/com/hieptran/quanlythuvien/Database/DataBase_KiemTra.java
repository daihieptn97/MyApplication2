package com.hieptran.quanlythuvien.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by hieptran on 5/8/2017.
 */

public class DataBase_KiemTra {
    SQLiteDatabase database;
    DatabaseHelper databaseHelper;
    private static DataBase_KiemTra dataBase_kiemTra;

    public DataBase_KiemTra(Context context) {
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public static DataBase_KiemTra getDataBase_kiemTra(Context context) {
        if (dataBase_kiemTra == null) {
            dataBase_kiemTra = new DataBase_KiemTra(context);
        }
        return dataBase_kiemTra;
    }

    public void OpenDatabaseKiemTra() {
        databaseHelper.getWritableDatabase();
    }

    public void InsertDatbaseKiemTra(KiemTra kiemTra) {

        database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(databaseHelper.Key_kiemTra_DocGia, kiemTra.getDocGia());
        contentValues.put(databaseHelper.Key_kiemTra_NhoMatKhau, kiemTra.getNhoMatKhau());

        database.insert(databaseHelper.TableName_KiemTra, null, contentValues);
    }

    public boolean isEmpty() {
        OpenDatabaseKiemTra();
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("select * from " + databaseHelper.TableName_KiemTra + " ", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return false;
        } else return true;
    }

    public void Delete_KiemTra() {
        Cursor cursor = databaseHelper.getWritableDatabase().rawQuery("delete from " + databaseHelper.TableName_KiemTra + " ", null);
        cursor.moveToFirst();
    }

    public KiemTra loadTTKiemTra() {
        KiemTra kiemTra = new KiemTra();
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("select * from " + databaseHelper.TableName_KiemTra + "", null);
        cursor.moveToFirst();
        kiemTra.setDocGia(cursor.getInt(cursor.getColumnIndex(databaseHelper.Key_kiemTra_DocGia)));
        kiemTra.setNhoMatKhau(cursor.getInt(cursor.getColumnIndex(databaseHelper.Key_kiemTra_NhoMatKhau)));

        return kiemTra;
    }
}
