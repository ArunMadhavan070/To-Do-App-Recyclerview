package com.example.coolm.realm.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.example.coolm.realm.R;
import com.example.coolm.realm.activity.MainActivity;

import java.util.Random;

/**
 * Created by coolm on 11/10/2016.
 */
public class MyReceiver extends BroadcastReceiver {

    NotificationManager mNM;
    int notifyID = 1;
    final static String GROUP_KEY_EMAILS = "group_key_emails";

    @Override
    public void onReceive(Context context, Intent intent) {

        String  str = intent.getStringExtra("task");

        /*if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {*/
           // intent.getStringExtra("task");
          //  Intent service1 = new Intent(context, NotifyService.class);
           // service1.putExtra(NotifyService.INTENT_NOTIFY, true);
          //  service1.putExtra("desc",intent.getStringExtra("task"));
          //  service1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          //  context.startService(service1);
        mNM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class),0);

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.cardmg);

       /* Random random = new Random();
        notifyID = random.nextInt(9999 - 1000) + 1000;*/

        Notification summaryNotification = new NotificationCompat.Builder(context)
                .setContentTitle("ToDo")
                .setContentText("You have remainders")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(bm)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine(str)
                        .setBigContentTitle("ToDo")
                        .setSummaryText("Tap On for Details")

                )

                .setGroup(GROUP_KEY_EMAILS)
                .setGroupSummary(true)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();

        mNM.notify(notifyID, summaryNotification);




        //}
    }
}
