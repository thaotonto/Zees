package com.example.tonto.zees;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class ShowSimpleColorActivity extends AppCompatActivity {
    private static int color = 1;
    private ImageView backGround;
    private ImageView simpleColor1;
    private ImageView simpleColor2;
    private ImageView simpleColor3;
    private ImageView simpleColor4;
    private ImageView simpleColor5;
    private ImageView simpleColor6;
    private ImageView simpleColor7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_simple_color);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        backGround = (ImageView) findViewById(R.id.simple_background);
        simpleColor1 = (ImageView) findViewById(R.id.simple_color1);
        simpleColor2 = (ImageView) findViewById(R.id.simple_color2);
        simpleColor3 = (ImageView) findViewById(R.id.simple_color3);
        simpleColor4 = (ImageView) findViewById(R.id.simple_color4);
        simpleColor5 = (ImageView) findViewById(R.id.simple_color5);
        simpleColor6 = (ImageView) findViewById(R.id.simple_color6);
        simpleColor7 = (ImageView) findViewById(R.id.simple_color7);
        simpleColor1.setOnClickListener(listener);
        simpleColor2.setOnClickListener(listener);
        simpleColor3.setOnClickListener(listener);
        simpleColor4.setOnClickListener(listener);
        simpleColor5.setOnClickListener(listener);
        simpleColor6.setOnClickListener(listener);
        simpleColor7.setOnClickListener(listener);
        Context context = backGround.getContext();
        int id = context.getResources().getIdentifier("color" + color + "_bg", "drawable", context.getPackageName());
        backGround.setImageResource(id);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.simple_color1:
                    color = 1;
                    break;
                case R.id.simple_color2:
                    color = 2;
                    break;
                case R.id.simple_color3:
                    color = 3;
                    break;
                case R.id.simple_color4:
                    color = 4;
                    break;
                case R.id.simple_color5:
                    color = 5;
                    break;
                case R.id.simple_color6:
                    color = 6;
                    break;
                case R.id.simple_color7:
                    color = 7;
                    break;
            }
            setColor();
        }
    };
    private void setColor()
    {
        Context context = backGround.getContext();
        int id = context.getResources().getIdentifier("color" + color + "_bg", "drawable", context.getPackageName());
        backGround.setImageResource(id);
    }
}
