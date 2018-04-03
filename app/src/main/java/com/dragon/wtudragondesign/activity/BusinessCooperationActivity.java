package com.dragon.wtudragondesign.activity;

import android.os.Bundle;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.template.BaseActivity;

/**
 * Created by dragon on 2018/4/3.
 * 商务合作界面
 */

public class BusinessCooperationActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_business_copperation);
        setTitle("商务合作");
        hideRightText();
        init();
    }

    @Override
    public void init() {

    }

    @Override
    public void initData() {

    }
}
