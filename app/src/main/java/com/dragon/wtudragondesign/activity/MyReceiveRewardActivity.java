package com.dragon.wtudragondesign.activity;

import android.os.Bundle;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.template.BaseActivity;

public class MyReceiveRewardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_my_receive_reward);
        setTitle("我接受的悬赏");
        hideRightText();
    }

    @Override
    public void init() {

    }

    @Override
    public void initData() {

    }
}
