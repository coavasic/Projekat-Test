package com.example.vasic.projekat;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.vasic.projekat.Adapters.ViewPagerAdapter;
import com.example.vasic.projekat.Fragments.BidListFragment;
import com.example.vasic.projekat.Fragments.ItemAuctionsFragment;
import com.example.vasic.projekat.Fragments.ItemInfoFragment;
import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.Tools.Mokap;
import com.example.vasic.projekat.dao.AuctionDao;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.ItemDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public Item item;


    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerPane;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<Object> auctions = new ArrayList<>();
    private ArrayList<Item> items1 = new ArrayList<Item>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_items);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();


        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {

                super.onDrawerClosed(view);

            }

            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerToggle.syncState();


        mDrawerLayout.addDrawerListener(mDrawerToggle);


        tabLayout = (TabLayout) findViewById(R.id.item_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.item_view_pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new ItemInfoFragment(), "Detalji");
        viewPagerAdapter.addFragments(new ItemAuctionsFragment(), "Aukcija");
        viewPagerAdapter.addFragments(new BidListFragment(),"Ponude");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }


    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void OnSettingsClick(MenuItem item) {


        Intent settings = new Intent(ItemActivity.this, PrefActivity.class);
        startActivity(settings);

    }

    public void onExitClick(MenuItem item) {
        finish();

    }


    public void onAllItemsClick(MenuItem item) {

        Intent allActivity = new Intent(ItemActivity.this, ItemsActivity.class);
        startActivity(allActivity);
        finish();
    }

    public void onAllAutctionsClick(MenuItem item) {


//        items1 = Mokap.items;
//        for (Item i : items1) {
//            for (Object a : i.getAutions()) {
//                auctions.add(a);
//            }
//        }

//
//        Intent allAuctions = new Intent(ItemActivity.this, AuctionsActivity.class);
//        allAuctions.putExtra("auctions", auctions);
//        startActivity(allAuctions);


    }

    public void onInitDatabaseClick(MenuItem item) {

        DatabaseHelper dh = new DatabaseHelper(ItemActivity.this);

        try {
            ItemDao itemDao = new ItemDao(dh.getConnectionSource());
            AuctionDao auctionDao = new AuctionDao(dh.getConnectionSource());

            for(Item item1 : Mokap.items){
                int resoult = itemDao.create(item1);

                if(resoult == 1){
                    for(Auction auction : item1.getAutions()){

                        Log.i("Auction Error", auction.getEndDate() + "  " + auction.getStartPrice());
                        auctionDao.create(auction);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        Intent intent = new Intent(ItemActivity.this,ItemsActivity.class);
        startActivity(intent);


    }

}
