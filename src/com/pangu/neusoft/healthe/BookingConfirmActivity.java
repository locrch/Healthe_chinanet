package com.pangu.neusoft.healthe;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BookingConfirmActivity extends FatherActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking_confirm);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.booking_confirm, menu);
		return true;
	}

}
