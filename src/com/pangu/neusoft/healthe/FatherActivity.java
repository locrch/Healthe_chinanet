package com.pangu.neusoft.healthe;

import com.baidu.mapapi.search.c;
import com.pangu.neusoft.tools.SysApplication;
import com.pangu.neusoft.user.LoginActivity;

import android.os.Bundle;
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
	Button back_index;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_father);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_back);
		
		back_index = (Button)findViewById(R.id.back_index);
		
		back_index.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),TabHostActivity.class));
				
				
				finish();
				
				System.exit(0);
				
				
				
			}
		});
	}
	/*private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            //finish();
	            //System.exit(0);
	        	//android.os.Process.killProcess(android.os.Process.myPid());  
	        	ActivityManager am= (ActivityManager) this
	        			.getSystemService(Context.ACTIVITY_SERVICE);
	        			am.killBackgroundProcesses(this.getPackageName());

	        	
	        }
	        return true;   
	    }

	    
	    return super.onKeyDown(keyCode, event);
	}*/

}
