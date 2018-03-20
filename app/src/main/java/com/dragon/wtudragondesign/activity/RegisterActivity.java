package com.dragon.wtudragondesign.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.utils.CountDownTimerUtils;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtName, mEtPass, mEtEmail, mEtCode;
    private TextView mTvCode;
    private Button mBtnRegist;

    private String userNameStr = "";
    private String passWordStr = "";
    private String emailStr = "";
    private String codeStr = "";

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
    private void CheckUserInput() {
        userNameStr = mEtName.getText().toString().trim();
        passWordStr = mEtPass.getText().toString().trim();
        emailStr = mEtEmail.getText().toString().trim();
        codeStr = mEtCode.getText().toString().trim();
        if (TextUtils.isEmpty(userNameStr) || TextUtils.isEmpty(passWordStr) || TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(codeStr)) {
            showToast("填写完整数据后再试");
        } else {
            //执行网络请求相关代码
        }
    }
}
