package com.example.vasic.projekat.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vasic.projekat.ItemsActivity;
import com.example.vasic.projekat.MainActivity;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.R;
import com.example.vasic.projekat.Tools.Mokap;
import com.example.vasic.projekat.dao.AuctionDao;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.ItemDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasic on 5/5/2017.
 */

public class ListItemsAdapter extends BaseAdapter implements Filterable{



    private Context mContext;
    private List<Item> items;
    public ListItemsAdapter(Context context, List<Item> items){


        this.mContext = context;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        Item item = items.get(position);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list_layout,null);

        }else{
            view = convertView;
        }

        TextView titleText = (TextView) view.findViewById(R.id.list_item_text_name);
        TextView descText = (TextView) view.findViewById(R.id.list_item_text_desc);
        ImageView itemIcon = (ImageView) view.findViewById(R.id.item_icon_list_id);

        titleText.setText(item.getName());
        descText.setText(item.getDescription());
        itemIcon.setImageResource(item.getPicture());

        return view;




    }

    @Override
    public Filter getFilter() {
        return null;
    }




}
