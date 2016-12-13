/*
package com.example.coolm.realm.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.os.IBinder;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.coolm.realm.R;
import com.example.coolm.realm.activity.MainActivity;
import com.example.coolm.realm.model.Book;
import com.example.coolm.realm.realm.RealmController;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

*/
/**
 * Created by coolm on 11/8/2016.
 *//*

public class NotifyService extends Service {

    int notifyID;


    */
/**
     * Class for clients to access
     *//*

    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    // Unique id to identify the notification.
   // private static final int NOTIFICATION = 123;
    // Name of an intent extra we can use to identify if this service was started to create a notification
    public static final String INTENT_NOTIFY = "com.example.coolm.realm.service.INTENT_NOTIFY";
    // The system notification manager
    private NotificationManager mNM;
    final static String GROUP_KEY_EMAILS = "group_key_emails";

    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);



            String  str = intent.getStringExtra("desc");





        // If this service was started by out AlarmTask intent then we want to show our notification
        //if(intent.getBooleanExtra(INTENT_NOTIFY, false))
            showNotification(str);

        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();

    */
/**
     * Creates a notification and shows it in the OS drag-down status bar
     *//*

    private void showNotification(String task) {
        // This is the 'title' of the notification
       // CharSequence title = "Reminder for " +day;
        // This is the icon to use on the notification
        //int icon = R.drawable.notification_icon;
        // This is the scrolling text of the notification
        //CharSequence text = "Your notification time is upon us.";
        // What time to show on the notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.cardmg);



        Random random = new Random();
         notifyID = random.nextInt(9999 - 1000) + 1000;

        Notification summaryNotification = new NotificationCompat.Builder(this)
                .setContentTitle("ToDo")
                .setContentText("You have remainders")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(bm)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine(task)
                        .setBigContentTitle("ToDo")
                        .setSummaryText("Tap On for Details")

                )

                .setGroup(GROUP_KEY_EMAILS)
                .setGroupSummary(true)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();

    mNM.notify(notifyID, summaryNotification);





        // Send the notification to the system.
       // mNM.notify(NOTIFICATION, mBuilder);

       // mNM.cancel(NOTIFICATION);

        // Stop the service when we are finished
        stopSelf();
    }



}
*/
