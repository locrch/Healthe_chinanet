package com.pangu.neusoft.healthcard;

import com.pangu.neusoft.healthe.R;


import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.pangu.neusoft.healthe.FatherActivity;

public class HistoryViewActivity extends FatherActivity {
	
	Button button2;
	Button button3;
	Button button4;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2_history);

		button2=(Button)findViewById(R.id.tab3_login);
		button3=(Button)findViewById(R.id.tab3_peopleinfo);
		button4=(Button)findViewById(R.id.tab3_bookinghist);
		
		button2.setOnClickListener(history_2);
		button3.setOnClickListener(history_3);
		button4.setOnClickListener(history_4);
		
	}
	OnClickListener history_2=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=getIntent();
			intent.putExtra("action", "history");
			intent.putExtra("type", "booking");
			intent.setClass(HistoryViewActivity.this, ShowHistoryActivity.class);
			startActivity(intent);
		}
		
	};
	OnClickListener history_3=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=getIntent();
			intent.putExtra("action", "history");
			intent.putExtra("type", "cancled");
			intent.setClass(HistoryViewActivity.this, ShowHistoryActivity.class);
			startActivity(intent);
		}
		
	};
	OnClickListener history_4=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=getIntent();
			intent.putExtra("action", "history");
			intent.putExtra("type", "passed");
			intent.setClass(HistoryViewActivity.this, ShowHistoryActivity.class);
			startActivity(intent);
		}
		
	};


}
