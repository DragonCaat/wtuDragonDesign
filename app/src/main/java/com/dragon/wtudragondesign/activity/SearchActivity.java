package com.dragon.wtudragondesign.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.view.FlowLayout;


public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private FlowLayout mFlowLayout;

    private TextView mTvCancel;

    private EditText mEtSearch;

    private String[] mValues = {"老乡开门", "社区送温暖", "查水表", "明眸", "可爱的女孩", "期待的是什么"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadMainUI(R.layout.activity_search);

        hideToolBar();
        init();
    }

    @Override
    public void init() {
        mFlowLayout = fv(R.id.flowLayout);
        mTvCancel = fv(R.id.tv_cancel);
        mTvCancel.setOnClickListener(this);

        mEtSearch = fv(R.id.et_search);

        initData();
    }

    @Override
    public void initData() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < mValues.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.search_item,
                    mFlowLayout, false);
            tv.setText(mValues[i]);
            mFlowLayout.addView(tv);
        }
        //给editText添加监听
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            default:
                break;
        }
    }
}
