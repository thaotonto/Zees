package com.example.tonto.zees;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.VideoView;

public class ShowLavaLightActivity extends AppCompatActivity {
    private static int NUMBER_OF_MODES = 3;
    private static int currentMode = 1;
    private TextView back;
    private TextView text;
    private TextView next;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lava_light);
        back = (TextView) findViewById(R.id.lava_back);
        text = (TextView) findViewById(R.id.lava_text);
        next = (TextView) findViewById(R.id.lava_next);
        back.setOnClickListener(listener);
        next.setOnClickListener(listener);
        mVideoView = (VideoView) findViewById(R.id.lava_video);
        setText();
        setVideo();

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lava_back:
                    if (currentMode - 1 == 0)
                        currentMode = NUMBER_OF_MODES;
                    else
                        currentMode--;
                    break;
                case R.id.lava_next:
                    if (currentMode + 1 > NUMBER_OF_MODES)
                        currentMode = 1;
                    else
                        currentMode++;
                    break;
            }
            setText();
            setVideo();
        }
    };

    private void setVideo() {
        String uriPath = null;
        if (currentMode == 1)

            uriPath = "android.resource://com.example.tonto.zees/" + R.raw.inferno;

        else if (currentMode == 2)
            uriPath = "android.resource://com.example.tonto.zees/" + R.raw.water;
        else if (currentMode == 3)

            uriPath = "android.resource://com.example.tonto.zees/" + R.raw.fire;

        playVideo(uriPath);

    }

    private void playVideo(String uriPath) {
        if(currentMode==3)
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

    private void setText() {
        if (currentMode == 1)
            text.setText("Inferno");
        else if (currentMode == 2)
            text.setText("Clouds on water");
        else if (currentMode == 3)
            text.setText("Camp fire");
        else if (currentMode == 4)
            text.setText("Thunder");
    }

}

