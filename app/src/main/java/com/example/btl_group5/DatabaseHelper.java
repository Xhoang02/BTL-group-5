package com.example.btl_group5;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Account.db";
    public static final int DATABASE_VERSION = 3;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void QueryData(String sql , String[] strings){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql,strings);
    }
    public Cursor getData(String sql , String[] strings){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,strings);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS tblShoppingCart (Username TEXT PRIMARY KEY, Tenmon TEXT, Giatien INTEGER)";
        db.execSQL(createTable);
        String createAccountTable = "CREATE TABLE IF NOT EXISTS tblAccount (Username Text primary key, Email Text, Phone Integer, Password Text)";
        db.execSQL(createAccountTable);
        String createKhaiviTable = "CREATE TABLE IF NOT EXISTS tblKhaivi(Tenmon Text primary key, Giatien Integer)";
        db.execSQL(createKhaiviTable);
        String createMonchinhTable = "CREATE TABLE tblMonchinh(Tenmon Text primary key, Giatien Integer)";
        db.execSQL(createMonchinhTable);
        String createMontrangmiengTable = "CREATE TABLE tblMontrangmieng(Tenmon Text primary key, Giatien Integer)";
        db.execSQL(createMontrangmiengTable);
        String createDouongTable = "CREATE TABLE tblDouong(Tenmon Text primary key, Giatien Integer)";
        db.execSQL(createDouongTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tblShoppingCart");
        db.execSQL("DROP TABLE IF EXISTS tblAccount");
        db.execSQL("DROP TABLE IF EXISTS tblMonchinh");
        db.execSQL("DROP TABLE IF EXISTS tblMontrangmieng");
        db.execSQL("DROP TABLE IF EXISTS tblDouong");
        onCreate(db);
    }
}