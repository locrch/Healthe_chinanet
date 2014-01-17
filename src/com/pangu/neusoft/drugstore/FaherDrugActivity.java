package com.pangu.neusoft.drugstore;

import com.pangu.neusoft.healthe.MapActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class FaherDrugActivity extends FragmentActivity
{
	Button map,back;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_faher_drug);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_drug);
		
		map = (Button)findViewById(R.id.title_drug_map);
		back = (Button)findViewById(R.id.title_drug_back);
		
		
		map.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),MapActivity.class));
			}
		});
		
		back.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}

	

}
