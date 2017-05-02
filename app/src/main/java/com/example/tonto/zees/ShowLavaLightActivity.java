package com.example.tonto.zees;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.VideoView;

public class ShowLavaLightActivity extends AppCompatActivity {
    private static int NUMBER_OF_MODES = 4;
    private static int currentMode = 1;
    private TextView back;
    private TextView text;
    private TextView next;
    private VideoView mVideoView;
    private boolean isFlashOn;
    private MediaPlayer mp;
    private MediaPlayer mp1;
    private Handler handler;
    private Camera camera;
    Camera.Parameters params;

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
        else if (currentMode == 4)
            uriPath = "android.resource://com.example.tonto.zees/" + R.raw.rain;
        playVideo(uriPath);
        if (currentMode == 4) {
            makeThunder();
        }
        else if(currentMode!=4)
        {
            if (handler != null)
            {
                handler.removeCallbacks(loop);
                mp1.stop();
                mp.stop();
            }
        }
    }

    private void playVideo(String uriPath) {
        if (currentMode == 3)
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

    private void makeThunder() {
        mp = MediaPlayer.create(this, R.raw.big_thunder);
        mp1 = MediaPlayer.create(this, R.raw.thunder);
        getCamera();
        handler = new Handler();
        handler.post(loop);
    }

    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.d("Camera error ", e.getMessage());
            }
        }
    }

    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }
    }

    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void handleActionTurnOnFlashLight(Context context) {
        try {

            CameraManager manager = null;
            manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            String[] list = manager.getCameraIdList();
            manager.setTorchMode(list[0], true);
        } catch (CameraAccessException cae) {
            cae.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void handleActionTurnOffFlashLight(Context context) {
        try {
            CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            manager.setTorchMode(manager.getCameraIdList()[0], false);
        } catch (CameraAccessException cae) {
            cae.printStackTrace();
        }
    }

    private void turnOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            handleActionTurnOnFlashLight(this);
        else
            turnOnFlash();
    }

    private void turnOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            handleActionTurnOffFlashLight(this);
        else
            turnOffFlash();
    }

    private Runnable loop = new Runnable() {
        int i = 0;
        int second = 0;

        @Override
        public void run() {
            try {

                i++;
                if (i % 50 == 0)
                    second++;
                if (second % 3 == 0 && i % 50 == 0) {
                    mp.seekTo(0);
                    mp.start();
                    turnOn();
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mp.pause();
                    turnOff();
                } else if (second % 5 == 0 && i % 50 == 0) {
                    mp1.seekTo(0);
                    mp1.start();
                    turnOn();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    turnOff();


                    turnOn();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    turnOff();

                    turnOn();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    turnOff();

                }

                handler.postDelayed(this, 20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (handler != null)
        {
            handler.removeCallbacks(loop);
            mp1.stop();
            mp.stop();
        }
        if(camera!=null)
            camera.release();
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
    }
}

