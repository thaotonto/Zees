package com.example.tonto.zees.adapters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Hoang on 4/24/2017.
 */

public class AlarmAdapter extends ArrayAdapter {
    private Context context;
    List<Alarm> alarmList;

    public AlarmAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Alarm> alarmList) {
        super(context, resource, alarmList);
        this.context = context;
        this.alarmList = alarmList;
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

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(alarmList.get(position).getDate()));

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        time.setText(formatter.format(calendar.getTime()));

        formatter = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(formatter.format(calendar.getTime()));

        name.setText(alarmList.get(position).getName());

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
                Toast.makeText(context, "Alarm deleted", Toast.LENGTH_SHORT).show();
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

                    alarm.setDate(calendar.getTimeInMillis() + "");

                    SQLiteDatabase db = AlarmActivity.zeesDatabase.getWritableDatabase();
                    db.execSQL("UPDATE alarm SET enabled = 'true' WHERE pending_id = '" + alarm.getPendingId()+"';");
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
                    long timeLeft = calendar.getTimeInMillis() - curTime;
                    hours = (int) ((timeLeft / (1000 * 60 * 60)) % 24);
                    minutes = (int) ((timeLeft / (1000 * 60)) % 60);
                    if (hours != 0 && minutes != 0) {
                        if (hours != 1 && minutes != 1) {
                            Toast.makeText(context, "Alarm set for " + hours + " hours and " + minutes + " minutes from now.", Toast.LENGTH_SHORT).show();
                        }
                        if (hours == 1 && minutes != 1) {
                            Toast.makeText(context, "Alarm set for " + hours + " hour and " + minutes + " minutes from now.", Toast.LENGTH_SHORT).show();
                        }
                        if (hours == 1 && minutes == 1) {
                            Toast.makeText(context, "Alarm set for " + hours + " hour and " + minutes + " minute from now.", Toast.LENGTH_SHORT).show();
                        }
                        if (hours != 1 && minutes == 1) {
                            Toast.makeText(context, "Alarm set for " + hours + " hours and " + minutes + " minute from now.", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(context, "Alarm set for " + minutes + " minute from now.", Toast.LENGTH_SHORT).show();
                    }
                }
                if (!isChecked) {
                    alarmList.get(alarmList.indexOf(buttonView.getTag())).setEnabled("false");
                    System.out.println("Pending Id: " + alarm.getPendingId());

                    SQLiteDatabase db = AlarmActivity.zeesDatabase.getWritableDatabase();
                    db.execSQL("UPDATE alarm SET enabled = 'false' WHERE pending_id = '" + alarm.getPendingId()+"';");
                    db.close();

                    Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
                    intent.putExtra("Alarm Info", alarm);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(alarm.getPendingId()), intent, 0);
                    pendingIntent.cancel();
                    AlarmActivity.alarmManager.cancel(pendingIntent);
                    Toast.makeText(context, "Alarm canceled.", Toast.LENGTH_SHORT).show();
                }
                System.out.println("State: " + alarmList.get(alarmList.indexOf(buttonView.getTag())).getEnabled());
            }
        });


        System.out.println(alarmList.get(position).toString());

        return rowView;
    }
}
