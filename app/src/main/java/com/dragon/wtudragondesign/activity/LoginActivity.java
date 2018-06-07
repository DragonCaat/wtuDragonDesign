package com.dragon.wtudragondesign.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.bean.ResultEntity;
import com.dragon.wtudragondesign.bean.UserDataEntity;
import com.dragon.wtudragondesign.retrofit.ApiService;
import com.dragon.wtudragondesign.retrofit.RetrofitClient;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.dragon.wtudragondesign.utils.KeyCodeUtils;
import com.dragon.wtudragondesign.utils.PreferencesUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by dragon on 2018/2/7.
 * 登陆界面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private final static int LOGIN_SUCESS = 0;
    private final static int LOGIN_FAIL = 1;

    private TextView mBtnLogin, mBtnRegister, mBtnFindPass;

    private View progress;

    private View mInputLayout;

    private float mWidth, mHeight;

    private LinearLayout mName, mPsw;

    private EditText mEtName, mEtPass;

    private String userName = "";
    private String passWord = "";
    private int userId = 0;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case LOGIN_SUCESS:
                    saveUserData();
                    break;
                case LOGIN_FAIL:
                    recovery();
                    mBtnLogin.setEnabled(true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideToolBar();
        loadMainUI(R.layout.activity_login);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void init() {
        mBtnLogin = fv(R.id.main_btn_login);
        mBtnRegister = fv(R.id.tv_register);
        mBtnRegister.setOnClickListener(this);
        mBtnFindPass = fv(R.id.tv_find_pass);
        mBtnFindPass.setOnClickListener(this);

        progress = fv(R.id.layout_progress);
        mInputLayout = fv(R.id.input_layout);
        mName = fv(R.id.input_layout_name);
        mPsw = fv(R.id.input_layout_psw);

        mEtName = fv(R.id.et_username);
        mEtPass = fv(R.id.et_password);

        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void initData() {
        userName = mEtName.getText().toString();
        passWord = mEtPass.getText().toString();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
            showToast("用户名或者密码不能为空");
        } else {
            mBtnLogin.setEnabled(false);
            // 计算出控件的高与宽
            mWidth = mBtnLogin.getMeasuredWidth();
            mHeight = mBtnLogin.getMeasuredHeight();
            // 隐藏输入框
            mName.setVisibility(View.INVISIBLE);
            mPsw.setVisibility(View.INVISIBLE);
            inputAnimator(mInputLayout, mWidth, mHeight);

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(2000);//休眠3秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /**
                     * 要执行的操作
                     */
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //loginToEase();
                            login(userName,passWord);
                        }
                    });
                }
            }.start();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn_login:
                initData();
                KeyCodeUtils.closeKeyCode(this,mBtnLogin);
                break;
            case R.id.tv_register:
                skipPageWithAnim(RegisterActivity.class);
                break;
            case R.id.tv_find_pass:
                skipPageWithAnim(FindPassActivity.class);
                break;
            default:
                break;
        }

    }

    /**
     * 输入框的动画效果
     *
     * @param view 控件
     * @param w    宽
     * @param h    高
     */
    private void inputAnimator(final View view, float w, float h) {
        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
    }

    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        //animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }

    /**
     * 恢复初始状态
     */
    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f, 1f);
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }

    private void loginToEase() {
        EMClient.getInstance().login(userName, passWord, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                mHandler.sendEmptyMessage(LOGIN_SUCESS);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

                mHandler.sendEmptyMessage(LOGIN_FAIL);
            }
        });
    }

    // 登录服务器
    private void login(String _loginName, String _loginPassWord) {
        ApiService api = RetrofitClient.getInstance(this).Api();
        Map<String, String> params = new HashMap<>();

        params.put("userName", _loginName);
        params.put("password", _loginPassWord);

        Call<ResultEntity> call = api.login(params);
        call.enqueue(new retrofit2.Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call,
                                   Response<ResultEntity> response) {

                if (response.body() == null) {
                    return;
                }
                ResultEntity result = response.body();
                int res = result.getCode();
                if (res == 1) {// 登录成功

                    userId = (int) result.getData();
                    loginToEase();

                } else {
                    Toast.makeText(LoginActivity.this,""+result.getMsg(),Toast.LENGTH_SHORT).show();

                    mHandler.sendEmptyMessage(LOGIN_FAIL);
                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {
                mHandler.sendEmptyMessage(LOGIN_FAIL);
            }
        });
    }

    //登陆成功后
    private void saveUserData() {
        PreferencesUtils.putString(getAct(), Const.USER_NAME, userName);
        PreferencesUtils.putString(getAct(), Const.PASS_WORD, passWord);
        PreferencesUtils.putInt(getAct(), Const.ID, userId);

        getUserData();

    }

    // 获取用户信息
    private void getUserData() {
        ApiService api = RetrofitClient.getInstance(this).Api();
        // Map<String, Object> params = new HashMap<>();
        Call<ResultEntity> call = api.getUserData(userId);
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
                        saveData(userDataEntity);
                    skipPage(MainActivity.class);
                    finish();

                } else {
                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {

            }
        });
    }

    private void saveData(UserDataEntity userDataEntity) {
        if (!TextUtils.isEmpty(userDataEntity.getHeadImg()))
            PreferencesUtils.putString(LoginActivity.this, Const.HEAD_IMAGE, userDataEntity.getHeadImg());

        if (!TextUtils.isEmpty(userDataEntity.getNickName()))
            PreferencesUtils.putString(LoginActivity.this, Const.NICK_NAME, userDataEntity.getNickName());

        if (!TextUtils.isEmpty(userDataEntity.getSign()))
            PreferencesUtils.putString(LoginActivity.this, Const.SIGN, userDataEntity.getSign());



        EaseUser eUser = new EaseUser(userName);
        if (!TextUtils.isEmpty(userDataEntity.getHeadImg())){

            PreferencesUtils.putString(LoginActivity.this, Const.SIGN, userDataEntity.getSign());
            eUser.setAvatar(userDataEntity.getHeadImg());
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBtnLogin.setEnabled(true);
    }
}
