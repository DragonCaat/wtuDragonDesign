package com.dragon.wtudragondesign.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.activity.WebViewActivity;
import com.dragon.wtudragondesign.utils.CornersTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragon on 2017-10-26.
 * banner图的view
 */
public class CustomerViewPagerComponent extends RelativeLayout implements
		ViewPager.OnPageChangeListener {
	private final int MAX_COUNT = Integer.MAX_VALUE;
	private final int TIME_INTERVAL = 3000;
	public final int MSG_CODE = 0x986;
	private int[] images;
	private ImageView[] imageViews;
	private ViewPager viewPager;
	private LinearLayout linearLayout;
	private List<View> dots;
	private boolean autoPlayFlag;
	private int currentNumber;

	private Context mContext;
	public void setImages(int[] images, final Context mContext) {
		this.images = images;
		this.mContext = mContext;

		if (images != null) {
			imageViews = new ImageView[images.length];
			for (int i = 0; i < images.length; i++) {
				imageViews[i] = new ImageView(getContext());
				Glide.with(getContext())
						.load(images[i])
						// 设置glide加载图片不缓存
						.transform(new CornersTransform(getContext(),10))
						.into(imageViews[i]);
				imageViews[i].setScaleType(ImageView.ScaleType.FIT_XY);
				imageViews[i].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mContext, WebViewActivity.class);
						intent.putExtra("url","https://github.com/DragonCaat/wtuDragonDesign.git");
						intent.putExtra("name","毕业设计");
						mContext.startActivity(intent);
					}
				});
			}
			// 设置适配器
			viewPager.setAdapter(new MyAdapter());
			// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
			currentNumber = (imageViews.length) * 100;
			viewPager.setCurrentItem(currentNumber);
			// 设置监听器
			viewPager.setOnPageChangeListener(this);
			// 开始自动播放
			final Message message = handler.obtainMessage(MSG_CODE);
			handler.sendMessageDelayed(message, TIME_INTERVAL);
			// 设置点点
			setDot(currentNumber);
		}
	}

	// 构造
	public CustomerViewPagerComponent(Context context, AttributeSet attrs) {
		super(context, attrs);
		viewPager = new ViewPager(getContext());
		viewPager.setLayoutParams(new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		addView(viewPager);
		autoPlayFlag = true;

	}

	// 设置ViewPager的适配器
	public class MyAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return MAX_COUNT;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageViews[position % imageViews.length]);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ViewGroup parent = (ViewGroup) imageViews[position
					% imageViews.length].getParent();
			if (parent != null) {
				parent.removeAllViews();

			}
			container.addView(imageViews[position % imageViews.length], 0);
			return imageViews[position % imageViews.length];
		}
	}

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == MSG_CODE) {
				if (autoPlayFlag) {
					showNext();
				}
				Message message = handler.obtainMessage(MSG_CODE);
				handler.sendMessageDelayed(message, TIME_INTERVAL);
			}

		}
	};

	public void showNext() {
		currentNumber++;
		viewPager.setCurrentItem(currentNumber);
		// 设置点点
		setDot(currentNumber);
	}

	public void showPrevious() {
		currentNumber--;
		viewPager.setCurrentItem(currentNumber);
		// 设置点点
		setDot(currentNumber);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		currentNumber = position;
		// 设置选中的点点
		setDot(currentNumber);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		switch (state) {
		case ViewPager.SCROLL_STATE_IDLE:
			// 无动作、初始状态
			break;
		case ViewPager.SCROLL_STATE_DRAGGING:
			// 点击、滑屏
			autoPlayFlag = false;
			break;
		case ViewPager.SCROLL_STATE_SETTLING:
			// 释放
			autoPlayFlag = true;
			break;
		}
	}

	// 设置当前选中的点点
	private void setDot(int currentNumber) {
		// remove
		removeView(linearLayout);
		// 设置点点
		linearLayout = new LinearLayout(getContext());
		LayoutParams reLayoutParams = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		reLayoutParams.setMargins(0, 0, 0, 20);
		reLayoutParams.addRule(ALIGN_PARENT_BOTTOM);

		linearLayout.setLayoutParams(reLayoutParams);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.setGravity(Gravity.RIGHT);
		linearLayout.setPadding(0,0,5,0);
		addView(linearLayout);
		dots = new ArrayList<>();
		// 设置当前选中
		int current = currentNumber % images.length;
		for (int i = 0; i < images.length; i++) {
			View view = new View(getContext());
			if (current == i) {
				view.setBackgroundResource(R.drawable.dot_focused);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						20, 20);
				// 小圆点的间距
				layoutParams.setMargins(6, 0, 6, 0);
				view.setLayoutParams(layoutParams);
			} else {
				// 小圆点的大小
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						20, 20);
				// 小圆点的间距
				layoutParams.setMargins(6, 0, 6, 0);
				view.setLayoutParams(layoutParams);
				view.setBackgroundResource(R.drawable.dot_normal);
			}
			linearLayout.addView(view);
		}
	}
}