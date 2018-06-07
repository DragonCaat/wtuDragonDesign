package com.dragon.wtudragondesign.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.adapter.BaseAdapter;
import com.dragon.wtudragondesign.adapter.ILoadCallback;
import com.dragon.wtudragondesign.adapter.LoadMoreAdapterWrapper;
import com.dragon.wtudragondesign.adapter.MyAdapter;
import com.dragon.wtudragondesign.adapter.NewsAdapter;
import com.dragon.wtudragondesign.adapter.OnLoad;
import com.dragon.wtudragondesign.bean.NewsEntity;
import com.dragon.wtudragondesign.utils.HttpUtil;
import com.dragon.wtudragondesign.view.CustomerViewPagerComponent;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PlayActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {
    RecyclerView mRecyclerView;
    BaseAdapter mAdapter;
    SwipeRefreshLayout refreshLayout;
    MyAdapter adapter;
    int loadCount = 1;
    List<NewsEntity.DataBean.ListBean> dataSet;

    private ImageView mIvBack;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //数据的处理最终还是交给被装饰的adapter来处理

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mRecyclerView = findViewById(R.id.recycler);

        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);

        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        //创建被装饰者类实例
        adapter = new MyAdapter(this);
        //创建装饰者实例，并传入被装饰者和回调接口
        mAdapter = new LoadMoreAdapterWrapper(adapter, new OnLoad() {
            @Override
            public void load(int pagePosition, int pageSize, final ILoadCallback callback) {
                //此处模拟做网络操作，2s延迟，将拉取的数据更新到adapter中
                requestNews(callback);

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        List<NewsEntity.DataBean.ListBean> dataSet = new ArrayList();
//                        for (int i = 0; i < 5; i++) {
//                            NewsEntity.DataBean.ListBean bean = new NewsEntity.DataBean.ListBean();
//                            bean.setTitle("40多天两度会晤，习近平同金正恩谈了哪些大事");
//                            bean.setRead_count("10");
//                            dataSet.add(bean);
//                        }
//                        //数据的处理最终还是交给被装饰的adapter来处理
//                        adapter.appendData(dataSet);
//                        callback.onSuccess();
//                        //模拟加载到没有更多数据的情况，触发onFailure
//                        if (loadCount++ == 3) {
//                            callback.onFailure();
//                        }
//                    }
//                }, 2000);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void requestNews(final ILoadCallback callback) {

        String newsUrl = "http://47.100.175.138:8885/api/v1/news?size=5&page=" + loadCount;

        //  Log.i("#################", "requestNews: "+loadCount);
        HttpUtil.sendOkhttpRequest(newsUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();

                Gson gson = new Gson();

                final NewsEntity newsEntity = gson.fromJson(responseText, NewsEntity.class);

                dataSet = newsEntity.getData().getList();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //数据的处理最终还是交给被装饰的adapter来处理
                        adapter.appendData(dataSet);
                        callback.onSuccess();
                        if (loadCount++ == newsEntity.getData().getPage()) {
                            callback.onFailure();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onRefresh() {

        dataSet.clear();
        requestNews();
    }

    private void requestNews() {

        String newsUrl = "http://47.100.175.138:8885/api/v1/news?size=5&page=1";

        //  Log.i("#################", "requestNews: "+loadCount);
        HttpUtil.sendOkhttpRequest(newsUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();

                Gson gson = new Gson();

                final NewsEntity newsEntity = gson.fromJson(responseText, NewsEntity.class);
                dataSet = newsEntity.getData().getList();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //数据的处理最终还是交给被装饰的adapter来处理
                        adapter.appendData(dataSet);
                        adapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(PlayActivity.this, LinearLayoutManager.VERTICAL, false));

                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:

                finish();
                break;

        }

    }
}