package com.nocom.capstone_stage2;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * Created by Moha on 1/19/2018.
 */

public class Splash extends AwesomeSplash {


    @Override
    public void initSplash(ConfigSplash configSplash) {


        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.color_Primary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(2000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.tennis); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Landing); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Path
        // configSplash.setPathSplash(SyncStateContract.Constants._COUNT); //set path String
        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(3000);
        configSplash.setPathSplashStrokeSize(6); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.color_PrimaryDark); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(3000);
        configSplash.setPathSplashFillColor(R.color.color_Primary); //path object filling color


        //Customize Title
        configSplash.setTitleSplash(getString(R.string.welcomescreen));
        configSplash.setTitleTextColor(R.color.colors_secondary);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
        //  configSplash.setTitleFont("fonts/myfont.ttf"); //provide string to your font located in assets/fonts/

    }


    @Override
    public void animationsFinished() {

        Intent home = new Intent(Splash.this, MainActivity.class);
        startActivity(home);


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
