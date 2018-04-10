package com.dragon.wtudragondesign.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.view.CircleWaveView;

/**
 * 会员查询的fragment
 */
public class FragmentVipQuery extends Fragment implements View.OnClickListener{

	private Button mBtnQueryNow;

	private RelativeLayout mRlMiFi;
	private CircleWaveView waveView;

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_vip_query, container, false);
		init();
		return view;

	}

	protected void init() {

		mBtnQueryNow =  view.findViewById(R.id.btn_query_now);
		mBtnQueryNow.setOnClickListener(this);

		waveView =  view.findViewById(R.id.circle_wave);
		wave(waveView);

		mRlMiFi =  view.findViewById(R.id.rl_show_mifi);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

		default:
			break;
		}
	}

	public void wave(View view) {
		CircleWaveView waveView = (CircleWaveView) view;
		waveView.setCurProgress(50);
		waveView.setWaveAnim(true);
	}

}
