package com.rusinak.carstat;

import android.content.Context;
import android.database.Cursor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculation class, where all statistics are calculated
 */
public class Calculations {

    private static Context context;

    /**
     *
     * counts and returns average fuel consumption between last and penultimate refuelling
     *
     * @param db instance of DBHelper
     * @return returns current average fuel consumption in string
     */
    public String averageConsCur(DBHelper db) {
        Cursor cursor = db.getFromDB("Refuelling");
        String output = null;
        if (cursor.getCount() <= 1) {
            output = "0.00";
            return output;
        }
        cursor.moveToLast();
        double odoAct = Double.parseDouble(cursor.getString(2));
        cursor.moveToPrevious();
        double odoPrev = Double.parseDouble(cursor.getString(2));
        double litres = Double.parseDouble(cursor.getString(0));
        double result = litres * 100 / (odoAct - odoPrev);
        double res = round(result);
        output = String.valueOf(res);
        return output;
    }

    /**
     *
     * counts and returns average fuel consumption between last and first refuelling
     *
     * @param db instance of DBHelper
     * @return returns all-time average fuel consumption in string
     */
    public String averageConsAver(DBHelper db) {
        Cursor cursor = db.getFromDB("Refuelling");
        String output = null;
        if (cursor.getCount() <= 1) {
            output = "0.00";
            return output;
        }
        cursor.moveToLast();
        double odoAct = Double.parseDouble(cursor.getString(2));
        cursor.moveToFirst();
        double odoFirst = Double.parseDouble(cursor.getString(2));
        double litres = 00.00;
        while (!cursor.isAfterLast()) {
            litres += Double.parseDouble(cursor.getString(0));
            cursor.moveToNext();
        }
        double result = litres * 100 / (odoAct - odoFirst);
        double res = round(result);
        output = String.valueOf(res);
        return output;
    }

    /**
     *
     * counts and returns price paid for 1 litre of fuel on last refuelling
     *
     * @param db instance of DBHelper
     * @return returns price paid for 1 litre of fuel on last refuelling
     */
    public String costLitreCur(DBHelper db) {
        Cursor cursor = db.getFromDB("Refuelling");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "0.00";
            return output;
        }
        cursor.moveToLast();
        double price = Double.parseDouble(cursor.getString(1));
        double litres = Double.parseDouble(cursor.getString(0));
        double result = price / litres;
        double res = round(result);
        output = String.valueOf(res);
        return output;
    }

    /**
     *
     * counts and returns all-time average price paid for 1 litre of fuel
     *
     * @param db instance of DBHelper
     * @return returns all-time average price paid for 1 litre of fuel
     */
    public String costLitreAver(DBHelper db) {
        Cursor cursor = db.getFromDB("Refuelling");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "0.00";
            return output;
        }
        cursor.moveToFirst();
        double price = 00.00;
        double litres = 00.00;
        while (!cursor.isAfterLast()) {
            price += Double.parseDouble(cursor.getString(1));
            litres =+ Double.parseDouble(cursor.getString(0));
            cursor.moveToNext();
        }
        double result = price / litres;
        double res = round(result);
        output = String.valueOf(res);
        return output;
    }

    /**
     *
     * counts and returns all-time total price paid for fuel
     *
     * @param db instance of DBHelper
     * @return returns all-time total price paid for fuel
     */
    public String totalRefuelCost(DBHelper db) {
        Cursor cursor = db.getFromDB("Refuelling");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "0.00";
            return output;
        }
        cursor.moveToFirst();
        double price = 00.00;
        while (!cursor.isAfterLast()) {
            price += Double.parseDouble(cursor.getString(1));
            cursor.moveToNext();
        }
        double res = round(price);
        output = String.valueOf(res);
        return output;
    }

    /**
     *
     * counts and returns price paid for 1 kilometre between last and penultimate refuelling
     *
     * @param db instance of DBHelper
     * @return returns price paid for 1 kilometre between last and penultimate refuelling
     */
    public String pricePerKMLast(DBHelper db) {
        Cursor cursor = db.getFromDB("Refuelling");
        String output = null;
        if (cursor.getCount() <= 1) {
            output = "0.00";
            return output;
        }
        cursor.moveToLast();
        double odoAct = Double.parseDouble(cursor.getString(2));
        cursor.moveToPrevious();
        double odoPrev = Double.parseDouble(cursor.getString(2));
        double price = Double.parseDouble(cursor.getString(0));
        double result = price / (odoAct - odoPrev);
        double res = round(result);
        output = String.valueOf(res);
        return output;
    }

    /**
     *
     * counts and returns all-time average price paid for 1 kilometre
     *
     * @param db instance of DBHelper
     * @return returns all-time average price paid for 1 kilometre
     */
    public String pricePerKMAver(DBHelper db) {
        Cursor cursor = db.getFromDB("Refuelling");
        String output = null;
        if (cursor.getCount() <= 1) {
            output = "0.00";
            return output;
        }
        cursor.moveToLast();
        double odoAct = Double.parseDouble(cursor.getString(2));
        cursor.moveToFirst();
        double odoFirst = Double.parseDouble(cursor.getString(2));
        double price = 00.00;
        while (cursor.moveToNext()) {
            price += Double.parseDouble(cursor.getString(1));
        }
        double result = price / (odoAct - odoFirst);
        double res = round(result);
        output = String.valueOf(res);
        return output;
    }

    /**
     *
     * returns date, when last refuel was made
     *
     * @param db instance of DBHelper
     * @return returns string with date, when last refuel was made
     */
    public String lastRefuel(DBHelper db) {
        Cursor cursor = db.getFromDB("Refuelling");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "Žiaden záznam";
            return output;
        }
        cursor.moveToLast();
        output = cursor.getString(3);
        return output;
    }

    /**
     *
     * counts and returns all-time total price paid for maintainance
     *
     * @param db instance of DBHelper
     * @return returns all-time total price paid for maintainance
     */
    public String totalMaintCost(DBHelper db) {
        Cursor cursor = db.getFromDB("Maintainance");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "0.00";
            return output;
        }
        cursor.moveToFirst();
        double price = 00.00;
        while (!cursor.isAfterLast()) {
            price += Double.parseDouble(cursor.getString(1));
            cursor.moveToNext();
        }
        double res = round(price);
        output = String.valueOf(res);
        return output;
    }

    /**
     *
     * returns date, when oil was changed
     *
     * @param db instance of DBHelper
     * @return returns string with date, when oil was changed
     */
    public String lastOil(DBHelper db) {
        Cursor cursor = db.getFromDB("Maintainance");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "Žiaden záznam";
            return output;
        }
        cursor.moveToLast();
        if (cursor.getString(0).equals("Výmena oleja a filtrov")) {
            output = cursor.getString(3);
            return output;
        }
        while (cursor.moveToPrevious()) {
            if (cursor.getString(0).equals("Výmena oleja a filtrov")) {
                break;
            }
        }
        if (cursor.isBeforeFirst()) {
            output = "Žiaden záznam";
            return output;
        }
        output = cursor.getString(3);
        return output;
    }

    /**
     *
     * returns date, when last warranty inspection was done
     *
     * @param db instance of DBHelper
     * @return returns string with date, when last warranty inspection was done
     */
    public String lastWarranty(DBHelper db) {
        Cursor cursor = db.getFromDB("Maintainance");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "Žiaden záznam";
            return output;
        }
        cursor.moveToLast();
        while (cursor.moveToPrevious()) {
            if (cursor.getString(0).equals("Záručná prehliadka")) {
                break;
            }
        }
        if (cursor.isBeforeFirst()) {
            output = "Žiaden záznam";
            return output;
        }
        output = cursor.getString(3);
        return output;
    }

    /**
     *
     * returns date, when last technical inspection was done
     *
     * @param db instance of DBHelper
     * @return returns string with date, when last technical inspection was done
     */
    public String lastInspection(DBHelper db) {
        Cursor cursor = db.getFromDB("Inspection");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "Žiaden záznam";
            return output;
        }
        cursor.moveToLast();
        if (cursor.getString(0).equals("Technická kontrola")) {
            output = cursor.getString(3);
            return output;
        }
        while (cursor.moveToPrevious()) {
            if (cursor.getString(0).equals("Technická kontrola")) {
                break;
            }
        }
        if (cursor.getPosition() < 0) {
            output = "Žiaden záznam";
            return output;
        }
        output = cursor.getString(3);
        return output;
    }

    /**
     *
     * returns date, when last emissions inspection was done
     *
     * @param db instance of DBHelper
     * @return returns string with date, when last emissions inspection was done
     */
    public String lastEmisInspection(DBHelper db) {
        Cursor cursor = db.getFromDB("Maintainance");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "Žiaden záznam";
            return output;
        }
        cursor.moveToLast();
        if (cursor.getString(0).equals("Emisná kontrola")) {
            output = cursor.getString(3);
            return output;
        }
        while (cursor.moveToPrevious()) {
            if (cursor.getString(0).equals("Emisná kontrola")) {
                break;
            }
        }
        if (cursor.getPosition() < 0) {
            output = "Žiaden záznam";
            return output;
        }
        output = cursor.getString(3);
        return output;
    }

    /**
     *
     * returns date, when last originality inspection was done
     *
     * @param db instance of DBHelper
     * @return returns string with date, when last originality inspection was done
     */
    public String lastOrigControl(DBHelper db) {
        Cursor cursor = db.getFromDB("Maintainance");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "Žiaden záznam";
            return output;
        }
        cursor.moveToLast();
        if (cursor.getString(0).equals("Kontrola Originality")) {
            output = cursor.getString(3);
            return output;
        }
        while (cursor.moveToPrevious()) {
            if (cursor.getString(0).equals("Kontrola Originality")) {
                break;
            }
        }
        if (cursor.getPosition() < 0) {
            output = "Žiaden záznam";
            return output;
        }
        output = cursor.getString(3);
        return output;
    }

    /**
     *
     * counts and returns all-time total price paid for inspection
     *
     * @param db instance of DBHelper
     * @return returns all-time total price paid for inspection
     */
    public String totalInspCost(DBHelper db) {
        Cursor cursor = db.getFromDB("Maintainance");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "0.00";
            return output;
        }
        cursor.moveToFirst();
        double price = 00.00;
        while (!cursor.isAfterLast()) {
            price += Double.parseDouble(cursor.getString(1));
            cursor.moveToNext();
        }
        double res = round(price);
        output = String.valueOf(res);
        return output;
    }

    /**
     *
     * counts and returns all-time total price paid for repairs
     *
     * @param db instance of DBHelper
     * @return returns all-time total price paid for repairs
     */
    public String totalRepairCost(DBHelper db) {
        Cursor cursor = db.getFromDB("Repairs");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "0.00";
            return output;
        }
        cursor.moveToFirst();
        double price = 00.00;
        while (!cursor.isAfterLast()) {
            price += Double.parseDouble(cursor.getString(1));
            cursor.moveToNext();
        }
        double res = round(price);
        output = String.valueOf(res);
        return output;
    }

    /**
     *
     * returns date, when last repair was done
     *
     * @param db instance of DBHelper
     * @return returns string with date, when last repair was done
     */
    public String lastRepair(DBHelper db) {
        Cursor cursor = db.getFromDB("Repairs");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "Žiaden záznam";
            return output;
        }
        cursor.moveToLast();
        output = cursor.getString(3);
        return output;
    }

    /**
     *
     * counts and returns all-time total price paid for car-related stuff
     *
     * @param db instance of DBHelper
     * @return returns all-time total price paid for car-related stuff
     */
    public String totalCostAll(DBHelper db) {

        Double fuel = Double.parseDouble(totalRefuelCost(db));
        Double maint = Double.parseDouble(totalMaintCost(db));
        Double insp = Double.parseDouble(totalInspCost(db));
        Double repair = Double.parseDouble(totalRepairCost(db));
        Double result = fuel + maint + repair + insp;
        String output = String.valueOf(result);
        return output;
    }

    /**
     *
     * rounds double to 2 decimal place
     *
     * @param value double value to round
     * @return returns double rounded to 2 decimal places
     */
    public double round(Double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
