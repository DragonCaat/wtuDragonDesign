package com.dragon.wtudragondesign.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.activity.AddNewCourierActivity;
import com.dragon.wtudragondesign.adapter.CourierAdapter;
import com.dragon.wtudragondesign.bean.CourierEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragon on 2018/3/20.
 * 主页的 Fragment
 */

public class FragmentMain extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private RecyclerView mRvMain;
    private CourierAdapter courierAdapter;
    private List<CourierEntity> mCourierList = new ArrayList<>();

    private SwipeRefreshLayout swipeRefresh;

    private FloatingActionButton mFabButton;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container,false);
        initCourier();
        init();
        initData();
        return view;
    }

    public void init() {
        mRvMain = view.findViewById(R.id.rv_main);
        swipeRefresh = view.findViewById(R.id.swipe_courier);
        mFabButton = view.findViewById(R.id.fab_add);
    }

    public void initData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        mRvMain.setLayoutManager(layoutManager);
        courierAdapter = new CourierAdapter(mCourierList);
        mRvMain.setAdapter(courierAdapter);

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);

        mFabButton.setOnClickListener(this);
    }

    private void initCourier() {
        mCourierList.clear();
        for (int i = 0; i < 10; i++) {
            CourierEntity entity = new CourierEntity();
            entity.setContent("我是内容" + i);
            entity.setTitle("我是标题" + i);
            entity.setPicUrl("http://ww3.sinaimg.cn/mw1024/7a965c92jw1eegiyu7p6vj203c01vjr9.jpg");
            mCourierList.add(entity);
        }
    }

    @Override
    public void onRefresh() {
        refreshCourier();
    }

    /**
     * 模拟刷新数据界面
     */
    private void refreshCourier() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add:
                showSnackBar(view);
                break;
            default:
                break;
        }
    }

    /**
     * 展示snackBar,让用户确认是否发布消息
     */
    @SuppressLint("ResourceAsColor")
    private void showSnackBar(View view) {
        Snackbar snackbar = Snackbar.make(view, "确认要发布悬赏带快递？", Snackbar.LENGTH_LONG);
        snackbar.setAction("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在此做跳转界面，编写发布界面
                Intent intent = new Intent(getActivity(), AddNewCourierActivity.class);
                startActivity(intent);
            }
        }).show();
    }
}
