package com.example.vasic.projekat.Model;


import android.net.sip.SipErrorCode;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by vasic on 5/28/2017.
 */
@DatabaseTable(tableName = "users")
public class User implements Serializable{

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String name;
    @DatabaseField
    private String email;
    @DatabaseField
    private String password;
    @DatabaseField
    private String picture;
    @DatabaseField
    private String address;
    @DatabaseField
    private String phone;

    @ForeignCollectionField(eager = true)
    private Collection<Auction> auctions;

    @ForeignCollectionField(eager=true)
    private Collection<Bid> bids;


    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param picture
     * @param id
     * @param phone
     * @param email
     * @param address
     * @param name
     * @param password
     */
    public User(Long id, String name, String email, String password, String picture, String address, String phone, Collection<Auction> auctions, Collection<Bid> bids) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.address = address;
        this.phone = phone;
        this.auctions = auctions;
        this.bids = bids;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Collection<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(Collection<Auction> auctions) {
        this.auctions = auctions;
    }

    public Collection<Bid> getBids() {
        return bids;
    }

    public void setBids(Collection<Bid> bids) {
        this.bids = bids;
    }
}