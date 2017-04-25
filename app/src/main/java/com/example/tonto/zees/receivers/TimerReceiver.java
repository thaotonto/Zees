package com.example.tonto.zees.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.tonto.zees.MainActivity;

/**
 * Created by Hoang on 4/23/2017.
 */

public class TimerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentFinish = new Intent(context.getApplicationContext(),MainActivity.class);
        intentFinish.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intentFinish.putExtra("Kill yourself",true);
        context.startActivity(intentFinish);
        System.exit(0);
    }
}
