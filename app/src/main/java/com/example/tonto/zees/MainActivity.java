package com.example.tonto.zees;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.tonto.zees.sounds.SoundManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int NUM_PAGES = 10;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;
    private ImageView top;
    private TextView page;
    private TabLayout tabDots;
    private ImageView background;
    private Toolbar toolbar;
    private Window window;
    private int currentButtonTint;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page = (TextView) findViewById(R.id.page_title);
        page.setText(title[0]);
        top = (ImageView) findViewById(R.id.top);
        background = (ImageView) findViewById(R.id.background);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        window = this.getWindow();

        setBackground(0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setStatusbarColor(0);
        }

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            ((ImageView) v).setColorFilter(Color.WHITE);
            seekBar.setVisibility(View.INVISIBLE);
            Iterator<String> iteratorName = playingLargeSoundsName.iterator();
            Iterator<MediaPlayer> iteratorMedia = playingLargeSounds.iterator();
            while (iteratorName.hasNext()) {
                String name = iteratorName.next();
                MediaPlayer mediaPlayer = iteratorMedia.next();
                if (name.equals(v.getTag().toString())) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.pause();
                    iteratorMedia.remove();
                    iteratorName.remove();
                }
            }
        } else {
            v.setSelected(true);
            seekBar.setProgress(50);
            seekBar.setVisibility(View.VISIBLE);
            if (!listenedSeekBar.contains(seekBar)) {
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        System.out.println("Progress: " + progress);
                        String seekBarName = seekBar.getResources().getResourceEntryName(seekBar.getId());
                        String subSeekBarName = seekBarName.substring(3);
                        System.out.println("SeekBar ID: " + subSeekBarName);
                        int index = createdSounds.indexOf(subSeekBarName);
                        float volume = (float) progress / 100;
                        System.out.println(String.format("Volume : %f", volume));
                        createdMedia.get(index).setVolume(volume, volume);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                listenedSeekBar.add(seekBar);
            }
            ((ImageView) v).setColorFilter(currentButtonTint);
            if (!createdSounds.contains(v.getTag().toString())) {
                int id = getResources().getIdentifier(v.getTag().toString(), "raw", getPackageName());
                MediaPlayer mediaPlayer = MediaPlayer.create(this, id);
                mediaPlayer.setVolume(0.5f, 0.5f);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                playingLargeSounds.add(mediaPlayer);
                playingLargeSoundsName.add(v.getTag().toString());
                createdSounds.add(v.getTag().toString());
                createdMedia.add(mediaPlayer);
            } else {
                int index = createdSounds.indexOf(v.getTag().toString());
                System.out.println("created Sound tag: " + v.getTag().toString());
                MediaPlayer mediaPlayer = createdMedia.get(index);
                mediaPlayer.setVolume(0.5f, 0.5f);
                mediaPlayer.start();
                playingLargeSounds.add(mediaPlayer);
                playingLargeSoundsName.add(v.getTag().toString());
            }
        }
    }

    public void seekBarClicked() {

    }

}
