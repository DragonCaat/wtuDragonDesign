package com.dragon.wtudragondesign.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.dragon.wtudragondesign.adapter.CourierAdapter;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.bean.CourierDetailEntity;
import com.dragon.wtudragondesign.bean.CourierEntity;
import com.dragon.wtudragondesign.bean.ResultEntity;
import com.dragon.wtudragondesign.retrofit.ApiService;
import com.dragon.wtudragondesign.retrofit.RetrofitClient;
import com.dragon.wtudragondesign.utils.PreferencesUtils;
import com.hyphenate.easeui.EaseConstant;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by dragon on 2018/3/24.
 * 带快递悬赏详情界面
 */
public class CourierDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mFabChat;

    private Button mBtnConnection;

    private int userId = 0;

    private String picString = "";

    private TextView mTvPublishName;
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvCount;
    private TextView mTvPublishTime;

    private TextView mTvReceive;

    private ProgressDialog proDialog;

    int pusherId = 0;
    boolean recive;

    private int id;

    private String userName = "";

    private int courierId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        picString = intent.getStringExtra("pic");
        id = PreferencesUtils.getInt(this,Const.ID);
        setContentView(R.layout.activity_courier_detail);
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
            Glide.with(this).load(Const.BASE_URL + picString).into(bg);

        //悬浮按钮
        mFabChat = findViewById(R.id.fab_photo);
        mFabChat.setOnClickListener(this);

        mBtnConnection = findViewById(R.id.btn_connection);
        mBtnConnection.setOnClickListener(this);

        mTvPublishName = findViewById(R.id.tv_publish_name);
        mTvTitle = findViewById(R.id.tv_detail_title);
        mTvContent = findViewById(R.id.tv_detail_content);
        mTvCount = findViewById(R.id.tv_reward_count);
        mTvReceive = findViewById(R.id.tv_receive);
        mTvReceive.setOnClickListener(this);
        mTvPublishTime = findViewById(R.id.tv_detail_time);
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
                int id = PreferencesUtils.getInt(this, Const.ID);

                if (pusherId != id) {
                    Intent intent = new Intent(this, ShowUserDataActivity.class);
                    intent.putExtra("id", pusherId);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, UserDataActivity.class);
                    intent.putExtra("id", pusherId);
                    startActivity(intent);
                }

                break;
            case R.id.btn_connection:

                chatWith(userName);
                break;

            case R.id.tv_receive:
                if (recive)
                    Toast.makeText(this,"已被他人接单",Toast.LENGTH_SHORT).show();
                else
                    receivePublish();

                break;
            default:
                break;
        }
    }

    private void chatWith(String userName){
        Intent chat = new Intent(this,
                ChatActivity.class);
        chat.putExtra(EaseConstant.EXTRA_USER_ID,
                userName); // 对方账号
        chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE,
                EaseConstant.CHATTYPE_SINGLE); // 单聊模式
        startActivity(chat);
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
                    CourierDetailEntity courierDetailEntity = JSON.parseObject(result.getData().toString(), CourierDetailEntity.class);

                    if (courierDetailEntity != null)
                        setData(courierDetailEntity);
                    else
                        Toast.makeText(CourierDetailsActivity.this, "" + result.getMsg(), Toast.LENGTH_SHORT).show();

                } else {
                    hideProgressDialog();
                    Toast.makeText(CourierDetailsActivity.this, "" + result.getMsg(), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {
                hideProgressDialog();
            }
        });
    }


    /** 接单
     * userId:发布悬赏的id
     * id ：接单人的id
     * */
    private void receivePublish() {
        ApiService api = RetrofitClient.getInstance(this).Api();
        Call<ResultEntity> call = api.receivePublish(userId,id);
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
                        Toast.makeText(CourierDetailsActivity.this, "接单成功，赶紧联系他吧", Toast.LENGTH_SHORT).show();
                    mTvReceive.setBackgroundColor(getResources().getColor(R.color.deep_gray_bg));
                    mTvReceive.setText("已被接单");

                } else {
                    Toast.makeText(CourierDetailsActivity.this, "" + result.getMsg(), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {
                hideProgressDialog();
            }
        });
    }






    private void setData(CourierDetailEntity courierDetailEntity) {
        mTvPublishName.setText(courierDetailEntity.getPublisherUserName());
        userName = courierDetailEntity.getPublisherUserName();
        mTvTitle.setText(courierDetailEntity.getTitle());
        mTvContent.setText(courierDetailEntity.getContent());
        mTvCount.setText("" + courierDetailEntity.getAmount());
        mTvPublishTime.setText(courierDetailEntity.getPublishTime());
        //发布人id赋值
        pusherId = courierDetailEntity.getPublisherId();

        courierId = courierDetailEntity.getId();

        boolean recived = courierDetailEntity.isRecived();
        if (!recived) {
            mTvReceive.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mTvReceive.setText("点我接单");
        } else {
            mTvReceive.setBackgroundColor(getResources().getColor(R.color.deep_gray_bg));
            mTvReceive.setText("已被接单");
        }

        if (null != courierDetailEntity.getPublisherUserHeadImg())
            Glide.with(this).load(Const.BASE_URL + courierDetailEntity.getPublisherUserHeadImg())
                    .placeholder(R.mipmap.tu).into(mFabChat);

        recive = courierDetailEntity.isRecived();
        if (!recive) {
            mTvReceive.setText("点击接单");
            mTvReceive.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            mTvReceive.setText("已被接单");
            mTvReceive.setBackgroundColor(getResources().getColor(R.color.gray_bg));
        }
    }

    //展示加载对话框
    private void showProgressDialog() {
        proDialog = android.app.ProgressDialog.show(CourierDetailsActivity.this, "", "正在加载");

        proDialog.setCanceledOnTouchOutside(true);
    }

    private void hideProgressDialog() {
        if (proDialog != null)
            proDialog.dismiss();
    }

}
