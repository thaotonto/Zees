package com.example.tonto.zees;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.tonto.zees.database.Alarm;
import com.example.tonto.zees.database.ZeesDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Hoang on 4/25/2017.
 */

public class AlarmRingerActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private TextView alarmDate;
    private TextView alarmTime;
    private SeekBar alarmSlider;
    private TextView alarmSlideHint;
    private ImageView alarmTurnOffIcon;
    private Alarm alarm;
    private Vibrator vibrator;
    private Window window;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringer);
        window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(android.R.color.black));
        }
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        alarmDate = (TextView) findViewById(R.id.alarm_date_ringer);
        alarmTime = (TextView) findViewById(R.id.alarm_time_ringer);
        alarmSlider = (SeekBar) findViewById(R.id.alarm_slider);
        alarmSlider.setBackground(null);
        alarmSlideHint = (TextView) findViewById(R.id.alarm_slide_hint);
        alarmTurnOffIcon = (ImageView) findViewById(R.id.alarm_turn_off_icon);
        alarmTurnOffIcon.setVisibility(View.GONE);
        alarm = (Alarm) getIntent().getSerializableExtra("Alarm Info");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(alarm.getDate()));
        ZeesDatabase zeesDatabase = new ZeesDatabase(this);
        SQLiteDatabase db = zeesDatabase.getWritableDatabase();
//        db.delete("alarm", "pending_id" + "='" + alarm.getPendingId() + "' ;", null);
        db.execSQL("UPDATE alarm SET enabled = 'false' WHERE pending_id = '" + alarm.getPendingId() + "';");
        db.execSQL("UPDATE alarm SET name = 'Re-enable to' WHERE pending_id = '" + alarm.getPendingId() + "';");
        db.close();
        if (AlarmActivity.alarmList != null) {
            if (!AlarmActivity.alarmList.isEmpty() && AlarmActivity.alarmList.contains(alarm)) {
                AlarmActivity.alarmList.get(AlarmActivity.alarmList.indexOf(alarm)).setEnabled("false");
                AlarmActivity.alarmList.get(AlarmActivity.alarmList.indexOf(alarm)).setName("Re-enable to");
            }
            if (AlarmActivity.alarmList.isEmpty())
                AlarmActivity.reserved.setVisibility(View.VISIBLE);
            AlarmActivity.alarmAdapter.notifyDataSetChanged();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        alarmTime.setText(formatter.format(calendar.getTime()));
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        alarmDate.setText(formatter.format(calendar.getTime()));
        vibrator.vibrate(60000);
//        alarmTurnOffBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mediaPlayer.stop();
//                mediaPlayer.release();
//                vibrator.cancel();
//                Intent intentFinish = new Intent(getApplicationContext(), MainActivity.class);
//                intentFinish.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                intentFinish.putExtra("Kill yourself", true);
//                startActivity(intentFinish);
//                finish();
//                System.exit(0);
//            }
//        });
        alarmSlider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return false;
            }
        });
        alarmSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    alarmSlideHint.setVisibility(View.VISIBLE);
                }
                if (progress > 20) {
                    alarmSlideHint.setVisibility(View.GONE);
                }
                if (progress > 60){
                    alarmTurnOffIcon.setVisibility(View.GONE);
                    seekBar.setThumb(getResources().getDrawable(R.drawable.ic_alarm_off_white_48dp));
                }
                else{
                    alarmTurnOffIcon.setVisibility(View.VISIBLE);
                }
                if (progress > 75) {
                    seekBar.setThumb(getResources().getDrawable(R.drawable.ic_alarm_off_white_48dp));
                    alarmTurnOffIcon.setVisibility(View.GONE);
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    vibrator.cancel();
                    Intent intentFinish = new Intent(getApplicationContext(), MainActivity.class);
                    intentFinish.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentFinish.putExtra("Kill yourself", true);
                    startActivity(intentFinish);
                    finish();
                    System.exit(0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setThumb(getResources().getDrawable(R.drawable.ic_adjust_white_48dp));
                seekBar.setProgress(0);
                seekBar.setThumbOffset(1);
                alarmSlideHint.setBackgroundResource(R.drawable.ic_trending_flat_white_48dp);
                alarmSlideHint.setText("");
                alarmTurnOffIcon.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                alarmSlideHint.setVisibility(View.VISIBLE);
                seekBar.setThumb(getResources().getDrawable(R.drawable.ic_touch_app_white_48dp));
                seekBar.setProgress(0);
                seekBar.setThumbOffset(1);
                alarmSlideHint.setText("Slide to turn off");
                alarmSlideHint.setBackgroundResource(0);
                alarmTurnOffIcon.setVisibility(View.GONE);
            }
        });

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_oxygen);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.start();
    }
}
