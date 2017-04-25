package com.example.tonto.zees.database;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by Hoang on 4/24/2017.
 */

public class Alarm implements Serializable {
    private String name;
    private String delay;
    private String date;
    private String tone;
    private String enabled;
    private String pendingId;

    public Alarm(String name, String delay, String date, String tone, String enabled, String pendingId) {
        this.name = name;
        this.delay = delay;
        this.date = date;
        this.tone = tone;
        this.enabled = enabled;
        this.pendingId = pendingId;
    }


    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getPendingId() {
        return pendingId;
    }

    public void setPendingId(String pendingId) {
        this.pendingId = pendingId;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "name='" + name + '\'' +
                ", delay='" + delay + '\'' +
                ", date='" + date + '\'' +
                ", tone='" + tone + '\'' +
                ", enabled='" + enabled + '\'' +
                ", pendingId='" + pendingId + '\'' +
                '}';
    }
}
