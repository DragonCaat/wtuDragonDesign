package com.dragon.wtudragondesign.activity;

import android.os.Bundle;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.template.BaseActivity;

public class FindPassActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_find_pass);
        hideRightText();
        setTitle("找回密码");
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
