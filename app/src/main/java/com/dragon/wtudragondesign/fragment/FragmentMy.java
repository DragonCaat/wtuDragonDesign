package com.dragon.wtudragondesign.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.activity.ActivityFriends;
import com.dragon.wtudragondesign.activity.AddFriendActivity;
import com.dragon.wtudragondesign.activity.MyPublishRewardActivity;
import com.dragon.wtudragondesign.activity.MyReceiveRewardActivity;

/**
 * Created by Dragon on 2018/3/24.
 * 我的界面
 */

public class FragmentMy extends Fragment implements View.OnClickListener {

    private TextView mTvMyFriends;

    private TextView mTvAddFriend;

    private TextView mTvMyReward;

    private TextView mTvMyReceive;

    private View view;

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
            default:
                break;
        }
    }

    //跳转页面
    private void intentToNewPage(Class<? extends Activity> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }
}
