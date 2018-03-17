package com.dragon.wtudragondesign.template;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon.wtudragondesign.R;


public abstract class BaseActivity extends TemplateActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView mTextViewTitle;
    private Toolbar toolbar;

    private DrawerLayout mDrawerLayout;

    private ImageView mImageView;

    private TextView mTvBack;

    private NavigationView navView;

    //右边的文字
    private TextView mTextViewRight;
    //右边的图片
    private ImageView mImageViewRight;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mDrawerLayout = fv(R.id.drawer_layout);
        mImageView = fv(R.id.iv_menu);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navView = fv(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        mTvBack = fv(R.id.tv_back);
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public abstract void init();
    public abstract void initData();

    /**
     * 加载主区域内容
     */
    public void loadMainUI(int layout) {
        ViewGroup main_cont = fv(R.id.main_contain);
        View v = getLayoutInflater().inflate(layout, null);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        main_cont.addView(v);
    }
    /**
     * 设置toolbar的背景颜色
     * */
    public void setToolbarColor(int color){
        toolbar = fv(R.id.toolbar);
        toolbar.setBackgroundResource(color);
    }


    /**
     * 跳转界面
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void skipPage(Class<? extends Activity> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
    /**
     * 跳转界面
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void skipPageWithAnim(Class<? extends Activity> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getAct()).toBundle());
    }


    /**
     * 设置主页面的标题
     * */
    public void setTitle(String string){
        mTextViewTitle = fv(R.id.tv_head_title);
        mTextViewTitle.setText(string);
    }
    public void hideTitle(){
        mTextViewTitle = fv(R.id.tv_head_title);
        mTextViewTitle.setVisibility(View.GONE);
    }
    public void showTitle(){
        mTextViewTitle = fv(R.id.tv_head_title);
        mTextViewTitle.setVisibility(View.VISIBLE);
    }

    public void hideToolBar(){
        toolbar = fv(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }
    public void showToolBar(){
        toolbar = fv(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }
    public void setRightText(String string){
        mTextViewRight = fv(R.id.tv_title_right);
        mTextViewRight.setText(string);
    }
    public void showRightText(){
        mTextViewRight = fv(R.id.tv_title_right);
        mTextViewRight.setVisibility(View.VISIBLE);
    }
    public void hideRightText(){
        mTextViewRight = fv(R.id.tv_title_right);
        mTextViewRight.setVisibility(View.GONE);
    }

    public void hideRightImage(){
        mImageViewRight = fv(R.id.iv_menu);
        mImageViewRight.setVisibility(View.GONE);
    }

    public void showLeftImage(){

        mTvBack.setVisibility(View.VISIBLE);
    }
    public void hideLeftImage(){

        mTvBack.setVisibility(View.GONE);
    }
    public void showToast(String string){
        Toast.makeText(getAct(), string,Toast.LENGTH_SHORT).show();
    }


    public void showMenuButton(){
        mImageView.setVisibility(View.VISIBLE);
    }
    public void hideMenuButton(){
        mImageView.setVisibility(View.GONE);
    }

    /**
     * 侧滑栏的点击事件
     * */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
//            case R.id.nav_weather:
//                skipPage(LocationActivity.class);
//
//            case R.id.nav_news:
//
//                break;

        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDrawerLayout.closeDrawers();
    }
}
