package com.dragon.wtudragondesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.utils.PreferencesUtils;

public class ChangeNickNameActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvRight;

    private EditText mEtNickName;

    private String nickName;

    private String newNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_change_nick_name);

        Intent intent = getIntent();

        nickName = intent.getStringExtra("nick");
        if (TextUtils.isEmpty(nickName))
            nickName = PreferencesUtils.getString(this, Const.USER_NAME);

        setRightText("确认");

        setTitle("更改昵称");

        init();
        initData();
    }

    @Override
    public void init() {

        mTvRight = fv(R.id.tv_title_right);
        mTvRight.setOnClickListener(this);

        mEtNickName = fv(R.id.et_nickName);

    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(nickName) && nickName.length()>0){

            mEtNickName.setText(nickName);

            mEtNickName.setSelection(nickName.length());//将光标移至文字末尾
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
        newNickName = mEtNickName.getText().toString().trim();

        if (newNickName.equals(nickName))
            showToast("昵称未改变");
        else
            goBackWithData();

    }

    private void goBackWithData() {
        Intent intent = new Intent();
        intent.putExtra("nick", newNickName); //将计算的值回传回去
        //通过intent对象返回结果，必须要调用一个setResult方法，
        //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
        setResult(2, intent);

        finish(); //结束当前的activity的生命周期
    }

}
