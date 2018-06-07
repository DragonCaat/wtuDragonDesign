package com.dragon.wtudragondesign.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.adapter.CourierAdapter;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.bean.CourierEntity;
import com.dragon.wtudragondesign.bean.ResultEntity;
import com.dragon.wtudragondesign.bean.UserDataEntity;
import com.dragon.wtudragondesign.retrofit.ApiService;
import com.dragon.wtudragondesign.retrofit.RetrofitClient;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.utils.PhotoUtils;
import com.dragon.wtudragondesign.utils.PreferencesUtils;
import com.dragon.wtudragondesign.utils.UriPathUtils;
import com.dragon.wtudragondesign.view.DialogGetPicture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class ChangeUserDataActivity extends BaseActivity implements View.OnClickListener {

    public static int CHANGE_NICK_CODE = 1;
    public static int CHANGE_SIGN_CODE = 2;


    private LinearLayout mLlNickName;
    private LinearLayout mLlPhone;
    private LinearLayout mLlSign;
    private RelativeLayout mLlPhoto;

    private TextView mTvNickName;
    private TextView mTvSign;
    private ImageView mIvHead;

    private String nickName = "";

    private String newNickName = "";

    private String sign = "";

    private String newSign = "";

    private String photoPath;

    private String userName;

    private TextView mTvRight;

    private ProgressDialog proDialog;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = PreferencesUtils.getString(this, Const.USER_NAME);
        id = PreferencesUtils.getInt(this, Const.ID);
        Intent intent = getIntent();
        nickName = intent.getStringExtra("nickName");
        sign = intent.getStringExtra("sign");
        loadMainUI(R.layout.activity_change_user_data);
        setTitle("修改数据");
        setRightText("确认");

        init();
        initData();

    }

    @Override
    public void init() {
        mLlNickName = fv(R.id.ll_nickName);
        mLlNickName.setOnClickListener(this);

        mLlPhone = fv(R.id.ll_phone);
        mLlPhone.setOnClickListener(this);

        mLlSign = fv(R.id.ll_sign);
        mLlSign.setOnClickListener(this);

        mTvNickName = fv(R.id.tv_nickName);
        mTvSign = fv(R.id.tv_sign);

        mIvHead = fv(R.id.iv_photo);

        mLlPhoto = fv(R.id.rl_photo);
        mLlPhoto.setOnClickListener(this);

        mTvRight = fv(R.id.tv_title_right);
        mTvRight.setOnClickListener(this);
    }

    @Override
    public void initData() {
        if (TextUtils.isEmpty(nickName))
            mTvNickName.setText(userName);
        else
            mTvNickName.setText(nickName);

        if (TextUtils.isEmpty(sign)) {
            mTvSign.setText("这个人很懒");
            sign = "这个人很懒";
            newSign = sign;
        } else
            mTvSign.setText(sign);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_nickName:
                intentToNewPage(ChangeNickNameActivity.class, "nick", nickName, CHANGE_NICK_CODE);
                break;

            case R.id.ll_phone:
                showToast("暂不支持改手机号码");
                break;

            case R.id.ll_sign:
                intentToNewPage(ChangeSignActivity.class, "sign", sign, CHANGE_SIGN_CODE);
                break;

            case R.id.rl_photo:

                new DialogGetPicture(this) {
                    @Override
                    public void amble() {
                        //PhotoUtils.selectPictureFromAlbum(ChangeUserDataActivity.this);
                    }

                    @Override
                    public void photo() {
                        //PhotoUtils.photograph(ChangeUserDataActivity.this);

                    }
                }.show();
                break;
            case R.id.tv_title_right:
                updateUserData();
                break;

            default:

                break;
        }
    }

    // 获取返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // operation succeeded. 默认值是-1
        if (resultCode == 2) {
            if (requestCode == CHANGE_NICK_CODE) {
                newNickName = data.getStringExtra("nick");
                //设置结果
                if (TextUtils.isEmpty(newNickName)) {

                    mTvNickName.setText(nickName);
                    newNickName = nickName;
                } else {
                    mTvNickName.setText(newNickName);
                }

            } else if (requestCode == CHANGE_SIGN_CODE) {
                newSign = data.getStringExtra("sign");
                if (TextUtils.isEmpty(newSign)) {

                    mTvSign.setText(sign);
                    newSign = sign;
                } else
                    mTvSign.setText(newSign);
            }
        }

//        if (resultCode == PhotoUtils.NONE)
//            return;
//        // 拍照
//        if (requestCode == PhotoUtils.PHOTOGRAPH) {
//            // 设置文件保存路径这里放在跟文件夹下
//            File picture = null;
//            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//                picture = new File(Environment.getExternalStorageDirectory() + PhotoUtils.imageName);
//                if (!picture.exists()) {
//                    picture = new File(Environment.getExternalStorageDirectory() + PhotoUtils.imageName);
//                }
//            } else {
//                picture = new File(this.getFilesDir() + PhotoUtils.imageName);
//                if (!picture.exists()) {
//                    picture = new File(ChangeUserDataActivity.this.getFilesDir() + PhotoUtils.imageName);
//                }
//            }
//
//            photoPath = PhotoUtils.getPath(this);// 生成一个地址用于存放剪辑后的图片
//            if (TextUtils.isEmpty(photoPath)) {
//                Log.e("########", "随机生成的用于存放剪辑后的图片的地址失败");
//                return;
//            }
//            Uri imageUri = UriPathUtils.getUri(this, photoPath);
//            PhotoUtils.startPhotoZoom(ChangeUserDataActivity.this, Uri.fromFile(picture), PhotoUtils.PICTURE_HEIGHT, PhotoUtils.PICTURE_WIDTH, imageUri);
//        }
//
//        if (data == null)
//            return;
//
//        // 读取相冊缩放图片
//        if (requestCode == PhotoUtils.PHOTOZOOM) {
//
//            photoPath = PhotoUtils.getPath(this);// 生成一个地址用于存放剪辑后的图片
//            if (TextUtils.isEmpty(photoPath)) {
//                Log.e("######", "随机生成的用于存放剪辑后的图片的地址失败");
//                return;
//            }
//            Uri imageUri = UriPathUtils.getUri(this, photoPath);
//            PhotoUtils.startPhotoZoom(ChangeUserDataActivity.this, data.getData(), PhotoUtils.PICTURE_HEIGHT, PhotoUtils.PICTURE_WIDTH, imageUri);
//
//            Glide.with(this).load(imageUri).into(mIvHead);
//        }
//        // 处理结果
//        if (requestCode == PhotoUtils.PHOTORESOULT) {
//            /**
//             * 在这里处理剪辑结果。能够获取缩略图，获取剪辑图片的地址。得到这些信息能够选则用于上传图片等等操作
//             * */
//
//            /**
//             * 如。依据path获取剪辑后的图片
//             */
//            Bitmap bitmap = PhotoUtils.convertToBitmap(photoPath, PhotoUtils.PICTURE_HEIGHT, PhotoUtils.PICTURE_WIDTH);
//            if (bitmap != null) {
//                showToast("" + bitmap.getHeight() + "x" + bitmap.getWidth() + "图");
//                //tv2.setText(bitmap.getHeight()+"x"+bitmap.getWidth()+"图");
//                mIvHead.setImageBitmap(bitmap);
//
//                Glide.with(this).load(bitmap).into(mIvHead);
//            }
//        }
    }

    //跳转页面
    private void intentToNewPage(Class<? extends Activity> cls, String key, String value, int code) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(key, value);
        startActivityForResult(intent, code);
    }

    // 更新用户的个人信息
    private void updateUserData() {
        showProgressDialog();
        ApiService api = RetrofitClient.getInstance(getAct()).Api();
        Map<String, Object> params = new HashMap<>();
        params.put("nickName", newNickName);
        params.put("sign", newSign);
        params.put("userName", userName);
        Call<ResultEntity> call = api.updateUserData(id, params);
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
                    Toast.makeText(getAct(), "" + result.getMsg(), Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                    saveData();
                    finish();
                } else {
                    Toast.makeText(getAct(), "" + result.getMsg(), Toast.LENGTH_SHORT).show();
                    hideProgressDialog();

                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {
                hideProgressDialog();
            }
        });
    }

    private void saveData() {
            PreferencesUtils.putString(ChangeUserDataActivity.this, Const.NICK_NAME, newNickName);

            PreferencesUtils.putString(ChangeUserDataActivity.this, Const.SIGN, newSign);


    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    //展示加载对话框
    private void showProgressDialog() {
        proDialog = android.app.ProgressDialog.show(getAct(), "", "正在更改");

        proDialog.setCanceledOnTouchOutside(true);
    }

    private void hideProgressDialog() {
        if (proDialog != null)
            proDialog.dismiss();
    }
}
