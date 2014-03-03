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
{	private ImageView drugstore_jianmin;
	private void Init()
	{
		drugstore_jianmin = (ImageView)findViewById(R.id.drugstore_jianmin);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drugstore_main_activity);
		Init();
		drugstore_jianmin.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(Drugstore_main_activity.this,Drugstore_detail.class);
				
				intent.putExtra("drugstorename", "健民药房");
				
				startActivity(intent);
				
				
			}
		});
	}
	
	

}
