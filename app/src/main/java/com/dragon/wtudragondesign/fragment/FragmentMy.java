package com.dragon.wtudragondesign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.activity.ActivityFriends;
import com.dragon.wtudragondesign.activity.AddFriendActivity;
import com.dragon.wtudragondesign.activity.ChatActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * Created by Dragon on 2018/3/24.
 * 我的界面
 */

public class FragmentMy extends Fragment implements View.OnClickListener {

    private TextView textView;

    private TextView tvAdd;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        textView = view.findViewById(R.id.tv_my_friends);
        textView.setOnClickListener(this);

        tvAdd = view.findViewById(R.id.tv_add_friend);
        tvAdd.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_my_friends:
                Intent intent = new Intent(getContext(), ActivityFriends.class);
                startActivity(intent);
                break;
            case R.id.tv_add_friend:
                Intent intent1 = new Intent(getContext(), AddFriendActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
