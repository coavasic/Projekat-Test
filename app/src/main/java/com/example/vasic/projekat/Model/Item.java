package com.example.vasic.projekat.Model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by vasic on 5/5/2017.
 */
@DatabaseTable(tableName = "items")
public class Item implements Serializable{

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String description;

    @DatabaseField
    private int picture;

    @ForeignCollectionField(eager = true)
    private Collection<Auction> auctions;

    @DatabaseField
    private boolean sold;


    public Item(long id, String name, String description, int picture, ForeignCollection<Auction> auctions, boolean sold) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.auctions = auctions;
        this.sold = sold;
    }

    public Item(){

    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public Collection<Auction> getAutions() {
        return auctions;
    }

    public void setAutions(Collection<Auction> autions) {
        this.auctions = autions;
    }



//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//
//        dest.writeLong(id);
//        dest.writeString(name);
//        dest.writeString(description);
//        dest.writeInt(picture);
//        dest.writeSerializable(auctions);
//        dest.writeByte((byte) (sold ? 1 : 0));
//
//
//    }
}



