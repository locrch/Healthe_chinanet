package com.pangu.neusoft.drugstore;

import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class FatherSonDrugActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_father_son_drug);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_only_back);
		
		Button back_only_back = (Button)findViewById(R.id.back_only_back);
		
		back_only_back.setOnClickListener(new OnClickListener()
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
