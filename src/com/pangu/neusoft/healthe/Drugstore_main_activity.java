package com.pangu.neusoft.healthe;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Drugstore_main_activity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drugstore_main_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.drugstore_main_activity, menu);
		return true;
	}

}
