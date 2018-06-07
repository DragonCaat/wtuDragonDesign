package com.dragon.wtudragondesign.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.adapter.CourierAdapter;
import com.dragon.wtudragondesign.bean.CourierEntity;
import com.dragon.wtudragondesign.bean.ResultEntity;
import com.dragon.wtudragondesign.retrofit.ApiService;
import com.dragon.wtudragondesign.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Dragon on 2018/4/2.
 * 普通悬赏的全部数据
 */

public class FragmentVipReward extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View view;

    private RecyclerView mRvReward;

    //快递信息的适配器
    private CourierAdapter courierAdapter;

    private List<CourierEntity> mCourierList;

    private SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vip_reward, container, false);
        init();
        initData();
        return view;
    }

    private void init(){
        mRvReward = view.findViewById(R.id.rv_normal_reward);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        mRvReward.setLayoutManager(layoutManager);

        swipeRefresh = view.findViewById(R.id.swipe_reward);

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);
    }

    private void initData(){
        getVipPush();
    }

    /**
     * 刷新监听
     *
     * */
    @Override
    public void onRefresh() {
        getVipPush();
    }

    // 获取会员悬赏数据
    private void getVipPush() {
        ApiService api = RetrofitClient.getInstance(getContext()).Api();
        Map<String, Object> params = new HashMap<>();

        Call<ResultEntity> call = api.publishV(params);
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
                    swipeRefresh.setRefreshing(false);
                    if (mCourierList.size()>0){
                        courierAdapter = new CourierAdapter(mCourierList);
                        mRvReward.setAdapter(courierAdapter);
                    }else {
                        Toast.makeText(getContext(),"没有会员数据",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(),""+result.getMsg(),Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {

            }
        });
    }
}
