package com.dragon.wtudragondesign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.activity.AddNewCourierActivity;
import com.dragon.wtudragondesign.activity.LoginActivity;
import com.dragon.wtudragondesign.activity.NewsActivity;
import com.dragon.wtudragondesign.activity.PlayActivity;
import com.dragon.wtudragondesign.activity.ReputationActivity;
import com.dragon.wtudragondesign.activity.RewardActivity;
import com.dragon.wtudragondesign.activity.SearchActivity;
import com.dragon.wtudragondesign.activity.VipActivity;
import com.dragon.wtudragondesign.adapter.CourierAdapter;
import com.dragon.wtudragondesign.adapter.JDViewAdapter;
import com.dragon.wtudragondesign.bean.AdverNotice;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.bean.CourierEntity;
import com.dragon.wtudragondesign.bean.ResultEntity;
import com.dragon.wtudragondesign.bean.RewardEntity;
import com.dragon.wtudragondesign.retrofit.ApiService;
import com.dragon.wtudragondesign.retrofit.RetrofitClient;
import com.dragon.wtudragondesign.utils.CornersTransform;
import com.dragon.wtudragondesign.utils.PreferencesUtils;
import com.dragon.wtudragondesign.view.CustomerViewPagerComponent;
import com.dragon.wtudragondesign.view.JDAdverView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Dragon on 2018/3/20.
 * 主页的 Fragment
 */

public class FragmentMain extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private RecyclerView mRvMain;
    //快递信息的适配器
    private CourierAdapter courierAdapter;

    private List<CourierEntity> mCourierList;

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

    //悬赏按钮
    private TextView mTvReward;

    //信誉按钮
    private TextView mTvReputation;

    private String userName = "";

    //会员图片
    private ImageView mIvVip;

    //加载
    private TextView mTvPlay;

    private TextView mTvNews;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userName = PreferencesUtils.getString(getContext(), Const.USER_NAME);

        view = inflater.inflate(R.layout.fragment_main, container, false);
        //initCourier();
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
        customerViewPagerComponent.setImages(imgs, getActivity());

        tbView = view.findViewById(R.id.notice);

        mIvVipReward = view.findViewById(R.id.iv_vip_reward);

        mTvSearchMain = view.findViewById(R.id.tv_search_main);

        mRvVipCourier = view.findViewById(R.id.rv_vip_reward);

        mTvReward = view.findViewById(R.id.tv_reward_main);
        mTvReward.setOnClickListener(this);

        mTvReputation = view.findViewById(R.id.tv_reputation_main);
        mTvReputation.setOnClickListener(this);

        mIvVip = view.findViewById(R.id.iv_vip_reward);
        mIvVip.setOnClickListener(this);

        mTvPlay = view.findViewById(R.id.tv_play);
        mTvPlay.setOnClickListener(this);

        mTvNews = view.findViewById(R.id.tv_news);
        mTvNews.setOnClickListener(this);
    }

    public void initData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        //layoutManager.setScrollEnabled(false);
        mRvMain.setLayoutManager(layoutManager);
        mRvMain.setNestedScrollingEnabled(false);

        //vip悬赏的recycleView
        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 2);
        mRvVipCourier.setLayoutManager(layoutManager1);

        mRvVipCourier.setNestedScrollingEnabled(false);

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);

        mFabButton.setOnClickListener(this);

        mTvSearchMain.setOnClickListener(this);

        Glide.with(this).load(R.mipmap.vip).transform(new CornersTransform(getContext(), 30)).into(mIvVipReward);
        //初始化滚动条
        initJdNotice();
    }

    private void initCourier() {
        if (mCourierList != null)
            mCourierList.clear();
        getNormalPush();

        getVipPush();
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
                        initCourier();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add:
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(getContext(), "请先登陆", Toast.LENGTH_SHORT).show();
                } else {

                    //在此做跳转界面，编写发布界面
                    Intent intent = new Intent(getActivity(), AddNewCourierActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_search_main:
                //在此做跳转界面，跳转搜索界面
                Intent intent1 = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent1);
                break;

            case R.id.tv_reward_main:
                //在此做跳转界面，跳转悬赏界面
                Intent intent2 = new Intent(getActivity(), RewardActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_reputation_main:
                //在此做跳转界面，跳转悬赏界面
                Intent intent3 = new Intent(getActivity(), ReputationActivity.class);
                startActivity(intent3);
                break;

            case R.id.iv_vip_reward:
                //在此做跳转界面，跳转会员界面
                Intent intent4 = new Intent(getActivity(), VipActivity.class);
                startActivity(intent4);
                break;

            case R.id.tv_play:
                //在此做跳转界面，跳转会员界面
                Intent intent5 = new Intent(getActivity(), PlayActivity.class);
                startActivity(intent5);
                break;

            case R.id.tv_news:
                //在此做跳转界面，跳转会员界面
                Intent intent6 = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent6);
                break;

            default:
                break;
        }
    }

    // 获取紧急悬赏数据
    private void getNormalPush() {
        ApiService api = RetrofitClient.getInstance(getContext()).Api();
        Map<String, Object> params = new HashMap<>();

        Call<ResultEntity> call = api.publishNormal(params);
        call.enqueue(new retrofit2.Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call,
                                   Response<ResultEntity> response) {

                if (response.body() == null) {
                    return;
                }
                ResultEntity result = response.body();
                int res = result.getCode();
                if (res == 1) {// 请求成功
                    mCourierList = JSON.parseArray(result.getData().toString(), CourierEntity.class);
                    if (mCourierList.size() > 0) {
                        courierAdapter = new CourierAdapter(mCourierList);
                        mRvMain.setAdapter(courierAdapter);
                    }

                } else {
                    Toast.makeText(getContext(), "" + result.getMsg(), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {

            }
        });
    }

    // 获取VIP悬赏数据
    private void getVipPush() {
        ApiService api = RetrofitClient.getInstance(getContext()).Api();
        Map<String, Object> params = new HashMap<>();

        Call<ResultEntity> call = api.publishVip(params);
        call.enqueue(new retrofit2.Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call,
                                   Response<ResultEntity> response) {

                if (response.body() == null) {
                    return;
                }
                ResultEntity result = response.body();
                int res = result.getCode();
                if (res == 1) {// 请求成功
                    mCourierList = JSON.parseArray(result.getData().toString(), CourierEntity.class);
                    if (mCourierList.size() > 0) {
                        courierAdapter = new CourierAdapter(mCourierList);
                        mRvVipCourier.setAdapter(courierAdapter);
                    }
                    swipeRefresh.setRefreshing(false);

                } else {
                    Toast.makeText(getContext(), "" + result.getMsg(), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {

            }
        });
    }

    //初始化滚动条
    private void initJdNotice() {
        datas.add(new AdverNotice("张承发布了一条悬赏，快去看看吧!!", "最新"));
        datas.add(new AdverNotice("胡钊发布了一条高价悬赏...。", "最火爆"));
        datas.add(new AdverNotice("高立存人品爆发，占据榜首", "HOT"));
        datas.add(new AdverNotice("天呐，汪俊获得了天价悬赏，秘密就在...", "new"));

        adapter = new JDViewAdapter(datas);
        tbView.setAdapter(adapter);
        //开启线程滚动
        tbView.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        initCourier();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (customerViewPagerComponent != null)
            customerViewPagerComponent.handler
                    .removeMessages(customerViewPagerComponent.MSG_CODE);
    }
}
