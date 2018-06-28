package com.fooddepot;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.fooddepot.activity.RoleActivity;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        ImageView logo = (ImageView)findViewById(R.id.logo);

        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

//Setup anim with desired properties
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
        anim.setDuration(40000); //Put desired duration per anim cycle here, in milliseconds

//Start animation
        logo.startAnimation(anim);
        Thread newThread = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(2000);
                    Intent roleIntent = new Intent(getApplicationContext(), RoleActivity.class);
                    startActivity(roleIntent);
                    overridePendingTransition(0, 0);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        newThread.start();
    }
}
