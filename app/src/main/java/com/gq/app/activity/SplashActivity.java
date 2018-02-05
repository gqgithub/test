package com.gq.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gq.app.MainActivity;
import com.gq.app.MyApp;
import com.gq.app.R;

public class SplashActivity extends AppCompatActivity {

    TextView tv_app_version;
    TextView shadeBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv_app_version= (TextView) findViewById(R.id.tv_app_version);
        shadeBg= (TextView) findViewById(R.id.shade_bg);


//        int currentSkinType = SkinManager.getCurrentSkinType(this);
//        if (currentSkinType == SkinManager.THEME_NIGHT) {
//            shadeBg.setVisibility(View.VISIBLE);
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 2000);

        tv_app_version.setText(String.valueOf("V " + MyApp.getVersionName()));


    }

}
