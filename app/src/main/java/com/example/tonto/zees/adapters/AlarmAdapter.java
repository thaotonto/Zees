package com.example.tonto.zees.adapters;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.support.v7.widget.SwitchCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tonto.zees.AlarmActivity;
import com.example.tonto.zees.R;
import com.example.tonto.zees.receivers.AlarmReceiver;
import com.example.tonto.zees.database.Alarm;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hoang on 4/24/2017.
 */

public class AlarmAdapter extends ArrayAdapter {
    private Context context;
    private List<Alarm> alarmList;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder alarmNotiBuilder;


    public AlarmAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Alarm> alarmList) {
        super(context, resource, alarmList);
        this.context = context;
        this.alarmList = alarmList;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.alarm_element, parent, false);
        rowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                } else v.setPressed(false);
                return false;
            }
        });
        TextView time = (TextView) rowView.findViewById(R.id.alarm_time);
        TextView name = (TextView) rowView.findViewById(R.id.alarm_name);
        TextView date = (TextView) rowView.findViewById(R.id.alarm_date);
        SwitchCompat enabled = (SwitchCompat) rowView.findViewById(R.id.alarm_switch);
        ImageView deleteButton = (ImageView) rowView.findViewById(R.id.delete_alarm);

        enabled.setTag(alarmList.get(position));
        deleteButton.setTag(alarmList.get(position));

        name.setText(alarmList.get(position).getName());

        if (!alarmList.get(position).getName().equals("Re-enable to")) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(alarmList.get(position).getDate()));

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            time.setText(formatter.format(calendar.getTime()));

            date.setVisibility(View.VISIBLE);
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            date.setText(formatter.format(calendar.getTime()));
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(alarmList.get(position).getDate()));

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            time.setText(formatter.format(calendar.getTime()));
            date.setText("set this alarm.");
        }


        if (alarmList.get(position).getEnabled().equals("true")) enabled.setChecked(true);
        else enabled.setChecked(false);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm alarm = alarmList.get(alarmList.indexOf(v.getTag()));
                System.out.println("Pending Id: " + alarm.getPendingId());
                SQLiteDatabase db = AlarmActivity.zeesDatabase.getWritableDatabase();
                Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
                intent.putExtra("Alarm Info", alarm);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), Integer.parseInt(alarm.getPendingId()), intent, 0);
                pendingIntent.cancel();
                AlarmActivity.alarmManager.cancel(pendingIntent);
                db.delete("alarm", "pending_id" + "='" + alarm.getPendingId() + "' ;", null);
                db.close();
                alarmList.remove(v.getTag());
                if (alarmList.isEmpty()) AlarmActivity.reserved.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
                Toast.makeText(context, "Alarm deleted.", Toast.LENGTH_SHORT).show();
                notificationManager.cancel(Integer.parseInt(alarm.getPendingId()));
            }
        });

        enabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Alarm alarm = alarmList.get(alarmList.indexOf(buttonView.getTag()));
                if (isChecked) {
                    alarmList.get(alarmList.indexOf(buttonView.getTag())).setEnabled("true");
                    Calendar calendar = Calendar.getInstance();
                    long delayms = Long.parseLong(alarm.getDelay());
                    long curTime = System.currentTimeMillis();
                    int minutes = (int) ((delayms / (1000 * 60)) % 60);
                    int hours = (int) ((delayms / (1000 * 60 * 60)) % 24);
                    calendar.set(Calendar.HOUR_OF_DAY, hours);
                    calendar.set(Calendar.MINUTE, minutes);
                    calendar.set(Calendar.SECOND, 0);

                    if (calendar.getTimeInMillis() < curTime) {
                        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
                    }

                    alarm.setName("Alarm set on");
                    alarm.setDate(calendar.getTimeInMillis() + "");

                    SQLiteDatabase db = AlarmActivity.zeesDatabase.getWritableDatabase();
                    db.execSQL("UPDATE alarm SET enabled = 'true' WHERE pending_id = '" + alarm.getPendingId() + "';");
                    db.execSQL("UPDATE alarm SET name = 'Alarm set on' WHERE pending_id = '" + alarm.getPendingId() + "';");
                    db.execSQL("UPDATE alarm SET date = '" + calendar.getTimeInMillis() + "' WHERE pending_id = '" + alarm.getPendingId() + "';");
                    db.close();

                    Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
                    intent.putExtra("Alarm Info", alarm);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), Integer.parseInt(alarm.getPendingId()), intent, 0);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        AlarmActivity.alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        AlarmActivity.alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    } else
                        AlarmActivity.alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    notifyDataSetChanged();
                    int timeLeft = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE) - (new Time(curTime).getHours() * 60 + new Time(curTime).getMinutes());
                    if (timeLeft < 0)
                        timeLeft = 1440 + timeLeft;
                    hours = timeLeft / 60;
                    minutes = timeLeft % 60;
                    System.out.println("Hours left: " + hours + " Minutes left: " + minutes);
                    if (hours != 0 && minutes != 0) {
                        if (hours != 1 && minutes != 1) {
                            Toast.makeText(context, "Alarm set for " + hours + " hours and " + minutes + " minutes from now.", Toast.LENGTH_SHORT).show();
                        }
                        if (hours == 1 && minutes != 1) {
                            Toast.makeText(context, "Alarm set for 1 hour and " + minutes + " minutes from now.", Toast.LENGTH_SHORT).show();
                        }
                        if (hours == 1 && minutes == 1) {
                            Toast.makeText(context, "Alarm set for 1 hour and 1 minute from now.", Toast.LENGTH_SHORT).show();
                        }
                        if (hours != 1 && minutes == 1) {
                            Toast.makeText(context, "Alarm set for " + hours + " hours and 1 minute from now.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (hours != 0 && minutes == 0) {
                        if (hours != 1)
                            Toast.makeText(context, "Alarm set for " + hours + " hours from now.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Alarm set for " + hours + " hour from now.", Toast.LENGTH_SHORT).show();
                    }
                    if (hours == 0 && minutes != 0) {
                        if (minutes != 1)
                            Toast.makeText(context, "Alarm set for " + minutes + " minutes from now.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Alarm set for 1 minute from now.", Toast.LENGTH_SHORT).show();
                    }
                    if (hours == 0 && minutes == 0) {
                        Toast.makeText(context, "Alarm set for 24 hours from now.", Toast.LENGTH_SHORT).show();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                    alarmNotiBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context).setSmallIcon(R.drawable.icon_notification_alarm).setContentTitle("Alarm set at " + formatter.format(calendar.getTime())).setContentText("Touch for more");
                    Intent intentAlarm = new Intent(context, AlarmActivity.class);
                    PendingIntent returnIntent = PendingIntent.getActivity(context, Integer.parseInt(alarm.getPendingId()), intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmNotiBuilder.setContentIntent(returnIntent);
                    notificationManager.notify(Integer.parseInt(alarm.getPendingId()), alarmNotiBuilder.build());
                }
                if (!isChecked) {
                    alarmList.get(alarmList.indexOf(buttonView.getTag())).setEnabled("false");
                    System.out.println("Pending Id: " + alarm.getPendingId());

                    SQLiteDatabase db = AlarmActivity.zeesDatabase.getWritableDatabase();
                    db.execSQL("UPDATE alarm SET enabled = 'false' WHERE pending_id = '" + alarm.getPendingId() + "';");
                    db.execSQL("UPDATE alarm SET name = 'Re-enable to' WHERE pending_id = '" + alarm.getPendingId() + "';");
                    db.close();

                    alarm.setName("Re-enable to");
                    notifyDataSetChanged();

                    Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
                    intent.putExtra("Alarm Info", alarm);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(alarm.getPendingId()), intent, 0);
                    pendingIntent.cancel();
                    AlarmActivity.alarmManager.cancel(pendingIntent);
                    Toast.makeText(context, "Alarm canceled.", Toast.LENGTH_SHORT).show();
                    notificationManager.cancel(Integer.parseInt(alarm.getPendingId()));
                }
                System.out.println("State: " + alarmList.get(alarmList.indexOf(buttonView.getTag())).getEnabled());
            }
        });


        System.out.println(alarmList.get(position).toString());

        return rowView;
    }
}
