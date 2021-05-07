package com.rusinak.carstat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Upozornenia extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        upozornenie(context, intent.getStringExtra("text"));
    }

    public void upozornenie(Context context, String string){
        NotificationCompat.Builder ncb = new NotificationCompat.Builder(context, "upoTank")
                .setSmallIcon(R.drawable.ic_menu_car)
                .setContentTitle("Info")
                //.setContentText("Štatistiky od posledného tankovania")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(string))
                .setAutoCancel(true);

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(100, ncb.build());
    }
}
