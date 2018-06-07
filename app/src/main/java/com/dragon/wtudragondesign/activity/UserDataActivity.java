package com.dragon.wtudragondesign.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.dragon.wtudragondesign.view.DialogGetPicture;

import java.io.File;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class UserDataActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_CAMERA = 1;// 拍照
    public static final int PHOTO_GALLERY = 2; // 相册
    public static final String IMAGE_UNSPECIFIED = "image/*";//随意图片类型

    private ImageView mIvBg;

    private TextView mTvVip;
    private TextView mTvFriends;

    private TextView mTvNickName;
    private TextView mTvSign;
    private TextView mTvUserName;

    private TextView mTvReputation;

    private CircleImageView mCvPhoto;


    private Button mBtnChangeData;
    private ProgressDialog proDialog;
    private int id;

    private String nickName = "";
    private String sign = "";
    private String userName = "";

    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = PreferencesUtils.getInt(this, Const.ID);
        setContentView(R.layout.activity_user_data);
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        Toolbar toolbar = findViewById(R.id.courier_detail_toolbar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("我的资料");
        collapsingToolbar.setCollapsedTitleGravity(Gravity.LEFT);
        collapsingToolbar.setExpandedTitleGravity(Gravity.CENTER | Gravity.TOP);

        ImageView bg = findViewById(R.id.courier_detail_background);
        Glide.with(this).load(R.mipmap.ta).into(bg);


        mTvVip = findViewById(R.id.user_vip);
        mTvVip.setOnClickListener(this);

        mTvFriends = findViewById(R.id.user_friends);
        mTvFriends.setOnClickListener(this);

        mBtnChangeData = findViewById(R.id.btn_change_data);
        mBtnChangeData.setOnClickListener(this);


        mTvNickName = findViewById(R.id.user_nick_name);
        mTvSign = findViewById(R.id.user_sign);
        mTvReputation = findViewById(R.id.user_reputation);

        mTvUserName = findViewById(R.id.user_name);

        mCvPhoto = findViewById(R.id.cv_user_photo);
        mCvPhoto.setOnClickListener(this);

        mIvBg = findViewById(R.id.courier_detail_background);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_vip:
                Intent intent = new Intent(this, VipActivity.class);
                startActivity(intent);
                break;
            case R.id.user_friends:
                Intent intent1 = new Intent(this, ActivityFriends.class);
                startActivity(intent1);
                break;

            case R.id.btn_change_data:
                Intent intent2 = new Intent(this, ChangeUserDataActivity.class);
                intent2.putExtra("nickName", nickName);
                intent2.putExtra("sign", sign);
                startActivity(intent2);
                break;

            //更换头像
            case R.id.cv_user_photo:
                new DialogGetPicture(this) {
                    @Override
                    public void amble() {
                        selectPictureFromAlbum(UserDataActivity.this);
                    }

                    @Override
                    public void photo() {
                        photograph(UserDataActivity.this);

                    }
                }.show();

                break;
            default:
                break;
        }
    }


    // 获取用户信息
    private void getUserData() {
        //showProgressDialog("正在加载数据");
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
                        Toast.makeText(UserDataActivity.this, "" + result.getMsg(), Toast.LENGTH_SHORT).show();

                    // hideProgressDialog();

                } else {
                    Toast.makeText(UserDataActivity.this, "" + result.getMsg(), Toast.LENGTH_SHORT).show();


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
            mTvVip.setText("你还不是会员");
            mTvVip.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_vip, null), null, getResources().getDrawable(R.mipmap.into, null), null);
        }

        mTvReputation.setText("信誉良好 ( " + userData.getReputation() + " )");

        if (!TextUtils.isEmpty(userData.getHeadImg())) {

            PreferencesUtils.putString(UserDataActivity.this, Const.HEAD_IMAGE, userData.getHeadImg());

            Glide.with(this).load(Const.BASE_URL + userData.getHeadImg())
                    .placeholder(R.mipmap.photo).into(mCvPhoto);

            Glide.with(this).load(Const.BASE_URL + userData.getHeadImg())
                    .placeholder(R.mipmap.photo).into(mIvBg);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        getUserData();
        String headImage = PreferencesUtils.getString(this, Const.HEAD_IMAGE);
        Glide.with(this).load(Const.BASE_URL + headImage)
                .placeholder(R.mipmap.photo).into(mCvPhoto);

        Glide.with(this).load(Const.BASE_URL + headImage)
                .placeholder(R.mipmap.photo).into(mIvBg);
    }

    /**
     * 拍照
     *
     * @param activity
     */
    public void photograph(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 从系统相冊中选取照片上传
     *
     * @param activity
     */
    public static void selectPictureFromAlbum(Activity activity) {
        // 调用系统的相冊
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_UNSPECIFIED);

        activity.startActivityForResult(intent, PHOTO_GALLERY);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                if (data.getData() != null) {
                    photoUri = data.getData();
                } else {
                    photoUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
                }
                Cursor cursor1 = getContentResolver().query(photoUri, null, null, null, null);
                if (cursor1 != null && cursor1.moveToFirst()) {
                    String path = cursor1.getString(cursor1.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    if (TextUtils.isEmpty(path))
                        Toast.makeText(UserDataActivity.this, "图像路径不存在", Toast.LENGTH_SHORT).show();
                    else
                        uploadHeadImg(path, photoUri);
                }

                Glide.with(UserDataActivity.this).load(photoUri).into(mCvPhoto);

                break;

            case PHOTO_GALLERY:
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    if (TextUtils.isEmpty(path))
                        Toast.makeText(UserDataActivity.this, "图像路径不存在", Toast.LENGTH_SHORT).show();
                    else
                        uploadHeadImg(path, uri);
                }
                break;
        }

    }

    // 发布新的悬赏
    private void uploadHeadImg(String path, final Uri uri) {
        showProgressDialog("正在上传头像");
        File file = new File(path);
        Map<String, RequestBody> partList = filesToRequestBodyParts(file);
        ApiService api = RetrofitClient.getInstance(this).Api();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", id);
        Call<ResultEntity> call = api.uploadHeadImg(params, partList);
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
                    Toast.makeText(UserDataActivity.this, "" + result.getMsg(), Toast.LENGTH_SHORT).show();
                    Glide.with(UserDataActivity.this).load(uri).into(mCvPhoto);
                    hideProgressDialog();

                } else {
                    Toast.makeText(UserDataActivity.this, "" + result.getMsg(), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {
                hideProgressDialog();
            }
        });
    }

    private Map<String, RequestBody> filesToRequestBodyParts(File file) {
        Map<String, RequestBody> parts = new HashMap<>();

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), file);
        parts.put("file\"; filename=\"" + file.getName(), requestBody);

        return parts;
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

}
