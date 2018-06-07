package com.dragon.wtudragondesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.activity.CourierDetailsActivity;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.bean.CourierEntity;

import java.util.List;

/**
 * Created by Dragon on 2018/3/20.
 * <p>
 * 主界面的recycleView的适配器
 */

public class CourierAdapter extends RecyclerView.Adapter<CourierAdapter.ViewHolder> {
    private Context mContext;
    private List<CourierEntity> mCourierList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleView;
        TextView contentView;
        TextView userView;

        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_pic);
            titleView = itemView.findViewById(R.id.tv_title);
            contentView = itemView.findViewById(R.id.tv_content);
            userView = itemView.findViewById(R.id.tv_user);

            cardView = itemView.findViewById(R.id.courier_item);
        }
    }

    public CourierAdapter(List<CourierEntity> mCourierList) {
        this.mCourierList = mCourierList;
    }

    @Override
    public CourierAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.courier_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourierAdapter.ViewHolder holder, int position) {
        final CourierEntity courierEntity = mCourierList.get(position);
        holder.titleView.setText(courierEntity.getTitle());
        holder.contentView.setText(courierEntity.getContent());
        holder.userView.setText(courierEntity.getPublisherUserName());

        Glide.with(mContext).load(Const.BASE_URL + courierEntity.getImgSrc())
                .placeholder(R.mipmap.ta)
                .centerCrop()
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourierDetailsActivity.class);
                intent.putExtra("userId", courierEntity.getId());
                intent.putExtra("pic", courierEntity.getImgSrc());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourierList.size();
    }
}
