package com.pangu.neusoft.healthe;

import com.baidu.mapapi.search.c;
import com.pangu.neusoft.tools.SysApplication;
import com.baidu.mobstat.StatService;
import com.hp.hpl.sparta.xpath.ThisNodeTest;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.R.anim;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FatherActivity extends Activity
{
	Button back_index,back_back;
	TextView notice;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_father);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_back);
		
		
		notice = (TextView)findViewById(R.id.notice);
		
		back_index = (Button)findViewById(R.id.back_index);
		back_back = (Button)findViewById(R.id.back_back);
		
		back_index.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),TabHostActivity.class);
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
				// TODO Auto-generated method stub

				finish();
				}
		});
		
		this.NetWorkStatus(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}
	
	public void setactivitytitle(String title){
		
		TextView title_title = (TextView)findViewById(R.id.title_title);
		
		title_title.setTextColor(Color.WHITE);
		
		title_title.setText(title);
	}
	
	public Boolean SetNotice(String notice_Str)
	{
		notice.setText(notice_Str);
		
		if (notice.getText().equals(notice_Str))
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	public void NetWorkStatus(Context context) {
		/*
		 * 本方法实现判断网络连接功能，并可点击跳转到网络设置
		 * */
		ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
	    NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

	    if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
	    	Builder b = new AlertDialog.Builder(this).setTitle("没有可用的网络")
                    .setMessage("是否对网络进行设置？");
            b.setPositiveButton("是", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    
                	Intent intent=null;
                    //判断手机系统的版本  即API大于10 就是3.0或以上版本 
                    if(android.os.Build.VERSION.SDK_INT>10){
                        intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    }else{
                        intent = new Intent();
                        ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                        intent.setComponent(component);
                        intent.setAction("android.intent.action.VIEW");
                    }
                    startActivity(intent);
                	
                	
                	
                	
                }
            }).setNeutralButton("否", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            }).show();
	     // unconnect network
	     }else {

	    // connect network
	     }
		
    }
}
	