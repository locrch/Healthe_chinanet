package com.pangu.neusoft.healthe;

import java.util.Random;

import com.baidu.mapapi.search.c;
import com.pangu.neusoft.tools.update.UpdateOperation;


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


public class TabActivity4 extends Activity  {	
	Button more_help,more_info,more_update;
	
	ProgressDialog pd;
	
	Handler handler;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.tab4_more);
		more_help = (Button)findViewById(R.id.more_help);
		more_info = (Button)findViewById(R.id.more_info);
		more_update = (Button)findViewById(R.id.more_update);
		
		
		
		more_update.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				/*pd = ProgressDialog.show(TabActivity4.this, "新版本检测", "检查更新中....");
				handler = new Handler()  
				
		        {  
		                 @Override  
		                 public void handleMessage(Message msg)  
		                 {  
		                     if(msg.what == 1) 
		                          {  
		                    	 pd.dismiss();
		                    	 
		                    	 Toast.makeText(getApplicationContext(), "该版本已经是最新！", Toast.LENGTH_LONG).show();
		                         
		                    	 
		                          }  
		                 }  
		        };  
				Runnable runnable = new Runnable()  
		        {  
		                 public void run()  
		                 {  
		                          try  
		                          {  
		                                    Thread.sleep(2500);
		                                    
		                          }   
		                          catch (InterruptedException e)  
		                          {  
		                                    e.printStackTrace();  
		                          }  
		                          handler.sendEmptyMessage(1);
		                 }  
		        };  
		        Thread thread = new Thread(runnable);  
		        thread.start(); */
		        //上面为更新界面，没有功能
				UpdateOperation update=new UpdateOperation(TabActivity4.this);
		    	update.checkUpdate();
				
						
					
				
				
			}
		});
		
		more_info.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(TabActivity4.this,VersionInfoActivity.class));
			}
		});
		
		more_help.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(TabActivity4.this,HelpActivity.class));
			}
		});
	}
	
	
}