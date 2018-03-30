package com.dragon.wtudragondesign.activity;

import android.os.Bundle;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.template.BaseActivity;

public class MyPublishRewardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_my_publish_reward);
        hideRightText();
        setTitle("我发布的悬赏");
        init();
        initData();
    }

    @Override
    public void init() {

    }

    @Override
    public void initData() {

    }
}
