package com.dragon.wtudragondesign.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.fragment.FragmChatFragment;
import com.dragon.wtudragondesign.fragment.FragmentMessage;
import com.dragon.wtudragondesign.utils.PreferencesUtils;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentHelper;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

/**
 * 聊天界面
 */
public class ChatActivity extends AppCompatActivity implements
        EaseChatFragmentHelper {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chat);
        init();
    }

    protected void init() {
        FragmChatFragment easeChatFragment = new FragmChatFragment(); // 环信聊天界面
        easeChatFragment.setChatFragmentHelper(this);
        easeChatFragment.setArguments(getIntent().getExtras()); // 需要的参数
        getSupportFragmentManager().beginTransaction()
                .add(R.id.chat, easeChatFragment).commit(); // Fragment切换
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        // 通过扩展属性，将userPic和userName发送出去。
//		String headUrl = PreferencesUtils.getString(this, Const.HEADURL);
//		if (!TextUtils.isEmpty(headUrl)) {
//			message.setAttribute("headUrl", headUrl);
//		}
//		String nickName = PreferencesUtils.getString(this, Const.NICKNAME);
//		if (!TextUtils.isEmpty(nickName)) {
//			message.setAttribute("nickName", nickName);
//		}
    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {

    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }

}
