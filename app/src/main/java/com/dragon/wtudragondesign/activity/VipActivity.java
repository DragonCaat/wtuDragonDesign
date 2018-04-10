package com.dragon.wtudragondesign.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.adapter.ViewPagerAdapter;
import com.dragon.wtudragondesign.fragment.FragmentVipQuery;
import com.dragon.wtudragondesign.fragment.FragmentVipRecharge;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.view.NavitationLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员界面
 * */
public class VipActivity extends BaseActivity {

    private NavitationLayout navitationLayout;
    private ViewPager viewPager;
    private String[] titles = { "会员充值", "会员查询" };

    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments;

    private FragmentVipRecharge mVipRecharge;
    private FragmentVipQuery mVipQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_vip);
        hideRightText();
        setTitle("会员");
        init();
        initData();
    }

    @Override
    public void init() {
        mVipRecharge = new FragmentVipRecharge();
        mVipQuery = new FragmentVipQuery();
        initShowNavigationBar();
    }

    @Override
    public void initData() {

    }

    /**
     * 展示搜索标题栏
     */
    private void initShowNavigationBar() {
        navitationLayout = fv(R.id.bar1);
        viewPager = fv(R.id.viewpager1);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);

        fragments = new ArrayList<>();
        fragments.add(mVipRecharge);
        fragments.add(mVipQuery);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(viewPagerAdapter);

        navitationLayout.setViewPager(this, titles, viewPager, R.color.black, R.color.colorPrimary, 16, 16, 0, 0, true);
        navitationLayout.setBgLine(this, 1, R.color.white);
        navitationLayout.setNavLine(this, 3, R.color.colorPrimary, 0);

    }
}
