package com.example.vasic.projekat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.R;

import org.w3c.dom.Text;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vasic on 5/7/2017.
 */



public class ListAuctionsAdapter extends BaseAdapter {



    private Context mContext;
    public List<Auction> auctions;
    private TextView startDateTextView,endDateTextView,startPriceTextView;

    public ListAuctionsAdapter(Context context, ArrayList<Auction> auctions){
        this.mContext = context;
        this.auctions = auctions;

    }








    @Override
    public int getCount() {
        return auctions.size();

    }

    @Override
    public Object getItem(int position) {
        return auctions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Auction auction = auctions.get(position);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view  = inflater.inflate(R.layout.auctions_list_layout, null);
        } else {

            view = convertView;
        }

        startDateTextView = (TextView)view.findViewById(R.id.inactive_auction_start_date);
        endDateTextView = (TextView) view.findViewById(R.id.inactive_auction_end_date);
        startPriceTextView = (TextView) view.findViewById(R.id.inactive_auction_start_price);


        Format formater = new SimpleDateFormat("dd-MM-yyy");

        String date1 = formater.format(auction.getStartDate());
        String date2 = formater.format(auction.getEndDate());

        startDateTextView.setText(date1);
        endDateTextView.setText(date2);
        startPriceTextView.setText(String.valueOf(auction.getStartPrice()));



        return view;



    }
}
