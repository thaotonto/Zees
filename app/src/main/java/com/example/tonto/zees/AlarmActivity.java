package com.example.tonto.zees;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tonto.zees.observers.AlarmVolumeChangeObserver;
import com.example.tonto.zees.receivers.AlarmReceiver;
import com.example.tonto.zees.database.Alarm;
import com.example.tonto.zees.adapters.AlarmAdapter;
import com.example.tonto.zees.database.ZeesDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hoang on 4/24/2017.
 */

public class AlarmActivity extends AppCompatActivity {
    public static ZeesDatabase zeesDatabase;
    public static List<Alarm> alarmList;
    private ImageButton addAlarmButton;
    public static AlarmManager alarmManager;
    public static AlarmAdapter alarmAdapter;
    private ListView listView;
    private ImageView background;
    private Window window;
    private Toolbar toolbar;
    public static TextView reserved;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder alarmNotiBuilder;
    private int currentPosition;
    private AudioManager am;
    private SeekBar sbVolAlarm;
    private TextView textVolAlarm;
    private ImageView iconVolAlarm;
    private int[] backgrounds = {
            R.drawable.background_rain,
            R.drawable.background_ocean,
            R.drawable.background_water,
            R.drawable.background_nature_night,
            R.drawable.background_nature_day,
            R.drawable.background_air_fire,
            R.drawable.background_music,
            R.drawable.background_oriental,
            R.drawable.background_city,
            R.drawable.background_home
    };

    private ColorDrawable[] actionBarColorCodes = {
            new ColorDrawable(Color.parseColor("#ff597f9c")),
            new ColorDrawable(Color.parseColor("#ff749daf")),
            new ColorDrawable(Color.parseColor("#ff899bbf")),
            new ColorDrawable(Color.parseColor("#ff1d3856")),
            new ColorDrawable(Color.parseColor("#ff357829")),
            new ColorDrawable(Color.parseColor("#ffc7b098")),
            new ColorDrawable(Color.parseColor("#ffdc8686")),
            new ColorDrawable(Color.parseColor("#ff8e91d4")),
            new ColorDrawable(Color.parseColor("#ff01579b")),
            new ColorDrawable(Color.parseColor("#ffaca08e"))
    };

    private String[] statusBarColorCodes = {
            "#ff233a5a",
            "#ff437b92",
            "#ff6680a8",
            "#ff0d1f37",
            "#ff194c23",
            "#ff6a594a",
            "#ff573636",
            "#ff3a3589",
            "#ff263238",
            "#ff7e7362"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        currentPosition = getIntent().getIntExtra("Position", 0);

        window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        toolbar = (Toolbar) findViewById(R.id.toolbar_alarm);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        background = (ImageView) findViewById(R.id.alarm_background);
        setBackground(currentPosition);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusbarColor(currentPosition);
        }

        reserved = (TextView) findViewById(R.id.reserved_alarm);

        zeesDatabase = new ZeesDatabase(this);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmList = zeesDatabase.loadAllAlarm();
        if (alarmList.isEmpty()) {
            reserved.setVisibility(View.VISIBLE);
        } else reserved.setVisibility(View.GONE);
        listView = (ListView) findViewById(R.id.alarm_list);
        alarmAdapter = new AlarmAdapter(this, R.layout.alarm_element, alarmList);
        listView.setAdapter(alarmAdapter);
        addAlarmButton = (ImageButton) findViewById(R.id.add_alarm);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AlarmActivity.this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        long curTime = System.currentTimeMillis();
                        long delayms = TimeUnit.HOURS.toMillis(hourOfDay) + TimeUnit.MINUTES.toMillis(minute);
                        System.out.println("Hour: " + hourOfDay + " Minute: " + minute + " Millis: " + delayms);

                        System.out.println("Time: " + curTime + delayms);
                        Calendar calendar = Calendar.getInstance();

                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.getTimeInMillis() < curTime) {
                            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
                        }

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

                        System.out.println(formatter.format(calendar.getTime()));

                        Random randomId = new Random();
                        int id = randomId.nextInt(100);
                        do {
                            id = randomId.nextInt(100);
                        } while (zeesDatabase.checkUniqueId(id) == false);

//                        if (zeesDatabase.checkUniqueDate(calendar.getTimeInMillis()) == false) {
//                            Toast.makeText(AlarmActivity.this, "This alarm has already been set", Toast.LENGTH_SHORT).show();
//                            return;
//                        }

                        Alarm alarm = new Alarm("Alarm set on", delayms + "", calendar.getTimeInMillis() + "", "Test", "true", id + "");
                        alarmList.add(alarm);
                        reserved.setVisibility(View.GONE);

                        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                        intent.putExtra("Alarm Info", alarm);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, 0);

                        SQLiteDatabase db = zeesDatabase.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("name", alarm.getName());
                        values.put("delay", alarm.getDelay());
                        values.put("date", alarm.getDate());
                        values.put("tone", alarm.getTone());
                        values.put("enabled", alarm.getEnabled());
                        values.put("pending_id", alarm.getPendingId());

                        db.insert("alarm", null, values);
                        db.close();

                        alarmAdapter.notifyDataSetChanged();


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        } else
                            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                        int timeLeft = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE) - (new Time(curTime).getHours() * 60 + new Time(curTime).getMinutes());

                        System.out.println("Hour set: " + calendar.get(Calendar.HOUR_OF_DAY));
                        System.out.println("Current hour: " + new Time(curTime).getHours());
                        if (timeLeft < 0)
                            timeLeft = 1440 + timeLeft;
                        System.out.println("Time left: " + timeLeft);
                        int hours = timeLeft / 60;
                        int minutes = timeLeft % 60;
                        System.out.println("Hours left: " + hours + " Minutes left: " + minutes);
                        if (hours != 0 && minutes != 0) {
                            if (hours != 1 && minutes != 1) {
                                Toast.makeText(AlarmActivity.this, "Alarm set for " + hours + " hours and " + minutes + " minutes from now.", Toast.LENGTH_SHORT).show();
                            }
                            if (hours == 1 && minutes != 1) {
                                Toast.makeText(AlarmActivity.this, "Alarm set for 1 hour and " + minutes + " minutes from now.", Toast.LENGTH_SHORT).show();
                            }
                            if (hours == 1 && minutes == 1) {
                                Toast.makeText(AlarmActivity.this, "Alarm set for 1 hour and 1 minute from now.", Toast.LENGTH_SHORT).show();
                            }
                            if (hours != 1 && minutes == 1) {
                                Toast.makeText(AlarmActivity.this, "Alarm set for " + hours + " hours and 1 minute from now.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (hours != 0 && minutes == 0) {
                            if (hours != 1)
                                Toast.makeText(AlarmActivity.this, "Alarm set for " + hours + " hours from now.", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(AlarmActivity.this, "Alarm set for " + hours + " hour from now.", Toast.LENGTH_SHORT).show();
                        }
                        if (hours == 0 && minutes != 0) {
                            if (minutes != 1)
                                Toast.makeText(AlarmActivity.this, "Alarm set for " + minutes + " minutes from now.", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(AlarmActivity.this, "Alarm set for 1 minute from now.", Toast.LENGTH_SHORT).show();
                        }
                        if (hours == 0 && minutes == 0) {
                            Toast.makeText(AlarmActivity.this, "Alarm set for 24 hours from now.", Toast.LENGTH_SHORT).show();
                        }

                        formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                        alarmNotiBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(AlarmActivity.this).setSmallIcon(R.drawable.ic_snooze_white_48dp).setContentTitle("Alarm set at " + formatter.format(calendar.getTime())).setContentText("Touch for more");
                        Intent intentAlarm = new Intent(getApplicationContext(), AlarmActivity.class);
                        PendingIntent returnIntent = PendingIntent.getActivity(AlarmActivity.this, id, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmNotiBuilder.setContentIntent(returnIntent);
                        notificationManager.notify(id, alarmNotiBuilder.build());
                    }
                }
                        , new Time(System.currentTimeMillis()).getHours(), new Time(System.currentTimeMillis()).getMinutes(), true);
                timePickerDialog.setTitle("Set alarm time (hh:mm)");
                timePickerDialog.show();
            }
        });
        addAlarmButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_alarm_volume) {
            AlertDialog.Builder alarmVolDialogBuilder = new AlertDialog.Builder(this, R.style.ThemeDialogAlarm);
            LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = layoutInflater.inflate(R.layout.alarm_vol_dialog, null);

            sbVolAlarm = (SeekBar) dialogView.findViewById(R.id.sb_alarm_vol);
            iconVolAlarm = (ImageView) dialogView.findViewById(R.id.alarm_vol_icon);
            textVolAlarm = (TextView) dialogView.findViewById(R.id.alarm_vol_text);

            sbVolAlarm.setMax(am.getStreamMaxVolume(AudioManager.STREAM_ALARM));
            sbVolAlarm.setProgress(am.getStreamVolume(AudioManager.STREAM_ALARM));

            AlarmVolumeChangeObserver alarmVolumeChangeObserver = new AlarmVolumeChangeObserver(new Handler(), sbVolAlarm, this);
            this.getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, alarmVolumeChangeObserver);

            if (sbVolAlarm.getProgress() != 0) {
                textVolAlarm.setText(sbVolAlarm.getProgress() + "");
                iconVolAlarm.setImageResource(R.drawable.ic_alarm_black_36dp);
            }

            sbVolAlarm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    System.out.println(progress);
                    if (progress == 0) {
                        iconVolAlarm.setImageResource(R.drawable.ic_alarm_off_black_36dp);
                    } else iconVolAlarm.setImageResource(R.drawable.ic_alarm_black_36dp);
                    textVolAlarm.setText(progress + "");
                    am.setStreamVolume(AudioManager.STREAM_ALARM, progress, 0);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            alarmVolDialogBuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alarmVolDialogBuilder.setView(dialogView);
            alarmVolDialogBuilder.setTitle("      Set alarm volume      ");
            alarmVolDialogBuilder.create();
            alarmVolDialogBuilder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zeesDatabase.close();
    }

    public void setBackground(int position) {
        background.setImageResource(backgrounds[position]);
        toolbar.setBackground(actionBarColorCodes[position]);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setStatusbarColor(int position) {
        window.setStatusBarColor(Color.parseColor(statusBarColorCodes[position]));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
}
