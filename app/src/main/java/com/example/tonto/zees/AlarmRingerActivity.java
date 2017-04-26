package com.example.tonto.zees;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
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
    private TextView alarmName;
    private TextView alarmTime;
    private ImageView alarmTurnOffBtn;
    private Alarm alarm;
    private Vibrator vibrator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringer);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        alarmName = (TextView) findViewById(R.id.alarm_name_ringer);
        alarmTime = (TextView) findViewById(R.id.alarm_time_ringer);
        alarmTurnOffBtn = (ImageView) findViewById(R.id.turn_off_alarm);
        alarm = (Alarm) getIntent().getSerializableExtra("Alarm Info");
        alarmName.setText(alarm.getName());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(alarm.getDate()));
        ZeesDatabase zeesDatabase = new ZeesDatabase(this);
        SQLiteDatabase db = zeesDatabase.getWritableDatabase();
        db.delete("alarm", "pending_id" + "='" + alarm.getPendingId() + "' ;", null);
        db.close();
        if (AlarmActivity.alarmList != null) {
            AlarmActivity.alarmList.remove(alarm);
            AlarmActivity.alarmNo--;
            if (AlarmActivity.alarmList.isEmpty())
                AlarmActivity.reserved.setVisibility(View.VISIBLE);
            AlarmActivity.alarmAdapter.notifyDataSetChanged();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        alarmTime.setText(formatter.format(calendar.getTime()));
        vibrator.vibrate(30000);
        alarmTurnOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!v.isSelected())
//                    v.setSelected(true);
//                else v.setSelected(false);
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
        });
        alarmTurnOffBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
//                    v.setPressed(true);
//                }
//                else v.setPressed(false);
                return false;
            }
        });
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_oxygen);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.start();
    }
}
