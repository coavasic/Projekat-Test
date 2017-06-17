package com.example.vasic.projekat.dao;

import com.example.vasic.projekat.Model.Bid;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by vasic on 6/7/2017.
 */

public class BidDao extends BaseDaoImpl<Bid, Long>{

    public BidDao(ConnectionSource cs) throws SQLException
    {
        super(Bid.class);
        setConnectionSource(cs);
        initialize();
    }
}
