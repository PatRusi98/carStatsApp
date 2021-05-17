package com.rusinak.carstat;


import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

/**
 *
 * MainActivity class
 *
 */
public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHelper(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_refuel, R.id.nav_maint, R.id.nav_insp, R.id.nav_repairs, R.id.nav_trips, R.id.nav_stats, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        notificationGenerator();
    }

    /**
     *
     * calls notification channel at exact time interval
     *
     */
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

    /**
     *
     * creates notification channel
     *
     */
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

    /**
     *
     * on click listener for "save" button, inserts values from user input into database table
     *
     * @param v
     */
    public void onClkRefuellingInp(View v) {
        EditText litres = findViewById(R.id.refuelLitres);
        EditText price = findViewById(R.id.refuelPrice);
        EditText odometer = findViewById(R.id.refuelOdo);
        EditText date = findViewById(R.id.refuelDate);
        Spinner fueltype = findViewById(R.id.refuelType);
        EditText place = findViewById(R.id.refuelPlace);
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

    /**
     *
     * on click listener for "list" button, puts values from database table into string
     * and creates alert dialog with those values
     *
     * @param v
     */
    public void onClkRefuellingOut(View v) {
        Cursor cur = db.getFromDB("Refuelling");
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

    /**
     *
     * on click listener for "save" button, inserts values from user input into database table
     *
     * @param v
     */
    public void onClkMaintInp(View v) {
        Spinner mainttype = findViewById(R.id.maintType);
        EditText price = findViewById(R.id.maintPrice);
        EditText odometer = findViewById(R.id.maintOdo);
        EditText date = findViewById(R.id.maintDate);
        EditText notes = findViewById(R.id.maintNotes);
        EditText service = findViewById(R.id.maintService);
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

    /**
     *
     * on click listener for "list" button, puts values from database table into string
     * and creates alert dialog with those values
     *
     * @param v
     */
    public void onClkMaintOut(View v) {
        Cursor cursor = db.getFromDB("Maintainance");
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

    /**
     *
     * on click listener for "save" button, inserts values from user input into database table
     *
     * @param v
     */
    public void onClkInspInp(View v) {
        Spinner insptype = findViewById(R.id.inspType);
        EditText price = findViewById(R.id.inspPrice);
        EditText odometer = findViewById(R.id.inspOdo);
        EditText date = findViewById(R.id.inspDate);
        CheckBox success = findViewById(R.id.inspSuccess);
        EditText station = findViewById(R.id.inspStation);
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

    /**
     *
     * on click listener for "list" button, puts values from database table into string
     * and creates alert dialog with those values
     *
     * @param v
     */
    public void onClkInspOut(View v) {
        Cursor cursor = db.getFromDB("Inspection");
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

    /**
     *
     * on click listener for "save" button, inserts values from user input into database table
     *
     * @param v
     */
    public void onClkRepairInp(View v) {
        EditText repairtype = findViewById(R.id.repairType);
        EditText price = findViewById(R.id.repairPrice);
        EditText odometer = findViewById(R.id.repairOdo);
        EditText date = findViewById(R.id.repairDate);
        EditText notes = findViewById(R.id.repairNotes);
        EditText service = findViewById(R.id.repairService);
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

    /**
     *
     * on click listener for "list" button, puts values from database table into string
     * and creates alert dialog with those values
     *
     * @param v
     */
    public void onClkRepairOut(View v) {
        Cursor cursor = db.getFromDB("Repairs");
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

    /**
     *
     * on click listener for "save" button, inserts values from user input into database table
     *
     * @param v
     */
    public void onClkRideInp(View v) {
        EditText trip = findViewById(R.id.tripRoute);
        EditText odometerPrev = findViewById(R.id.tripOdoSt);
        EditText odometerCur = findViewById(R.id.tripOdoFin);
        EditText date = findViewById(R.id.tripDate);
        EditText notes = findViewById(R.id.tripNotes);
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

    /**
     *
     * on click listener for "list" button, puts values from database table into string
     * and creates alert dialog with those values
     *
     * @param v
     */
    public void onClkRideOut(View v) {
        Cursor cursor = db.getFromDB("Trips");
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

    public void onClkTableDelete(View v)
    {
        db.deleteFromDB("Refuelling");
        db.deleteFromDB("Maintainance");
        db.deleteFromDB("Inspection");
        db.deleteFromDB("Repairs");
        db.deleteFromDB("Trips");
        db.deleteFromDB("Settings");
    }

    public void onClkCarSet(View v)
    {
        db.deleteFromDB("Settings");
        EditText brand = findViewById(R.id.carBrand);
        EditText model = findViewById(R.id.carModel);
        String sBrand = brand.getText().toString();
        String sModel = model.getText().toString();
        if (sBrand.isEmpty() || sModel.isEmpty()){
            Toast.makeText(this, "Prázdne pole", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean inserted = db.addSetting(sBrand, sModel);
        if (inserted) {
            Toast.makeText(MainActivity.this, "Údaje boli vložené", Toast.LENGTH_SHORT).show();
        }

        setHeader();
    }

    public void setHeader(){
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);

        TextView carBrandModel = (TextView) findViewById(R.id.carModelBrand);

        Cursor cur = db.getFromDB("Settings");
        if (cur.getCount() == 0) {
            carBrandModel.setText(" ");
        } else {
            cur.moveToLast();

            String abrand = cur.getString(0);
            String amodel = cur.getString(1);

            carBrandModel.setText(abrand + " " + amodel);
        }
    }

    public void onClkSetHeader(View v)
    {
        setHeader();
    }

    /**
     *
     * DBHelper instance getter
     *
     * @return instance of DBHelper
     */
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