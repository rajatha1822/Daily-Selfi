package com.example.rajatha.rcameratest_2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Rajatha on 21-Apr-2017.
 */

public class NotificationReceiver extends BroadcastReceiver {


    private static final int NOTIFICATION_ID=1;
    private static final String TAG = "NotificationReceiver";
    private final CharSequence tickerText="Selfie Reminder!!!!!!";
    private final CharSequence contentTitle ="Daily Selfie";
    private final CharSequence contentText ="Time for another selfie";
    private Intent mNotificationIntent;
    private PendingIntent mContentIntent;
    private final long[] mVibratePattern = { 0, 200, 200, 300 };



    @Override
    public void onReceive(Context context, Intent intent) {

        mNotificationIntent=new Intent(context,MainActivity.class);
        mContentIntent=PendingIntent.getActivity(context,0,mNotificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder notificationBuilder = new Notification.Builder(
                context).setTicker(tickerText).setSmallIcon(android.R.drawable.ic_menu_camera)
                .setAutoCancel(true).setContentTitle(contentTitle)
                .setContentText(contentText).setContentIntent(mContentIntent).setVibrate(mVibratePattern);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID,
                notificationBuilder.build());

    }
}
