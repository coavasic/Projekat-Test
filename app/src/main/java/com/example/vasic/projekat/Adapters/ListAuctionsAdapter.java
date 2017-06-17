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

        CheckBox cbActive = (CheckBox) view.findViewById(R.id.checkBox_active);
        ImageView imgView = (ImageView) view.findViewById(R.id.auction_item_image_view);
        TextView nameText = (TextView) view.findViewById(R.id.auction_item_name_text_view);
        TextView priceTextView = (TextView) view.findViewById(R.id.auctions_price_list_text_view);
        imgView.setImageResource(auction.getItem().getPicture());
        cbActive.setChecked(auction.isActive());

        Format formater = new SimpleDateFormat("dd-MM-yyy");

        String date1 = formater.format(auction.getStartDate());
        String date2 = formater.format(auction.getEndDate());
        priceTextView.setText(String.valueOf(auction.getId()));
        nameText.setText(date1+"  "+date2);


        return view;



    }
}
