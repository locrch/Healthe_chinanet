package com.pangu.neusoft.healthe;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class WelcomeActivity extends Activity {
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		
		handler = new Handler()  
		
        {  
                 @Override  
                 public void handleMessage(Message msg)  
                 {  
                     if(msg.what == 1) 
                          {  
                    	 Intent intent = new Intent(WelcomeActivity.this,FristActivity.class);
                    	 
                    	 startActivity(intent);  
                         
                         
                          }  
                 }  
        };  
        
           Runnable runnable = new Runnable()  
        {  
                 public void run()  
                 {  
                          try  
                          {  
                                    Thread.sleep(1000);
                                    finish();
                          }   
                          catch (InterruptedException e)  
                          {  
                                    e.printStackTrace();  
                          }  
                          handler.sendEmptyMessage(1);
                 }  
        };  
        Thread thread = new Thread(runnable);  
        thread.start();            
        
	}

	

}
