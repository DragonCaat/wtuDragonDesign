package com.dragon.wtudragondesign.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.adapter.ViewPagerAdapter;
import com.dragon.wtudragondesign.fragment.FragmentMain;
import com.dragon.wtudragondesign.fragment.FragmentNormalReward;
import com.dragon.wtudragondesign.fragment.FragmentVipReward;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.view.NavitationLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 悬赏界面
 */
public class RewardActivity extends BaseActivity {

    private NavitationLayout navitationLayout;
    private ViewPager viewPager;
    private String[] titles = { "普通悬赏", "会员悬赏" };

    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments;

    private FragmentNormalReward fragmentNormalReward;
    private FragmentVipReward fragmentVipReward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_reward);
        setTitle("悬赏");
        hideRightText();
        init();
        initData();
    }

    @Override
    public void init() {
        fragmentNormalReward = new FragmentNormalReward();
        fragmentVipReward = new FragmentVipReward();
        /**
         * 展示搜索标题栏
         */
        initShowNaviationBar();
    }

    @Override
    public void initData() {

    }

    /**
     * 展示搜索标题栏
     */
    private void initShowNaviationBar() {
        navitationLayout = findViewById(R.id.bar1);
        viewPager =  findViewById(R.id.viewpager1);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);

        fragments = new ArrayList<>();
        fragments.add(fragmentNormalReward);
        fragments.add(fragmentVipReward);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(viewPagerAdapter);

        navitationLayout.setViewPager(this, titles, viewPager, R.color.black, R.color.colorPrimary, 14, 16, 0, 0, true);
        navitationLayout.setBgLine(this, 1, R.color.white);
        navitationLayout.setNavLine(this, 3, R.color.colorPrimary, 0);

    }
}
