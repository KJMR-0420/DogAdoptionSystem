package com.example.dogadoptionsystem.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dogadoptionsystem.Dashboard.Dashboard;
import com.example.dogadoptionsystem.MainActivity;
import com.example.dogadoptionsystem.R;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 8000;

    Animation top,bottom;
    ImageView splashimage;
    TextView splashtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

        top = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        splashimage = findViewById(R.id.splashpic);
        splashtext = findViewById(R.id.splasttext);

        splashimage.setAnimation(top);
        splashtext.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}