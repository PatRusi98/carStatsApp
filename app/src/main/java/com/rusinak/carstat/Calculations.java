package com.rusinak.carstat;

import android.content.Context;
import android.database.Cursor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculations {

    private static Context context;

    /**
     *
     * counts and returns average fuel consumption between last and penultimate refuelling
     *
     * @param db
     * @return returns current average fuel consumption in string
     */
    public String averageConsCur(DBHelper db) {
        Cursor cursor = db.getFromDB("Tankovanie");
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
     * @param db
     * @return returns all-time average fuel consumption in string
     */
    public String averageConsAver(DBHelper db) {
        Cursor cursor = db.getFromDB("Tankovanie");
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
     * counts and returns price
     *
     * @param db
     * @return returns
     */
    public String costLitreCur(DBHelper db) {
        Cursor cursor = db.getFromDB("Tankovanie");
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

    public String costLitreAver(DBHelper db) {
        Cursor cursor = db.getFromDB("Tankovanie");
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

    public String totalRefuelCost(DBHelper db) {
        Cursor cursor = db.getFromDB("Tankovanie");
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

    public String pricePerKMLast(DBHelper db) {
        Cursor cursor = db.getFromDB("Tankovanie");
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

    public String pricePerKMAver(DBHelper db) {
        Cursor cursor = db.getFromDB("Tankovanie");
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

    public String lastRefuel(DBHelper db) {
        Cursor cursor = db.getFromDB("Tankovanie");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "Žiaden záznam";
            return output;
        }
        cursor.moveToLast();
        output = cursor.getString(3);
        return output;
    }

    public String totalMaintCost(DBHelper db) {
        Cursor cursor = db.getFromDB("Udrzba");
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

    public String lastOil(DBHelper db) {
        Cursor cursor = db.getFromDB("Udrzba");
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

    public String lastWarranty(DBHelper db) {
        Cursor cursor = db.getFromDB("Udrzba");
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

    public String lastInspection(DBHelper db) {
        Cursor cursor = db.getFromDB("STK");
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

    public String lastEmisInspection(DBHelper db) {
        Cursor cursor = db.getFromDB("STK");
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

    public String lastOrigControl(DBHelper db) {
        Cursor cursor = db.getFromDB("STK");
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

    public String totalInspCost(DBHelper db) {
        Cursor cursor = db.getFromDB("STK");
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

    public String totalRepairCost(DBHelper db) {
        Cursor cursor = db.getFromDB("Opravy");
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


    public String lastRepair(DBHelper db) {
        Cursor cursor = db.getFromDB("Opravy");
        String output = null;
        if (cursor.getCount() == 0) {
            output = "Žiaden záznam";
            return output;
        }
        cursor.moveToLast();
        output = cursor.getString(3);
        return output;
    }

    public String totalCostAll(DBHelper db) {

        Double fuel = Double.parseDouble(totalRefuelCost(db));
        Double maint = Double.parseDouble(totalMaintCost(db));
        Double insp = Double.parseDouble(totalInspCost(db));
        Double repair = Double.parseDouble(totalRepairCost(db));
        Double result = fuel + maint + repair + insp;
        String output = String.valueOf(result);
        return output;
    }

    public double round(Double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
