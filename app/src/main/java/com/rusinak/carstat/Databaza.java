package com.rusinak.carstat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databaza extends SQLiteOpenHelper {

    private static final String DB_FULL_PATH = "af.db";

    Databaza(Context context) {
        super(context, "af.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!kontrola()) {
            db.execSQL("create table Tankovanie(natankovaneLitre TEXT, zaplatenaSuma TEXT, " +
                    "stavTachometra TEXT, datum TEXT, typPaliva TEXT, miestoTankovania TEXT)");

            db.execSQL("create table Udrzba(typUdrzby TEXT, zaplatenaSuma TEXT, " +
                    "stavTachometra TEXT, datum TEXT, poznamky TEXT, servis TEXT)");

            db.execSQL("create table STK(typKontroly TEXT, zaplatenaSuma TEXT, " +
                    "stavTachometra TEXT, datum TEXT, uspesnost TEXT, stanicaTK TEXT)");

            db.execSQL("create table Opravy(typOpravy TEXT, zaplatenaSuma TEXT, " +
                    "stavTachometra TEXT, datum TEXT, poznamky TEXT, servis TEXT)");

            db.execSQL("create table Jazdy(cesta TEXT, tachometer1 TEXT, " +
                    "tachometer2 TEXT, date TEXT, poznamka TEXT)");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL();
        onCreate(db);
    }

    public boolean pridajTankovanie(String natankovaneLitre, String zaplatenaSuma, String stavTachometra, String datum, String typPaliva, String miestoTankovania) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("natankovaneLitre", natankovaneLitre);
        contentValues.put("zaplatenaSuma", zaplatenaSuma);
        contentValues.put("stavTachometra", stavTachometra);
        contentValues.put("datum", datum);
        contentValues.put("typPaliva", typPaliva);
        contentValues.put("miestoTankovania", miestoTankovania);
        long vysledok = sqLiteDatabase.insert("Tankovanie", null, contentValues);
        if (vysledok == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean pridajUdrzbu(String typUdrzby, String zaplatenaSuma, String stavTachometra, String datum, String poznamky, String servis) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("typUdrzby", typUdrzby);
        contentValues.put("zaplatenaSuma", zaplatenaSuma);
        contentValues.put("stavTachometra", stavTachometra);
        contentValues.put("datum", datum);
        contentValues.put("poznamky", poznamky);
        contentValues.put("servis", servis);
        long vysledok = sqLiteDatabase.insert("Udrzba", null, contentValues);
        if (vysledok == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean pridajSTK(String typKontroly, String zaplatenaSuma, String stavTachometra, String datum, String uspesnost, String stanicaTK) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("typKontroly", typKontroly);
        contentValues.put("zaplatenaSuma", zaplatenaSuma);
        contentValues.put("stavTachometra", stavTachometra);
        contentValues.put("datum", datum);
        contentValues.put("uspesnost", uspesnost);
        contentValues.put("stanicaTK", stanicaTK);
        long vysledok = sqLiteDatabase.insert("STK", null, contentValues);
        if (vysledok == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean pridajOpravy(String typOpravy, String zaplatenaSuma, String stavTachometra, String datum, String poznamky, String servis) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("typOpravy", typOpravy);
        contentValues.put("zaplatenaSuma", zaplatenaSuma);
        contentValues.put("stavTachometra", stavTachometra);
        contentValues.put("datum", datum);
        contentValues.put("poznamky", poznamky);
        contentValues.put("servis", servis);
        long vysledok = sqLiteDatabase.insert("Opravy", null, contentValues);
        if (vysledok == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean pridajJazdy(String cesta, String tachometer1, String tachometer2, String date, String poznamka) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cesta", cesta);
        contentValues.put("tachometer1", tachometer1);
        contentValues.put("tachometer2", tachometer2);
        contentValues.put("date", date);
        contentValues.put("poznamka", poznamka);
        long vysledok = sqLiteDatabase.insert("Jazdy", null, contentValues);
        if (vysledok == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getFromDB(String tabulka) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tabulka, null);
        return cursor;
    }

    private boolean kontrola() {
        SQLiteDatabase kontrolaDB = null;
        try {
            kontrolaDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null, SQLiteDatabase.OPEN_READWRITE);
            kontrolaDB.close();
        } catch (SQLException e) {}

        return  kontrolaDB != null;
    }
}
