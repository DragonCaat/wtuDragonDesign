package com.dragon.wtudragondesign.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Dragon on 2018/3/20.
 * 我的个人application
 */

public class DragonApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        // the following line is important
        Fresco.initialize(getApplicationContext());
    }
}