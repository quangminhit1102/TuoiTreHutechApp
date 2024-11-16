package com.itgenz.studentmanage.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.itgenz.studentmanage.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Animation var
        Animation topAnim , bottomAnim ;
        ImageView image ;
        TextView slogan ;

        super.onCreate(savedInstanceState);
        // hide action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        //Hooks
        image = findViewById(R.id.imgDoanVien);
        slogan = findViewById(R.id.slogan);
        image.setAnimation(bottomAnim);
        slogan.setAnimation(topAnim);
        //login Delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}