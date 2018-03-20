package com.dragon.wtudragondesign.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import com.dragon.wtudragondesign.R;

import java.io.File;
import java.util.List;

/**
 * Created by Dragon on 2017/6/30.
 * 图片多选适配器
 */
@SuppressLint("HandlerLeak")
public class CourierPictureAdapter extends BaseAdapter {

    private List<String> mPicList;
    private Context mContext;

    public CourierPictureAdapter(Context context, List<String> mPicList) {
        this.mContext = context;
        this.mPicList = mPicList;
    }

    @Override
    public int getCount() {
        return mPicList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.courier_pic_item, parent, false);
        } else {
            view = convertView;
        }
        ImageView imageView = view.findViewById(R.id.iv_courier_item);
        File file = new File(mPicList.get(position));
        if(file.exists()){
            Bitmap bm = BitmapFactory.decodeFile(mPicList.get(position));
            imageView.setImageBitmap(bm);
        }else {
            Toast.makeText(mContext,"当前路径图片不存在",Toast.LENGTH_SHORT).show();
        }

       // Glide.with(mContext).load(mPicList.get(position)) .placeholder(R.mipmap.ic_launcher).into(imageView);
        return view;
    }
}