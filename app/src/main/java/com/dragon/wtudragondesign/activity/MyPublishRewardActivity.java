package com.dragon.wtudragondesign.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.adapter.CourierAdapter;
import com.dragon.wtudragondesign.adapter.MyCourierAdapter;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.bean.CourierEntity;
import com.dragon.wtudragondesign.bean.ResultEntity;
import com.dragon.wtudragondesign.retrofit.ApiService;
import com.dragon.wtudragondesign.retrofit.RetrofitClient;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class MyPublishRewardActivity extends BaseActivity {

    private RecyclerView mRvMyReward;
    //快递信息的适配器
    private MyCourierAdapter courierAdapter;

    private List<CourierEntity> mCourierList;

    private LinearLayout mLlNo;

    private ProgressDialog proDialog;

    private int userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = PreferencesUtils.getInt(getAct(),Const.ID);

        loadMainUI(R.layout.activity_my_publish_reward);
        hideRightText();
        setTitle("我发布的悬赏");
        init();
        initData();
    }

    @Override
    public void init() {
        mRvMyReward = fv(R.id.rv_my_reward);
        GridLayoutManager layoutManager = new GridLayoutManager(getAct(), 2);
        mRvMyReward.setLayoutManager(layoutManager);

        mLlNo = fv(R.id.ll_no);
    }

    @Override
    public void initData() {
        getMyPush();
    }

    // 获取普通悬赏数据
    private void getMyPush() {
        showProgressDialog();
        ApiService api = RetrofitClient.getInstance(getAct()).Api();
        Call<ResultEntity> call = api.Mypublish(userId);
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
                    hideProgressDialog();
                    mCourierList = JSON.parseArray(result.getData().toString(), CourierEntity.class);
                    if (mCourierList.size() > 0) {
                        mLlNo.setVisibility(View.GONE);
                        courierAdapter = new MyCourierAdapter(mCourierList);
                        mRvMyReward.setAdapter(courierAdapter);
                    }else {
                        mLlNo.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(getAct(), "" + result.getMsg(), Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {

            }
        });
    }

    //展示加载对话框
    private void showProgressDialog() {
        proDialog = android.app.ProgressDialog.show(getAct(), "", "正在加载数据");
        proDialog.setCanceledOnTouchOutside(true);
    }

    private void hideProgressDialog() {
        if (proDialog != null)
            proDialog.dismiss();
    }
}
