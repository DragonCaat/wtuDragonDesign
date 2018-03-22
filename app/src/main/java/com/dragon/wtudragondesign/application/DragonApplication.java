package com.dragon.wtudragondesign.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import io.rong.imkit.RongIM;

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
        //融云的全局初始化
        RongIM.init(this);
    }
}