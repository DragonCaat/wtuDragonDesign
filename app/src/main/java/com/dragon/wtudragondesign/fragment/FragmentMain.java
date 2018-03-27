package com.dragon.wtudragondesign.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.activity.AddNewCourierActivity;
import com.dragon.wtudragondesign.activity.SearchActivity;
import com.dragon.wtudragondesign.adapter.CourierAdapter;
import com.dragon.wtudragondesign.adapter.JDViewAdapter;
import com.dragon.wtudragondesign.bean.AdverNotice;
import com.dragon.wtudragondesign.bean.CourierEntity;
import com.dragon.wtudragondesign.utils.CornersTransform;
import com.dragon.wtudragondesign.utils.CustomLinearLayoutManager;
import com.dragon.wtudragondesign.view.CustomerViewPagerComponent;
import com.dragon.wtudragondesign.view.JDAdverView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragon on 2018/3/20.
 * 主页的 Fragment
 */

public class FragmentMain extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private RecyclerView mRvMain;
    //快递信息的适配器
    private CourierAdapter courierAdapter;
    private List<CourierEntity> mCourierList = new ArrayList<>();

    private CourierAdapter courierAdapter1;
    private RecyclerView mRvVipCourier;

    //自定义的bannerView
    private CustomerViewPagerComponent customerViewPagerComponent;
    // banner图片资源
    private int[] imgs = {R.mipmap.banner, R.mipmap.banner6, R.mipmap.banner7, R.mipmap.banner8};

    private SwipeRefreshLayout swipeRefresh;

    private FloatingActionButton mFabButton;
    private View view;

    //滚动条适配器
    private JDViewAdapter adapter;
    //滚动条
    private JDAdverView tbView;
    //滚动条所需的数据
    private List<AdverNotice> datas = new ArrayList<>();

    private ImageView mIvVipReward;

    //搜索框
    private TextView mTvSearchMain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initCourier();
        init();
        initData();
        return view;
    }

    public void init() {
        mRvMain = view.findViewById(R.id.rv_main);
        swipeRefresh = view.findViewById(R.id.swipe_courier);
        mFabButton = view.findViewById(R.id.fab_add);

        customerViewPagerComponent = view
                .findViewById(R.id.customerViewPager);
        customerViewPagerComponent.setImages(imgs);

        tbView = view.findViewById(R.id.notice);

        mIvVipReward = view.findViewById(R.id.iv_vip_reward);

        mTvSearchMain = view.findViewById(R.id.tv_search_main);

        mRvVipCourier = view.findViewById(R.id.rv_vip_reward);
    }

    public void initData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        //layoutManager.setScrollEnabled(false);
        mRvMain.setLayoutManager(layoutManager);
        courierAdapter = new CourierAdapter(mCourierList);
        mRvMain.setAdapter(courierAdapter);
        mRvMain.setNestedScrollingEnabled(false);

        //vip悬赏的recycleView
        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 2);
        mRvVipCourier.setLayoutManager(layoutManager1);
        //courierAdapter1 = new CourierAdapter(mCourierList);
        mRvVipCourier.setAdapter(courierAdapter);
        mRvVipCourier.setNestedScrollingEnabled(false);


        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);

        mFabButton.setOnClickListener(this);

        mTvSearchMain.setOnClickListener(this);

        Glide.with(this).load(R.mipmap.vip).transform(new CornersTransform(getContext(), 30)).into(mIvVipReward);

        initJdNotice();
    }

    private void initCourier() {
        mCourierList.clear();
        for (int i = 0; i < 4; i++) {
            CourierEntity entity = new CourierEntity();
            entity.setContent("我是内容" + i);
            entity.setTitle("我是标题" + i);
            entity.setUser("我是发布人" + i);
            entity.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522381888&di=f211133b39e6c1560086398fca108096&imgtype=" +
                    "jpg&er=1&src=http%3A%2F%2Fpic.makepolo.net%2Fnews%2Fallimg%2F20170104%2F1483523748005045.jpg");
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
                        Toast.makeText(getContext(), "没数据，刷个毛啊", Toast.LENGTH_SHORT).show();
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
                //在此做跳转界面，编写发布界面
                Intent intent = new Intent(getActivity(), AddNewCourierActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_search_main:
                //在此做跳转界面，跳转搜索界面
                Intent intent1 = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent1);
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

    //初始化滚动条
    private void initJdNotice() {
        datas.add(new AdverNotice("张承发布了一条悬赏，快去看看吧!!", "最新"));
        datas.add(new AdverNotice("胡钊昨晚打游戏，打到凌晨2点半。", "最火爆"));
        datas.add(new AdverNotice("高立存天天晚上吃夜宵，还不归宿", "HOT"));
        datas.add(new AdverNotice("天呐，汪俊获得了天价悬赏，秘密就在...", "new"));

        adapter = new JDViewAdapter(datas);
        tbView.setAdapter(adapter);
        //开启线程滚动
        tbView.start();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        customerViewPagerComponent.handler
                .removeMessages(customerViewPagerComponent.MSG_CODE);
    }
}
