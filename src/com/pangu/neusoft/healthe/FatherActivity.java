package com.pangu.neusoft.healthe;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.Timer;
import java.util.TimerTask;

import com.baidu.mapapi.search.c;
import com.pangu.neusoft.healthcard.ShowHistoryActivity;
import com.pangu.neusoft.healthe.R.drawable;
import com.pangu.neusoft.tools.SysApplication;
import com.baidu.mobstat.StatService;
import com.hp.hpl.sparta.xpath.ThisNodeTest;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.Settings.System;
import android.R.anim;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.StaticLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FatherActivity extends Activity
{
	Button back_index, back_back;
	static TextView notice;
	ActivityManager am ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_father);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_back);

		notice = (TextView) findViewById(R.id.notice);
		notice.setText(Setting.defaultnotice);
		back_index = (Button) findViewById(R.id.back_index);
		back_back = (Button) findViewById(R.id.back_back);

		back_index.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						TabHostActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

				finish();

			}
		});

		back_back.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				doReturn();
			}
		});

		this.NetWorkStatus(this);
	}

	public void doReturn()
	{
		// 获取activityName 再操作
		// Toast.makeText(this, getRunningActivityName()+"",
		// Toast.LENGTH_SHORT).show();

		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			doReturn();
			return false;
		}
		return false;
	}

	private String getRunningActivityName()
	{
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity
				.getClassName();
		return runningActivity;
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
		
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}

	public void setactivitytitle(String title)
	{

		TextView title_title = (TextView) findViewById(R.id.title_title);

		title_title.setTextColor(Color.WHITE);

		title_title.setText(title);
	}

	public Boolean SetNotice(String notice_Str)
	{
		notice.setText(notice_Str);

		final Timer t = new Timer();
		t.schedule(new TimerTask()
		{
			public void run()
			{
				runOnUiThread(new Runnable()
				{
					public void run()
					{
						notice.setText(Setting.defaultnotice);
						t.cancel();
					}

				});

			}
		}, Setting.noticetime);

		if (notice.getText().equals(notice_Str))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public void NetWorkStatus(Context context)
	{
		/*
		 * 本方法实现判断网络连接功能，并可点击跳转到网络设置
		 */
		ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNetInfo = connectMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected())
		{
			Builder b = new AlertDialog.Builder(this).setTitle("没有可用的网络")
					.setMessage("是否对网络进行设置？");
			b.setPositiveButton("是", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{

					Intent intent = null;
					// 判断手机系统的版本 即API大于10 就是3.0或以上版本
					if (android.os.Build.VERSION.SDK_INT > 10)
					{
						intent = new Intent(
								android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					} else
					{
						intent = new Intent();
						ComponentName component = new ComponentName(
								"com.android.settings",
								"com.android.settings.WirelessSettings");
						intent.setComponent(component);
						intent.setAction("android.intent.action.VIEW");
					}
					startActivity(intent);

				}
			}).setNeutralButton("否", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{
					dialog.cancel();
				}
			}).show();
			// unconnect network
		} else
		{

			// connect network
		}

	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub

		if (item.getItemId() == R.id.menu_action_exit)
		{
			onBackPressed();
		}

		return true;
	}*/
	
	@Override 
	   public void onBackPressed() { 
	new AlertDialog.Builder(this).setTitle("确认退出吗？") 
	    .setIcon(android.R.drawable.ic_dialog_info) 
	    .setPositiveButton("确定", new DialogInterface.OnClickListener() { 
	 
	        @Override 
	        public void onClick(DialogInterface dialog, int which) { 
	        // 点击“确认”后的操作 
	        	
	        } 
	    }) 
	    .setNegativeButton("返回", new DialogInterface.OnClickListener() { 
	 
	        @Override 
	        public void onClick(DialogInterface dialog, int which) { 
	        // 点击“返回”后的操作,这里不设置没有任何操作 
	        } 
	    }).show(); 
	// super.onBackPressed(); 
	   
  
	  
	     
	  
	   
	
	}  
}
