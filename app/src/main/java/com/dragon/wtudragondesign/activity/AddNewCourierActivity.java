package com.dragon.wtudragondesign.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.adapter.CourierPictureAdapter;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.view.NoScrollGridView;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.util.ArrayList;

/**
 * Created by dragon on 2018/3/20.
 * 发布新的带快递消息界面
 */
public class AddNewCourierActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 732;

    //用于存放图片的返回地址
    private ArrayList<String> mResults = new ArrayList<>();
    /* 图片适配器 */
    private CourierPictureAdapter adapter;

    private GridView mGridView;

    private ImageView mIvAddPic;

    private TextView mTvRight;

    private EditText mEtTitle, mEtContent;

    private String titleStr = "";
    private String contentStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainUI(R.layout.activity_add_new_courier);
        setTitle("发布悬赏");
        setRightText("确认");
        init();
        initData();
    }

    @Override
    public void init() {
        mGridView = fv(R.id.noScrollgridview);
        mIvAddPic = fv(R.id.iv_add_photo);
        mTvRight = fv(R.id.tv_title_right);

        mEtTitle = fv(R.id.et_courier_title);
        mEtContent = fv(R.id.et_courier_content);
    }

    @Override
    public void initData() {
        mIvAddPic.setOnClickListener(this);
        mTvRight.setOnClickListener(this);
    }

    /**
     * 打开选择图片框
     */
    private void openGallery() {
        // start multiple photos selector
        Intent intent = new Intent(AddNewCourierActivity.this, ImagesSelectorActivity.class);
        // max number of images to be selected
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5);
        // min size of image which will be shown; to filter tiny images (mainly icons)
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
        // show camera or not
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
        // pass current selected images as the initial value
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
        // start the selector
        startActivityForResult(intent, REQUEST_CODE);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get selected images from selector
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                if (mResults.size() > 0) {
                    adapter = new CourierPictureAdapter(AddNewCourierActivity.this, mResults);
                    mGridView.setAdapter(adapter);
                } else {
                    showToast("用户取消上传图片");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_photo:
                openGallery();
                break;
            case R.id.tv_title_right:
                checkUserInput();
                break;
            default:
                break;
        }
    }

    /**
     * 检查用户的输入信息
     */
    private void checkUserInput() {
        titleStr = mEtTitle.getText().toString().trim();
        contentStr = mEtContent.getText().toString().trim();
        if (TextUtils.isEmpty(titleStr)|| TextUtils.isEmpty(titleStr)){
            showToast("红色 * 为必填内容");
        }else {
            //在此做网络上传工作
        }
    }
}
