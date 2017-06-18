package com.example.vasic.projekat;

import android.content.Intent;
import android.support.design.widget.NavigationView;
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

import com.example.vasic.projekat.Adapters.ListItemsAdapter;
import com.example.vasic.projekat.Model.Auction;
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










    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }


    public void onInitDatabaseClick(MenuItem item) {

        DatabaseHelper dh = new DatabaseHelper(ItemsActivity.this);

        try {
            ItemDao itemDao = new ItemDao(dh.getConnectionSource());
            AuctionDao auctionDao = new AuctionDao(dh.getConnectionSource());
            UserDao userDao = new UserDao(dh.getConnectionSource());

            for(Item item1 : Mokap.items){
                int resoult = itemDao.create(item1);

                if(resoult == 1){
                    for(Auction auction : item1.getAutions()){

                        Log.i("Auction Error", auction.getEndDate() + "  " + auction.getStartPrice());
                        auctionDao.create(auction);
                        userDao.createIfNotExists(auction.getUser());

                    }
                }
            }

            for (User user1: Mokap.users){

                userDao.createIfNotExists(user1);
            }





        } catch (SQLException e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(ItemsActivity.this,ItemsActivity.class);
        startActivity(intent);


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
        startActivity(allActivity);
        finish();
    }


    public void onAllAutctionsClick(MenuItem item){


//        items1 = Mokap.items;
//        for(Item i:items1){
//            for(Auction a:i.getAutions()){
//                auctions.add(a);
//            }
//        }



        Intent allAuctions = new Intent(ItemsActivity.this, AuctionsActivity.class);
        allAuctions.putExtra("auctions",auctions);
        startActivity(allAuctions);









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




}
