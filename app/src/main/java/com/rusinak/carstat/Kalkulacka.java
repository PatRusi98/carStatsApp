package com.rusinak.carstat;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Kalkulacka {

    private static Context context;
    //Databaza databaza = new Databaza(context);

    ///////TANKOVANIE//////

    public String priemernaSpotrebaPosl(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Tankovanie");
        String vypis = null;
        if (cursor.getCount() <= 1) {
            vypis = "0.00";
            return vypis;
        }
        cursor.moveToLast();
        double poslKM = Double.parseDouble(cursor.getString(2));
        cursor.moveToPrevious();
        double predposlKM = Double.parseDouble(cursor.getString(2));
        double natankovaneLitre = Double.parseDouble(cursor.getString(0));
        double pomocna = natankovaneLitre * 100 / (poslKM - predposlKM);
        double vysledok = zaokruhlovanie(pomocna);
        vypis = String.valueOf(vysledok);
        return vypis;
    }

    public String priemernaSpotrebaCelk(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Tankovanie");
        String vypis = null;
        if (cursor.getCount() <= 1) {
            vypis = "0.00";
            return vypis;
        }
        cursor.moveToLast();
        double poslUdajKM = Double.parseDouble(cursor.getString(2));
        cursor.moveToFirst();
        double prvyUdajKM = Double.parseDouble(cursor.getString(2));
        double natankovaneLitre = 00.00;
        while (!cursor.isAfterLast()) {
            natankovaneLitre += Double.parseDouble(cursor.getString(0));
            cursor.moveToNext();
        }
        double pomocna = natankovaneLitre * 100 / (poslUdajKM - prvyUdajKM);
        double vysledok = zaokruhlovanie(pomocna);
        vypis = String.valueOf(vysledok);
        return vypis;
    }

    public String cenaZaLiterPosl(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Tankovanie");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "0.00";
            return vypis;
        }
        cursor.moveToLast();
        double zaplSuma = Double.parseDouble(cursor.getString(1));
        double natankovaneLitre = Double.parseDouble(cursor.getString(0));
        double pomocna = zaplSuma / natankovaneLitre;
        double vysledok = zaokruhlovanie(pomocna);
        vypis = String.valueOf(vysledok);
        return vypis;
    }

    public String cenaZaLiterPriemer(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Tankovanie");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "0.00";
            return vypis;
        }
        cursor.moveToFirst();
        double zaplSuma = 00.00;
        double natankovaneLitre = 00.00;
        while (!cursor.isAfterLast()) {
            zaplSuma += Double.parseDouble(cursor.getString(1));
            natankovaneLitre =+ Double.parseDouble(cursor.getString(0));
            cursor.moveToNext();
        }
        double pomocna = zaplSuma / natankovaneLitre;
        double vysledok = zaokruhlovanie(pomocna);
        vypis = String.valueOf(vysledok);
        return vypis;
    }

    public String celkovaSumaZaTank(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Tankovanie");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "0.00";
            return vypis;
        }
        cursor.moveToFirst();
        double zaplSuma = 00.00;
        while (!cursor.isAfterLast()) {
            zaplSuma += Double.parseDouble(cursor.getString(1));
            cursor.moveToNext();
        }
        double vysledok = zaokruhlovanie(zaplSuma);
        vypis = String.valueOf(vysledok);
        return vypis;
    }

    public String cenaZaKMPoslTank(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Tankovanie");
        String vypis = null;
        if (cursor.getCount() <= 1) {
            vypis = "0.00";
            return vypis;
        }
        cursor.moveToLast();
        double poslKM = Double.parseDouble(cursor.getString(2));
        cursor.moveToPrevious();
        double predposlKM = Double.parseDouble(cursor.getString(2));
        double zaplSuma = Double.parseDouble(cursor.getString(0));
        double pomocna = zaplSuma / (poslKM - predposlKM);
        double vysledok = zaokruhlovanie(pomocna);
        vypis = String.valueOf(vysledok);
        return vypis;
    }

    public String cenaZaKMCelkTank(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Tankovanie");
        String vypis = null;
        if (cursor.getCount() <= 1) {
            vypis = "0.00";
            return vypis;
        }
        cursor.moveToLast();
        double poslUdajKM = Double.parseDouble(cursor.getString(2));
        cursor.moveToFirst();
        double prvyUdajKM = Double.parseDouble(cursor.getString(2));
        double zaplSuma = 00.00;
        while (cursor.moveToNext()) {
            zaplSuma += Double.parseDouble(cursor.getString(1));
        }
        double pomocna = zaplSuma / (poslUdajKM - prvyUdajKM);
        double vysledok = zaokruhlovanie(pomocna);
        vypis = String.valueOf(vysledok);
        return vypis;
    }

    public String posledneTank(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Tankovanie");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "Žiaden záznam";
            return vypis;
        }
        cursor.moveToLast();
        vypis = cursor.getString(3);
        return vypis;
    }

    ///////UDRZBA//////

    public String celkovaSumaZaUdrzbu(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Udrzba");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "0.00";
            return vypis;
        }
        cursor.moveToFirst();
        double zaplSuma = 00.00;
        while (!cursor.isAfterLast()) {
            zaplSuma += Double.parseDouble(cursor.getString(1));
            cursor.moveToNext();
        }
        double vysledok = zaokruhlovanie(zaplSuma);
        vypis = String.valueOf(vysledok);
        return vypis;
    }

    public String poslednaVymOleja(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Udrzba");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "Žiaden záznam";
            return vypis;
        }
        cursor.moveToLast();
        if (cursor.getString(0).equals("Výmena oleja a filtrov")) {
            vypis = cursor.getString(3);
            return vypis;
        }
        while (cursor.moveToPrevious()) {
            if (cursor.getString(0).equals("Výmena oleja a filtrov")) {
                break;
            }
        }
        if (cursor.isBeforeFirst()) {
            vypis = "Žiaden záznam";
            return vypis;
        }
        vypis = cursor.getString(3);
        return vypis;
    }

    public String poslednaZarPrehliadka(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Udrzba");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "Žiaden záznam";
            return vypis;
        }
        cursor.moveToLast();
        while (cursor.moveToPrevious()) {
            if (cursor.getString(0).equals("Záručná prehliadka")) {
                break;
            }
        }
        if (cursor.isBeforeFirst()) {
            vypis = "Žiaden záznam";
            return vypis;
        }
        vypis = cursor.getString(3);
        return vypis;
    }

    ///////STK//////

    public String poslednaTK(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("STK");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "Žiaden záznam";
            return vypis;
        }
        cursor.moveToLast();
        if (cursor.getString(0).equals("Technická kontrola")) {
            vypis = cursor.getString(3);
            return vypis;
        }
        while (cursor.moveToPrevious()) {
            if (cursor.getString(0).equals("Technická kontrola")) {
                break;
            }
        }
        if (cursor.getPosition() < 0) {
            vypis = "Žiaden záznam";
            return vypis;
        }
        vypis = cursor.getString(3);
        return vypis;
    }

    public String poslednaEK(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("STK");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "Žiaden záznam";
            return vypis;
        }
        cursor.moveToLast();
        if (cursor.getString(0).equals("Emisná kontrola")) {
            vypis = cursor.getString(3);
            return vypis;
        }
        while (cursor.moveToPrevious()) {
            if (cursor.getString(0).equals("Emisná kontrola")) {
                break;
            }
        }
        if (cursor.getPosition() < 0) {
            vypis = "Žiaden záznam";
            return vypis;
        }
        vypis = cursor.getString(3);
        return vypis;
    }

    public String poslednaKO(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("STK");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "Žiaden záznam";
            return vypis;
        }
        cursor.moveToLast();
        if (cursor.getString(0).equals("Kontrola Originality")) {
            vypis = cursor.getString(3);
            return vypis;
        }
        while (cursor.moveToPrevious()) {
            if (cursor.getString(0).equals("Kontrola Originality")) {
                break;
            }
        }
        if (cursor.getPosition() < 0) {
            vypis = "Žiaden záznam";
            return vypis;
        }
        vypis = cursor.getString(3);
        return vypis;
    }

    public String celkovaSumaZaSTK(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("STK");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "0.00";
            return vypis;
        }
        cursor.moveToFirst();
        double zaplSuma = 00.00;
        while (!cursor.isAfterLast()) {
            zaplSuma += Double.parseDouble(cursor.getString(1));
            cursor.moveToNext();
        }
        double vysledok = zaokruhlovanie(zaplSuma);
        vypis = String.valueOf(vysledok);
        return vypis;
    }

    ///////OPRAVY//////

    public String celkovaSumaZaOpravy(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Opravy");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "0.00";
            return vypis;
        }
        cursor.moveToFirst();
        double zaplSuma = 00.00;
        while (!cursor.isAfterLast()) {
            zaplSuma += Double.parseDouble(cursor.getString(1));
            cursor.moveToNext();
        }
        double vysledok = zaokruhlovanie(zaplSuma);
        vypis = String.valueOf(vysledok);
        return vypis;
    }


    public String poslednaOprava(Databaza databaza) {
        Cursor cursor = databaza.getFromDB("Opravy");
        String vypis = null;
        if (cursor.getCount() == 0) {
            vypis = "Žiaden záznam";
            return vypis;
        }
        cursor.moveToLast();
        vypis = cursor.getString(3);
        return vypis;
    }

    ///////VSEOBECNE//////

    public String celkovaSumaZaAuto(Databaza databaza) {

        Double tank = Double.parseDouble(celkovaSumaZaTank(databaza));
        Double udrzba = Double.parseDouble(celkovaSumaZaUdrzbu(databaza));
        Double stk = Double.parseDouble(celkovaSumaZaSTK(databaza));
        Double opravy = Double.parseDouble(celkovaSumaZaOpravy(databaza));
        Double vysledok = tank + udrzba + opravy + stk;
        String vypis = String.valueOf(vysledok);
        return vypis;
    }

    public double zaokruhlovanie(Double hodnota) {
        BigDecimal bd = BigDecimal.valueOf(hodnota);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
