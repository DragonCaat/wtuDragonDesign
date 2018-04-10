package com.dragon.wtudragondesign.activity;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.fragment.FragmentMain;
import com.dragon.wtudragondesign.fragment.FragmentMessage;
import com.dragon.wtudragondesign.fragment.FragmentMy;
import com.dragon.wtudragondesign.receiver.NetBroadcastReceiver;
import com.dragon.wtudragondesign.service.NewMessageService;
import com.dragon.wtudragondesign.utils.NetUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, EMMessageListener, NetBroadcastReceiver.NetEvent {
    public static NetBroadcastReceiver.NetEvent event;
    /**
     * 网络类型
     */
    private int netMobile;

    private DrawerLayout mDrawerLayout;
    private CircleImageView mCircleMenu;
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
    //三个fragment
    private FragmentMessage fragmentMessage;
    private FragmentMain fragmentMain;
    private FragmentMy fragmentMy;

    //nav头布局的控件
    private RelativeLayout mRlHead;

    private Toolbar toolbar;

    //显示位置的textView
    private TextView mTvLocation;

    public final static int NOTIFICATION = 3;// 新消息；

    public final static int EXIT_APPLICATION = 0;// 新消息；

    private static Boolean isExit = false;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NOTIFICATION:

                    break;
                case EXIT_APPLICATION:
                    //finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mCircleMenu = findViewById(R.id.circle_menu);

        mCircleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        init();
        initData();
        inspectNet();

        //开启服务
        Intent intent = new Intent(this, NewMessageService.class);
        startService(intent);

        EMClient.getInstance().chatManager().addMessageListener(this);
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
        //侧滑栏的头布局
        mRlHead = (RelativeLayout) navView.getHeaderView(0);
        mRlHead.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar1);

        mLlHome.performClick();
    }

    public void initData() {
//        fragmentMessage = new FragmentMessage();
//        fragmentMain = new FragmentMain();
//        fragmentMy = new FragmentMy();
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_contain, fragmentMain).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_head:
                Intent intent = new Intent(this, UserDataActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 侧滑栏的点击事件
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_call:
                Toast.makeText(this, "打电话", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDrawerLayout != null)
            mDrawerLayout.closeDrawers();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (view.getId() == R.id.ll_bottom_main) {
                showFragment(fragmentMain, FragmentMain.class);
                mIvHome.setImageResource(R.mipmap.home_press);
                mTvHome.setTextColor(getResources().getColor(R.color.colorPrimary));
                //fragmentTransaction.replace(R.id.main_contain, fragmentMain).commit();

                toolbar.setVisibility(View.VISIBLE);
                mTvTitle.setText("主页");
                mCircleMenu.setVisibility(View.VISIBLE);
            } else {
                mIvHome.setImageResource(R.mipmap.home_normal);
                mTvHome.setTextColor(getResources().getColor(R.color.gray));
            }

            if (view.getId() == R.id.ll_bottom_message) {
                showFragment(fragmentMessage, FragmentMessage.class);
                mIvMessage.setImageResource(R.mipmap.message_press);
                mTvMessage.setTextColor(getResources().getColor(R.color.colorPrimary));
                //fragmentTransaction.replace(R.id.main_contain, fragmentMessage).commit();
                toolbar.setVisibility(View.VISIBLE);
                mTvTitle.setText("消息");
                mCircleMenu.setVisibility(View.GONE);
            } else {
                mIvMessage.setImageResource(R.mipmap.message_normal);
                mTvMessage.setTextColor(getResources().getColor(R.color.gray));
            }

            if (view.getId() == R.id.ll_bottom_my) {
                showFragment(fragmentMy, FragmentMy.class);
                mIvMy.setImageResource(R.mipmap.my_press);
                mTvMy.setTextColor(getResources().getColor(R.color.colorPrimary));
                // fragmentTransaction.replace(R.id.main_contain, fragmentMy).commit();
                toolbar.setVisibility(View.GONE);
            } else {
                mIvMy.setImageResource(R.mipmap.my_normal);
                mTvMy.setTextColor(getResources().getColor(R.color.gray));
            }
        }
    };

    private void showFragment(Fragment fragment, Class<?> cl) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        hideFragments(transaction);
        if (fragment == null) {
            if (cl == FragmentMain.class) {
                if (fragmentMain == null) {
                    fragmentMain = new FragmentMain();
                    transaction.remove(fragmentMain)
                            .add(R.id.main_contain, fragmentMain).show(fragmentMain);
                }
            } else if (cl == FragmentMessage.class) {
                if (fragmentMessage == null) {
                    fragmentMessage = new FragmentMessage();
                    transaction.remove(fragmentMessage)
                            .add(R.id.main_contain, fragmentMessage)
                            .show(fragmentMessage);
                }
            } else if (cl == FragmentMy.class) {
                if (fragmentMy == null) {
                    fragmentMy = new FragmentMy();
                    transaction.remove(fragmentMy)
                            .add(R.id.main_contain, fragmentMy)
                            .show(fragmentMy);
                }
            }
        } else {
            if (cl == FragmentMain.class)
                transaction.show(fragmentMain);
            if (cl == FragmentMessage.class)
                transaction.show(fragmentMessage);
            if (cl == FragmentMy.class)
                transaction.show(fragmentMy);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (fragmentMain != null) {
            transaction.hide(fragmentMain);
        }
        if (fragmentMessage != null) {
            transaction.hide(fragmentMessage);
        }
        if (fragmentMy != null) {
            transaction.hide(fragmentMy);
        }
    }

    /**
     * 环信消息监听
     */
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        //在此处理扩展消息
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
        if (FragmentMessage.easeConversationList != null)
            FragmentMessage.easeConversationList.refresh();
    }

    @Override
    public void onMessageRead(List<EMMessage> list) {
        if (FragmentMessage.easeConversationList != null)
            FragmentMessage.easeConversationList.refresh();
    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentMain = null;
        fragmentMessage = null;
        fragmentMy = null;

        isExit = false;
    }

    /**
     * 监听到有网络变化的时候执行
     */
    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        boolean netConnect = isNetConnect();
        if (!netConnect) {
            //无网络
            Toast.makeText(this, "当前设备无网络", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtils.getNetWorkState(this);
        return isNetConnect();
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;

        }
        return false;
    }

    /**
     * 展示snackBar
     */
    @SuppressLint("ResourceAsColor")
    private void showSnackBar(View view) {
        final Snackbar snackbar = Snackbar.make(view, "当前设备无网络，请确认网络状态良好", Snackbar.LENGTH_LONG);
        snackbar.setAction("知道了", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在此做跳转界面，编写发布界面
                snackbar.dismiss();
            }
        }).show();
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(false);
//            return true;
//        }
//        return super.dispatchKeyEvent(event);
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 连续点击两次返回退出
     */
    protected void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(EXIT_APPLICATION, 2000);
        } else {
            finish();
        }
    }
}
