package com.example.tonto.zees;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

public class ShowMoodLightActivity extends AppCompatActivity {
    private int position;
    VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mood_light);
        mVideoView = (VideoView) findViewById(R.id.mood_video);
        String uriPath = "android.resource://com.example.tonto.zees/" + R.raw.mood;

        Uri uri = Uri.parse(uriPath);
        mVideoView.setVideoURI(uri);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        mVideoView.requestFocus();
        mVideoView.start();
    }
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            if (mVideoView != null) {
                position = mVideoView.getCurrentPosition();
               mVideoView.pause();
            }
        }catch (Exception e) {
        }
    }

    protected void onResume() {
        super.onResume();
        try{
            if (mVideoView != null) {
                mVideoView.seekTo(position);
                mVideoView.start();
            }
        }catch (Exception e) {
        }
    }
}
