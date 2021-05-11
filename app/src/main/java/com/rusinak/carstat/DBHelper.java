package com.rusinak.carstat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_FULL_PATH = "af.db";

    DBHelper(Context context) {
        super(context, "af.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!check()) {
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
        onCreate(db);
    }

    public boolean addRefuel(String litres, String price, String odo, String date, String fueltype, String place) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("natankovaneLitre", litres);
        cValues.put("zaplatenaSuma", price);
        cValues.put("stavTachometra", odo);
        cValues.put("datum", date);
        cValues.put("typPaliva", fueltype);
        cValues.put("miestoTankovania", place);
        long result = sqlDB.insert("Tankovanie", null, cValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addMaint(String mainttype, String price, String odo, String date, String notes, String service) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("typUdrzby", mainttype);
        cValues.put("zaplatenaSuma", price);
        cValues.put("stavTachometra", odo);
        cValues.put("datum", date);
        cValues.put("poznamky", notes);
        cValues.put("servis", service);
        long result = sqlDB.insert("Udrzba", null, cValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addInsp(String insptype, String price, String odo, String date, String success, String station) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("typKontroly", insptype);
        cValues.put("zaplatenaSuma", price);
        cValues.put("stavTachometra", odo);
        cValues.put("datum", date);
        cValues.put("uspesnost", success);
        cValues.put("stanicaTK", station);
        long result = sqlDB.insert("STK", null, cValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean pridajOpravy(String repairtype, String price, String odo, String date, String notes, String service) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("typOpravy", repairtype);
        cValues.put("zaplatenaSuma", price);
        cValues.put("stavTachometra", odo);
        cValues.put("datum", date);
        cValues.put("poznamky", notes);
        cValues.put("servis", service);
        long result = sqlDB.insert("Opravy", null, cValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addTrip(String trip, String odoPrev, String odoCur, String date, String notes) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("cesta", trip);
        cValues.put("tachometer1", odoPrev);
        cValues.put("tachometer2", odoCur);
        cValues.put("date", date);
        cValues.put("poznamka", notes);
        long result = sqlDB.insert("Jazdy", null, cValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getFromDB(String tableName) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        Cursor cur = sqlDB.rawQuery("select * from " + tableName, null);
        return cur;
    }

    private boolean check() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null, SQLiteDatabase.OPEN_READWRITE);
            checkDB.close();
        } catch (SQLException e) {}

        return  checkDB != null;
    }
}
