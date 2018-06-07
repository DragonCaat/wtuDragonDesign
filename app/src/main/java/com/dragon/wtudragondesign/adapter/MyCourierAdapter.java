package com.dragon.wtudragondesign.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.activity.CourierDetailsActivity;
import com.dragon.wtudragondesign.activity.UpdateRewardActivity;
import com.dragon.wtudragondesign.bean.Const;
import com.dragon.wtudragondesign.bean.CourierDetailEntity;
import com.dragon.wtudragondesign.bean.CourierEntity;
import com.dragon.wtudragondesign.bean.ResultEntity;
import com.dragon.wtudragondesign.retrofit.ApiService;
import com.dragon.wtudragondesign.retrofit.RetrofitClient;
import com.dragon.wtudragondesign.view.CustomDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Dragon on 2018/3/20.
 * <p>
 * 我发布的界面的recycleView的适配器
 */

public class MyCourierAdapter extends RecyclerView.Adapter<MyCourierAdapter.ViewHolder> {
    private Context mContext;
    private List<CourierEntity> mCourierList;

    private ProgressDialog proDialog;

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

    public MyCourierAdapter(List<CourierEntity> mCourierList) {
        this.mCourierList = mCourierList;
    }

    @Override
    public MyCourierAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.courier_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCourierAdapter.ViewHolder holder, int position) {
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
                Intent intent = new Intent(mContext, UpdateRewardActivity.class);
                intent.putExtra("userId", courierEntity.getId());
                intent.putExtra("pic", courierEntity.getImgSrc());
                mContext.startActivity(intent);
            }
        });

        //长点击删除悬赏
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDeleteDialog(courierEntity.getId(),courierEntity);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCourierList.size();
    }


    /**
     * 显示CustomDialog
     */
    private void showDeleteDialog(final int id, final CourierEntity courierEntity) {

        final CustomDialog dialog = new CustomDialog(mContext);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setHintText("确定要删除吗？");
        dialog.setLeftButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setRightButton("删除", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePublish(id,courierEntity);
                dialog.dismiss();
            }
        });
    }

    // 获取悬赏数据详情
    private void deletePublish(final int publishId, final CourierEntity courierEntity) {
        showProgressDialog();
        ApiService api = RetrofitClient.getInstance(mContext).Api();

        Call<ResultEntity> call = api.deletePublish(publishId);
        call.enqueue(new retrofit2.Callback<ResultEntity>() {
            @Override
            public void onResponse(Call<ResultEntity> call,
                                   Response<ResultEntity> response) {

                if (response.body() == null) {
                    return;
                }
                ResultEntity result = response.body();
                int res = result.getCode();
                if (res == 1) {// 请求成功
                    hideProgressDialog();
                    Toast.makeText(mContext, "" + result.getMsg(), Toast.LENGTH_SHORT).show();

                    mCourierList.remove(courierEntity);
                    notifyDataSetChanged();

                } else {
                    hideProgressDialog();
                    Toast.makeText(mContext, "" + result.getMsg(), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ResultEntity> arg0, Throwable arg1) {
                hideProgressDialog();
            }
        });
    }

    //展示加载对话框
    private void showProgressDialog() {
        proDialog = android.app.ProgressDialog.show(mContext, "", "正在加载");

        proDialog.setCanceledOnTouchOutside(true);
    }

    private void hideProgressDialog() {
        if (proDialog != null)
            proDialog.dismiss();
    }

}
