package com.dragon.wtudragondesign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.activity.ChatActivity;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.EaseConversationList;

/**
 * Created by Dragon on 2018/3/22.
 * <p>
 * 消息界面
 */

public class FragmentMessage extends Fragment {
    private View view;
    public static EaseConversationListFragment easeConversationList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //会话列表控件
        initView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);
        return view;
    }

    private void initView(){
        if (easeConversationList == null) {
            easeConversationList = new EaseConversationListFragment();
            easeConversationList.hideTitleBar();
        }
        easeConversationList
                .setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

                    @Override
                    public void onListItemClicked(EMConversation conversation) {
                        if (conversation.getType() == EMConversation.EMConversationType.Chat) {
                            Intent chat = new Intent(getActivity(),
                                    ChatActivity.class);
                            chat.putExtra(EaseConstant.EXTRA_USER_ID,
                                    conversation.conversationId()); // 对方账号
                            chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE,
                                    EaseConstant.CHATTYPE_SINGLE); // 单聊模式
                            startActivity(chat);

                        } else if (conversation.getType() == EMConversation.EMConversationType.GroupChat) {
                            Intent chat = new Intent(getActivity(),
                                    ChatActivity.class);
                            chat.putExtra(EaseConstant.EXTRA_USER_ID,
                                    conversation.conversationId()); // 对方账号
                            chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE,
                                    EaseConstant.CHATTYPE_GROUP); // 群聊模式
                            startActivity(chat);
                        }
                    }
                });

        getChildFragmentManager().beginTransaction()
                .add(R.id.container, easeConversationList).commit();
    }
}
