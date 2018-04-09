package com.dragon.wtudragondesign.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.activity.ActivityFriends;
import com.dragon.wtudragondesign.activity.AddFriendActivity;
import com.dragon.wtudragondesign.activity.BusinessCooperationActivity;
import com.dragon.wtudragondesign.activity.LoginActivity;
import com.dragon.wtudragondesign.activity.MyPublishRewardActivity;
import com.dragon.wtudragondesign.activity.MyReceiveRewardActivity;
import com.dragon.wtudragondesign.activity.VipActivity;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.utils.PreferencesUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dragon on 2018/3/24.
 * 我的界面
 */

public class FragmentMy extends Fragment implements View.OnClickListener {

    private final static int LOGIN_OUT_PROGRESS = 1;//正在退出登录
    private final static int LOGIN_OUT_SUCCESS = 0;//退出登录成功

    private TextView mTvMyFriends;

    private TextView mTvAddFriend;

    private TextView mTvMyReward;

    private TextView mTvMyReceive;

    private TextView mTvBusinessCooperation;

    private TextView mTvVipRecharge;

    private View view;

    private ProgressDialog proDialog;

    private CircleImageView mCvPhoto;

    private Button mBtnLoginOut;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN_OUT_SUCCESS:
                    cleanUserData();
                    break;
                case LOGIN_OUT_PROGRESS:
                    showProgressDialog();
                default:
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        init();
        return view;
    }

    /**
     * 初始化控件
     */
    @SuppressLint("CutPasteId")
    private void init() {
        mTvMyFriends = view.findViewById(R.id.tv_my_friends);
        mTvMyFriends.setOnClickListener(this);

        mTvAddFriend = view.findViewById(R.id.tv_add_friend);
        mTvAddFriend.setOnClickListener(this);

        mTvMyReward = view.findViewById(R.id.tv_my_publish_reward);
        mTvMyReward.setOnClickListener(this);

        mTvMyReceive = view.findViewById(R.id.tv_my_receive_reward);
        mTvMyReceive.setOnClickListener(this);

        mCvPhoto = view.findViewById(R.id.cv_photo);
        mCvPhoto.setOnClickListener(this);

        mBtnLoginOut = view.findViewById(R.id.btn_login_out);
        mBtnLoginOut.setOnClickListener(this);

        mTvBusinessCooperation = view.findViewById(R.id.tv_about_our);
        mTvBusinessCooperation.setOnClickListener(this);

        mTvVipRecharge = view.findViewById(R.id.tv_recharge_vip);
        mTvVipRecharge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_my_friends:
                intentToNewPage(ActivityFriends.class);
                break;
            case R.id.tv_add_friend:
                intentToNewPage(AddFriendActivity.class);
                break;
            case R.id.tv_my_publish_reward:
                intentToNewPage(MyPublishRewardActivity.class);
                break;
            case R.id.tv_my_receive_reward:
                intentToNewPage(MyReceiveRewardActivity.class);
                break;
            case R.id.cv_photo:
                intentToNewPage(LoginActivity.class);
                break;
            case R.id.btn_login_out:
                //退出登录
                showDialog();
                break;
            case R.id.tv_about_our:
                intentToNewPage(BusinessCooperationActivity.class);
                break;

            case R.id.tv_recharge_vip:
                intentToNewPage(VipActivity.class);
                break;

            default:
                break;
        }
    }

    //跳转页面
    private void intentToNewPage(Class<? extends Activity> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    // 退出登录的弹框
    @SuppressWarnings("deprecation")
    private void showDialog() {
        // 创建构建器
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // 设置参数
        builder.setTitle("温馨提示").setIcon(R.mipmap.ic_launcher_round)
                .setMessage("您是否确认退出登录")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        loginOut();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {// 消极

            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                builder.create().dismiss();
            }
        });
        builder.create().show();
    }

    //退出环信登录
    private void loginOut() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                mHandler.sendEmptyMessage(LOGIN_OUT_SUCCESS);
            }

            @Override
            public void onProgress(int progress, String status) {
                mHandler.sendEmptyMessage(LOGIN_OUT_PROGRESS);
            }

            @Override
            public void onError(int code, String message) {

            }
        });
    }
    //退出登陆清除用户数据
    private void cleanUserData(){
        PreferencesUtils.putString(getActivity(), Const.USER_NAME,"");
        PreferencesUtils.putString(getActivity(), Const.PASS_WORD,"");
        Toast.makeText(getContext(), "退出成功", Toast.LENGTH_SHORT).show();
        hideProgressDialog();
    }

    //展示加载对话框
    private void showProgressDialog() {
        proDialog = android.app.ProgressDialog.show(getActivity(), "", "正在退出登录");
    }

    private void hideProgressDialog() {
        if (proDialog != null)
            proDialog.dismiss();
    }
}
