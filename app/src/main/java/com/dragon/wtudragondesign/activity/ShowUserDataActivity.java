package com.dragon.wtudragondesign.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.bean.ResultEntity;
import com.dragon.wtudragondesign.bean.UserDataEntity;
import com.dragon.wtudragondesign.retrofit.ApiService;
import com.dragon.wtudragondesign.retrofit.RetrofitClient;
import com.dragon.wtudragondesign.utils.PreferencesUtils;
import com.hyphenate.easeui.EaseConstant;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;


/**
 * 查看用户信息的activity
 */
public class ShowUserDataActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnChat, mBtnCall;

    private int id;

    private ProgressDialog proDialog;

    private CircleImageView mCvPhoto;
    private TextView mTvNickName;
    private TextView mTvSign;
    private TextView mTvUserName;
    private TextView mTvVip;
    private TextView mTvReputation;

    private String nickName = "";
    private String sign = "";
    private String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        setContentView(R.layout.activity_show_user_data);
        init();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.courier_detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mBtnCall = findViewById(R.id.btn_phone);
        mBtnCall.setOnClickListener(this);
        mBtnChat = findViewById(R.id.btn_chat);
        mBtnChat.setOnClickListener(this);

        mCvPhoto = findViewById(R.id.cv_show_user);
        mTvUserName =  findViewById(R.id.tv_show_userName);
        mTvSign = findViewById(R.id.tv_show_sign);
        mTvNickName = findViewById(R.id.tv_show_nickName);
        mTvVip = findViewById(R.id.user_vip);
        mTvReputation = findViewById(R.id.user_reputation);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_phone:

                break;

            case R.id.btn_chat:
                chatWith(userName);
                break;

            default:

                break;
        }
    }
    // 获取用户信息
    private void getUserData() {
        showProgressDialog("正在加载数据");
        ApiService api = RetrofitClient.getInstance(this).Api();
        // Map<String, Object> params = new HashMap<>();
        Call<ResultEntity> call = api.getUserData(id);
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
                    UserDataEntity userDataEntity = JSON.parseObject(result.getData().toString(), UserDataEntity.class);
                    if (userDataEntity != null)
                        setUserData(userDataEntity);
                    else
                        Toast.makeText(ShowUserDataActivity.this, "" + result.getMsg(), Toast.LENGTH_SHORT).show();

                    hideProgressDialog();

                } else {
                    Toast.makeText(ShowUserDataActivity.this, "" + result.getMsg(), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {

            }
        });
    }

    //设置用户信息
    private void setUserData(UserDataEntity userData) {

        nickName = userData.getNickName();
        userName = userData.getUserName();
        mTvUserName.setText(userName);

        sign = userData.getSign();
        boolean isVip = userData.isVip();


        if (nickName == null) {
            mTvNickName.setText(userName);

        } else
            mTvNickName.setText(nickName);

        if (sign == null)
            mTvSign.setText("这个人很懒,什么也没留下");
        else
            mTvSign.setText(sign);

        if (isVip) {
            mTvVip.setText("天王会员");
            mTvVip.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.have_vip, null), null, getResources().getDrawable(R.mipmap.into, null), null);
        } else {
            mTvVip.setText("普通用户");
            mTvVip.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_vip, null), null, getResources().getDrawable(R.mipmap.into, null), null);
        }

        mTvReputation.setText("信誉良好 ( " + userData.getReputation() + " )");

        if (!TextUtils.isEmpty(userData.getHeadImg())){

            PreferencesUtils.putString(ShowUserDataActivity.this, Const.HEAD_IMAGE,userData.getHeadImg());

            Glide.with(this).load(Const.BASE_URL + userData.getHeadImg())
                    .placeholder(R.mipmap.photo).into(mCvPhoto);

        }

    }

    //展示加载对话框
    private void showProgressDialog(String s) {
        proDialog = android.app.ProgressDialog.show(this, "", s);

        proDialog.setCanceledOnTouchOutside(true);
    }

    private void hideProgressDialog() {
        if (proDialog != null)
            proDialog.dismiss();
    }


    @Override
    protected void onStart() {
        super.onStart();

        getUserData();
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
}
