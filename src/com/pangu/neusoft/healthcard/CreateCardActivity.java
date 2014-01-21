package com.pangu.neusoft.healthcard;

import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CreateCardActivity extends FatherActivity {

	SharedPreferences sp;
	Editor editor;
	static boolean  open=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		open=true;
		setContentView(R.layout.create_card_layout);
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		WebView wv = (WebView)findViewById(R.id.word_web_view);  
		wv.loadUrl(Setting.link+sp.getString("username", ""));
		wv.getSettings().setJavaScriptEnabled(true);
		
		wv.setWebViewClient(new WebViewClient(){  
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url); 
		       
		        return true;  
		    }  
		    
		    public void onPageFinished(WebView view, String url) { 
		    	 super.onPageFinished(view, url); 
		    	if(!url.contains("219.130.221.120")&&open){
		    		 Intent intent=getIntent();
		    		 intent.setClass(CreateCardActivity.this, ListCardActivity.class);
		    		 startActivity(intent);
		    		 finish();
		    		 open=false;
		    	}
            } 
		});
		
		
		
	}

	

}
