package com.nocom.capstone_stage2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Moha on 1/19/2018.
 */

public class splash extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;

    private static int  splashtime = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ImageView imageView = (ImageView)findViewById(R.id.myimage);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.cordi);


        AdView mAdView = (AdView)findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);





        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Welcome to Tennis World", Snackbar.LENGTH_LONG);

        snackbar.show();


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run (){

                Intent home = new Intent(splash.this,MainActivity.class);
                startActivity(home);

                finish();
            }
        },splashtime);


    }
}
