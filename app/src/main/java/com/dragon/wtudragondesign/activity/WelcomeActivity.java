package com.dragon.wtudragondesign.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.utils.PreferencesUtils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dragon on 2017/11/26.
 * 欢迎界面
 */

public class WelcomeActivity extends AppCompatActivity {
    private final static int PIC_COMPILE = 1;

    private TextView mTextViewCount;

    private LinearLayout mLlCount;

    private ImageView imageView;

    private static int count = 5;
    private Timer timer;// = new Timer();

    private String firstGuide = "";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Glide.with(WelcomeActivity.this)
                    .load(finalPicUrl)
                    //加载时显示的图片
                    .placeholder(R.mipmap.splash)
                    .error(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(imageView);
        }

    };
    private String finalPicUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏

        firstGuide = PreferencesUtils.getString(this,Const.FIRST_GUIDE);
        //标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_welcome);

        mTextViewCount = findViewById(R.id.circle_text_count);

        mLlCount = findViewById(R.id.ll_count);

        mLlCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHome();
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                }
                finish();
            }
        });
        count = 5;
        imageView = findViewById(R.id.imageView);
        getPic();
        timer = new Timer();

        timer.schedule(task, 1000, 1000);
    }

    public void getHome() {
        if (TextUtils.isEmpty(firstGuide)){
            Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
            startActivity(intent);
        }else {

            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mTextViewCount.setText("" + count);

                    if (count < 1) {
                        getHome();
                        if (timer != null) {
                            timer.cancel();
                            timer.purge();
                        }
                        finish();
                    }
            }
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            count--;
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    //获取天气信息
    private void getPic() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(Const.WEL_PIC)
                        .build();
                Response response = null;
                try {
                    response = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (response!=null)
                    finalPicUrl = response.body().string();
                    if (!TextUtils.isEmpty(finalPicUrl))
                        mHandler.sendEmptyMessage(PIC_COMPILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
