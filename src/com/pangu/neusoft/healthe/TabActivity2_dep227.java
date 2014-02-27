package com.pangu.neusoft.healthe;


import com.pangu.neusoft.drugstore.Drugstore_main_activity;
import com.pangu.neusoft.healthinfo.HealthInfoActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TabActivity2_dep227 extends Activity
{
	ImageButton tab2_booking;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tab_zhineng_dep227);
		
		tab2_booking = (ImageButton) findViewById(R.id.tab2_booking_dep);
		
		tab2_booking.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(TabActivity2_dep227.this,
						BookingMainActivity.class);

				startActivity(intent);
				//finish();

			}
		});

		
		
	}

}