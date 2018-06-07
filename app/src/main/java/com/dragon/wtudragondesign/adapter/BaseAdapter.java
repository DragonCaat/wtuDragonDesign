package com.dragon.wtudragondesign.adapter;

import android.support.v7.widget.RecyclerView;

import com.dragon.wtudragondesign.bean.NewsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dragon on 2018/5/1.
 * 与数据源相关的字段和方法封装在父类中
 */

public abstract class  BaseAdapter<T> extends RecyclerView.Adapter {

    protected List<NewsEntity.DataBean.ListBean> dataSet = new ArrayList<>();

    public void updateData(List dataSet) {
        this.dataSet.clear();
        appendData(dataSet);
    }

    public void appendData(List dataSet) {
        if (dataSet != null && !dataSet.isEmpty()) {
            this.dataSet.addAll(dataSet);
            notifyDataSetChanged();
        }
    }

    public List<NewsEntity.DataBean.ListBean> getDataSet() {
        return dataSet;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
