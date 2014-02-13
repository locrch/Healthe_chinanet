package com.pangu.neusoft.healthe;

import com.pangu.neusoft.healthcard.BookingAction;
import com.pangu.neusoft.healthcard.ListCardActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


@SuppressLint("ResourceAsColor")
public class BookingConfirmActivity extends FatherActivity
{
	TextView username,healthcard,hospitalname,departmentname,doctorname,date,time,place,pay;
	LinearLayout linearLayout_1,linearLayout_2,linearLayout_3,linearLayout_4,linearLayout_5,linearLayout_6,
	linearLayout_7,linearLayout_8,linearLayout_9;
	Button conbbtn,cancelbtn;
	BookingAction bookingaction; 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking_confirm);
		setactivitytitle("预约信息确认");
		linearLayout_1 = (LinearLayout)findViewById(R.id.linearLayout_1);
	    username = (TextView)findViewById(R.id.booking_confirm_username);
	    healthcard = (TextView)findViewById(R.id.booking_confirm_healthcard);
	    hospitalname = (TextView)findViewById(R.id.booking_confirm_hospitalname);
	    departmentname = (TextView)findViewById(R.id.booking_confirm_departmentname);
	    doctorname = (TextView)findViewById(R.id.booking_confirm_doctorname);
	    date = (TextView)findViewById(R.id.booking_confirm_date);
	    time = (TextView)findViewById(R.id.booking_confirm_time);
	    place = (TextView)findViewById(R.id.booking_confirm_place);
	    pay = (TextView)findViewById(R.id.booking_confirm_pay);
	    healthcard.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
	    if(Setting.bookingdata==null||Setting.bookingdata.getDoctorid()==null||Setting.bookingdata.getDoctorid().equals("")){
	    	//Intent intent = new Intent(BookingConfirmActivity.this,BookingMainActivity.class);
			Setting.state="booking";
			//startActivity(intent);
			 finish();
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
	    
	    
	    healthcard.setOnClickListener(new OnClickListener()
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

	
	

}
