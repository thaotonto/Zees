package com.example.tonto.zees;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class ShowColorActivity extends AppCompatActivity {

    private ImageView backGround;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_color);
        backGround= (ImageView)findViewById(R.id.iv_bg);
        Bundle bundle= getIntent().getExtras();
        int color= bundle.getInt("COlOR TO SHOW");
        Context context = backGround.getContext();
        int id = context.getResources().getIdentifier("color"+color+"_bg", "drawable", context.getPackageName());
        backGround.setImageResource(id);

    }
}
