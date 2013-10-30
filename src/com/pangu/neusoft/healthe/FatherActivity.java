package com.pangu.neusoft.healthe;

import com.baidu.mapapi.search.c;
import com.pangu.neusoft.tools.SysApplication;
import com.pangu.neusoft.user.LoginActivity;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.R.anim;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class FatherActivity extends Activity
{
	Button back_index,back_back;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_father);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_back);
		back_index = (Button)findViewById(R.id.back_index);
		back_back = (Button)findViewById(R.id.back_back);
		
		back_index.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),TabHostActivity.class));
				
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
	}
	

}
