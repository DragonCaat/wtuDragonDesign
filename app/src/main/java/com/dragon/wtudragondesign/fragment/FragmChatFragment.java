package com.dragon.wtudragondesign.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dragon.wtudragondesign.R;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class FragmChatFragment extends EaseChatFragment {
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//EaseUI easeUI = EaseUI.getInstance();
		// 需要EaseUI库显示用户头像和昵称设置此provider

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		messageList.refresh();
	}

	@Override
	protected void setUpView() {
		super.setUpView();
		titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
		titleBar.setRightLayoutVisibility(View.GONE);
		
		
	}
}
