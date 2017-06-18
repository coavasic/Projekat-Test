package com.example.vasic.projekat.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vasic.projekat.Adapters.ListBidAdapter;
import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Bid;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.R;
import com.example.vasic.projekat.dao.BidDao;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.ItemDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidListFragment extends Fragment {

    Item item;
    Auction auction;
    ListView listView;


    public BidListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bid_list, container, false);

        long itemId = getActivity().getIntent().getLongExtra("item_id",-1);

        item = getItemById(itemId);

        auction = getActiveAuction(item);

        List<Bid> bids= new ArrayList<>(auction.getBids());


        listView = (ListView) view.findViewById(R.id.bids_list_view);
        ListBidAdapter adapter = new ListBidAdapter(getActivity(),bids);
        listView.setAdapter(adapter);




        return view;
    }


    private Auction getActiveAuction(Item item){


        for(Auction auction: item.getAutions()){
            if(auction.isActive()){

                return auction;
            }
        }

        return null;
    }

    public Bid getBidById(long id) {


        DatabaseHelper dh = new DatabaseHelper(getActivity());
        Bid bid = null;
        try {
            BidDao bidDao = new BidDao(dh.getConnectionSource());
            bid = bidDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bid;

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




}
