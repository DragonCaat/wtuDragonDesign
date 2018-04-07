package com.dragon.wtudragondesign.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.adapter.ReputationAdapter;
import com.dragon.wtudragondesign.bean.ReputationEntity;
import com.dragon.wtudragondesign.template.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 信誉排行界面
 */
public class ReputationActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView mRvReputation;

    private ReputationAdapter reputationAdapter;

    private List<ReputationEntity> reputationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_reputation);
        setTitle("信誉排行");
        hideRightText();

        initReputation();

        init();
        initData();

    }

    @Override
    public void init() {
        swipeRefresh = fv(R.id.swipe_reputation);

        GridLayoutManager layoutManager = new GridLayoutManager(getAct(), 1);
        mRvReputation = fv(R.id.rv_reputation);
        //layoutManager.setScrollEnabled(false);
        mRvReputation.setLayoutManager(layoutManager);

        reputationAdapter = new ReputationAdapter(reputationList);

        mRvReputation.setAdapter(reputationAdapter);

    }

    @Override
    public void initData() {
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);

    }

    /**
     * 刷新数据
     */
    @Override
    public void onRefresh() {
        refreshReputation();
    }

    /**
     * 刷新信誉数据
     */
    private void refreshReputation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("没数据，刷个毛啊");
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


    private void initReputation() {
        reputationList.clear();

        for (int i = 0; i < 12; i++) {
            ReputationEntity reputationEntity = new ReputationEntity();
            reputationEntity.setUserName("用户"+i);

            reputationList.add(reputationEntity);
        }
    }
}
