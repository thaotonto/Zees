package com.example.tonto.zees.application;

import android.app.Application;

import com.example.tonto.zees.database.ZeesDatabase;

/**
 * Created by tonto on 4/24/2017.
 */

public class ZeesAplication extends Application {
    private static ZeesAplication instance;
    private ZeesDatabase zeesDatabase;

    @Override
    public void onCreate() {
        zeesDatabase = new ZeesDatabase(this);
        instance = this;
        super.onCreate();
    }

    public static ZeesAplication getInstance() {
        return instance;
    }

    public ZeesDatabase getZeesDatabase() {
        return zeesDatabase;
    }
}
