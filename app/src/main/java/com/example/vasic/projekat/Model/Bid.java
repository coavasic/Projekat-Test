package com.example.vasic.projekat.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vasic on 5/28/2017.
 */

@DatabaseTable(tableName = "bids")
public class Bid  implements Serializable{

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private double price;
    @DatabaseField
    private Date dataTime;
    @DatabaseField(foreign = true)
    private Auction auction;
    @DatabaseField(foreign = true)
    private User user;

    public Bid(){

    }

    public Bid(Long id, double price, Date dataTime, Auction auction, User user) {
        this.id = id;
        this.price = price;
        this.dataTime = dataTime;
        this.auction = auction;
        this.user= user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User usre) {
        this.user= usre;
    }
}
