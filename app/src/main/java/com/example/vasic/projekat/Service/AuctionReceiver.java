package com.example.vasic.projekat.Service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.vasic.projekat.R;

/**
 * Created by vasic on 6/12/2017.
 */

public class AuctionReceiver  extends BroadcastReceiver{

    private static int notificationID = 1;


    @Override
    public void onReceive(Context context, Intent intent) {


        double currentPrice = intent.getDoubleExtra("current_price",100);

        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        mBuilder.setSmallIcon(R.mipmap.ic_attach_money_black_24dp);
        mBuilder.setContentTitle("BRAVOOOOO");
        mBuilder.setContentText("Vasa ponuda je najveca: " + String.valueOf(currentPrice));

        mNotificationManager.notify(notificationID, mBuilder.build());
    }
}
