package com.example.tonto.zees.database;

/**
 * Created by tonto on 4/24/2017.
 */

public class Preset {
    private String name;
    private String sounds;
    private String volume;

    public Preset(String name, String sounds, String volume) {
        this.name = name;
        this.sounds = sounds;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public String getSounds() {
        return sounds;
    }

    public String getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return name;
    }
}
