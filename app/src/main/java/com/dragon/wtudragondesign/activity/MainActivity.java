package com.dragon.wtudragondesign.activity;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.application.DragonApplication;
import com.dragon.wtudragondesign.fragment.FragmentMain;
import com.dragon.wtudragondesign.fragment.FragmentMessage;
import com.dragon.wtudragondesign.fragment.FragmentMy;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private TextView mTvMenu;
    private NavigationView navView;

    private FrameLayout mFlContainer;

    //底部的三个按钮
    private LinearLayout mLlHome;
    private ImageView mIvHome;
    private TextView mTvHome;

    private LinearLayout mLlMessage;
    private ImageView mIvMessage;
    private TextView mTvMessage;

    private LinearLayout mLlMy;
    private ImageView mIvMy;
    private TextView mTvMy;

    //标题
    private TextView mTvTitle;

    private FragmentMessage fragmentMessage;
    private FragmentMain fragmentMain;
    private FragmentMy fragmentMy;

    //nav的控件
    private RelativeLayout mRlHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        mTvMenu = findViewById(R.id.tv_menu);

        mTvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        init();
        initData();
    }

    public void init() {
        mFlContainer = findViewById(R.id.main_contain);

        mLlHome = findViewById(R.id.ll_bottom_main);
        mLlHome.setOnClickListener(listener);
        mIvHome = findViewById(R.id.iv_bottom_main);
        mTvHome = findViewById(R.id.tv_bottom_main);

        mLlMessage = findViewById(R.id.ll_bottom_message);
        mLlMessage.setOnClickListener(listener);
        mIvMessage = findViewById(R.id.iv_bottom_message);
        mTvMessage = findViewById(R.id.tv_bottom_message);

        mLlMy = findViewById(R.id.ll_bottom_my);
        mLlMy.setOnClickListener(listener);
        mIvMy = findViewById(R.id.iv_bottom_my);
        mTvMy = findViewById(R.id.tv_bottom_my);

        mTvTitle = findViewById(R.id.tv_head_title);

//        mRlHead = findViewById(R.id.nav_head);
//        mRlHead.setOnClickListener(this);

    }

    public void initData() {
        fragmentMessage = new FragmentMessage();
        fragmentMain = new FragmentMain();
        fragmentMy = new FragmentMy();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_contain, fragmentMain).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_head:
                Toast.makeText(this, "测试", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    /**
     * 侧滑栏的点击事件
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_call:
                Toast.makeText(this, "测试+1", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDrawerLayout.closeDrawers();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (view.getId() == R.id.ll_bottom_main) {
                mIvHome.setImageResource(R.mipmap.home_press);
                mTvHome.setTextColor(getResources().getColor(R.color.colorPrimary));
                fragmentTransaction.replace(R.id.main_contain, fragmentMain).commit();
                mTvTitle.setText("主页");
            } else {
                mIvHome.setImageResource(R.mipmap.home_normal);
                mTvHome.setTextColor(getResources().getColor(R.color.gray));
            }

            if (view.getId() == R.id.ll_bottom_message) {
                mIvMessage.setImageResource(R.mipmap.message_press);
                mTvMessage.setTextColor(getResources().getColor(R.color.colorPrimary));
                fragmentTransaction.replace(R.id.main_contain, fragmentMessage).commit();
                mTvTitle.setText("消息");
            } else {
                mIvMessage.setImageResource(R.mipmap.message_normal);
                mTvMessage.setTextColor(getResources().getColor(R.color.gray));
            }

            if (view.getId() == R.id.ll_bottom_my) {
                mIvMy.setImageResource(R.mipmap.my_press);
                mTvMy.setTextColor(getResources().getColor(R.color.colorPrimary));
                fragmentTransaction.replace(R.id.main_contain, fragmentMy).commit();
                mTvTitle.setText("我的");
            } else {
                mIvMy.setImageResource(R.mipmap.my_normal);
                mTvMy.setTextColor(getResources().getColor(R.color.gray));
            }
        }
    };
}
