package com.example.tonto.zees.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonto on 4/24/2017.
 */

public class ZeesDatabase extends SQLiteAssetHelper {
    private static String DATABASE_NAME = "zees.db";
    private static int DATABASE_VERSION = 1;
    private static String PRESET_TABLE = "preset";
    private static String PRESET_NAME = "name";
    private static String PRESET_SOUND = "sound";
    private static String PRESET_VOLUME = "volume";
    private static String[] PRESET_ALL_COLUMN = {
            PRESET_NAME,
            PRESET_SOUND,
            PRESET_VOLUME
    };

    private static String ALARM_TABLE = "alarm";
    private static String ALARM_NAME = "name";
    private static String ALARM_DELAY = "delay";
    private static String ALARM_DATE = "date";
    private static String ALARM_TONE = "tone";
    private static String ALARM_ENABLED = "enabled";
    private static String ALARM_ID = "pending_id";

    private static String[] ALARM_ALL_COLUMN = {
            ALARM_NAME,
            ALARM_DELAY,
            ALARM_DATE,
            ALARM_TONE,
            ALARM_ENABLED,
            ALARM_ID
    };

    private static String[] ALARM_ID_ONLY = {
            ALARM_ID
    };

    public ZeesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<Preset> loadAllPreset() {
        List<Preset> presets = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(PRESET_TABLE, PRESET_ALL_COLUMN, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(PRESET_NAME));
            String sound = cursor.getString(cursor.getColumnIndex(PRESET_SOUND));
            String volume = cursor.getString(cursor.getColumnIndex(PRESET_VOLUME));
            Preset preset = new Preset(name, sound, volume);
            presets.add(preset);
        }

        cursor.close();
        db.close();
        return presets;
    }

    public List<Alarm> loadAllAlarm() {
        List<Alarm> presets = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ALARM_TABLE, ALARM_ALL_COLUMN, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ALARM_NAME));
            String delay = cursor.getString(cursor.getColumnIndex(ALARM_DELAY));
            String date = cursor.getString(cursor.getColumnIndex(ALARM_DATE));
            String tone = cursor.getString(cursor.getColumnIndex(ALARM_TONE));
            String enabled = cursor.getString(cursor.getColumnIndex(ALARM_ENABLED));
            String id = cursor.getString(cursor.getColumnIndex(ALARM_ID));

            Alarm alarm = new Alarm(name, delay, date, tone, enabled, id);
            System.out.println(alarm);
            presets.add(alarm);
        }

        cursor.close();
        db.close();
        return presets;
    }

    public boolean checkUniqueId(int idToCheck) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ALARM_TABLE, ALARM_ID_ONLY, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ALARM_ID));
            if (idToCheck == Integer.parseInt(id)) {
                return false;
            }
        }
        cursor.close();
        db.close();
        return true;
    }
}
