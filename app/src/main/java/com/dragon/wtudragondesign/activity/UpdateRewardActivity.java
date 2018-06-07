package com.dragon.wtudragondesign.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.bean.CourierDetailEntity;
import com.dragon.wtudragondesign.bean.ResultEntity;
import com.dragon.wtudragondesign.retrofit.ApiService;
import com.dragon.wtudragondesign.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Response;

public class UpdateRewardActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mFabChat;

    private Button mBtnUpdateReward;

    private int userId = 0;

    private String picString = "";

    private TextView mTvPublishName;
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvCount;

    private TextView mBtnDeleteReward;

    private ProgressDialog proDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId",0);
        picString = intent.getStringExtra("pic");

        setContentView(R.layout.activity_update_reward);
        init();

        getDetailPush();
    }

    /**
     * 初始化控件
     */
    private void init() {
        Toolbar toolbar = findViewById(R.id.courier_detail_toolbar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("悬赏详情");
        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER_VERTICAL);
        ImageView bg = findViewById(R.id.courier_detail_background);
        if (TextUtils.isEmpty(picString))
            Glide.with(this).load(R.mipmap.ti).into(bg);
        else
            Glide.with(this).load(Const.BASE_URL+picString).into(bg);

        //悬浮按钮
        mFabChat = findViewById(R.id.fab_photo);
        mFabChat.setOnClickListener(this);

        mBtnUpdateReward = findViewById(R.id.btn_update_reward);
        mBtnUpdateReward.setOnClickListener(this);


        mTvPublishName = findViewById(R.id.tv_publish_name);
        mTvTitle = findViewById(R.id.tv_detail_title);
        mTvContent = findViewById(R.id.tv_detail_content);
        mTvCount = findViewById(R.id.tv_reward_count);

        mBtnDeleteReward = findViewById(R.id.btn_delete_reward);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_photo:
                Intent intent = new Intent(this, UserDataActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_update_reward:

                //Toast.makeText(this,"求别点",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    // 获取悬赏数据详情
    private void getDetailPush() {
        showProgressDialog();
        ApiService api = RetrofitClient.getInstance(this).Api();
        Call<ResultEntity> call = api.publishDetail(userId);
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
                    CourierDetailEntity courierDetailEntity = JSON.parseObject(result.getData().toString(),CourierDetailEntity.class);

                    if (courierDetailEntity != null)
                        setData(courierDetailEntity);
                    else
                        Toast.makeText(UpdateRewardActivity.this,""+result.getMsg(),Toast.LENGTH_SHORT).show();

                } else {
                    hideProgressDialog();
                    Toast.makeText(UpdateRewardActivity.this,""+result.getMsg(),Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {
                hideProgressDialog();
            }
        });
    }

    private void setData(CourierDetailEntity courierDetailEntity){
        mTvPublishName.setText(courierDetailEntity.getPublisherUserName());
        mTvTitle.setText(courierDetailEntity.getTitle());
        mTvContent.setText(courierDetailEntity.getContent());
        mTvCount.setText(""+courierDetailEntity.getAmount());
    }

    //展示加载对话框
    private void showProgressDialog() {
        proDialog = android.app.ProgressDialog.show(UpdateRewardActivity.this, "", "正在加载");

        proDialog.setCanceledOnTouchOutside(true);
    }

    private void hideProgressDialog() {
        if (proDialog != null)
            proDialog.dismiss();
    }

}