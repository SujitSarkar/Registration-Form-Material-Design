package com.example.carrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class FlashScreen2 extends AppCompatActivity {

    private static int SPLASH_TIME = 3000;
    //animation...
    Animation top2,bottom2,middleAnimation;
    //Fields...
    TextView aaa,tagLine;
    View yellowLine,blueLine,redLine,yellowLine2,greenLine,blueLine2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide Status Bar...
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_flash_screen2);

        //Initialize Animations...
        top2 = AnimationUtils.loadAnimation(this, R.anim.top2);
        bottom2 = AnimationUtils.loadAnimation(this, R.anim.bottom2);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        yellowLine = findViewById(R.id.yellowLine);
        blueLine = findViewById(R.id.blueLine);
        redLine = findViewById(R.id.redLine);
        yellowLine2 = findViewById(R.id.yellowLine2);
        greenLine = findViewById(R.id.greenLine);
        blueLine2 = findViewById(R.id.blueLine2);
        aaa = findViewById(R.id.aaa);
        tagLine = findViewById(R.id.tagLine);

        //Set Animation to Fields...
        yellowLine.setAnimation(top2);
        blueLine.setAnimation(top2);
        redLine.setAnimation(top2);
        yellowLine2.setAnimation(top2);
        greenLine.setAnimation(top2);
        blueLine2.setAnimation(top2);
        aaa.setAnimation(middleAnimation);
        tagLine.setAnimation(bottom2);

        //SPLASH Screen...
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FlashScreen2.this,UserProfile.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME);
    }
}