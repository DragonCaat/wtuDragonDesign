package com.dragon.wtudragondesign.fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragon.wtudragondesign.R;


/**
 * mifi充值的fragment
 */
public class FragmentVipRecharge extends Fragment {

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_vip_recharge, container, false);

		init();
		return view;

	}
	protected void init() {
		

	}

}
