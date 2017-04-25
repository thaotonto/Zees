package com.example.tonto.zees.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.tonto.zees.AlarmRingerActivity;
import com.example.tonto.zees.database.Alarm;

/**
 * Created by Hoang on 4/24/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Alarm alarm = (Alarm) intent.getSerializableExtra("Alarm Info");
        Intent intentRinger = new Intent(context, AlarmRingerActivity.class);
        intentRinger.putExtra("Alarm Info", alarm);
        intentRinger.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentRinger);
    }
}
