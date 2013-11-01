package com.pangu.neusoft.healthe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class TabActivity1 extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		
		/*Intent intent = getIntent();

		String Value = intent.getStringExtra("extra");

		if (Value.equals("zhineng"))
		{
			setContentView(R.layout.tab_zhineng);

		} else
		{
			setContentView(R.layout.tab_shuzi);
		}*/
		setContentView(R.layout.tab_zhineng);
		
		
		

		ImageButton yuyue = (ImageButton) findViewById(R.id.tab2_booking);

		yuyue.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(TabActivity1.this,
						BookingMainActivity.class);

				startActivity(intent);
			}
		});

	}

}