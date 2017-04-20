package com.example.tonto.zees.sounds;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static android.media.AudioManager.STREAM_MUSIC;

/**
 * Created by tonto on 4/19/2017.
 */

public class SoundManager {
    private static int MAX_STREAMS = 10;
    public static SoundPool soundPool = new SoundPool(MAX_STREAMS, STREAM_MUSIC, 0);
    public static ArrayList<Integer> soundIDList = new ArrayList<>();
    private static int STREAM_COUNT;
    static HashMap<String, Integer> listSoundID = new HashMap<>();
    private static ArrayList<Integer> streamIDs = new ArrayList<>();
    private static int streamID;
    private static ArrayList<String> playingSounds = new ArrayList<>();

    public static void loadSoundIntoList(Context context, String[] soundPack) {
        for (int i = 1; i <= soundPack.length; i++) {
            int resIDSound = context.getResources().getIdentifier(soundPack[i - 1], "raw", context.getPackageName());
            listSoundID.put(soundPack[i - 1], STREAM_COUNT);
            STREAM_COUNT++;
            int soundPoolID = soundPool.load(context, resIDSound, 1);
            Log.d("Load sound:",soundPack[i - 1]);
            soundIDList.add(soundPoolID);
        }
    }


    public static void playSound(String string) {
        boolean playing = false;
        Iterator<String> iterator = playingSounds.iterator();
        Iterator<Integer> iteratorStreamIDs = streamIDs.iterator();
        while (iterator.hasNext()) {
            Integer streamID = iteratorStreamIDs.next();
            String playingSound = iterator.next();
            if (playingSound.equals(string)) {
                soundPool.stop(streamID);
                playing = true;
                iteratorStreamIDs.remove();
                iterator.remove();
            }
        }
        if (!playing) {
            try {
                streamID = soundPool.play(soundIDList.get(listSoundID.get(string)), 1.f, 1.f, 1, -1, 1.0f);
                streamIDs.add(streamID);
                playingSounds.add(string);
            } catch (NullPointerException e){

            }
        }
    }

}
