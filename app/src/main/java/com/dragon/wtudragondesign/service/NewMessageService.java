package com.dragon.wtudragondesign.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.activity.MainActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * 监听有新消息到达的服务
 */
public class NewMessageService extends Service {

    private static final int NEWMESSAGE = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == NEWMESSAGE)
                notification();
        }
    };

    public NewMessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //添加新消息监听
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            mHandler.sendEmptyMessage(NEWMESSAGE);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        //服务销毁的时候移除监听
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @SuppressWarnings("deprecation")
    private void notification() {
        int unMsgCount = getUnreadMsgCountTotal();
        String mess = "您有" + unMsgCount + "条未读的新消息";
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        Intent clickIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification1 = new NotificationCompat.Builder(this)
                .setContentTitle("毕业设计")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentText(mess)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                .build();
        notification1.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1000, notification1);
    }
    // 获取未读消息总数
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;

        for (EMConversation conversation : EMClient.getInstance().chatManager()
                .getAllConversations().values()) {
            unreadMsgCountTotal += conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal;
    }

}
