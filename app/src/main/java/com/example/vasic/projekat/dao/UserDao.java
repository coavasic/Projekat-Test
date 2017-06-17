package com.example.vasic.projekat.dao;

import com.example.vasic.projekat.Model.User;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by vasic on 6/7/2017.
 */

public class UserDao extends BaseDaoImpl<User, Long> {

    public UserDao(ConnectionSource cs) throws SQLException{
        super(User.class);
        setConnectionSource(cs);
        initialize();
    }
}
