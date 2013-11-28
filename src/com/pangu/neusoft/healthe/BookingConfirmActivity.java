package com.pangu.neusoft.healthe;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BookingConfirmActivity extends FatherActivity
{
	TextView username,healthcard,hospitalname,departmentname,doctorname,date,time,place,pay;
	
	Button conbbtn,cancelbtn,changecard;
			 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking_confirm);
		
	    username = (TextView)findViewById(R.id.booking_confirm_username);
	    healthcard = (TextView)findViewById(R.id.booking_confirm_healthcard);
	    hospitalname = (TextView)findViewById(R.id.booking_confirm_hospitalname);
	    departmentname = (TextView)findViewById(R.id.booking_confirm_departmentname);
	    doctorname = (TextView)findViewById(R.id.booking_confirm_doctorname);
	    date = (TextView)findViewById(R.id.booking_confirm_date);
	    time = (TextView)findViewById(R.id.booking_confirm_time);
	    place = (TextView)findViewById(R.id.booking_confirm_place);
	    pay = (TextView)findViewById(R.id.booking_confirm_pay);
	    
	    conbbtn = (Button)findViewById(R.id.booking_confirm_conbbtn);
	    cancelbtn = (Button)findViewById(R.id.booking_confirm_cancelbtn);
	    changecard = (Button)findViewById(R.id.booking_confirm_changecard);
	    
	    conbbtn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
			}
		});
	    cancelbtn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
			}
		});
	    changecard.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.booking_confirm, menu);
		return true;
	}

}
