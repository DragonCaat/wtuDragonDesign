package com.dragon.wtudragondesign.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.bean.ResultEntity;
import com.dragon.wtudragondesign.retrofit.ApiService;
import com.dragon.wtudragondesign.retrofit.RetrofitClient;
import com.dragon.wtudragondesign.template.BaseActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by dragon on 2018/2/7.
 * 登陆界面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView mBtnLogin, mBtnRegister, mBtnFindPass;

    private View progress;

    private View mInputLayout;

    private float mWidth, mHeight;

    private LinearLayout mName, mPsw;

    private EditText mEtName, mEtPass;

    private String userName = "";
    private String passWord = "";

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
                            loginToEase();
                            //login(userName,passWord);
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

    private void loginToEase(){
        EMClient.getInstance().login(userName, passWord,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                skipPage(MainActivity.class);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
                recovery();
            }
        });
    }

    // 登录服务器
    private void login(String _loginName, String _loginPassWord) {
        ApiService api = RetrofitClient.getInstance(this).Api();
        Map<String, String> params = new HashMap<>();
        params.put("username", _loginName);
        params.put("pwd", _loginPassWord);
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
                if (res == 0) {// 登录成功
                    Log.i("%%%%%%%%%%%%%%%%%", "onResponse: 登陆成功");

                } else {

                }
            }
            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {
                Log.i("%%%%%%%%%%%%%%%%%", "onResponse: 登陆失败");
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBtnLogin.setEnabled(true);
    }
}
