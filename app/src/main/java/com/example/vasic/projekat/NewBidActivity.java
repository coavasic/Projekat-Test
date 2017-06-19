package com.example.vasic.projekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Bid;
import com.example.vasic.projekat.Model.User;
import com.example.vasic.projekat.dao.AuctionDao;
import com.example.vasic.projekat.dao.BidDao;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.UserDao;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.Date;

public class NewBidActivity extends AppCompatActivity {

    EditText priceEditText;
    Button newBid;
    TextView currentPriceTextView;
    Bid bid = new Bid();
    DatabaseHelper dh = new DatabaseHelper(NewBidActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bid);

        priceEditText = (EditText) findViewById(R.id.bid_price_edit_text);
        newBid = (Button) findViewById(R.id.new_bid_button);

        Intent lastInent = getIntent();

        long auctionId = lastInent.getLongExtra("auction_id",-1);
        long userId = lastInent.getLongExtra("user_id", -1);
        final double currentPrice = lastInent.getDoubleExtra("current_price",-1);
        currentPriceTextView = (TextView)findViewById(R.id.new_bid_current_price);
        currentPriceTextView.setText(String.valueOf(currentPrice));
        final User user = findUserById(userId);
        final Auction auction = findAuctionById(auctionId);

        newBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double price = Double.parseDouble(priceEditText.getText().toString());
                if(price > currentPrice){
                    bid.setAuction(auction);
                    Toast.makeText(NewBidActivity.this, user.getName(), Toast.LENGTH_LONG).show();
                    bid.setUser(user);
                    bid.setDataTime(new Date());
                    bid.setPrice(price);
                    //auction.getBids().add(bid);


                    try {
                        BidDao bidDao = new BidDao(dh.getConnectionSource());
                        UserDao userDao = new UserDao(dh.getConnectionSource());
                        bidDao.createIfNotExists(bid);
                        userDao.createIfNotExists(user);
                        Intent nazad = new Intent();
                        nazad.putExtra("new_current_price",price);
                        setResult(RESULT_OK, nazad);
                        finish();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else{






                    Toast.makeText(NewBidActivity.this,"Cena mora biti veca od trenutne cene", Toast.LENGTH_LONG).show();






                }


            }
        });
    }


    private User findUserById(long id){
        User user = new User();

        DatabaseHelper dh = new DatabaseHelper(NewBidActivity.this);

        try {
            UserDao userDao = new UserDao(dh.getConnectionSource());
            user = userDao.queryForId(id);



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private Auction findAuctionById(long id){
        Auction auction= new Auction();

        DatabaseHelper dh = new DatabaseHelper(NewBidActivity.this);

        try {
            AuctionDao auctionDaoc= new AuctionDao(dh.getConnectionSource());
            auction = auctionDaoc.queryForId(id);



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auction;
    }



}
