package com.dragon.wtudragondesign.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.bean.ReputationEntity;

import java.util.List;

/**
 * Created by dragon on 2018/4/5.
 * 信誉的适配器
 */

public class ReputationAdapter extends RecyclerView.Adapter<ReputationAdapter.ViewHolder> {
    private Context mContext;
    private List<ReputationEntity> mReputationList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userView;

        TextView rangeView;
        public ViewHolder(View itemView) {
            super(itemView);
            userView = itemView.findViewById(R.id.tv_reputation_user);
            rangeView = itemView.findViewById(R.id.tv_range);
        }
    }

    public ReputationAdapter(List<ReputationEntity> mReputationList) {
        this.mReputationList = mReputationList;
    }

    @Override
    public ReputationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.reputation_item, parent, false);
        return new ReputationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReputationAdapter.ViewHolder holder, int position) {
        ReputationEntity reputationEntity = mReputationList.get(position);

        holder.userView.setText(reputationEntity.getUserName());

        holder.rangeView.setText(position+" 楼");

//        Glide.with(mContext).load(courierEntity.getPicUrl())
//                .placeholder(R.mipmap.ic_launcher)
//                .into(holder.imageView);
//
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, CourierDetailsActivity.class);
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mReputationList.size();
    }
}
