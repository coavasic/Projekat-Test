package com.example.vasic.projekat;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vasic.projekat.Adapters.ListAuctionsAdapter;
import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.Tools.Mokap;

import java.util.ArrayList;

public class AuctionsActivity extends AppCompatActivity {


    private ArrayList<Auction> auctions;
    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerPane;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auctions);

        auctions = (ArrayList<Auction>) getIntent().getSerializableExtra("auctions");

        ListView listView = (ListView) findViewById(R.id.all_auctions_list_view);
        ListAuctionsAdapter adapter = new ListAuctionsAdapter(AuctionsActivity.this, auctions);
        listView.setAdapter(adapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_items);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();



        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar, R.string.drawer_open, R.string.drawer_close){
            public void onDrawerClosed(View view) {

                super.onDrawerClosed(view);

            }

            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerToggle.syncState();



        mDrawerLayout.addDrawerListener(mDrawerToggle);









//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent = new Intent(AuctionsActivity.this, ItemActivity.class);
//                Item item = (Item) parent.getItemAtPosition(position);
//                intent.putExtra("abc",item);
//                startActivity(intent);
//
//
//            }
//        });










    }





    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }










    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }




    public void OnSettingsClick(MenuItem item) {


        Intent settings = new Intent(AuctionsActivity.this, PrefActivity.class);
        startActivity(settings);

    }

    public void onExitClick(MenuItem item) {
        finish();

    }


    public void onAllItemsClick(MenuItem item){

        Intent allActivity = new Intent(AuctionsActivity.this, ItemsActivity.class);
        startActivity(allActivity);
        finish();
    }


    public void onAllAutctionsClick(MenuItem item){


//        items = Mokap.getItems();
//        for(Item i:items){
//            for(Auction a:i.getAutions()){
//                auctions.add(a);
//            }
//        }



        Intent allAuctions = new Intent(AuctionsActivity.this, AuctionsActivity.class);
        allAuctions.putExtra("auctions",auctions);
        startActivity(allAuctions);











    }



}

