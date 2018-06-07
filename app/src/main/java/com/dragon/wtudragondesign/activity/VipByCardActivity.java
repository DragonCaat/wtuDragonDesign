package com.dragon.wtudragondesign.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.adapter.CourierAdapter;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.bean.CourierEntity;
import com.dragon.wtudragondesign.bean.ResultEntity;
import com.dragon.wtudragondesign.retrofit.ApiService;
import com.dragon.wtudragondesign.retrofit.RetrofitClient;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class VipByCardActivity extends BaseActivity implements View.OnClickListener {

    private String userNameStr = "";
    private TextView mTvUserName;

    private EditText mEtInputNumber;
    private Button mBtnRecharge;

    private String userCardStr = "";

    private int userId;

    private ProgressDialog proDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userNameStr = PreferencesUtils.getString(getAct(), Const.USER_NAME);
        userId = PreferencesUtils.getInt(getAct(),Const.ID);

        loadMainUI(R.layout.activity_vip_by_card);

        setTitle("充值卡充值");
        hideRightText();

        init();
        initData();
    }

    @Override
    public void init() {
        mTvUserName = fv(R.id.tv_user_name);
        mTvUserName.setText(userNameStr);

        mEtInputNumber = fv(R.id.et_recharge_number);
        mBtnRecharge = fv(R.id.btn_recharge);
        mBtnRecharge.setOnClickListener(this);
    }

    @Override
    public void initData() {
        userCardStr = mEtInputNumber.getText().toString().trim();
        if (TextUtils.isEmpty(userCardStr)){
            showToast("请输入充值卡号");
        }else if ("17671714521".equals(userCardStr)){
            //执行充值代码
            rechargeVip();
        }else {
            showToast("无效充值卡");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_recharge:
                initData();
                break;

            default:

                break;
        }
    }


    // 获取紧急悬赏数据
    private void rechargeVip() {
        showProgressDialog();
        ApiService api = RetrofitClient.getInstance(getAct()).Api();
        Map<String, Object> params = new HashMap<>();
        params.put("userName",userNameStr);
        params.put("vip",true);
        Call<ResultEntity> call = api.rechargeVip(userId,params);
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
                    showToast("充值成功");

                } else {
                    hideProgressDialog();
                    Toast.makeText(getAct(),""+result.getMsg(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {
                hideProgressDialog();
            }
        });
    }



    //展示加载对话框
    private void showProgressDialog() {
        proDialog = android.app.ProgressDialog.show(getAct(), "", "正在充值");

        proDialog.setCanceledOnTouchOutside(true);
    }

    private void hideProgressDialog() {
        if (proDialog != null)
            proDialog.dismiss();
    }

}
