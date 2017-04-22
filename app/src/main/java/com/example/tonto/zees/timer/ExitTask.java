package com.example.tonto.zees.timer;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.TimerTask;


/**
 * Created by Hoang on 4/22/2017.
 */

public class ExitTask extends TimerTask {
    private long countDown;
    private Handler handler;

    public ExitTask(long countDown, Handler handler) {
        this.countDown = countDown;
        this.handler = handler;
    }

    @Override
    public void run() {
        System.out.println(countDown);
        countDown--;
        if (countDown == 0){
            handler.sendEmptyMessage(0);
        }
    }
}
