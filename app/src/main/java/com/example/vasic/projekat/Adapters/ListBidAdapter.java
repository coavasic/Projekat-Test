package com.example.vasic.projekat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vasic.projekat.Model.Bid;
import com.example.vasic.projekat.Model.User;
import com.example.vasic.projekat.NewBidActivity;
import com.example.vasic.projekat.R;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.UserDao;

import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by vasic on 6/11/2017.
 */

public class ListBidAdapter extends BaseAdapter {

    private Context mContext;
    private List<Bid> bids;

    public ListBidAdapter(Context context, List<Bid> bids){
        this.mContext = context;
        this.bids = bids;
    }




    @Override
    public int getCount() {
        return bids.size();
    }

    @Override
    public Object getItem(int position) {
        return bids.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Bid bid = bids.get(position);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.bid_layout,null);
        }else{
            view = convertView;
        }

        TextView bidDate = (TextView) view.findViewById(R.id.single_bid_date_text);
        TextView bidPrice = (TextView) view.findViewById(R.id.single_bid_price_text);
        TextView bidUser = (TextView) view.findViewById(R.id.bid_user_info);

        Format formater = new SimpleDateFormat("dd-MM-yyyy");

        String date1 = formater.format(bid.getDataTime());

        User user = findUserById(bid.getUser().getId());

        bidDate.setText(date1);
        bidPrice.setText(String.valueOf(bid.getPrice()));
        bidUser.setText(user.getName() + " " + user.getEmail());

        return view;
    }


    public void refresh()
    {
        notifyDataSetChanged();
    }


    private User findUserById(long id){
        User user = new User();

        DatabaseHelper dh = new DatabaseHelper(mContext);

        try {
            UserDao userDao = new UserDao(dh.getConnectionSource());
            user = userDao.queryForId(id);



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


}
