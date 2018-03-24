package com.dragon.wtudragondesign.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dragon.wtudragondesign.R;

/**
 * Created by dragon on 2018/3/24.
 * 带快递悬赏详情界面
 */
public class CourierDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton mFabChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_detail);
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
        collapsingToolbar.setTitle("悬赏详情");
        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER_VERTICAL);
        ImageView bg = findViewById(R.id.courier_detail_background);
        Glide.with(this).load(R.mipmap.ti).into(bg);

        mFabChat = findViewById(R.id.fab_photo);
        mFabChat.setOnClickListener(this);
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
            case R.id.fab_photo:
                Toast.makeText(this,"求别点",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
