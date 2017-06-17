package com.example.vasic.projekat.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Bid;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.Model.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by vasic on 6/6/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "projekat.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Item.class);
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, Auction.class);
            TableUtils.createTableIfNotExists(connectionSource, Bid.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

        try {
            TableUtils.dropTable(connectionSource, Item.class,true);
            TableUtils.dropTable(connectionSource, User.class,true);
            TableUtils.dropTable(connectionSource, Auction.class,true);
            TableUtils.dropTable(connectionSource, Bid.class, true);
            onCreate(sqLiteDatabase,connectionSource);
    } catch (SQLException e) {
        e.printStackTrace();
    }


}
}
