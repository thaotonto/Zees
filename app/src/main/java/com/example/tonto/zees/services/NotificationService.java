package com.example.tonto.zees.services;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.tonto.zees.receivers.TimerReceiver;

/**
 * Created by Hoang on 5/4/2017.
 */

public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), TimerReceiver.class);
        PendingIntent timerIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        timerIntent.cancel();
        alarmManager.cancel(timerIntent);
    }
}
