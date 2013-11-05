package com.pangu.neusoft.healthe;

import java.net.ContentHandler;

import com.baidu.platform.comapi.map.r;
import com.pangu.neusoft.healthcard.LoginActivity;
import com.pangu.neusoft.healthcard.RegisterActivity;
import com.pangu.neusoft.tools.SysApplication;
import com.pangu.neusoft.tools.update.UpdateOperation;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class FristActivity extends Activity {
	ImageButton zhineng, shuzi,phone;
	
	Button zhuce, denglu;
	private SharedPreferences sp;
	private Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		setContentView(R.layout.activity_frist);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_frist);
		
		phone = (ImageButton)findViewById(R.id.phone);
		
		zhineng = (ImageButton) findViewById(R.id.zhineng);
		
		shuzi = (ImageButton) findViewById(R.id.shuzi);

		zhuce = (Button) findViewById(R.id.zhuce);

		denglu = (Button) findViewById(R.id.denglu);

		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		if(sp.getInt("fontsize", 0)==0){
			editor.putInt("fontsize", 16);
			editor.commit();
		}
		
		zhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FristActivity.this,
						RegisterActivity.class));
			}
		});
		
		denglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FristActivity.this,
						LoginActivity.class));
			}
		});
		
		zhineng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				
				intent.putExtra("extra", "zhineng");
			    
			    intent.setClass(FristActivity.this,TabHostActivity.class);
				
				startActivity(intent);
			}
		});

		shuzi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Toast.makeText(getApplicationContext(), "正在建设中，敬请期待", Toast.LENGTH_LONG).show();
				
			  /*Intent intent = new Intent();
				 
				intent.putExtra("extra", "shuzi");
			    
			    intent.setClass(FristActivity.this,TabHostActivity.class);
				
			    startActivity(intent);*/
			}
		});
		
		phone.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				Intent tell = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"12320"));
				
				FristActivity.this.startActivity(tell);
			}
		});
		SysApplication.getInstance().addActivity(this);
		
		UpdateOperation update=new UpdateOperation(FristActivity.this);
		
    	update.checkUpdate();
    	
    	this.NetWorkStatus(this);
	}
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		this.onDestroy();
		
		
		return super.onKeyDown(keyCode, event);
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
