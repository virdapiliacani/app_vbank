package com.virda.appvbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread timer = new Thread(){
            public void run(){
             try{
                 sleep(3000);
             }catch (Exception ex){
                ex.printStackTrace();
             }finally {
                 Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                 startActivity(intent);
             }
            }
        };
        timer.start();
    }
}