package com.pangu.neusoft.healthe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class TabActivity2 extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//点击后加载不同的layout
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
		
		TextView huoqu = (TextView) findViewById(R.id.huoqu);

		huoqu.setText("欢迎你");

		ImageButton yuyue = (ImageButton) findViewById(R.id.tab2_booking);

		yuyue.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				/*View view = HealthActivityGroup
						.group
						.getLocalActivityManager()
						.startActivity("Activity2", 
								new Intent(TabActivity2.this, BookingMainActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView(); */
				
				Intent intent = new Intent(TabActivity2.this,
						BookingMainActivity.class);

				startActivity(intent);
				
				
			}
		});
	}

}