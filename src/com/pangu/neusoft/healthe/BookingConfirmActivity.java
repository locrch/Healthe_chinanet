package com.pangu.neusoft.healthe;

import com.pangu.neusoft.healthcard.BookingAction;
import com.pangu.neusoft.healthcard.ListCardActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BookingConfirmActivity extends FatherActivity
{
	TextView username,healthcard,hospitalname,departmentname,doctorname,date,time,place,pay;
	
	Button conbbtn,cancelbtn,changecard;
	BookingAction bookingaction; 
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
	    
	    if(Setting.bookingdata==null||Setting.bookingdata.getUsername()==null||Setting.bookingdata.getUsername().equals("")){
	    	Intent intent = new Intent(BookingConfirmActivity.this,BookingMainActivity.class);
			Setting.state="booking";
			startActivity(intent);
	    	return;
	    }
	    username.setText(Setting.bookingdata.getUsername());
	    
	    healthcard.setText(Setting.bookingdata.getCardnumber());
	    hospitalname.setText(Setting.bookingdata.getHospitalname());
	    departmentname.setText(Setting.bookingdata.getDepartmentname());
	    doctorname.setText(Setting.bookingdata.getDoctorname());
	    date.setText(Setting.bookingdata.getReservedate());
	    time.setText(Setting.bookingdata.getReservetime());
	    place.setText("现场挂号处");
	    pay.setText("现场支付");
	    
	    conbbtn = (Button)findViewById(R.id.booking_confirm_conbbtn);
	    cancelbtn = (Button)findViewById(R.id.booking_confirm_cancelbtn);
	    changecard = (Button)findViewById(R.id.booking_confirm_changecard);
	    
	    bookingaction=new BookingAction(BookingConfirmActivity.this);
	    
	    conbbtn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				bookingaction.booking_action();
			}
		});
	    cancelbtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	    changecard.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(BookingConfirmActivity.this,ListCardActivity.class);
				Setting.state="booking";
				startActivity(intent);
				finish();
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
