package com.example.tonto.zees;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import static com.example.tonto.zees.ChooseColorActivity.*;

public class ShowColorActivity extends AppCompatActivity {

    private ImageView backGround;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_color);
        backGround= (ImageView)findViewById(R.id.iv_bg);
        VideoView mVideoView = (VideoView)findViewById(R.id.videoview);
        Bundle bundle= getIntent().getExtras();
        int color= bundle.getInt("COlOR TO SHOW");
        int mode= bundle.getInt("MODE TO SHOW");
        int lava_mode= bundle.getInt("LAVA MODE TO SHOW");
        if(mode==1)
        {
            mVideoView.setVisibility(View.INVISIBLE);
            backGround.setVisibility(View.VISIBLE);
            Context context = backGround.getContext();
            int id = context.getResources().getIdentifier("color"+color+"_bg", "drawable", context.getPackageName());
            backGround.setImageResource(id);
        }
        else if(mode==3)
        {
            backGround.setVisibility(View.INVISIBLE);
            mVideoView.setVisibility(View.VISIBLE);
            String uriPath=null;
            if(lava_mode== INFERNO)
                uriPath = "android.resource://com.example.tonto.zees/"+R.raw.inferno;
            else if(lava_mode==WATER)
                uriPath = "android.resource://com.example.tonto.zees/"+R.raw.water;
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

    }
}
