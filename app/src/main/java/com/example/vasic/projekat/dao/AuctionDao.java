package com.example.vasic.projekat.dao;

import com.example.vasic.projekat.Model.Auction;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by vasic on 6/7/2017.
 */

public class AuctionDao extends BaseDaoImpl<Auction, Long> {

    public AuctionDao(ConnectionSource cs) throws SQLException{
        super(Auction.class);
        setConnectionSource(cs);
        initialize();
    }
}


