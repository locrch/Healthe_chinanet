package com.pangu.neusoft.healthe;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HelpActivity extends FatherActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		setactivitytitle("预约指南");
	}

	

}
