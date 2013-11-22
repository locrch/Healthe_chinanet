package com.pangu.neusoft.healthe;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.widget.TextView;

public class VersionInfoActivity extends FatherActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_version_info);
		setactivitytitle("版本信息");
		TextView version_num = (TextView)findViewById(R.id.verison_num);
		
		try
		{
			version_num.setText("V"+getVersionName());
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
	

}
