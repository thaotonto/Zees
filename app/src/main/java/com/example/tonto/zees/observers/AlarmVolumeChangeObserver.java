package com.example.tonto.zees.observers;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.widget.SeekBar;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by Hoang on 5/8/2017.
 */

public class AlarmVolumeChangeObserver extends ContentObserver {
    private SeekBar seekBar;
    private Context context;

    public AlarmVolumeChangeObserver(Handler handler, SeekBar seekBar, Context context) {
        super(handler);
        this.seekBar = seekBar;
        this.context = context;
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        AudioManager am = (AudioManager)context.getSystemService(AUDIO_SERVICE);
        int volume_level = am.getStreamVolume(AudioManager.STREAM_ALARM);
        seekBar.setProgress(volume_level);
    }
}
