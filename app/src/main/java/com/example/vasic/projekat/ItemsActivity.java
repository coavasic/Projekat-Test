package com.example.vasic.projekat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasic.projekat.Adapters.ListItemsAdapter;
import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Bid;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.Model.User;
import com.example.vasic.projekat.Tools.Mokap;
import com.example.vasic.projekat.dao.AuctionDao;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.ItemDao;
import com.example.vasic.projekat.dao.UserDao;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {





    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerPane;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<Auction> auctions = new ArrayList<>();
    private ArrayList<Item> items1 = new ArrayList<Item>();
    private List<Item> items;
    ListView listView;
    private TextView usernameTextView;
    private ImageView userPicture;

    private Long currentUserId;
    private User currentUser;

    private static int notificationID = 1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        ArrayList<Item> listItems = new ArrayList<Item>();
        listView = (ListView) findViewById(R.id.items_list_view);
        Intent intent = getIntent();
       currentUserId = intent.getLongExtra("current_user",-1);
        ListItemsAdapter adapter = new ListItemsAdapter(ItemsActivity.this,sqlInit());
        listView.setAdapter(adapter);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_items);



        currentUser = findUserById(currentUserId);



        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer_pane_items);
        //navigationView.setNavigationItemSelectedListener(ItemsActivity.this);
        View header=navigationView.getHeaderView(0);

        usernameTextView = (TextView)header.findViewById(R.id.userName);
        userPicture = (ImageView)header.findViewById(R.id.avatar);
        usernameTextView.setText(currentUser.getEmail());
        userPicture.setImageResource(Integer.valueOf(currentUser.getPicture()));

        checkBids();










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









        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ItemsActivity.this, ItemActivity.class);
                Item item = (Item) parent.getItemAtPosition(position);
                intent.putExtra("item_id",item.getId());
                intent.putExtra("current_user",currentUserId);
                startActivity(intent);


            }
        });







    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        listView.invalidateViews();

    }

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    public Auction getAuctionById(long itemId){
        Auction auction = new Auction();
        DatabaseHelper dh = new DatabaseHelper(ItemsActivity.this);

        try {
            AuctionDao auctionDao = new AuctionDao(dh.getConnectionSource());
            auction = auctionDao.queryForId(itemId);


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return auction;

    }


    public void checkBids(){

        List<Auction> auctions123 = new ArrayList<Auction>();
        for (Bid bid : currentUser.getBids()){

            Auction auction123 = getAuctionById(bid.getAuction().getId());
            if(!auctions123.contains(auction123)){


                auctions123.add(auction123);
            }

        }


        double sta = 0;


        for(Auction auction: auctions123){

            User userNeki = isMax(auction,currentUser);
           if(userNeki != null)
               createNotification(userNeki,auction);
    }





    }





    public User isMax(Auction auction, User user){
        List<Double> userPrices = new ArrayList<>();
        double maxPriceByAuction = getCurrentPrice(auction);


        for(Bid bid :user.getBids()){
            Auction auction1  = getAuctionById(bid.getAuction().getId());
            if(auction1.getId() == auction.getId()){

                userPrices.add(bid.getPrice());
               // Toast.makeText(this, String.valueOf(bid.getPrice()), Toast.LENGTH_SHORT).show();
            }
        }

        double maxPrice = 0;

        if (!userPrices.isEmpty()){
            maxPrice = Collections.max(userPrices);
        }

        if(maxPrice == 0){

            return null;
        }
        Bid maxBid = findMaxBid(auction,maxPriceByAuction);

        //Toast.makeText(this, String.valueOf(maxPriceByAuction), Toast.LENGTH_SHORT).show();

        if(maxPrice < maxPriceByAuction){

            return findUserById(maxBid.getUser().getId());
        }else{

            return null;
        }














    }


    private void createNotification(User user, Auction auction){


        NotificationManager mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setSmallIcon(R.mipmap.ic_attach_money_black_24dp);
        mBuilder.setContentTitle("Vasa ponuda nije najveca");
        mBuilder.setContentText("Korisnik " + user.getName() +" je ponudio vise od vas na aukciji rb.: "+String.valueOf(auction.getId()));
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("current_user",currentUserId);
        intent.putExtra("item_id",auction.getItem().getId());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);


        mNotificationManager.notify(notificationID, mBuilder.build());


    }

    private Bid findMaxBid(Auction auction, double price){

        for(Bid bid:auction.getBids()){
            if (bid.getPrice()==price){

                return bid;
            }
        }

        return null;

    }


    private double getCurrentPrice(Auction auction){

        ArrayList<Double> prices = new ArrayList<Double>();

        if (auction.getBids().isEmpty()){

            return auction.getStartPrice();
        }
        else{

            for(Bid bid: auction.getBids()){
                prices.add(bid.getPrice());
            }

            return Collections.max(prices);
        }
    }











    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }









    public void OnSettingsClick(MenuItem item) {


        Intent settings = new Intent(ItemsActivity.this, PrefActivity.class);
        startActivity(settings);

    }

    public void onExitClick(MenuItem item) {
        finish();



    }


    public void onAllItemsClick(MenuItem item){

        Intent allActivity = new Intent(ItemsActivity.this, ItemsActivity.class);
        allActivity.putExtra("current_user",currentUserId);
        startActivity(allActivity);
        finish();
    }








    public List<Item> sqlInit(){

        long firstId = 0;


        DatabaseHelper dh = new DatabaseHelper(ItemsActivity.this);

        try {


            ItemDao itemDao = new ItemDao(dh.getConnectionSource());
            AuctionDao auctionDao = new AuctionDao(dh.getConnectionSource());




            items = itemDao.queryForAll();
            for (Item item : items){
                Log.i("Script",item.getName() + "   " + item.getDescription() + "  ");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    private User findUserById(long id){
        User user = new User();

        DatabaseHelper dh = new DatabaseHelper(ItemsActivity.this);

        try {
            UserDao userDao = new UserDao(dh.getConnectionSource());
            user = userDao.queryForId(id);



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    public void onLogoutClick(MenuItem item) {

        Intent intent = new Intent(ItemsActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();


    }
}
