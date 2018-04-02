package com.dragon.wtudragondesign.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragon.wtudragondesign.R;

/**
 * Created by Dragon on 2018/4/2.
 * 普通悬赏的全部数据
 */

public class FragmentVipReward extends Fragment{

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vip_reward, container, false);
        init();
        initData();
        return view;
    }

    private void init(){

    }

    private void initData(){

    }
}
