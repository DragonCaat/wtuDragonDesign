package com.dragon.wtudragondesign.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;


/**
 * Created by Dragon on 2018/3/23.
 * 不可滑动的recycleView
 */

public class CustomLinearLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomLinearLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }


    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}