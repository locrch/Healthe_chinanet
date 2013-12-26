package com.pangu.neusoft.service;

import java.util.List;

import com.pangu.neusoft.healthe.Setting;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;



public class ListenService extends Service {
	private SharedPreferences sp;
	private Editor editor;
	
	    //定义个一个Tag标签  
	    private static final String TAG = "MyService";  
	    //这里定义吧一个Binder类，用在onBind()有方法里，这样Activity那边可以获取到  
	    private MyBinder mBinder = new MyBinder();  
	    @Override  
	    public IBinder onBind(Intent intent) {  
	        Log.e(TAG, "start IBinder~~~");  
	        return mBinder;  
	    }  
	    @Override  
	    public void onCreate() {  
	        Log.e(TAG, "start onCreate~~~");  
	       
	        super.onCreate();  
	    }  
	      
	    @Override  
	    public void onStart(Intent intent, int startId) {  
	        Log.e(TAG, "start onStart~~~");  
	        super.onStart(intent, startId);   
	    }  
	      
	    @Override  
	    public void onDestroy() {  
	        Log.e(TAG, "start onDestroy~~~"); 
	  	  //自动登陆
	    	sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
			editor = sp.edit();
		    if(!sp.getBoolean("auto_login_ischecked", true)){
		    	editor.putString("username", "");
		    	editor.putString("password", "");
		    	editor.putBoolean("loginsuccess",false);
				editor.putString("defaultcardno","0");
		    	editor.commit();
		    }
	        super.onDestroy();  
	    }  
	      
	      
	    @Override  
	    public boolean onUnbind(Intent intent) {  
	        Log.e(TAG, "start onUnbind~~~");  
	        return super.onUnbind(intent);  
	    }  
	      
	
	      
	    public class MyBinder extends Binder{  
	        ListenService getService()  
	        {  
	            return ListenService.this;  
	        }  
	    }  
	
}