package com.example.vasic.projekat.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by vasic on 5/7/2017.
 */
@DatabaseTable(tableName = "auctions")
public class Auction  implements Serializable{

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private double startPrice;
    @DatabaseField
    private Date startDate;
    @DatabaseField
    private Date endDate;

    @DatabaseField
    private boolean active;

    @DatabaseField(foreign = true)
    Item item;

    @DatabaseField(foreign = true)
    User user;

    @ForeignCollectionField(eager = true)
    private Collection<Bid> bids;





    public Auction(long id, double startPrice, Date startDate, Date endDate,boolean active,Item item, User user, Collection<Bid> bids) {
        this.id = id;
        this.startPrice = startPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.item = item;
        this.user = user;
        this.bids = bids;

    }

    public Auction(){

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Item getItem() {return item;}

    public void setItem(Item item) {
        this.item = item;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Bid> getBids() {
        return bids;
    }

    public void setBids(Collection<Bid> bids) {
        this.bids = bids;
    }
}
