package com.example.vasic.projekat.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.R;
import com.example.vasic.projekat.Tools.Mokap;
import com.example.vasic.projekat.dao.AuctionDao;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.ItemDao;

import java.sql.SQLException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemInfoFragment extends Fragment {

    TextView nameText, descText, nmbrOfAuction;
    ImageView itemImage;
    Button bidBtn;

    Item item;



    public ItemInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     //  Bundle b = getActivity().getIntent().getExtras();

        View view = inflater.inflate(R.layout.fragment_item_info, container, false);





        //TextView nameText;
        //TextView descText;
        nameText = (TextView) view.findViewById(R.id.item_name_text_view);
        descText = (TextView) view.findViewById(R.id.item_desc_text_view);
        itemImage =(ImageView) view.findViewById(R.id.item_image_view);
        bidBtn = (Button) view.findViewById(R.id.bid_button);
        long itemId = getActivity().getIntent().getLongExtra("item_id",-1);

        item = getItemById(itemId);


        getActivity().setTitle(item.getName());


        nmbrOfAuction = (TextView) view.findViewById(R.id.number_of_auction);



        nameText.setText(item.getName());
        descText.setText("B");
      //  nmbrOfAuction.append(String.valueOf(item.getAutions().size()));
        itemImage.setImageResource(item.getPicture());

        bidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //sqlKurac();

            }
        });

        return view;
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
