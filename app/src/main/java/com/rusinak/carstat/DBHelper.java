package com.rusinak.carstat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * DBHelper class, interacts with database file
 *
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_FULL_PATH = "carstatDB.db";

    DBHelper(Context context) {
        super(context, "carstatDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!check()) {
            db.execSQL("create table Refuelling(natankovaneLitre TEXT, zaplatenaSuma TEXT, " +
                    "stavTachometra TEXT, datum TEXT, typPaliva TEXT, miestoTankovania TEXT)");

            db.execSQL("create table Maintainance(typUdrzby TEXT, zaplatenaSuma TEXT, " +
                    "stavTachometra TEXT, datum TEXT, poznamky TEXT, servis TEXT)");

            db.execSQL("create table Inspection(typKontroly TEXT, zaplatenaSuma TEXT, " +
                    "stavTachometra TEXT, datum TEXT, uspesnost TEXT, stanicaTK TEXT)");

            db.execSQL("create table Repairs(typOpravy TEXT, zaplatenaSuma TEXT, " +
                    "stavTachometra TEXT, datum TEXT, poznamky TEXT, servis TEXT)");

            db.execSQL("create table Trips(cesta TEXT, tachometer1 TEXT, " +
                    "tachometer2 TEXT, date TEXT, poznamka TEXT)");

            db.execSQL("create table Settings(znacka TEXT, model TEXT)");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    /**
     *
     * inserts refuelling values into database table
     *
     * @param litres number of refuelled litres
     * @param price amount of money paid for fuel
     * @param odo state of odometer
     * @param date date, when refuelling was made
     * @param fueltype type of fuel
     * @param place name of gas station
     * @return true or false, checks, if inserting was successful
     */
    public boolean addRefuel(String litres, String price, String odo, String date, String fueltype, String place) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("natankovaneLitre", litres);
        cValues.put("zaplatenaSuma", price);
        cValues.put("stavTachometra", odo);
        cValues.put("datum", date);
        cValues.put("typPaliva", fueltype);
        cValues.put("miestoTankovania", place);
        long result = sqlDB.insert("Refuelling", null, cValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * inserts maintainance values into database table
     *
     * @param mainttype type of maintainance
     * @param price amount of money paid for maintainance
     * @param odo state of odometer
     * @param date date, when maintainance was done
     * @param notes notes
     * @param service name of service
     * @return true or false, checks, if inserting was successful
     */
    public boolean addMaint(String mainttype, String price, String odo, String date, String notes, String service) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("typUdrzby", mainttype);
        cValues.put("zaplatenaSuma", price);
        cValues.put("stavTachometra", odo);
        cValues.put("datum", date);
        cValues.put("poznamky", notes);
        cValues.put("servis", service);
        long result = sqlDB.insert("Maintainance", null, cValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * inserts inspection values into database table
     *
     * @param insptype type of inspection
     * @param price amount of money paid for inspection
     * @param odo state of odometer
     * @param date date, when inspection was done
     * @param success whenever inspection was successful or not
     * @param station name of inspection station
     * @return true or false, checks, if inserting was successful
     */
    public boolean addInsp(String insptype, String price, String odo, String date, String success, String station) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("typKontroly", insptype);
        cValues.put("zaplatenaSuma", price);
        cValues.put("stavTachometra", odo);
        cValues.put("datum", date);
        cValues.put("uspesnost", success);
        cValues.put("stanicaTK", station);
        long result = sqlDB.insert("Inspection", null, cValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * inserts repair values into database table
     *
     * @param repairtype type of repair
     * @param price amount of money paid for repair
     * @param odo state of odometer
     * @param date date, when repair was done
     * @param notes notes
     * @param service name of service
     * @return true or false, checks, if inserting was successful
     */
    public boolean pridajOpravy(String repairtype, String price, String odo, String date, String notes, String service) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("typOpravy", repairtype);
        cValues.put("zaplatenaSuma", price);
        cValues.put("stavTachometra", odo);
        cValues.put("datum", date);
        cValues.put("poznamky", notes);
        cValues.put("servis", service);
        long result = sqlDB.insert("Repairs", null, cValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * inserts trip values into database table
     *
     * @param trip point A - point B of trip
     * @param odoPrev state of odometer at the beginning of trip
     * @param odoCur state of odometer at the end of trip
     * @param date date, when trip was done
     * @param notes notes
     * @return true or false, checks, if inserting was successful
     */
    public boolean addTrip(String trip, String odoPrev, String odoCur, String date, String notes) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("cesta", trip);
        cValues.put("tachometer1", odoPrev);
        cValues.put("tachometer2", odoCur);
        cValues.put("date", date);
        cValues.put("poznamka", notes);
        long result = sqlDB.insert("Trips", null, cValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addSetting(String brand, String model)
    {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("znacka", brand);
        cValues.put("model", model);
        long result = sqlDB.insert("Settings", null, cValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * method which returns cursor to access data in database
     *
     * @param tableName string which contains name of table in database
     * @return cursor
     */
    public Cursor getFromDB(String tableName) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        Cursor cur = sqlDB.rawQuery("select * from " + tableName, null);
        return cur;
    }

    /**
     *
     *
     *
     * @return
     */
    private boolean check() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null, SQLiteDatabase.OPEN_READWRITE);
            checkDB.close();
        } catch (SQLException e) {}

        return  checkDB != null;
    }

    public void deleteFromDB(String tableName) {
        //SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("delete from " + tableName);
        //db.close();
    }
}
