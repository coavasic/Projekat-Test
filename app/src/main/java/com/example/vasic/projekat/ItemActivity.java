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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vasic.projekat.Adapters.ViewPagerAdapter;
import com.example.vasic.projekat.Fragments.BidListFragment;
import com.example.vasic.projekat.Fragments.ItemAuctionsFragment;
import com.example.vasic.projekat.Fragments.ItemInfoFragment;
import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.Model.User;
import com.example.vasic.projekat.Tools.Mokap;
import com.example.vasic.projekat.dao.AuctionDao;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.ItemDao;
import com.example.vasic.projekat.dao.UserDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public Item item;
    private TextView usernameTextView;
    private ImageView userPicture;

    private Long currentUserId;
    private User currentUser;


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

        Intent intent = getIntent();
        currentUserId = intent.getLongExtra("current_user",-1);

        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer_pane_items);
        //navigationView.setNavigationItemSelectedListener(ItemsActivity.this);
        View header=navigationView.getHeaderView(0);
        currentUser = findUserById(currentUserId);
        usernameTextView = (TextView)header.findViewById(R.id.userName);
        userPicture = (ImageView)header.findViewById(R.id.avatar);
        usernameTextView.setText(currentUser.getEmail());
        userPicture.setImageResource(Integer.valueOf(currentUser.getPicture()));



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

    public void onLogoutClick(MenuItem item) {

        Intent intent = new Intent(ItemActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();



    }


    public void onMyAccountClick(MenuItem item) {

        Intent intent = new Intent(this,MyAccountActivity.class);
        intent.putExtra("current_user",currentUserId);
        startActivity(intent);

    }


    public void onAllItemsClick(MenuItem item) {

        Intent allActivity = new Intent(ItemActivity.this, ItemsActivity.class);
        allActivity.putExtra("current_user",currentUserId);
        startActivity(allActivity);
        finish();
    }





    private User findUserById(long id){
        User user = new User();

        DatabaseHelper dh = new DatabaseHelper(ItemActivity.this);

        try {
            UserDao userDao = new UserDao(dh.getConnectionSource());
            user = userDao.queryForId(id);



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


}
