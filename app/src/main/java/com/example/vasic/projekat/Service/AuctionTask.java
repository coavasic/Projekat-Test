package com.example.vasic.projekat.Service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * Created by vasic on 6/12/2017.
 */

public class AuctionTask extends AsyncTask<Void, Void, Void> {


    private Context context;

    public AuctionTask(Context context){

        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Intent intent = new Intent("AuctionIntent");
        intent.setAction("com.vasic.Projekat.AUCTION");
        context.sendBroadcast(intent);
    }
}

