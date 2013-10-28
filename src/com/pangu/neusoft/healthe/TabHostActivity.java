package com.pangu.neusoft.healthe;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class TabHostActivity extends ActivityGroup {
    /** Called when the activity is first created. */
	private TabHost tabHost = null;
	private LayoutInflater mInflater = null;
	TextView tab2_text;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		setContentView(R.layout.acitivty_tabhost);
		tab2_text = (TextView)findViewById(R.id.tab2_text);
		Intent getintent = getIntent();
		String Value=getintent.getStringExtra("extra");
		/*if (Value.equals("zhineng"))
		{
			tab2_text.setText("智能健康");
		}
		else {
			tab2_text.setText("数字医院");
		}*/
		mInflater = LayoutInflater.from(this);
        tabHost = (TabHost) findViewById(R.id.mytabhost);
		tabHost.setup(this.getLocalActivityManager());
		
		Intent intent;
		
		intent = new Intent(this, TabActivity1.class);
		intent.putExtra("extra", Value);
		View tab1Spec = mInflater.inflate(R.layout.tab1_spec, null);
		tabHost.addTab(tabHost
				.newTabSpec("tab1")
				.setIndicator(tab1Spec)
				.setContent(intent));
		
		tab1Spec.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(TabHostActivity.this,FristActivity.class));
				finish();
				
			}
		});
		
		
		intent = new Intent(this, TabActivity2.class);
		intent.putExtra("extra", Value);
		View tab2Spec = mInflater.inflate(R.layout.tab2_spec, null);
		tabHost.addTab(tabHost
				.newTabSpec("tab2")
				.setIndicator(tab2Spec)
				.setContent(intent));
		
		
		intent = new Intent(this, TabActivity3.class);
		View tab3Spec = mInflater.inflate(R.layout.tab3_spec, null);
		tabHost.addTab(tabHost
				.newTabSpec("tab3")
				.setIndicator(tab3Spec)
				.setContent(intent));
		
		intent = new Intent(this, TabActivity4.class);
		View tab4Spec = mInflater.inflate(R.layout.tab4_spec, null);
		tabHost.addTab(tabHost
				.newTabSpec("tab4")
				.setIndicator(tab4Spec)
				.setContent(intent));
	
    }
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		this.onDestroy();
		
		
		return super.onKeyDown(keyCode, event);
	}
	
}