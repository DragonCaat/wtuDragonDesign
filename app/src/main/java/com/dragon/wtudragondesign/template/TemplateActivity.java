package com.dragon.wtudragondesign.template;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dragon.wtudragondesign.R;


public class TemplateActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT > Const.ANDROID_SDK){
//            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//            getWindow().setEnterTransition(new Fade());
//            getWindow().setExitTransition(new Fade());//explode:分解 fade:淡出 slide:滑动
//
//        }
        setContentView(R.layout.activity_template);
    }
    /**
     * 获取控件id
     *
     * @param resId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T fv(int resId) {
        return (T) this.findViewById(resId);
    }

    /**
     * 获取控件id
     *
     * @param v
     * @param resId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T fv(View v, int resId) {
        return (T) v.findViewById(resId);
    }

    public Activity getAct(){
        return this;
    }
}
