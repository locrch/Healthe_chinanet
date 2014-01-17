package com.pangu.neusoft.drugstore;

import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Drugstore_main_activity extends FatherSonDrugActivity
{	private ImageView imageButton2;
	private void Init()
	{
		imageButton2 = (ImageView)findViewById(R.id.imageButton2);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drugstore_main_activity);
		Init();
		imageButton2.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(Drugstore_main_activity.this,Drugstore_detail.class));
			}
		});
	}
	
	

}
