package com.pangu.neusoft.healthinfo;

import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;

import android.R.integer;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.view.View.OnKeyListener;
@SuppressLint("NewApi")
public class HealthInfoActivity extends FatherActivity {
	WebView webview;
	ProgressBar Progress;
	private String errorHtml = "";
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_health_healthinfo);
		setactivitytitle("健康大全");
		errorHtml = "<html><body><h1>error</h1></body></html>"; 
		Progress = (ProgressBar) findViewById(R.id.Progress);
		webview = (WebView) findViewById(R.id.healthinfo_context);
		
		webview.loadUrl("http://192.168.2.236:8081/index.html");
		//webview.loadUrl("file:///android_asset/index.html"); 
		
		webview.getSettings().setJavaScriptEnabled(true);
		
		webview.canGoBack();
		
		webview.setWebViewClient(new WebViewClient() {
			// 点击网页中按钮时，在原页面打开
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				view.loadData(errorHtml, "text/html", "UTF-8");
				
			}
		});

		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				Progress.setProgress(newProgress);
				if (newProgress == 100) {
					Progress.setVisibility(View.GONE);
	            } else {
	                if (Progress.getVisibility() ==View.GONE)
	                	Progress.setVisibility(View.VISIBLE);
	                Progress.setProgress(newProgress);
	            }
				
				
				super.onProgressChanged(view, newProgress);
			}
		});
		
		webview.setOnKeyListener(new View.OnKeyListener() {    
	        @Override    
	        public boolean onKey(View v, int keyCode, KeyEvent event) {    
	            if (event.getAction() == KeyEvent.ACTION_DOWN) {    
	                if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {  //表示按返回键时的操作  
	                	webview.goBack();   //后退    

	                    //webview.goForward();//前进  
	                    return true;    //已处理    
	                }    
	            }    
	            return false;    
	        }    
	    });  
	}
	
}
