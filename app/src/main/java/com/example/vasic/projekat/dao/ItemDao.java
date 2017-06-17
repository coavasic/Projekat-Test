package com.example.vasic.projekat.dao;

import com.example.vasic.projekat.Model.Item;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by vasic on 6/7/2017.
 */

public class ItemDao extends BaseDaoImpl<Item, Long>{

    public ItemDao(ConnectionSource cs) throws SQLException{
        super(Item.class);
        setConnectionSource(cs);
        initialize();
    }

}
