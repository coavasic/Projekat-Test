package com.example.vasic.projekat.Tools;


import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.Model.User;
import com.example.vasic.projekat.R;
import com.j256.ormlite.dao.ForeignCollection;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by vasic on 5/6/2017.
 */

public class Mokap {


    public static User user = new User();
    public static ArrayList<Item> items = new ArrayList<Item>();

    public static void initItems(){


        user.setName("Aleksandaer");
        user.setEmail("vasic@gmail.com");
        user.setPassword("password");
        user.setPhone("062326386");
        user.setPicture("Slikaaa");
        user.setAddress("ADresaaaaaaa");




        for (int i = 0; i < 5; i++) {
            Item item = new Item();
            Collection<Auction> auctions = new ArrayList<Auction>();

            item.setAutions(auctions);
            item.setDescription("Opis " + i);
            item.setName("Ime " + i);
            item.setSold(false);
            item.setPicture(R.mipmap.ic_launcher_round);
            int x = 5;
            for (int j = 0; j < x; j++) {
                Auction auction = new Auction();
                int day = 1;
                String dayString = String.valueOf(day);
                String dateString = day + "-01-2015";
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = format.parse(dateString);

                    auction.setStartPrice(j * 100);
                    auction.setEndDate(date);
                    auction.setStartDate(date);
                    auction.setItem(item);
                    auctions.add(auction);
                    if(j == (x - 1) ){
                        auction.setActive(true);
                    }else{
                        auction.setActive(false);
                    }
                    auction.setUser(user);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }



            items.add(item);


        }


    }


    public static void init(){





        for (int i = 0; i < 5; i++) {
            Item item = new Item();
            Collection<Auction> auctions = new ArrayList<Auction>();
            item.setId(i);
            item.setAutions(auctions);
            item.setDescription("Opis " + i);
            item.setName("Ime " + i);
            item.setSold(false);
            item.setPicture(R.mipmap.ic_launcher_round);
            Auction auction = new Auction();
            auction.setId(i);
            auction.setStartPrice(i * 100);
            String dateString = "01-01-2016";
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try {
                auction.setStartDate(format.parse(dateString));
                auction.setEndDate(format.parse(dateString));
                auction.setItem(item);
            } catch (ParseException e) {
                e.printStackTrace();
            }




            items.add(item);


        }


    }



}







//
//        Item item1 = new Item(1, "Ime", "neki ludi opasdasdasddasadsasdasdasdasdasdasdasdis", R.mipmap.ic_launcher, false);
//        Item item2 = new Item(1, "Ime", "neki ludi opis", R.mipmap.ic_launcher, false);
//        Item item3 = new Item(1, "Ime", "neki ludi opis", R.mipmap.ic_launcher, false);
//        Item item4 = new Item(1, "Ime", "neki ludi opis", R.mipmap.ic_launcher, false);
//        Item item5 = new Item(1, "Ime", "neki ludi opis", R.mipmap.ic_launcher, false);
//
//        mItems.add(item1);
//        mItems.add(item2);
//        mItems.add(item3);
//        mItems.add(item4);
//        mItems.add(item5);
//
//        return mItems;






