package com.dragon.wtudragondesign.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.bean.ResultEntity;
import com.dragon.wtudragondesign.retrofit.ApiService;
import com.dragon.wtudragondesign.retrofit.RetrofitClient;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.utils.CountDownTimerUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
/**
 * 注册界面
 * */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtName, mEtPass, mEtEmail, mEtCode;
    private TextView mTvCode;
    private Button mBtnRegist;

    private String userNameStr = "";
    private String passWordStr = "";
    private String emailStr = "";
    private String codeStr = "";

    private ProgressDialog proDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_register);
        hideRightText();
        setTitle("注册");
        init();
        initData();
    }

    @Override
    public void init() {
        mEtName = fv(R.id.et_registerName);
        mEtPass = fv(R.id.et_registerPass);
        mEtEmail = fv(R.id.et_registerEmail);
        mTvCode = fv(R.id.tv_check_code);
        mEtCode = fv(R.id.et_check_code);
        mBtnRegist = fv(R.id.btn_register);
    }

    @Override
    public void initData() {
        mTvCode.setOnClickListener(this);
        mBtnRegist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_check_code:
                checkCode();
                break;
            case R.id.btn_register:
                CheckUserInput();
                break;
            default:
                break;
        }
    }

    /**
     * 检查用户输入的验证码
     */
    private void checkCode() {
        emailStr = mEtEmail.getText().toString().trim();
        if (TextUtils.isEmpty(emailStr)) {
            showToast("请先输入邮箱");
        } else {
            CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(
                    mTvCode, 30000, 1000);
            mCountDownTimerUtils.start();

            //执行相关的网络代码

        }
    }

    /**
     * 检查用户输入的信息
     */
    private void CheckUserInput(){
        userNameStr = mEtName.getText().toString().trim();
        passWordStr = mEtPass.getText().toString().trim();
        emailStr = mEtEmail.getText().toString().trim();
        codeStr = mEtCode.getText().toString().trim();
        if (TextUtils.isEmpty(userNameStr) || TextUtils.isEmpty(passWordStr) || TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(codeStr)) {
            showToast("填写完整数据后再试");
        } else {
            //执行网络请求相关代码
            //注册失败会抛出HyphenateException
            register();

            //    EMClient.getInstance().createAccount(userNameStr, passWordStr);//同步方法

        }
    }


    private void register(){
        showProgressDialog();

        ApiService api = RetrofitClient.getInstance(this).Api();
        Map<String, String> params = new HashMap<>();

        params.put("userName", userNameStr);
        params.put("password", passWordStr);

        Call<ResultEntity> call = api.register(params);
        call.enqueue(new retrofit2.Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call,
                                   Response<ResultEntity> response) {

                if (response.body() == null) {
                    return;
                }
                ResultEntity result = response.body();
                int res = result.getCode();
                if (res == 1) {// 注册成功
                    showToast(""+result.getMsg());
                    hideProgressDialog();

                    skipPage(MainActivity.class);
                    finish();
                    //后台做用户登陆

                } else {
                    showToast(""+result.getMsg());
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {
               // Log.i("%%%%%%%%%%%%%%%%%", "onResponse: 注册失败");
            }
        });

    }

    //展示加载对话框
    private void showProgressDialog() {
        proDialog = android.app.ProgressDialog.show(this, "", "正在注册");

        proDialog.setCanceledOnTouchOutside(true);
    }

    private void hideProgressDialog() {
        if (proDialog != null)
            proDialog.dismiss();
    }
}
