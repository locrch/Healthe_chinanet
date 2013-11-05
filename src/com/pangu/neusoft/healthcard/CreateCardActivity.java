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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CreateCardActivity extends FatherActivity {

	SharedPreferences sp;
	Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_card, menu);
		return true;
	}

}
