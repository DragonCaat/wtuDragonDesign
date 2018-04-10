package com.dragon.wtudragondesign.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.database.RecordDatabaseUtils;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.utils.KeyCodeUtils;
import com.dragon.wtudragondesign.view.FlowLayout;

import java.util.List;


public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private static int TEXTVIEW_FLAG = 0;
    //大家都在搜
    private FlowLayout mFlowLayout;
    // 历史搜索
    private FlowLayout mFlHistory;
    // 清除历史记录
    private ImageView mImageViewDeleteHisSearch;

    private TextView mTvCancel;

    private EditText mEtSearch;

    private String[] mValues = {"老乡开门", "社区送温暖", "查水表", "明眸", "可爱的女孩", "期待的是什么"};

    private TextView mTvSearch;

    private ImageView mIvCleanText;

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

        mTvSearch = fv(R.id.tv_search);

        mIvCleanText = fv(R.id.iv_search_delete);
        mIvCleanText.setOnClickListener(this);
        initData();

        // 历史搜索
        mFlHistory = fv(R.id.flowLayout_history);

        LayoutInflater mInflater = LayoutInflater.from(this);
        List<String> list = RecordDatabaseUtils.queryData(this);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                TextView tv = (TextView) mInflater.inflate(R.layout.search_item,
                        mFlHistory, false);
                final String word = list.get(i);
                tv.setText("" + word);
                mFlHistory.addView(tv);
                mFlHistory.getChildAt(i).setOnClickListener(
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
//                                Intent intent = new Intent(getActivity(),
//                                        ActEnterprise.class);
//                                intent.putExtra("keyword", word);
//                                startActivity(intent);

                            }
                        });
            }
        }

        // 清除历史记录
        mImageViewDeleteHisSearch = fv(R.id.iv_delete_history_search);
        mImageViewDeleteHisSearch.setOnClickListener(this);


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
                if (charSequence.length() > 0) {
                    mTvSearch.setVisibility(View.GONE);
                    TEXTVIEW_FLAG = 1;
                    mTvCancel.setText("搜索");
                    mIvCleanText.setVisibility(View.VISIBLE);
                } else {
                    mTvSearch.setVisibility(View.VISIBLE);
                    TEXTVIEW_FLAG = 0;
                    mTvCancel.setText("取消");
                    mIvCleanText.setVisibility(View.GONE);
                }
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
                searchData();
                break;
            case R.id.iv_search_delete:
                mEtSearch.setText("");
                break;
            case R.id.iv_delete_history_search:
                RecordDatabaseUtils.deleteData();
                mFlHistory.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void searchData() {
        if (TEXTVIEW_FLAG == 0) {
            finish();
        } else {
            KeyCodeUtils.closeKeyCode(this,mTvCancel);
            showToast("没数据啊");
            String inputString = mEtSearch.getText().toString();
            // 向数据库中添加数据
            RecordDatabaseUtils.insertData(inputString, getAct());
        }
    }
}
