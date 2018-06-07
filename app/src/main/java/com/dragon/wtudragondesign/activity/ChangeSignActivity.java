package com.dragon.wtudragondesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.template.BaseActivity;

public class ChangeSignActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTvRight;

    private EditText mEtSign;

    private String sign;

    private String newSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_change_sign);
        Intent intent = getIntent();
        sign = intent.getStringExtra("sign");
        if (TextUtils.isEmpty(sign))
            sign = "这个人很懒";

        setRightText("确认");

        setTitle("更改签名");

        init();
        initData();
    }

    @Override
    public void init() {
        mTvRight = fv(R.id.tv_title_right);
        mTvRight.setOnClickListener(this);

        mEtSign = fv(R.id.et_sign);
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(sign) && sign.length()>0){

            mEtSign.setText(sign);

            mEtSign.setSelection(sign.length());//将光标移至文字末尾
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title_right:

                checkData();

                break;

            default:

                break;
        }
    }

    private void checkData() {
        newSign = mEtSign.getText().toString().trim();

        if (newSign.equals(sign))
            showToast("签名未改变");
        else
            goBackWithData();

    }

    private void goBackWithData() {
        Intent intent = new Intent();
        intent.putExtra("sign", newSign); //将计算的值回传回去
        //通过intent对象返回结果，必须要调用一个setResult方法，
        //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
        setResult(2, intent);

        finish(); //结束当前的activity的生命周期
    }
}
