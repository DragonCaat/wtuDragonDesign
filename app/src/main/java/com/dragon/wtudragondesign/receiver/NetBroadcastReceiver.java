package com.dragon.wtudragondesign.receiver;

/**
 * Created by 梁 on 2017/9/18.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.dragon.wtudragondesign.activity.MainActivity;
import com.dragon.wtudragondesign.utils.NetUtils;


/**
 * Created by dragon on 2017/9/8.
 * 监听网络拜变化的广播
 */

public class NetBroadcastReceiver extends BroadcastReceiver {

    private NetEvent netEvent = MainActivity.event;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            //检查网络状态的类型
            int netWrokState = NetUtils.getNetWorkState(context);
            if (netEvent != null)
                // 接口回传网络状态的类型
                netEvent.onNetChange(netWrokState);
        }
    }
    //自定义接口
    public interface NetEvent{
        void onNetChange(int netMobile);
    }
}
