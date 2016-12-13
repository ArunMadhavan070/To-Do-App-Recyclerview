package com.example.coolm.realm.alarm;


import android.content.Context;
import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
import android.widget.Toast;

import com.example.coolm.realm.service.MyReceiver;
//import com.example.coolm.realm.service.NotifyService;

import java.util.Calendar;

/**
 * Created by coolm on 11/8/2016.
 */
public class AlarmTask implements Runnable{
    // The date selected for the alarm
    private final Calendar date;
    // The android system alarm manager
    private final AlarmManager am;
    // Your context to retrieve the alarm manager from
    private final Context context;

    String task;


    public AlarmTask(Context context, Calendar date, String s) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
        this.task = s;

    }


    @Override
    public void run() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        Intent intent = new Intent(context, MyReceiver.class);
        intent.putExtra("task",task);
        final int _id = (int) System.currentTimeMillis();


       // PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, _id, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //long l = date.getTimeInMillis();



        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
        am.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
    }
}

