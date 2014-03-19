package com.pangu.neusoft.drugstore;

import java.util.ArrayList;
import java.util.List;

import com.pangu.neusoft.adapters.DS_DrugCompanyList;
import com.pangu.neusoft.healthe.R;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.TabPageIndicator;

@SuppressLint("NewApi")
public class Drugstore_detail extends FaherDrugActivity
{
	private com.pangu.neusoft.CustomView.CustomViewPager viewPager;// 页卡内容
	private ImageView viewpager_line;// 动画图片
	private TextView textView1, textView2, textView3;
	private List<View> views;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private View view1, view2, view3;// 各个页卡
	private ArrayList<Fragment> fragmentsList;
		

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drugstore_detail_main);
		
		Intent intent = getIntent();

		String drugstorename = intent.getExtras().getString("drugstorename",
				"药房优惠");

		setactivitytitle(drugstorename);

		Init();
		InitImageView();
		InitViewPager();
		//InitFragment();
		DS_DrugCompanyList list = new DS_DrugCompanyList();
		
		//fragment_store_detail_store_detail.setText(list.getIntroduction());
		/* 侧滑菜单 */
		/*
		 * menu = new SlidingMenu(this); menu.setMode(SlidingMenu.LEFT); //设置菜单
		 * 滑动模式，左滑还是右滑 menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		 * menu.setShadowWidthRes(R.dimen.slidingmenu_shadowWidth);
		 * menu.setShadowDrawable(R.drawable.shadow);
		 * menu.setBehindOffsetRes(R.dimen.slidingmenu_behindOffset);
		 * menu.setFadeDegree(0.35f); menu.attachToActivity(this,
		 * SlidingMenu.SLIDING_CONTENT); menu.setMenu(R.layout.frame_menu);
		 */
		
	}

	private void InitViewPager() {
		viewPager=(com.pangu.neusoft.CustomView.CustomViewPager) findViewById(R.id.vPager);
		views=new ArrayList<View>();
		StoreDetailFragment storeDetailFragment = new StoreDetailFragment();
		NewsFragment newsFragment = new NewsFragment();
		OtherStoreFragment otherStoreFragment = new OtherStoreFragment();
		fragmentsList = new ArrayList<Fragment>();
		fragmentsList.add(newsFragment);
		fragmentsList.add(storeDetailFragment);
		fragmentsList.add(otherStoreFragment);
		LayoutInflater inflater=getLayoutInflater();
		view1=inflater.inflate(R.layout.lay1, null);
		view2=inflater.inflate(R.layout.lay2, null);
		view3=inflater.inflate(R.layout.lay3, null);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		//viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
	}
	
	void InitFragment(){
		StoreDetailFragment storeDetailFragment = new StoreDetailFragment();
		
		android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction(); 
		
		//transaction.replace(R.id.StoreDetailFragment, storeDetailFragment);
		
		transaction.addToBackStack(null);
		
		transaction.commit();
	}
	
	private void Init()
	{
		
		textView1 = (TextView) findViewById(R.id.text1);
		textView2 = (TextView) findViewById(R.id.text2);
		textView3 = (TextView) findViewById(R.id.text3);

		
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		textView3.setOnClickListener(new MyOnClickListener(2));
		
		
	}
	
	private void InitImageView()
	{
		viewpager_line = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		viewpager_line.setImageMatrix(matrix);// 设置动画初始位置
	}

	private class MyOnClickListener implements OnClickListener
	{
		private int index = 0;

		public MyOnClickListener(int i)
		{
			index = i;
		}

		public void onClick(View v)
		{
			viewPager.setCurrentItem(index);
		}

	}

	public class MyViewPagerAdapter extends PagerAdapter
	{
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews)
		{
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			container.addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

		@Override
		public int getCount()
		{
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == arg1;
		}
	}

	
	
	public class MyOnPageChangeListener implements OnPageChangeListener
	{

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		public void onPageScrollStateChanged(int arg0)
		{

		}

		public void onPageScrolled(int arg0, float arg1, int arg2)
		{

		}

		public void onPageSelected(int arg0)
		{
			/*
			 * Animation animation = null; switch (arg0) { case 0: if (currIndex
			 * == 1) { animation = new TranslateAnimation(one, 0, 0, 0); } else
			 * if (currIndex == 2) { animation = new TranslateAnimation(two, 0,
			 * 0, 0); } break; case 1: if (currIndex == 0) { animation = new
			 * TranslateAnimation(offset, one, 0, 0); } else if (currIndex == 2)
			 * { animation = new TranslateAnimation(two, one, 0, 0); } break;
			 * case 2: if (currIndex == 0) { animation = new
			 * TranslateAnimation(offset, two, 0, 0); } else if (currIndex == 1)
			 * { animation = new TranslateAnimation(one, two, 0, 0); } break;
			 * 
			 * }
			 */
			Animation animation = new TranslateAnimation(one * currIndex, one
					* arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			viewpager_line.startAnimation(animation);
			
		}

	}

}
