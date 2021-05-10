package com.rusinak.carstat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Upozornenia extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        //upozornenie(context, intent.getStringExtra("text"));
        Databaza databaza = new Databaza(context);
        Kalkulacka kalkulacka = new Kalkulacka();

        if (aktDatum().equals(kalkulacka.posledneTank(databaza))) {
            upozornenie(context, "Od posledného tankovania bola priemerná spotreba " + kalkulacka.priemernaSpotrebaPosl(databaza)
                    + " l/100km a jeden kilometer ťa stál " + kalkulacka.cenaZaKMPoslTank(databaza) + "€", 100);
        }

        if (orezanyDatum().equals(kalkulacka.poslednaVymOleja(databaza))) {
            upozornenie(context, "Blíži sa servisný interval výmeny oleja. Olej bol naposledy menený 2 roky dozadu.", 200);
        }

        if (aktDatum().equals("08.11." + aktDatum().substring(6))) {
            upozornenie(context, "Od 15.novembra platí povinnosť mať pri súvislej vrstve snehu zimné pneumatiky. Nezabudni ich čo najskôr prezuť.", 300);
        }

        if (orezanyDatum().equals(kalkulacka.poslednaTK(databaza))) {
            upozornenie(context, "Platnosť STK ti končí o 1 mesiac. Nezabudni sa prihlásiť.", 400);
        }
    }

    public void upozornenie(Context context, String string, int id){
        NotificationCompat.Builder ncb = new NotificationCompat.Builder(context, "upozornenie")
                .setSmallIcon(R.drawable.ic_menu_car)
                .setContentTitle("Info")
                //.setContentText("Štatistiky od posledného tankovania")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(string))
                .setAutoCancel(true);

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(id, ncb.build());
    }

    private String aktDatum() {
        Date datum = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(datum).toString();
    }

    private String orezanyDatum() {
        Date datum = new Date();
        int den = 0;
        int rok = 0;
        int mesiac = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        den = Integer.parseInt(format.format(datum).toString().substring(0, 2));
        rok = Integer.parseInt(format.format(datum).toString().substring(6));
        mesiac = Integer.parseInt(format.format(datum).toString().substring(3, 5));
        mesiac++;
        rok -= 2;

        if (mesiac == 13) {
            rok++;
            mesiac = 1;
        }

        if (mesiac == 2 ||mesiac == 4 || mesiac == 6 || mesiac == 9 || mesiac == 11) {
            if (den == 31)
                den--;

            if (den > 28 && mesiac == 2)
                den = 28;
        }

        if (den < 10 && mesiac < 10) {
            return "0" + den + ".0" + mesiac + "." + rok;
        } else if (den < 10 && mesiac >= 10) {
            return "0" + den + "." + mesiac + "." + rok;
        } else if (den >= 10 && mesiac < 10) {
            return den + ".0" + mesiac + "." + rok;
        }

        return den + "." + mesiac + "." + rok;
    }
}
