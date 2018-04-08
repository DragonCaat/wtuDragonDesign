package com.dragon.wtudragondesign.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.view.CircleWaveView;

/**
 * mifi查询的fragment
 */
public class FragmentVipQuery extends Fragment implements View.OnClickListener, TextWatcher {

	private int FLAG = 1;// 1：点击查询 ，0：点击续费

	private EditText mEtMiFiId;
	private ImageView mIvDelete;
	private Button mBtnQueryNow;

	private RelativeLayout mRlMiFi;
	private CircleWaveView waveView;

	private View view;

	private String MiFiIdStr = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_query_mifi, container, false);
		init();
		return view;

	}

	protected void init() {
		mIvDelete = (ImageView) view.findViewById(R.id.iv_delete);
		mIvDelete.setOnClickListener(this);

		mEtMiFiId = (EditText) view.findViewById(R.id.et_mifi_id);

		mEtMiFiId.addTextChangedListener(this);

		mBtnQueryNow = (Button) view.findViewById(R.id.btn_query_now);
		mBtnQueryNow.setOnClickListener(this);

		waveView = (CircleWaveView) view.findViewById(R.id.circle_wave);
		wave(waveView);

		mRlMiFi = (RelativeLayout) view.findViewById(R.id.rl_show_mifi);

	}

	@Override
	public void afterTextChanged(Editable arg0) {

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		if (arg0 == null || arg0.length() == 0) {
			mIvDelete.setVisibility(View.GONE);
		} else {
			mIvDelete.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.iv_delete:
			mEtMiFiId.setText("");
			break;
		case R.id.btn_query_now:
			if (FLAG == 1) {
				checkUserInput();
				FLAG = 0;
			} else {
				mBtnQueryNow.setText("立即续费");
				FLAG = 1;
			}

			break;

		default:
			break;
		}
	}

	private void checkUserInput() {
		MiFiIdStr = mEtMiFiId.getText().toString();
		if (TextUtils.isEmpty(MiFiIdStr)) {
			Toast.makeText(getActivity(), "请先输入设备ID", Toast.LENGTH_SHORT).show();
		} else {

		}
	}

	public void wave(View view) {
		CircleWaveView waveView = (CircleWaveView) view;
		waveView.setCurProgress(50);
		waveView.setWaveAnim(true);
	}

}
