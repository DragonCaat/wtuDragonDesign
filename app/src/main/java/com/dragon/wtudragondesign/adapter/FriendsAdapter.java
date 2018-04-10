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
import com.dragon.wtudragondesign.activity.ChatActivity;
import com.dragon.wtudragondesign.activity.CourierDetailsActivity;
import com.dragon.wtudragondesign.bean.CourierEntity;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.utils.EaseUserUtils;

import java.util.List;

/**
 * Created by Dragon on 2018/3/20.
 * <p>
 * 好友界面的recycleView的适配器
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mFriendsList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView friendView;

        ImageView imageHead;
        public ViewHolder(View itemView) {
            super(itemView);
            friendView = itemView.findViewById(R.id.tv_friend);
            imageHead = itemView.findViewById(R.id.iv_user_head);
        }
    }

    public FriendsAdapter(List<String> mFriendsList, Context context) {
        this.mFriendsList = mFriendsList;
        this.mContext = context;
    }

    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.friends_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendsAdapter.ViewHolder holder, int position) {
        final String friendName = mFriendsList.get(position);
        holder.friendView.setText(friendName);
        Glide.with(mContext).load(R.mipmap.tu).transform(new EaseUserUtils.GlideCircleTransform(mContext)).into(holder.imageHead);

        holder.friendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent(mContext,
                        ChatActivity.class);
                chat.putExtra(EaseConstant.EXTRA_USER_ID,
                        friendName); // 对方账号
                chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE,
                        EaseConstant.CHATTYPE_SINGLE); // 单聊模式
                mContext.startActivity(chat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFriendsList.size();
    }
}
