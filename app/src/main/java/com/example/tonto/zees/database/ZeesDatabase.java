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
}
