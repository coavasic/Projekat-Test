package com.example.vasic.projekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.vasic.projekat.Adapters.ListAuctionsAdapter;
import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.Model.User;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.ItemDao;
import com.example.vasic.projekat.dao.UserDao;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllAuctionActivity extends AppCompatActivity {

    private long itemId;
    private Item item;
    private ArrayList<Auction> auctions = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_auction);
        Intent intent = getIntent();
        itemId = intent.getLongExtra("item_id",1);
        item = findItemById(itemId);

        auctions = findInactiveAuctions(item);
        listView = (ListView)findViewById(R.id.inactive_auction_list_view);
        ListAuctionsAdapter adapter = new ListAuctionsAdapter(this,auctions);

        listView.setAdapter(adapter);












    }




    private Item findItemById(long id){
        Item item = new Item();

        DatabaseHelper dh = new DatabaseHelper(AllAuctionActivity.this);

        try {
            ItemDao itemDao= new ItemDao(dh.getConnectionSource());
            item = itemDao.queryForId(id);



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    private ArrayList<Auction> findInactiveAuctions(Item item){
        ArrayList<Auction> auctions1 = new ArrayList<>();


        for(Auction auction: item.getAutions()){
            if (!auction.isActive())
                auctions1.add(auction);

        }

        return auctions1;


    }

}
