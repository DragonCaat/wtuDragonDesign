package com.dragon.wtudragondesign.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dragon.wtudragondesign.R;

public class UserDataActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvVip;
    private TextView mTvFriends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        Toolbar toolbar = findViewById(R.id.courier_detail_toolbar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("我的资料");
        collapsingToolbar.setCollapsedTitleGravity(Gravity.LEFT);
        collapsingToolbar.setExpandedTitleGravity(Gravity.CENTER | Gravity.TOP);

        ImageView bg = findViewById(R.id.courier_detail_background);
        Glide.with(this).load(R.mipmap.ta).into(bg);


        mTvVip = findViewById(R.id.user_vip);
        mTvVip.setOnClickListener(this);

        mTvFriends = findViewById(R.id.user_friends);
        mTvFriends.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_vip:
                Intent intent = new Intent(this,VipActivity.class);
                startActivity(intent);
                break;
            case R.id.user_friends:
                Intent intent1 = new Intent(this,ActivityFriends.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
