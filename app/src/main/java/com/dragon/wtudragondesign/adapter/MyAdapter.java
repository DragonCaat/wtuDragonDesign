package com.dragon.wtudragondesign.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.bean.NewsEntity;

/**
 * Created by dragon on 2018/5/1.
 * 被装饰类要和装饰类继承自同一父类
 */

public class MyAdapter extends BaseAdapter<String> {
    private static Context mContext;

    public MyAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_mine, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        NewsEntity.DataBean.ListBean bean = getDataSet().get(position);
        ((MyViewHolder) holder).bind(bean);
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        TextView mTvRead;
        ImageView mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.tv_title);

            mTvRead = itemView.findViewById(R.id.tv_read);

            mImageView = itemView.findViewById(R.id.iv_image);
        }

        public void bind(NewsEntity.DataBean.ListBean bean) {
            mTextView.setText(bean.getTitle());

            mTvRead.setText("阅读量： "+bean.getRead_count());

            Glide.with(mContext).load(bean.getImage())
                    .placeholder(R.mipmap.ta)
                    .centerCrop()
                    .into(mImageView);
        }
    }

}
