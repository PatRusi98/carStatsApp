package com.rusinak.carstat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Notifications class, builds notifications when conditions are met
 *
 */
public class Notifications extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        DBHelper db = new DBHelper(context);
        Calculations calc = new Calculations();

        if (dateCur().equals(calc.lastRefuel(db))) {
            notification(context, "Od posledného tankovania bola priemerná spotreba " + calc.averageConsCur(db)
                    + " l/100km a jeden kilometer ťa stál " + calc.pricePerKMLast(db) + "€", 100);
        }

        if (dateCheck().equals(calc.lastOil(db))) {
            notification(context, "Blíži sa servisný interval výmeny oleja. Olej bol naposledy menený 2 roky dozadu.", 200);
        }

        if (dateCur().equals("08.11." + dateCur().substring(6))) {
            notification(context, "Od 15.novembra platí povinnosť mať pri súvislej vrstve snehu zimné pneumatiky. Nezabudni ich čo najskôr prezuť.", 300);
        }

        if (dateCheck().equals(calc.lastInspection(db))) {
            notification(context, "Platnosť STK ti končí o 1 mesiac. Nezabudni sa prihlásiť.", 400);
        }
    }

    /**
     *
     * builds notifications
     *
     * @param context
     * @param text text to be displayed in notification
     * @param id notification id
     */
    public void notification(Context context, String text, int id){
        NotificationCompat.Builder ncb = new NotificationCompat.Builder(context, "upozornenie")
                .setSmallIcon(R.drawable.ic_menu_car)
                .setContentTitle("Info")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(text))
                .setAutoCancel(true);

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(id, ncb.build());
    }

    /**
     *
     * method which returns current date
     *
     * @return string with date
     */
    private String dateCur() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date).toString();
    }

    /**
     *
     * method which calculates date, which was 23 months before current date
     * for notification conditions
     *
     * @return string with calculated date (current date minus 23 months)
     */
    private String dateCheck() {
        Date date = new Date();
        int day = 0;
        int year = 0;
        int month = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        day = Integer.parseInt(sdf.format(date).toString().substring(0, 2));
        year = Integer.parseInt(sdf.format(date).toString().substring(6));
        month = Integer.parseInt(sdf.format(date).toString().substring(3, 5));
        month++;
        year -= 2;

        if (month == 13) {
            year++;
            month = 1;
        }

        if (month == 2 ||month == 4 || month == 6 || month == 9 || month == 11) {
            if (day == 31)
                day--;

            if (day > 28 && month == 2)
                day = 28;
        }

        if (day < 10 && month < 10) {
            return "0" + day + ".0" + month + "." + year;
        } else if (day < 10 && month >= 10) {
            return "0" + day + "." + month + "." + year;
        } else if (day >= 10 && month < 10) {
            return day + ".0" + month + "." + year;
        }

        return day + "." + month + "." + year;
    }
}
