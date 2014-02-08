package com.pangu.neusoft.healthe;

import java.util.Random;

import com.baidu.mapapi.search.c;
import com.pangu.neusoft.tools.SysApplication;
import com.pangu.neusoft.tools.update.UpdateOperation;







import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class TabActivity5 extends Activity  {	
	Button more_help,more_info,more_update,more_disclaimer;
	
	ProgressDialog pd;
	
	Handler handler;
	
	public static int updated = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.tab5_more);
		more_help = (Button)findViewById(R.id.more_help);
		more_info = (Button)findViewById(R.id.more_info);
		more_disclaimer = (Button)findViewById(R.id.more_disclaimer);
		more_update = (Button)findViewById(R.id.more_update);
		
		
		
		more_update.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				UpdateOperation update=new UpdateOperation(TabActivity5.this);
				
		    	update.checkUpdate();
		    	
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						
						switch (updated) {
						case 1:
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(getApplicationContext(), "已经是最新版本！", Toast.LENGTH_SHORT).show();
								}
							});
							
							break;
						case 2:
						   break;
						default:
							break;
						}
						
					}
				}).run();
			}
		});
		
		more_info.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(TabActivity5.this,VersionInfoActivity.class));
			}
		});
		
		more_disclaimer.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(TabActivity5.this,DisclaimerActivity.class));
			}
		});
		
		more_help.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(TabActivity5.this,HelpActivity.class));
			}
		});
		
		//com.pangu.neusoft.healthe.PressButton cust_btn = (com.pangu.neusoft.healthe.PressButton)findViewById(R.id.cust_btn);
		
		//cust_btn.setBackground(cust_btn,R.drawable.button_def_img,R.drawable.button_press_img);
	}
	
	
}