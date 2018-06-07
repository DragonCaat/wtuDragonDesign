package com.dragon.wtudragondesign.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.adapter.NewsAdapter;
import com.dragon.wtudragondesign.bean.NewsEntity;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.utils.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewsActivity extends BaseActivity {

    private RecyclerView mRvNews;

    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_news);
        setTitle("新闻");
        hideRightText();

        init();
        initData();
    }

    @Override
    public void init() {
        mRvNews = fv(R.id.rv_news);
    }

    @Override
    public void initData() {

        requestNews();
    }

    private void requestNews() {

        String newsUrl = "http://47.100.175.138:8885/api/v1/news?size=5&page=1";

        HttpUtil.sendOkhttpRequest(newsUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();

                Gson gson = new Gson();

                NewsEntity newsEntity = gson.fromJson(responseText, NewsEntity.class);

                final List<NewsEntity.DataBean.ListBean> dataList = newsEntity.getData().getList();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newsAdapter = new NewsAdapter(dataList);
                        GridLayoutManager manager = new GridLayoutManager(getAct(), 1);
                        mRvNews.setLayoutManager(manager);
                        mRvNews.setAdapter(newsAdapter);
                    }
                });
            }
        });
    }
}
