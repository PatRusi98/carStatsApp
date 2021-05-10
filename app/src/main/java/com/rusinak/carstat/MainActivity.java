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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    public FloatingActionButton floatingActionButton;
    private int idNotif = 0;

    Databaza databaza = new Databaza(this);
    Kalkulacka kalkulacka = new Kalkulacka();

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
                R.id.nav_tankovanie, R.id.nav_udzba, R.id.nav_stk, R.id.nav_opravy, R.id.nav_jazdy, R.id.nav_statistiky, R.id.nav_dashboard, R.id.nav_nastavenia)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        generujUpozornenie();
    }

    public void generujUpozornenie() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.HOUR_OF_DAY, 19);
        calendar.set(calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);

        Intent intentTank = new Intent(this, Upozornenia.class);
        PendingIntent pendingIntentTank = PendingIntent.getBroadcast(this, 0, intentTank, 0);
        AlarmManager alarmManagerTank = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManagerTank.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntentTank);
        notifChannel();
    }

    public void notifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Moja notifikacia";
            String description = "tu napisem info ";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("upozornenie", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void onClkUlozTankovanie(View v) {
        EditText natankovaneLitre = findViewById(R.id.natankovaneLitreTankovanie);
        EditText zaplatenaSuma = findViewById(R.id.zaplatenaSumaTankovanie);
        EditText stavTachometra = findViewById(R.id.stavTachometraTankovanie);
        EditText datum = findViewById(R.id.datumTankovanie);
        Spinner typPaliva = findViewById(R.id.typPalivaTankovanie);
        EditText miestoTankovania = findViewById(R.id.miestoTankovaniaTankovanie);
        String litre = natankovaneLitre.getText().toString();
        String suma = zaplatenaSuma.getText().toString();
        String tachometer = stavTachometra.getText().toString();
        String date = datum.getText().toString();
        String palivo = typPaliva.getSelectedItem().toString();
        String miesto = miestoTankovania.getText().toString();
        if (litre.isEmpty() || suma.isEmpty() || tachometer.isEmpty() || date.isEmpty()){
            Toast.makeText(this, "Prázdne pole", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean vlozene = databaza.pridajTankovanie(litre, suma, tachometer, date, palivo, miesto);
        if (vlozene) {
            Toast.makeText(MainActivity.this, "Údaje boli vložené", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClkVypisTankovanie (View v) {
        Cursor cursor = databaza.getFromDB("Tankovanie");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Žiaden záznam", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer sb = new StringBuffer();
        while (cursor.moveToNext()) {
            sb.append("Dátum: " + cursor.getString(3) + "\n");
            sb.append("Miesto tankovania: " + cursor.getString(5) + "\n");
            sb.append("Typ paliva: " + cursor.getString(4) + "\n");
            sb.append("Stav tachometra: " + cursor.getString(2) + "\n");
            sb.append("Natankované litre: " + cursor.getString(0) + "\n");
            sb.append("Zaplatená suma: " + cursor.getString(1) + "\n\n\n");
        }

        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setCancelable(true);
        adb.setTitle("Tankovania");
        adb.setMessage(sb.toString());
        adb.show();
    }

    public void onClkUlozUdrzbu(View v) {
        Spinner typUdrzby = findViewById(R.id.typUdrzbyUdrzba);
        EditText zaplatenaSuma = findViewById(R.id.zaplatenaSumaUdrzba);
        EditText stavTachometra = findViewById(R.id.stavTachometraUdrzba);
        EditText datum = findViewById(R.id.datumUdrzba);
        EditText poznamky = findViewById(R.id.poznamkyUdrzba);
        EditText servis = findViewById(R.id.servisUdrzba);
        String udrzba = typUdrzby.getSelectedItem().toString();
        String suma = zaplatenaSuma.getText().toString();
        String tachometer = stavTachometra.getText().toString();
        String date = datum.getText().toString();
        String poznamka = poznamky.getText().toString();
        String miesto = servis.getText().toString();
        if (suma.isEmpty() || tachometer.isEmpty() || date.isEmpty()){
            Toast.makeText(this, "Prázdne pole", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean vlozene = databaza.pridajUdrzbu(udrzba, suma, tachometer, date, poznamka, miesto);
        if (vlozene) {
            Toast.makeText(MainActivity.this, "Údaje boli vložené", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClkVypisUdrzbu (View v) {
        Cursor cursor = databaza.getFromDB("Udrzba");
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

    public void onClkUlozSTK(View v) {
        Spinner stk = findViewById(R.id.typKontrolySTK);
        EditText zaplatenaSuma = findViewById(R.id.zaplatenaSumaSTK);
        EditText stavTachometra = findViewById(R.id.stavTachometraSTK);
        EditText datum = findViewById(R.id.datumSTK);
        CheckBox uspesnost = findViewById(R.id.uspesnostSTK);
        EditText stredisko = findViewById(R.id.miestoKontrolySTK);
        String kontrola = stk.getSelectedItem().toString();
        String suma = zaplatenaSuma.getText().toString();
        String tachometer = stavTachometra.getText().toString();
        String date = datum.getText().toString();
        String uspesna;
        if (uspesnost.isChecked())
            uspesna = "úspešná";
        else
            uspesna = "neúspešná";
        String miesto = stredisko.getText().toString();
        if (suma.isEmpty() || tachometer.isEmpty() || date.isEmpty()){
            Toast.makeText(this, "Prázdne pole", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean vlozene = databaza.pridajSTK(kontrola, suma, tachometer, date, uspesna, miesto);
        if (vlozene) {
            Toast.makeText(MainActivity.this, "Údaje boli vložené", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClkVypisSTK (View v) {
        Cursor cursor = databaza.getFromDB("STK");
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

    public void onClkUlozOpravu(View v) {
        EditText typOpravy = findViewById(R.id.typOpravyOprava);
        EditText zaplatenaSuma = findViewById(R.id.zaplatenaSumaOprava);
        EditText stavTachometra = findViewById(R.id.stavTachometraOprava);
        EditText datum = findViewById(R.id.datumOprava);
        EditText poznamky = findViewById(R.id.poznamkyOprava);
        EditText servis = findViewById(R.id.servisOprava);
        String oprava = typOpravy.getText().toString();
        String suma = zaplatenaSuma.getText().toString();
        String tachometer = stavTachometra.getText().toString();
        String date = datum.getText().toString();
        String poznamka = poznamky.getText().toString();
        String miesto = servis.getText().toString();
        if (oprava.isEmpty() || suma.isEmpty() || tachometer.isEmpty() || date.isEmpty()){
            Toast.makeText(this, "Prázdne pole", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean vlozene = databaza.pridajOpravy(oprava, suma, tachometer, date, poznamka, miesto);
        if (vlozene) {
            Toast.makeText(MainActivity.this, "Údaje boli vložené", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClkVypisOpravu (View v) {
        Cursor cursor = databaza.getFromDB("Opravy");
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

    public void onClkUlozJazdu(View v) {
        EditText trasa = findViewById(R.id.trasaJazda);
        EditText stavTachometraSt = findViewById(R.id.stavTachometraStJazda);
        EditText stavTachometraEnd = findViewById(R.id.stavTachometraEndJazda);
        EditText datum = findViewById(R.id.datumJazda);
        EditText poznamky = findViewById(R.id.poznamkyJazda);
        String cesta = trasa.getText().toString();
        String tachometer1 = stavTachometraSt.getText().toString();
        String tachometer2 = stavTachometraEnd.getText().toString();
        String date = datum.getText().toString();
        String poznamka = poznamky.getText().toString();
        if (cesta.isEmpty() || tachometer1.isEmpty() || tachometer2.isEmpty() || date.isEmpty()){
            Toast.makeText(this, "Prázdne pole", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean vlozene = databaza.pridajJazdy(cesta, tachometer1, tachometer2, date, poznamka);
        if (vlozene) {
            Toast.makeText(MainActivity.this, "Údaje boli vložené", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClkVypisJazdu (View v) {
        Cursor cursor = databaza.getFromDB("Jazdy");
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

    public Databaza getDatabaza() {
        return databaza;
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