package com.pangu.neusoft.healthe;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class VersionInfoActivity extends FatherActivity
{
	TextView version_num,verison_code;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_version_info);
		setactivitytitle("版本信息");
		version_num = (TextView)findViewById(R.id.verison_num);
		verison_code = (TextView)findViewById(R.id.verison_code);
		verison_code.setVisibility(View.INVISIBLE);
		version_num.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (verison_code.getVisibility()) {
				case View.VISIBLE:
					verison_code.setVisibility(View.INVISIBLE);
					break;
				case View.INVISIBLE:
					verison_code.setVisibility(View.VISIBLE);
				default:
					break;
				}
				
			}
		});
		try
		{
			version_num.setText("V"+getVersionName());
			
			verison_code.setText("Build"+getVersionCode());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/*
	 * 获取当前程序的版本号 
	 */
	private String getVersionName() throws Exception{
		//获取packagemanager的实例 
		PackageManager packageManager = getPackageManager();
		//getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
		
	    return packInfo.versionName;
	}
	
	private int getVersionCode() throws Exception{
		
		PackageManager packageManager = getPackageManager();
		
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 1);
		
		return packInfo.versionCode;
	}
}
