package com.rusinak.carstat;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;

    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_tankovanie, R.id.nav_udzba, R.id.nav_stk, R.id.nav_opravy, R.id.nav_jazdy, R.id.nav_statistiky, R.id.nav_nastavenia)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        notificationGenerator();
    }

    public void notificationGenerator() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.HOUR_OF_DAY, 19);
        cal.set(cal.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);

        Intent notifIntent = new Intent(this, Notifications.class);
        PendingIntent pendingNotifIntent = PendingIntent.getBroadcast(this, 0, notifIntent, 0);
        AlarmManager notifAM = (AlarmManager) getSystemService(ALARM_SERVICE);
        notifAM.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingNotifIntent);
        notifChannel();
    }

    public void notifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotificationChannel";
            String description = "desc";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("upozornenie", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void onClkRefuellingInp(View v) {
        EditText litres = findViewById(R.id.natankovaneLitreTankovanie);
        EditText price = findViewById(R.id.zaplatenaSumaTankovanie);
        EditText odometer = findViewById(R.id.stavTachometraTankovanie);
        EditText date = findViewById(R.id.datumTankovanie);
        Spinner fueltype = findViewById(R.id.typPalivaTankovanie);
        EditText place = findViewById(R.id.miestoTankovaniaTankovanie);
        String sLitres = litres.getText().toString();
        String sPrice = price.getText().toString();
        String sOdometer = odometer.getText().toString();
        String sDate = date.getText().toString();
        String sFueltype = fueltype.getSelectedItem().toString();
        String sPlace = place.getText().toString();
        if (sLitres.isEmpty() || sPrice.isEmpty() || sOdometer.isEmpty() || sDate.isEmpty()){
            Toast.makeText(this, "Prázdne pole", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean inserted = db.addRefuel(sLitres, sPrice, sOdometer, sDate, sFueltype, sPlace);
        if (inserted) {
            Toast.makeText(MainActivity.this, "Údaje boli vložené", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClkRefuellingOut(View v) {
        Cursor cur = db.getFromDB("Tankovanie");
        if (cur.getCount() == 0) {
            Toast.makeText(this, "Žiaden záznam", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer sb = new StringBuffer();
        while (cur.moveToNext()) {
            sb.append("Dátum: " + cur.getString(3) + "\n");
            sb.append("Miesto tankovania: " + cur.getString(5) + "\n");
            sb.append("Typ paliva: " + cur.getString(4) + "\n");
            sb.append("Stav tachometra: " + cur.getString(2) + "\n");
            sb.append("Natankované litre: " + cur.getString(0) + "\n");
            sb.append("Zaplatená suma: " + cur.getString(1) + "\n\n\n");
        }

        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setCancelable(true);
        adb.setTitle("Tankovania");
        adb.setMessage(sb.toString());
        adb.show();
    }

    public void onClkMaintInp(View v) {
        Spinner mainttype = findViewById(R.id.typUdrzbyUdrzba);
        EditText price = findViewById(R.id.zaplatenaSumaUdrzba);
        EditText odometer = findViewById(R.id.stavTachometraUdrzba);
        EditText date = findViewById(R.id.datumUdrzba);
        EditText notes = findViewById(R.id.poznamkyUdrzba);
        EditText service = findViewById(R.id.servisUdrzba);
        String sMainttype = mainttype.getSelectedItem().toString();
        String sPrice = price.getText().toString();
        String sOdometer = odometer.getText().toString();
        String sDate = date.getText().toString();
        String sNotes = notes.getText().toString();
        String sService = service.getText().toString();
        if (sPrice.isEmpty() || sOdometer.isEmpty() || sDate.isEmpty()){
            Toast.makeText(this, "Prázdne pole", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean inserted = db.addMaint(sMainttype, sPrice, sOdometer, sDate, sNotes, sService);
        if (inserted) {
            Toast.makeText(MainActivity.this, "Údaje boli vložené", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClkMaintOut(View v) {
        Cursor cursor = db.getFromDB("Udrzba");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Žiaden záznam", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer sb = new StringBuffer();
        while (cursor.moveToNext()) {
            sb.append("Dátum: " + cursor.getString(3) + "\n");
            sb.append("Miesto servisovania: " + cursor.getString(5) + "\n");
            sb.append("Typ údržby: " + cursor.getString(0) + "\n");
            sb.append("Stav tachometra: " + cursor.getString(2) + "\n");
            sb.append("Zaplatená suma: " + cursor.getString(1) + "\n");
            sb.append("Poznámky: " + cursor.getString(4) + "\n\n\n");
        }

        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setCancelable(true);
        adb.setTitle("Údržby");
        adb.setMessage(sb.toString());
        adb.show();
    }

    public void onClkInspInp(View v) {
        Spinner insptype = findViewById(R.id.typKontrolySTK);
        EditText price = findViewById(R.id.zaplatenaSumaSTK);
        EditText odometer = findViewById(R.id.stavTachometraSTK);
        EditText date = findViewById(R.id.datumSTK);
        CheckBox success = findViewById(R.id.uspesnostSTK);
        EditText station = findViewById(R.id.miestoKontrolySTK);
        String sInsptype = insptype.getSelectedItem().toString();
        String sPrice = price.getText().toString();
        String sOdometer = odometer.getText().toString();
        String sDate = date.getText().toString();
        String sSuccess;
        if (success.isChecked())
            sSuccess = "úspešná";
        else
            sSuccess = "neúspešná";
        String sStation = station.getText().toString();
        if (sPrice.isEmpty() || sOdometer.isEmpty() || sDate.isEmpty()){
            Toast.makeText(this, "Prázdne pole", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean inserted = db.addInsp(sInsptype, sPrice, sOdometer, sDate, sSuccess, sStation);
        if (inserted) {
            Toast.makeText(MainActivity.this, "Údaje boli vložené", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClkInspOut(View v) {
        Cursor cursor = db.getFromDB("STK");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Žiaden záznam", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer sb = new StringBuffer();
        while (cursor.moveToNext()) {
            sb.append("Dátum: " + cursor.getString(3) + "\n");
            sb.append("Miesto kontroly: " + cursor.getString(5) + "\n");
            sb.append("Typ kontroly: " + cursor.getString(0) + "\n");
            sb.append("Stav tachometra: " + cursor.getString(2) + "\n");
            sb.append("Zaplatená suma: " + cursor.getString(1) + "\n");
            sb.append("Úspešnosť: " + cursor.getString(4) + "\n\n\n");
        }

        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setCancelable(true);
        adb.setTitle("STK");
        adb.setMessage(sb.toString());
        adb.show();
    }

    public void onClkRepairInp(View v) {
        EditText repairtype = findViewById(R.id.typOpravyOprava);
        EditText price = findViewById(R.id.zaplatenaSumaOprava);
        EditText odometer = findViewById(R.id.stavTachometraOprava);
        EditText date = findViewById(R.id.datumOprava);
        EditText notes = findViewById(R.id.poznamkyOprava);
        EditText service = findViewById(R.id.servisOprava);
        String sRepairtype = repairtype.getText().toString();
        String sPrice = price.getText().toString();
        String sOdometer = odometer.getText().toString();
        String sDate = date.getText().toString();
        String sNotes = notes.getText().toString();
        String sService = service.getText().toString();
        if (sRepairtype.isEmpty() || sPrice.isEmpty() || sOdometer.isEmpty() || sDate.isEmpty()){
            Toast.makeText(this, "Prázdne pole", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean inserted = db.pridajOpravy(sRepairtype, sPrice, sOdometer, sDate, sNotes, sService);
        if (inserted) {
            Toast.makeText(MainActivity.this, "Údaje boli vložené", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClkRepairOut(View v) {
        Cursor cursor = db.getFromDB("Opravy");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Žiaden záznam", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer sb = new StringBuffer();
        while (cursor.moveToNext()) {
            sb.append("Dátum: " + cursor.getString(3) + "\n");
            sb.append("Miesto servisovania: " + cursor.getString(5) + "\n");
            sb.append("Typ opravy: " + cursor.getString(0) + "\n");
            sb.append("Stav tachometra: " + cursor.getString(2) + "\n");
            sb.append("Zaplatená suma: " + cursor.getString(1) + "\n");
            sb.append("Poznámky: " + cursor.getString(4) + "\n\n\n");
        }

        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setCancelable(true);
        adb.setTitle("Opravy");
        adb.setMessage(sb.toString());
        adb.show();
    }

    public void onClkRideInp(View v) {
        EditText trip = findViewById(R.id.trasaJazda);
        EditText odometerPrev = findViewById(R.id.stavTachometraStJazda);
        EditText odometerCur = findViewById(R.id.stavTachometraEndJazda);
        EditText date = findViewById(R.id.datumJazda);
        EditText notes = findViewById(R.id.poznamkyJazda);
        String sTrip = trip.getText().toString();
        String sOdometerPrev = odometerPrev.getText().toString();
        String sOdometerCur = odometerCur.getText().toString();
        String sDate = date.getText().toString();
        String sNotes = notes.getText().toString();
        if (sTrip.isEmpty() || sOdometerPrev.isEmpty() || sOdometerCur.isEmpty() || sDate.isEmpty()){
            Toast.makeText(this, "Prázdne pole", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean inserted = db.addTrip(sTrip, sOdometerPrev, sOdometerCur, sDate, sNotes);
        if (inserted) {
            Toast.makeText(MainActivity.this, "Údaje boli vložené", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClkRideOut(View v) {
        Cursor cursor = db.getFromDB("Jazdy");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Žiaden záznam", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer sb = new StringBuffer();
        while (cursor.moveToNext()) {
            sb.append("Dátum: " + cursor.getString(3) + "\n");
            sb.append("Trasa: " + cursor.getString(0) + "\n");
            sb.append("Stav tachometra pred jazdou: " + cursor.getString(1) + "\n");
            sb.append("Stav tachometra po jazde: " + cursor.getString(2) + "\n");
            sb.append("Poznámky: " + cursor.getString(4) + "\n\n\n");
        }

        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setCancelable(true);
        adb.setTitle("Jazdy");
        adb.setMessage(sb.toString());
        adb.show();
    }

    public DBHelper getDBHelper() {
        return db;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}