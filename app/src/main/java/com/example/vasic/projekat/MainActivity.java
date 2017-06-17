package com.example.vasic.projekat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.*;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.Tools.Mokap;
import com.example.vasic.projekat.dao.AuctionDao;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.ItemDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static boolean active = true;
    private static final String TRAJANJE_UVODNOG_EKRANA = "trajanje_uvodnog_ekrana";
    private static final String PRIKAZ_POCETNOG_EKRANA ="prikaz_pocetnog_ekrana";
    private long firstId = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mokap.initItems();




        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cekaj = sharedPreferences.getBoolean(PRIKAZ_POCETNOG_EKRANA, true);
        String trajanjeS = sharedPreferences.getString(TRAJANJE_UVODNOG_EKRANA,"3000");

        int trajanje = Integer.parseInt(trajanjeS);


        if (cekaj == true) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(active == true){
                    Intent dajDrugiEkran = new Intent(MainActivity.this, ItemsActivity.class);
                    startActivity(dajDrugiEkran);
                        active= false;
                    finish();}
                }
            }, trajanje);


        }
        else{

            Intent dajDrugiEkran = new Intent(MainActivity.this, ItemsActivity.class);
            startActivity(dajDrugiEkran);
            active = false;
            finish();

        }

    }




    public void onStartImgClick(View view) {

        Intent toItemsActivity = new Intent(MainActivity.this,ItemsActivity.class);
        startActivity(toItemsActivity);
        active = false;
        finish();



    }





}
