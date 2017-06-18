package com.example.vasic.projekat.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by vasic on 5/29/2017.
 */

public class AuctionService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        double currentPrice = intent.getDoubleExtra("current_price",100);

        new AuctionTask(getApplicationContext(),currentPrice).execute();

        stopSelf();
        return START_NOT_STICKY;




    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
