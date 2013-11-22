package com.pangu.neusoft.healthe;

import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CancelbookingActivity extends FatherActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cancelbooking);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cancelbooking, menu);
		return true;
	}

}
