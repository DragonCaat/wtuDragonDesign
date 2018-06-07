package com.dragon.wtudragondesign.application;


import android.support.multidex.MultiDexApplication;

import com.dragon.wtudragondesign.bean.Const;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by Dragon on 2018/3/20.
 * 我的个人application
 */

public class DragonApplication extends MultiDexApplication implements
        EaseUI.EaseUserProfileProvider {

    public static DragonApplication application;
    private Map<String, EaseUser> usrList = new HashMap<String, EaseUser>();
    @Override
    public void onCreate() {
        super.onCreate();
        // the following line is important
        Fresco.initialize(getApplicationContext());

        //环信相关的初始化
        EMOptions options = new EMOptions();
       // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(true);
       // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发          者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
       // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        //初始化
        EMClient.getInstance().init(getApplicationContext(), options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        //EMClient.getInstance().setDebugMode(true);

        //easeUI的初始化
        EaseUI.getInstance().init(getApplicationContext(), options);

        //EaseUI.getInstance().setUserProfileProvider(this);
    }

    public static DragonApplication getInstance() {
        if (null == application) {
            application = new DragonApplication();
        }
        return application;
    }

    @Override
    public void onLowMemory() {
        System.gc();
        super.onLowMemory();
    }

    public Map<String, EaseUser> getUsrList() {
        return usrList;
    }

    @Override
    public EaseUser getUser(String username) {
        

        return null;
    }
}