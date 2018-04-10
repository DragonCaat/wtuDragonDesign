package com.dragon.wtudragondesign.fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.adapter.VipRechargeAdapter;
import com.dragon.wtudragondesign.bean.VipEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * 会员充值的fragment
 */
public class FragmentVipRecharge extends Fragment implements AdapterView.OnItemClickListener{

	private View view;

	private GridView mGvVip;

	private List<VipEntity> list;

	private VipRechargeAdapter adapter;

	private ImageView mImageViewWXPay;
	private ImageView mImageViewALiPay;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_vip_recharge, container, false);

		init();
		return view;

	}
	protected void init() {

		mGvVip = view.findViewById(R.id.gv_vip);

		initData();

		adapter = new VipRechargeAdapter(list,getContext());

		adapter.setSeclection(0);

		mGvVip.setAdapter(adapter);

		mGvVip.setOnItemClickListener(this);

		mImageViewWXPay = view.findViewById(R.id.iv_wx_pay);
		mImageViewWXPay.setOnClickListener(listener);

		mImageViewALiPay = view.findViewById(R.id.iv_ali_pay);
		mImageViewALiPay.setOnClickListener(listener);

	}

	private void initData(){
		list = new ArrayList<>();

		VipEntity vipEntity = new VipEntity();
		vipEntity.setVipMonth("1个月");
		vipEntity.setVipPrice("¥ 5.0");

		VipEntity vipEntity1 = new VipEntity();
		vipEntity1.setVipMonth("3个月");
		vipEntity1.setVipPrice("¥ 12.0");

		VipEntity vipEntity2 = new VipEntity();
		vipEntity2.setVipMonth("6个月");
		vipEntity2.setVipPrice("¥ 25.0");

		VipEntity vipEntity3 = new VipEntity();
		vipEntity3.setVipMonth("9个月");
		vipEntity3.setVipPrice("¥ 40.0");

		VipEntity vipEntity4 = new VipEntity();
		vipEntity4.setVipMonth("12个月");
		vipEntity4.setVipPrice("¥ 65.0");

		list.add(vipEntity);
		list.add(vipEntity1);
		list.add(vipEntity2);
		list.add(vipEntity3);
		list.add(vipEntity4);
	}

	View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.iv_wx_pay) {
				setButtonOn(mImageViewWXPay);
				//PAYFLAG = Const.WX_PAY;
			} else {
				setButtonOff(mImageViewWXPay);
			}
			if (v.getId() == R.id.iv_ali_pay) {
				setButtonOn(mImageViewALiPay);
				//PAYFLAG = Const.ALI_PAY;
			} else {
				setButtonOff(mImageViewALiPay);
			}
		}
	};

	private void setButtonOff(ImageView imageView) {
		imageView.setImageResource(R.mipmap.button_choice_pay_off);
	}

	private void setButtonOn(ImageView imageView) {
		imageView.setImageResource(R.mipmap.button_choice_pay_on);
	}


	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		adapter.setSeclection(i);
		adapter.notifyDataSetChanged();
	}
}
