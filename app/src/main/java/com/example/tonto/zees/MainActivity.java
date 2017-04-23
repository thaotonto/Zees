package com.example.tonto.zees;

import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

import android.widget.TimePicker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int NUM_PAGES = 10;
    private int MAX_SOUND = 3;
    private int countSound = 0;
    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;
    private ImageView top;
    private TextView page;
    private TabLayout tabDots;
    private ImageView background;
    private Toolbar toolbar;
    private Window window;
    private int currentButtonTint;
    private TextView reserved;
    private long delayms;
    //    private Timer timer;
    private Thread timerThread;
    private SeekBar sbMasterVol;
    private ImageView iconMasterVol;
    private TextView textMasterVol;
    private AudioManager am;
    private VolumeChangeObserver volumeChangeObserver;
    private long offsetStart;
    private long offsetEnd;
    private boolean timerEnabled = false;
    private MenuItem timerItem;

    private static ArrayList<MediaPlayer> playingLargeSounds = new ArrayList<>();
    private static ArrayList<MediaPlayer> createdMedia = new ArrayList<>();
    private static ArrayList<String> playingLargeSoundsName = new ArrayList<>();
    private static ArrayList<String> createdSounds = new ArrayList<>();
    private static ArrayList<SeekBar> listenedSeekBar = new ArrayList<>();
    private int[] images = {
            R.drawable.top_rain,
            R.drawable.top_ocean,
            R.drawable.top_water,
            R.drawable.top_nature_night,
            R.drawable.top_nature_day,
            R.drawable.top_air_fire,
            R.drawable.top_music,
            R.drawable.top_oriental,
            R.drawable.top_city,
            R.drawable.top_home
    };

    private int[] backgrounds = {
            R.drawable.background_rain,
            R.drawable.background_ocean,
            R.drawable.background_water,
            R.drawable.background_nature_night,
            R.drawable.background_nature_day,
            R.drawable.background_air_fire,
            R.drawable.background_music,
            R.drawable.background_oriental,
            R.drawable.background_city,
            R.drawable.background_home
    };

    private ColorDrawable[] actionBarColorCodes = {
            new ColorDrawable(Color.parseColor("#ff597f9c")),
            new ColorDrawable(Color.parseColor("#ff749daf")),
            new ColorDrawable(Color.parseColor("#ff899bbf")),
            new ColorDrawable(Color.parseColor("#ff1d3856")),
            new ColorDrawable(Color.parseColor("#ff357829")),
            new ColorDrawable(Color.parseColor("#ffc7b098")),
            new ColorDrawable(Color.parseColor("#ffdc8686")),
            new ColorDrawable(Color.parseColor("#ff8e91d4")),
            new ColorDrawable(Color.parseColor("#ff01579b")),
            new ColorDrawable(Color.parseColor("#ffaca08e"))
    };

    private String[] statusBarColorCodes = {
            "#ff233a5a",
            "#ff437b92",
            "#ff6680a8",
            "#ff0d1f37",
            "#ff194c23",
            "#ff6a594a",
            "#ff573636",
            "#ff3a3589",
            "#ff263238",
            "#ff7e7362"
    };

    private String[] title = {
            "Rain sounds",
            "Ocean sounds",
            "River sounds",
            "Night sounds",
            "Countryside sounds",
            "Wind and fire sounds",
            "Relaxing music",
            "Oriental sounds",
            "City sounds",
            "Home sounds"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        int volume_level = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        System.out.println("Volume LVL: " + volume_level);

        page = (TextView) findViewById(R.id.page_title);
        page.setText(title[0]);
        top = (ImageView) findViewById(R.id.top);
        background = (ImageView) findViewById(R.id.background);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        window = this.getWindow();

        setBackground(0);

        reserved = (TextView) findViewById(R.id.reserved);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setStatusbarColor(0);
        }

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        sbMasterVol = (SeekBar) findViewById(R.id.sb_master_vol);
        iconMasterVol = (ImageView) findViewById(R.id.master_vol_icon);
        textMasterVol = (TextView) findViewById(R.id.master_vol_text);
        sbMasterVol.setMax(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sbMasterVol.setProgress(volume_level);

        if (sbMasterVol.getProgress() != 0) {
            textMasterVol.setText(sbMasterVol.getProgress() + "");
            iconMasterVol.setImageResource(R.drawable.ic_material_volume_on);
        }

        sbMasterVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    iconMasterVol.setImageResource(R.drawable.ic_material_volume_off);
                } else iconMasterVol.setImageResource(R.drawable.ic_material_volume_on);
                textMasterVol.setText(progress + "");
                am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        volumeChangeObserver = new VolumeChangeObserver(new Handler(), sbMasterVol, this);
        this.getApplicationContext().getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, volumeChangeObserver);


        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), this);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new PageTransformer());
        mPager.setOffscreenPageLimit(9);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTop(position);
                setBackground(position);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    setStatusbarColor(position);
                }
                currentButtonTint = Color.parseColor(statusBarColorCodes[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabDots = (TabLayout) findViewById(R.id.tabDots);
        tabDots.setupWithViewPager(mPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!timerThread.isInterrupted()) {
                    //TODO
                    if (timerEnabled == true) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        delayms--;

                        runOnUiThread(new Runnable() {
                            int seconds = (int) (delayms / 1000) % 60;
                            int minutes = (int) ((delayms / (1000 * 60)) % 60);
                            int hours = (int) ((delayms / (1000 * 60 * 60)) % 24);

                            @Override
                            public void run() {
                                reserved.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                                System.out.println(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                            }
                        });

                        if (delayms <= 0) System.exit(0);
                    }
                }
            }
        });
        timerThread.start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_timer) {
            //TODO
            timerItem = item;
            if (timerEnabled == true) {
                timerEnabled = false;
                reserved.setVisibility(View.GONE);
                timerItem.setIcon(R.drawable.ic_timer_white_48dp);
            }
//            if (timer != null) {
//                timer.cancel();
//                timer.purge();
//                timer = null;
//            }
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, 0, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (hourOfDay == 0 && minute == 0) {
                        System.out.println("Invalid time");
                    } else {
                        //Don't do this
//                        android.os.Handler handler = new android.os.Handler(new android.os.Handler.Callback() {
//                            @Override
//                            public boolean handleMessage(Message msg) {
//                                if (msg.what == 0) {
//                                    System.exit(0);
//                                }
//                                return false;
//                            }
//                        });
//                        timer = new Timer();
                        delayms = TimeUnit.HOURS.toMillis(hourOfDay) + TimeUnit.MINUTES.toMillis(minute);
//                        ExitTask exitTask = new ExitTask(delayms, handler);
//                        timer.schedule(exitTask, 1);
                        reserved.setVisibility(View.VISIBLE);
                        timerEnabled = true;
                        timerItem.setIcon(R.drawable.ic_timer_off_white_48dp);
                    }
                }
            }
                    , 0, 0, true);
            timePickerDialog.show();
            return true;
        }

        if (id == R.id.action_light) {
            Intent intent = new Intent(this, LampActivity.class);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        //TODO
        super.onPause();
        if (timerEnabled == true){
            offsetStart = System.currentTimeMillis();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timerEnabled == true){
            offsetEnd = System.currentTimeMillis();
            delayms = delayms - (offsetEnd - offsetStart);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setTop(int position) {
        page.setText(title[position]);
        top.setImageResource(images[position]);
    }

    public void setBackground(int position) {
        background.setImageResource(backgrounds[position]);
        toolbar.setBackground(actionBarColorCodes[position]);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setStatusbarColor(int position) {
        window.setStatusBarColor(Color.parseColor(statusBarColorCodes[position]));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void viewClicked(View v) {
        int resID = getResources().getIdentifier("sb_" + v.getTag(), "id", getPackageName());
        SeekBar seekBar = (SeekBar) findViewById(resID);
        if (v.isSelected()) {
            v.setSelected(false);
            countSound--;
            ((ImageView) v).setColorFilter(Color.WHITE);
            seekBar.setVisibility(View.INVISIBLE);
            Iterator<String> iteratorName = playingLargeSoundsName.iterator();
            Iterator<MediaPlayer> iteratorMedia = playingLargeSounds.iterator();
            Iterator<SeekBar> iteratorSeekBar = listenedSeekBar.iterator();
            while (iteratorName.hasNext()) {
                String name = iteratorName.next();
                iteratorSeekBar.next();
                MediaPlayer mediaPlayer = iteratorMedia.next();
                if (name.equals(v.getTag().toString())) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.pause();
                    mediaPlayer.release();
                    iteratorSeekBar.remove();
                    iteratorMedia.remove();
                    iteratorName.remove();
                }
            }
        } else {
            if (countSound < MAX_SOUND) {
                v.setSelected(true);
                countSound++;
//            if (!listenedSeekBar.contains(seekBar)) {


//            }
                ((ImageView) v).setColorFilter(currentButtonTint);
//            if (!createdSounds.contains(v.getTag().toString())) {

                int id = getResources().getIdentifier(v.getTag().toString(), "raw", getPackageName());
                MediaPlayer mediaPlayer = MediaPlayer.create(this, id);
                mediaPlayer.setVolume(0.5f, 0.5f);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                playingLargeSounds.add(mediaPlayer);
                playingLargeSoundsName.add(v.getTag().toString());

                seekBar.setProgress(50);
                seekBar.setVisibility(View.VISIBLE);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        System.out.println("Progress: " + progress);
                        String seekBarName = seekBar.getResources().getResourceEntryName(seekBar.getId());
                        String subSeekBarName = seekBarName.substring(3);
                        System.out.println("SeekBar ID: " + subSeekBarName);
                        int index = playingLargeSoundsName.indexOf(subSeekBarName);
                        float volume = (float) progress / 100;
                        System.out.println(String.format("Volume : %f", volume));
                        playingLargeSounds.get(index).setVolume(volume, volume);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                            //
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                listenedSeekBar.add(seekBar);
            } else {
                Toast.makeText(MainActivity.this, "Can only play " + MAX_SOUND + " sounds at once!", Toast.LENGTH_SHORT).show();
            }
//                createdSounds.add(v.getTag().toString());
//                createdMedia.add(mediaPlayer);
//            } else {
//                int index = createdSounds.indexOf(v.getTag().toString());
//                System.out.println("created Sound tag: " + v.getTag().toString());
//                MediaPlayer mediaPlayer = createdMedia.get(index);
//                mediaPlayer.setVolume(0.5f, 0.5f);
//                mediaPlayer.start();
//                playingLargeSounds.add(mediaPlayer);
//                playingLargeSoundsName.add(v.getTag().toString());
//            }
        }
    }


}
