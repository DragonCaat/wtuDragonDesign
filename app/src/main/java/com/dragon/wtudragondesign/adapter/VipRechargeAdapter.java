package com.dragon.wtudragondesign.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.bean.VipEntity;

import java.util.List;

/**
 * Created by dragon on 2018/4/9.
 * 会员充值适配器
 */

public class VipRechargeAdapter extends BaseAdapter{

    private int clickTemp = -1;

    private List<VipEntity> list;

    private Context mContext;

    public VipRechargeAdapter(List<VipEntity> list,Context mContext){
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        VipEntity vipEntity = list.get(position);

        View view;
        if (convertView != null)
            view = convertView;
        else
            view = View.inflate(mContext, R.layout.vip_item,null);

        TextView tvMonth = view.findViewById(R.id.vip_month);
        TextView tvPrice = view.findViewById(R.id.vip_price);
        RelativeLayout rlVip = view.findViewById(R.id.rl_vip);

        tvMonth.setText(vipEntity.getVipMonth());
        tvPrice.setText(vipEntity.getVipPrice());

        if (clickTemp == position)
            rlVip.setBackgroundResource(R.drawable.vip_press);
        else
            rlVip.setBackgroundResource(R.drawable.vip_normal);

        return view;
    }

    public void setSeclection(int position){
        clickTemp = position;
    }
}
