package com.dragon.wtudragondesign.activity;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.fragment.FragmentMain;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener , NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private TextView mTvMenu;
    private NavigationView navView;


    private FrameLayout mFlContainer;

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
    }

    public void initData() {
        FragmentMain fragmentMain = new FragmentMain();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_contain, fragmentMain).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }
    }

    /**
     * 侧滑栏的点击事件
     * */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
//            case R.id.nav_weather:
//                skipPage(LocationActivity.class);
//
//            case R.id.nav_news:
//
//                break;

        }
        return true;
    }
    @Override
    protected void onStop() {
        super.onStop();
        mDrawerLayout.closeDrawers();
    }

}
