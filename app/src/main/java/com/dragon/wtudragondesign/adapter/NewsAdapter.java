package com.dragon.wtudragondesign.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.dragon.wtudragondesign.bean.NewsEntity;

import java.util.List;

/**
 * Created by Dragon on 2018/3/20.
 * <p>
 * 主界面的新闻的适配器
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context mContext;
    private List<NewsEntity.DataBean.ListBean> listBeans;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleView;
        TextView readCount;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
            titleView = itemView.findViewById(R.id.tv_title);

            readCount = itemView.findViewById(R.id.tv_read);

        }
    }

    public NewsAdapter(List<NewsEntity.DataBean.ListBean> mCourierList) {
        this.listBeans = mCourierList;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_mine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        final NewsEntity.DataBean.ListBean bean = listBeans.get(position);
        holder.titleView.setText(bean.getTitle());
        holder.readCount.setText("阅读量: "+bean.getRead_count());

        Glide.with(mContext).load(bean.getImage())
                .placeholder(R.mipmap.ta)
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }
}
