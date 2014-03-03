package com.pangu.neusoft.drugstore;

import java.util.ArrayList;
import java.util.List;


import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.SetTextSizeActivity;
import com.pangu.neusoft.healthe.TabActivity2;
import com.slidingmenu.lib.SlidingMenu;
import android.view.View.OnClickListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


@SuppressLint("NewApi")
public class Drugstore_detail extends FaherDrugActivity  {
    private static final String TAG = "MainActivity";
    private com.pangu.neusoft.CustomView.CustomViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private ImageView ivBottomLine;
    private TextView tvTabActivity, tvTabGroups, tvTabFriends, tvTabChat;
    
    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    private int position_two;
    private int position_three;
    private Resources resources;
    private TextView detail,otherstore;
    private SlidingMenu menu;
	private Fragment showFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.drugstore_detail_main);
        resources = getResources();
        InitWidth();
        InitTextView();
        InitViewPager();
        
        Intent intent = getIntent();
        
        String drugstorename = intent.getExtras().getString("drugstorename","药房优惠");
        
        setactivitytitle(drugstorename);
        
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //MenuFragment menuFragment = new MenuFragment();
        //fragmentTransaction.replace(R.id.menu, menuFragment);
        //fragmentTransaction.replace(R.id.content, new ContentFragment());
        //fragmentTransaction.commit();
        
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);	//设置菜单 滑动模式，左滑还是右滑
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.slidingmenu_shadowWidth);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_behindOffset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.frame_menu);
        
        detail = (TextView)findViewById(R.id.drugstore_menu_detail_button);
        otherstore = (TextView)findViewById(R.id.drugstore_menu_otherstore_button);
        detail.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(Drugstore_detail.this,StoreDetialActivity.class));
				
			}
		});
        
        otherstore.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(Drugstore_detail.this,OtherStoreActivity.class));
				StoreDetailFragment.newInstance();
			}
		});
    }

    private void InitTextView() {
        tvTabActivity = (TextView) findViewById(R.id.tv_tab_activity);
        tvTabGroups = (TextView) findViewById(R.id.tv_tab_groups);
        tvTabFriends = (TextView) findViewById(R.id.tv_tab_friends);
        tvTabChat = (TextView) findViewById(R.id.tv_tab_chat);

        tvTabActivity.setOnClickListener(new MyOnClickListener(0));
        tvTabGroups.setOnClickListener(new MyOnClickListener(1));
        tvTabFriends.setOnClickListener(new MyOnClickListener(2));
        tvTabChat.setOnClickListener(new MyOnClickListener(3));
    }

    private void InitViewPager() {
        mPager = (com.pangu.neusoft.CustomView.CustomViewPager) findViewById(R.id.vPager);
        fragmentsList = new ArrayList<Fragment>();
        
        Fragment activityfragment = ShowFragment.newInstance();
        Fragment groupFragment = TestFragment.newInstance("在线购药");
        Fragment friendsFragment=TestFragment.newInstance("预约煎药");
        Fragment chatFragment=TestFragment.newInstance("更多");
        
        
        
        fragmentsList.add(activityfragment);
        fragmentsList.add(groupFragment);
        fragmentsList.add(friendsFragment);
        fragmentsList.add(chatFragment);
        
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void InitWidth() {
        ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        Log.d(TAG, "cursor imageview width=" + bottomLineWidth);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / 4.0 - bottomLineWidth) / 2);
        Log.i("MainActivity", "offset=" + offset);

        position_one = (int) (screenW / 4.0);
        position_two = position_one * 2;
        position_three = position_one * 3;
    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, 0, 0, 0);
                    tvTabGroups.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, 0, 0, 0);
                    tvTabFriends.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, 0, 0, 0);
                    tvTabChat.setTextColor(resources.getColor(R.color.black));
                }
                tvTabActivity.setTextColor(resources.getColor(R.color.black));
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(0, position_one, 0, 0);
                    tvTabActivity.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, position_one, 0, 0);
                    tvTabFriends.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, position_one, 0, 0);
                    tvTabChat.setTextColor(resources.getColor(R.color.black));
                }
                tvTabGroups.setTextColor(resources.getColor(R.color.black));
                break;
            case 2:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(0, position_two, 0, 0);
                    tvTabActivity.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, position_two, 0, 0);
                    tvTabGroups.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, position_two, 0, 0);
                    tvTabChat.setTextColor(resources.getColor(R.color.black));
                }
                tvTabFriends.setTextColor(resources.getColor(R.color.black));
                break;
            case 3:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(0, position_three, 0, 0);
                    tvTabActivity.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, position_three, 0, 0);
                    tvTabGroups.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, position_three, 0, 0);
                    tvTabFriends.setTextColor(resources.getColor(R.color.black));
                }
                tvTabChat.setTextColor(resources.getColor(R.color.black));
                break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            ivBottomLine.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
        
    }
    
    
}