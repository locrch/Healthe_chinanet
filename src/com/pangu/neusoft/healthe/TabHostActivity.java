package com.pangu.neusoft.healthe;

import com.baidu.mobstat.StatService;
import com.pangu.neusoft.healthe.R.drawable;
import com.pangu.neusoft.healthe.R.id;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class TabHostActivity extends ActivityGroup {
	/** Called when the activity is first created. */
	private TabHost tabHost;
	private LayoutInflater mInflater = null;
	TextView tab2_text;
	Button back_index, back_back;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.acitivty_tabhost);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		 R.layout.title_tabhost);
		/*
		 * back_back = (Button)findViewById(R.id.back_back); back_index =
		 * (Button)findViewById(R.id.back_index); back_index.setVisibility(8);
		 * back_back.setVisibility(8);
		 */

		
		/*
		 * if (Value.equals("zhineng")) { tab2_text.setText("智能健康"); } else {
		 * tab2_text.setText("数字医院"); }
		 */
		
		
		mInflater = LayoutInflater.from(this);
		tabHost = (TabHost) findViewById(R.id.mytabhost);
		tabHost.setup(this.getLocalActivityManager());
		Intent intent;

		intent = new Intent(this, TabActivity2.class);
		
		View tab1Spec = mInflater.inflate(R.layout.tab1_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(tab1Spec)
				.setContent(intent));
		
		
		tab1Spec.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TabHostActivity.this,
						FristActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();

			}
		});

		intent = new Intent(this, TabActivity2.class);
		
		View tab2Spec = mInflater.inflate(R.layout.tab2_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(tab2Spec)
				.setContent(intent));

		intent = new Intent(this, TabActivity3.class);
		View tab3Spec = mInflater.inflate(R.layout.tab3_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(tab3Spec)
				.setContent(intent));
		
		intent = new Intent(this, TabActivity5.class);
		View tab5Spec = mInflater.inflate(R.layout.tab5_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab5").setIndicator(tab5Spec)
				.setContent(intent));
		
		intent = new Intent(this, TabActivity4.class);
		View tab4Spec = mInflater.inflate(R.layout.tab4_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator(tab4Spec)
				.setContent(intent));
		
		tabHost.setCurrentTab(1);//设置默认显示第二个tab
		tab5Spec.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "该功能正在建设中，敬请期待", Toast.LENGTH_SHORT).show();

			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}
}