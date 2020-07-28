package com.example.carrent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Variables
    private static int SPLASH_TIME= 2500;
    ImageView carImage;
    TextView logo, slogan;
    //Animations
    Animation topAnimation,bottomAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Field initialization
        carImage = findViewById(R.id.carImage);
        logo = findViewById(R.id.logo);
        slogan = findViewById(R.id.slogan);

        //Animations
        topAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_animation);

        //Set Animations to fields
        carImage.setAnimation(topAnimation);
        logo.setAnimation(bottomAnimation);
        slogan.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);

                Pair[] pairs =new Pair[2];
                pairs[0] = new Pair<View, String>(carImage,"logo_image"); //transitionName="logo_image" in activity_main.xml
                pairs[1] = new Pair<View, String>(logo,"logo_text"); //transitionName="logo_image" in activity_main.xml

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent,options.toBundle());
                finish();
            }
        },SPLASH_TIME);
    }
}