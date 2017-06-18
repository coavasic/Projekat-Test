package com.example.vasic.projekat.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vasic.projekat.Adapters.ListAuctionsAdapter;
import com.example.vasic.projekat.Adapters.ListBidAdapter;
import com.example.vasic.projekat.ItemActivity;
import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Bid;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.Model.User;
import com.example.vasic.projekat.NewBidActivity;
import com.example.vasic.projekat.R;
import com.example.vasic.projekat.Service.AuctionService;
import com.example.vasic.projekat.Tools.Mokap;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.ItemDao;
import com.example.vasic.projekat.dao.UserDao;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemAuctionsFragment extends Fragment {



    private Item item;
    private Auction auction;
    private User user;
    private TextView startPrice, startDate, endDate, currentPrice, userName;
    private FloatingActionButton fab;
    private User currentUser;
    private Long currentUserId;



    public ItemAuctionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        long itemId = getActivity().getIntent().getLongExtra("item_id",-1);
        currentUserId = getActivity().getIntent().getLongExtra("current_user",-1);
        currentUser = getUserById(currentUserId);

        item = getItemById(itemId);

        auction = getActiveAuction(item);

        user = getUserById(auction.getUser().getId());



        View view = inflater.inflate(R.layout.fragment_item_auctions, container, false);


        startPrice = (TextView) view.findViewById(R.id.auction_start_price_text_view);
        startDate = (TextView) view.findViewById(R.id.auction_start_date_text_view);
        endDate = (TextView) view.findViewById(R.id.auction_end_date_text_view);
        currentPrice = (TextView) view.findViewById(R.id.auction_current_price_text_view);
        userName = (TextView) view.findViewById(R.id.auction_user_text_view);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_bid);

        Format formater = new SimpleDateFormat("dd-MM-yyy");

        String date1 = formater.format(auction.getStartDate());
        String date2 = formater.format(auction.getEndDate());

        startPrice.setText(String.valueOf(auction.getStartPrice()));
        startDate.setText(date1);
        endDate.setText(date2);

        userName.setText(user.getName());
        final double current = getCurrentPrice(auction);
        currentPrice.setText(String.valueOf(current));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int resoult = 1;
                 Intent intent = new Intent(getActivity(), NewBidActivity.class);
                intent.putExtra("auction_id", auction.getId());
                intent.putExtra("current_price", current);
                intent.putExtra("user_id",currentUserId);
                startActivityForResult(intent,resoult);
            }
        });









//        ListView listView = (ListView) view.findViewById(R.id.item_auction_list);
//        ArrayList<Auction> auctions = new ArrayList<Auction>(item.getAutions());
//        ListAuctionsAdapter adapter = new ListAuctionsAdapter(getActivity(),auctions);
//        listView.setAdapter(adapter);






        return view;

    }

    private User getUserById(long i) {

        DatabaseHelper dh = new DatabaseHelper(getActivity());

        try {
            UserDao userDao = new UserDao(dh.getConnectionSource());
            user = userDao.queryForId(i);


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return user;


    }


    public Item getItemById(long itemId){

        DatabaseHelper dh = new DatabaseHelper(getActivity());

        try {
            ItemDao itemDao = new ItemDao(dh.getConnectionSource());
            item = itemDao.queryForId(itemId);


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return item;

    }


    private Auction getActiveAuction(Item item){


        for(Auction auction: item.getAutions()){
            if(auction.isActive()){

                return auction;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            double newCurrentPrice = data.getDoubleExtra("new_current_price", 1);
            currentPrice.setText(String.valueOf(newCurrentPrice));
            Intent intent = new Intent(getActivity(), ItemActivity.class);
            intent.putExtra("item_id",item.getId());
            Intent auctionService = new Intent(getActivity(), AuctionService.class);
            intent.putExtra("current_user",currentUserId);
            auctionService.putExtra("current_price", newCurrentPrice);
            getContext().startService(auctionService);
            startActivity(intent);

        }
    }
}
